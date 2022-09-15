package com.skyvault05.remindme.repository;

import com.skyvault05.remindme.domain.ScheduleMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleMemberRepository extends JpaRepository<ScheduleMember, Long> {
}
