package com.skyvault05.remindme.repository;

import com.skyvault05.remindme.domain.ScheduleReply;
import com.skyvault05.remindme.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleReplyRepository extends JpaRepository<ScheduleReply, Long> {
    List<ScheduleReply> findAllByUser(User user);
}
