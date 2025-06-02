package com.example.expensesplitter.controller;

import com.example.expensesplitter.dto.request.group.CreateGroupRequestDto;
import com.example.expensesplitter.dto.request.group.GroupStatusRequestDto;
import com.example.expensesplitter.dto.response.group.GroupResponseDto;
import com.example.expensesplitter.dto.response.success.SuccessResponseDto;
import com.example.expensesplitter.service.GroupService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/api/groups")
@RestController
public class GroupController {

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    // will return only the users groups later
    @GetMapping
    public ResponseEntity<SuccessResponseDto> getGroups() {
        List<GroupResponseDto> groups = groupService.getGroups();
        return ResponseEntity.ok(SuccessResponseDto.<List<GroupResponseDto>>builder()
                                                   .message("Groups retrieved.")
                                                   .data(groups)
                                                   .build()
                                );
    }

    @GetMapping("/{groupId}")
    public ResponseEntity<SuccessResponseDto> getGroup(@PathVariable String groupId) {
        GroupResponseDto group = groupService.getGroupById(groupId);
        return ResponseEntity.ok(SuccessResponseDto.<GroupResponseDto>builder()
                                                   .message("Group retrieved successfully.")
                                                   .data(group)
                                                   .build());
    }

    @PostMapping
    public ResponseEntity<SuccessResponseDto> createGroup(@Valid @RequestBody CreateGroupRequestDto group) {
        GroupResponseDto createdGroup = groupService.createGroup(group);

        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(SuccessResponseDto.<GroupResponseDto>builder()
                                                     .message("Group created.")
                                                     .data(createdGroup)
                                                     .build());
    }

    @PostMapping("/join")
    public ResponseEntity<SuccessResponseDto> joinGroup(
            @RequestParam
            @NotEmpty(message = "Invite code cannot be empty.")
            String inviteCode
                                                       ) {
        GroupResponseDto group = groupService.joinGroup(inviteCode);

        return ResponseEntity.ok(SuccessResponseDto.<GroupResponseDto>builder()
                                                   .message("Joined group successfully.")
                                                   .data(group)
                                                   .build());
    }

    @PostMapping("/{groupId}/leave")
    public ResponseEntity<SuccessResponseDto> leaveGroup(@PathVariable String groupId) {
        groupService.leaveGroup(groupId);
        return ResponseEntity.ok(SuccessResponseDto.builder()
                                                   .message("Group left successfully.")
                                                   .data(Map.of("groupId", groupId))
                                                   .build());
    }

    @PatchMapping("/{groupId}/status")
    public ResponseEntity<SuccessResponseDto> updateStatus(@PathVariable String groupId,
                                                           @Valid @RequestBody GroupStatusRequestDto groupStatusRequestDto
                                                          ) {
        GroupResponseDto groupResponseDto = groupService.updateStatus(groupId, groupStatusRequestDto.getStatus());

        return ResponseEntity.ok(SuccessResponseDto.builder()
                                                   .message("Group status updated successfully.")
                                                   .data(groupResponseDto)
                                                   .build());
    }
}
