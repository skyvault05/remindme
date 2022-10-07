package com.skyvault05.remindme.repository;

import com.skyvault05.remindme.domain.ScheduleMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScheduleMemberRepository extends JpaRepository<ScheduleMember, Long> {
    List<ScheduleMember> findAllByMember(Long member);
    List<ScheduleMember> findAllBySchedule(Long schedule);
    Optional<ScheduleMember> findByScheduleAndMember(Long schedule, Long member);
    Long deleteAllBySchedule(Long schedule);

}
