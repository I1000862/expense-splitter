package com.example.expensesplitter.service.impl;

import com.example.expensesplitter.dto.request.group.CreateGroupRequestDto;
import com.example.expensesplitter.dto.response.group.GroupMemberDto;
import com.example.expensesplitter.dto.response.group.GroupResponseDto;
import com.example.expensesplitter.entity.Group;
import com.example.expensesplitter.entity.GroupMembership;
import com.example.expensesplitter.entity.User;
import com.example.expensesplitter.enums.group.Currency;
import com.example.expensesplitter.enums.group.GroupStatus;
import com.example.expensesplitter.enums.group.GroupType;
import com.example.expensesplitter.exception.*;
import com.example.expensesplitter.repository.GroupRepository;
import com.example.expensesplitter.service.GroupService;
import com.example.expensesplitter.util.InviteCodeUtil;
import com.example.expensesplitter.util.SecurityUtil;
import com.example.expensesplitter.util.UuidUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class GroupServiceImpl implements GroupService {
    private final GroupRepository groupRepository;

    @Value("${app.invite.base-url}")
    private String baseInviteUrl;

    public GroupServiceImpl(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    public GroupResponseDto getGroupById(String id) {
        UUID userId = SecurityUtil.getCurrentUser().getId();
        UUID groupId = UuidUtil.parse(id, "groupId");

        Group group = groupRepository.findById(groupId)
                                     .orElseThrow(() -> new ResourceNotFoundException(
                                             "Group with the given ID does not exist."));

        if (!group.hasMember(userId)) {
            throw new NotGroupMemberException("You do not have permission to view this group.");
        }

        return convertToDto(group);
    }

    @Override
    public List<GroupResponseDto> getGroups() {
        UUID userId = SecurityUtil.getCurrentUser().getId();

        List<Group> groups = groupRepository.findAll();

        List<Group> userGroups = groups.stream().filter(group -> group.hasMember(userId)).toList();

        return userGroups.stream().map(this::convertToDto).toList();
    }

    @Override
    public GroupResponseDto createGroup(CreateGroupRequestDto groupRequestDto) {
        User user = SecurityUtil.getCurrentUser();

        String groupCurrency = groupRequestDto.getCurrency();
        String groupType = groupRequestDto.getType();
        String inviteCode = InviteCodeUtil.generateInviteCode();
        String inviteUrl = InviteCodeUtil.generateInviteUrl(inviteCode, baseInviteUrl);

        Group group = Group.builder()
                           .name(groupRequestDto.getName())
                           .currency(groupCurrency != null ? Currency.from(groupCurrency) : Currency.USD)
                           .photoUrl(groupRequestDto.getPhotoUrl())
                           .type(groupType != null ? GroupType.from(groupType) : GroupType.SHARED)
                           .createdBy(user.getId())
                           .inviteCode(inviteCode)
                           .inviteUrl(inviteUrl)
                           .build();

        Group savedGroup = groupRepository.save(group);

        GroupMembership groupMembership = GroupMembership.builder()
                                                         .user(user)
                                                         .isOwner(true)
                                                         .joinedAt(LocalDateTime.now())
                                                         .build();

        savedGroup.addMember(groupMembership);

        groupRepository.save(savedGroup);

        return convertToDto(savedGroup);
    }

    @Override
    public GroupResponseDto joinGroup(String inviteCode) {
        User user = SecurityUtil.getCurrentUser();

        Group group = groupRepository.findByInviteCode(inviteCode).orElseThrow(
                () -> new ResourceNotFoundException("Group with invite code not found"));

        if (group.hasMember(user.getId())) {
            throw new AlreadyGroupMemberException("User is already a member of this group.");
        }

        if (!group.isActive()) {
            throw new InactiveGroupException("Cannot join an inactive group.");
        }

        GroupMembership membership = GroupMembership.builder()
                                                    .user(user)
                                                    .joinedAt(LocalDateTime.now())
                                                    .build();

        group.addMember(membership);

        groupRepository.save(group);

        return convertToDto(group);
    }

    @Override
    public void leaveGroup(String id) {
        UUID userId = SecurityUtil.getCurrentUser().getId();
        UUID groupId = UuidUtil.parse(id, "groupId");
        Group group = groupRepository.findById(groupId)
                                     .orElseThrow(() -> new ResourceNotFoundException(
                                             "Group with the given ID does not exist."));

        if (!group.hasMember(userId)) {
            throw new NotGroupMemberException("Only group members can leave a group.");
        }

        if (group.isOwner(userId)) {
            throw new OwnerCannotLeaveGroupException("Owner cannot leave the group. Transfer the ownership first.");
        }

        group.removeMember(userId);
        groupRepository.save(group);
    }

    @Override
    public GroupResponseDto updateStatus(String id, String status) {
        UUID userId = SecurityUtil.getCurrentUser().getId();
        UUID groupId = UuidUtil.parse(id, "groupId");
        GroupStatus newStatus = GroupStatus.from(status);

        Group group = groupRepository.findById(groupId)
                                     .orElseThrow(() -> new ResourceNotFoundException(
                                             "Group with the given ID does not exist."));

        if (!group.isOwner(userId)) {
            throw new NotGroupOwnerException("Only the group owner can update group status.");
        }

        if (group.getStatus() == newStatus) {
            throw new RedundantGroupStatusException("The group already has this status: " + newStatus);
        }

        group.setStatus(newStatus);
        groupRepository.save(group);
        return convertToDto(group);
    }

    private GroupResponseDto convertToDto(Group group) {
        List<GroupMemberDto> memberDto = group.getMembers() != null
                                         ? group.getMembers().stream()
                                                .map(member -> GroupMemberDto.builder()
                                                                             .userId(member.getUser().getId())
                                                                             .username(member.getUser().getUsername())
                                                                             .email(member.getUser().getEmail())
                                                                             .isOwner(member.getIsOwner())
                                                                             .joinedAt(member.getJoinedAt())
                                                                             .build())
                                                .toList()
                                         : List.of();

        return GroupResponseDto.builder()
                               .groupId(group.getId())
                               .name(group.getName())
                               .inviteCode(group.getInviteCode())
                               .inviteUrl(group.getInviteUrl())
                               .photoUrl(group.getPhotoUrl())
                               .status(group.getStatus())
                               .currencyCode(group.getCurrency().getCode())
                               .currencySymbol(group.getCurrency().getSymbol())
                               .type(group.getType())
                               .createdAt(group.getCreatedAt())
                               .members(memberDto)
                               .build();
    }
}
