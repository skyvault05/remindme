package com.skyvault05.remindme.service;

import com.skyvault05.remindme.config.security.dto.SessionUser;
import com.skyvault05.remindme.domain.ScheduleReply;
import com.skyvault05.remindme.domain.User;
import com.skyvault05.remindme.dto.ScheduleReplyDto;
import com.skyvault05.remindme.repository.ScheduleReplyRepository;
import com.skyvault05.remindme.repository.UserRepository;
import com.skyvault05.remindme.utils.exceptions.ScheduleReplyNotFoundException;
import com.skyvault05.remindme.utils.exceptions.UserNotFoundException;
import com.skyvault05.remindme.utils.exceptions.UserNotMatchedException;
import com.skyvault05.remindme.utils.mapper.ScheduleReplyMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ScheduleReplyService {
    private final UserRepository userRepository;
    private final ScheduleReplyRepository scheduleReplyRepository;
    private final ScheduleReplyMapper scheduleReplyMapper;

    public List<ScheduleReplyDto> getMyScheduleReplies(HttpSession session){
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<ScheduleReply> scheduleReplies = scheduleReplyRepository.findAllByUserAndIsDeleted(principal.getId(), false);
        List<ScheduleReplyDto> scheduleReplyDtos = scheduleReplyMapper.entityListToDtoList(scheduleReplies);

        return scheduleReplyDtos;
    }

    @Transactional
    public ScheduleReplyDto storeScheduleReply(ScheduleReplyDto scheduleReplyDto) {
        ScheduleReply scheduleReply = scheduleReplyMapper.dtoToEntity(scheduleReplyDto);

        ScheduleReply newScheduleReply = scheduleReplyRepository.save(scheduleReply);
        ScheduleReplyDto newScheduleReplyDto = scheduleReplyMapper.entityToDto(newScheduleReply);

        log.info("스케쥴: " + scheduleReply.getSchedule() + ", 유저: " + scheduleReply.getUser() + ", 댓글추가: " + scheduleReply.getDescription());
        return newScheduleReplyDto;

    }

    public void deleteScheduleReply(Long id, HttpSession session) {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findById(principal.getId()).orElseThrow(() -> new UserNotFoundException("세션에 유저 정보가 없습니다"));

        ScheduleReply scheduleReply = scheduleReplyRepository.findByIdAndIsDeleted(id, false)
                .orElseThrow(() -> new ScheduleReplyNotFoundException("삭제하려는 댓글이 존재하지 않습니다."));
        if(!user.getId().equals(scheduleReply.getUser())) throw new UserNotMatchedException("삭제하려는 댓글의 작성자가 아닙니다.");

        scheduleReply.setIsDeleted(true);
        scheduleReplyRepository.save(scheduleReply);

        log.info("스케쥴: " + scheduleReply.getSchedule() + ", 유저: " + scheduleReply.getUser() + ", 댓글삭제: " + scheduleReply.getDescription());
    }

    @Transactional
    public void deleteScheduleReplies(List<ScheduleReply> list){
        for(ScheduleReply scheduleReply : list){
            scheduleReply.setIsDeleted(true);
            log.info("스케쥴: " + scheduleReply.getSchedule() + ", 유저: " + scheduleReply.getUser() + ", 댓글추가: " + scheduleReply.getDescription());
        }

        scheduleReplyRepository.saveAll(list);

    }

    public List<ScheduleReplyDto> getScheduleRepliesBySchedule(Long scheduleId) {
        List<ScheduleReply> scheduleReplies = scheduleReplyRepository.findAllByScheduleAndIsDeleted(scheduleId, false);
        return scheduleReplyMapper.entityListToDtoList(scheduleReplies);
    }
}
