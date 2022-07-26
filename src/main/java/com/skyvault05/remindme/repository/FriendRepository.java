package com.skyvault05.remindme.repository;

import com.skyvault05.remindme.domain.Friend;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendRepository extends JpaRepository<Friend, Long> {
}
