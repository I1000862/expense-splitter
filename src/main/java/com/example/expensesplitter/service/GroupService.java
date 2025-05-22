package com.example.expensesplitter.service;

import com.example.expensesplitter.dto.request.group.GroupRequestDto;
import com.example.expensesplitter.dto.response.group.GroupResponseDto;

import java.util.List;


public interface GroupService {
    GroupResponseDto getGroupById(String id);

//    will update this to only show groups of current user
    List<GroupResponseDto> getGroups();

    GroupResponseDto createGroup(GroupRequestDto group);
}
