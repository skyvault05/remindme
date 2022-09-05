package com.skyvault05.remindme.repository;

import com.skyvault05.remindme.domain.Schedule;
import com.skyvault05.remindme.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
