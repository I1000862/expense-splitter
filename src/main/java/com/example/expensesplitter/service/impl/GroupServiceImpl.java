package com.example.expensesplitter.service.impl;

import com.example.expensesplitter.dto.request.group.GroupRequestDto;
import com.example.expensesplitter.dto.response.group.GroupResponseDto;
import com.example.expensesplitter.entity.Group;
import com.example.expensesplitter.enums.Currency;
import com.example.expensesplitter.enums.GroupType;
import com.example.expensesplitter.exception.InvalidIdException;
import com.example.expensesplitter.exception.ResourceNotFoundException;
import com.example.expensesplitter.repository.GroupRepository;
import com.example.expensesplitter.service.GroupService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class GroupServiceImpl implements GroupService {
    private final GroupRepository groupRepository;

    public GroupServiceImpl(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    public GroupResponseDto getGroupById(String id) {
    try {
        UUID groupId = UUID.fromString(id);
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("No such group found with id: " + id));
        return convertToDto(group);
    } catch (IllegalArgumentException e) {
        throw new InvalidIdException("Invalid id passed.");
    }
    }

    @Override
    public List<GroupResponseDto> getGroups() {
        List<Group> groups = groupRepository.findAll();
        return groups.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public GroupResponseDto createGroup(GroupRequestDto groupRequestDto) {

        String groupType = groupRequestDto.getType();
        Group group = Group.builder()
                .name(groupRequestDto.getName())
                .currency(Currency.from(groupRequestDto.getCurrency()))
                .photoUrl(groupRequestDto.getPhotoUrl())
                .type( groupType != null ? GroupType.from(groupType) : GroupType.SHARED)
                .build();

        return convertToDto(groupRepository.save(group));
    }

    private GroupResponseDto convertToDto(Group group) {
        return GroupResponseDto.builder()
                .name(group.getName())
                .inviteCode(group.getInviteCode())
                .inviteUrl(group.getInviteUrl())
                .photoUrl(group.getPhotoUrl())
                .status(group.getStatus())
                .currency(group.getCurrency())
                .type(group.getType())
                .createdAt(group.getCreatedAt())
                .build();
    }
}
