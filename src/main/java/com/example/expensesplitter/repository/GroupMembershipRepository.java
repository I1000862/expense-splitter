package com.example.expensesplitter.repository;

import com.example.expensesplitter.entity.GroupMembership;
import com.example.expensesplitter.entity.GroupMembershipId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupMembershipRepository extends JpaRepository<GroupMembership, GroupMembershipId> {
}
