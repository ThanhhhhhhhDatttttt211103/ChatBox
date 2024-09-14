package com.chatbox.repository;

import com.chatbox.entity.GroupDetail;
import com.chatbox.entity.component.GroupDetailId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupDetailRepository extends JpaRepository<GroupDetail, GroupDetailId> {
}
