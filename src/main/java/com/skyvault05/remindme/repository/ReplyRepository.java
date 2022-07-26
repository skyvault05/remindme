package com.skyvault05.remindme.repository;

import com.skyvault05.remindme.domain.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
}
