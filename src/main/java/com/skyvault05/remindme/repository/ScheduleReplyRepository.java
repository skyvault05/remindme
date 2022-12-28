package com.skyvault05.remindme.repository;

import com.skyvault05.remindme.domain.ScheduleReply;
import com.skyvault05.remindme.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScheduleReplyRepository extends JpaRepository<ScheduleReply, Long> {
    List<ScheduleReply> findAllByUser(Long user);
    List<ScheduleReply> findAllByUserAndIsDeleted(Long user, Boolean isDeleted);
    Optional<ScheduleReply> findByIdAndIsDeleted(Long user, Boolean isDeleted);
    Long deleteAllBySchedule(Long schedule);
    Optional<ScheduleReply> findScheduleReplyByIdAndIsDeleted(Long id, Boolean isDeleted);
    List<ScheduleReply> findAllByScheduleAndIsDeleted(Long schedule, Boolean isDeleted);


}
