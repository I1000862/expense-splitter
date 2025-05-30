package com.example.expensesplitter.controller;

import com.example.expensesplitter.dto.request.group.CreateGroupRequestDto;
import com.example.expensesplitter.dto.response.group.GroupResponseDto;
import com.example.expensesplitter.dto.response.success.SuccessResponseDto;
import com.example.expensesplitter.service.GroupService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/groups")
@RestController
public class GroupController {

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

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

        return ResponseEntity.ok(SuccessResponseDto.<GroupResponseDto>builder()
                                                   .message("Group created.")
                                                   .data(createdGroup)
                                                   .build());
    }
}
