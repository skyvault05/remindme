package com.skyvault05.remindme.repository;

import com.skyvault05.remindme.domain.Friend;
import com.skyvault05.remindme.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Long> {
    List<Friend> findAllByUser(Long userId);
    Optional<Friend> findFriendByUserAndFriend(Long userId, Long friendId);
}
