package com.skyvault05.remindme.repository;

import com.skyvault05.remindme.domain.Schedule;
import com.skyvault05.remindme.domain.ScheduleMember;
import com.skyvault05.remindme.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ScheduleMemberRepository extends JpaRepository<ScheduleMember, Long> {
    List<ScheduleMember> findALlByMember(Long member);
    Optional<ScheduleMember> findByScheduleAndMember(Schedule schedule, Long member);

}
