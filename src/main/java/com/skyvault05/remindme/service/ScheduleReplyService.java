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
        SessionUser sessionUser = (SessionUser) session.getAttribute("user");
        User user = userRepository.findById(sessionUser.getId()).orElseThrow(() -> new UserNotFoundException("해당 유저를 찾을 수 없습니다."));

        List<ScheduleReply> scheduleReplies = scheduleReplyRepository.findAllByUser(user);
        List<ScheduleReplyDto> scheduleReplyDtos = scheduleReplyMapper.entityListToDtoList(scheduleReplies);

        return scheduleReplyDtos;
    }

    public ScheduleReplyDto storeScheduleReply(ScheduleReplyDto scheduleReplyDto) {
        ScheduleReply scheduleReply = scheduleReplyMapper.dtoToEntity(scheduleReplyDto);

        ScheduleReply newScheduleReply = scheduleReplyRepository.save(scheduleReply);
        ScheduleReplyDto newScheduleReplyDto = scheduleReplyMapper.entityToDto(newScheduleReply);

        return newScheduleReplyDto;

    }

    public void deleteScheduleReply(Long id, HttpSession session) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("user");
        ScheduleReply scheduleReply = scheduleReplyRepository.findById(id)
                .orElseThrow(() -> new ScheduleReplyNotFoundException("삭제하려는 댓글이 존재하지 않습니다."));
        if(!sessionUser.getId().equals(scheduleReply.getUser())) throw new UserNotMatchedException("삭제하려는 댓글의 작성자가 아닙니다.");

        scheduleReply.setSchedule(null);

        scheduleReplyRepository.delete(scheduleReply);
    }

    @Transactional
    public void deleteScheduleReplies(List<ScheduleReply> list){
        for(ScheduleReply scheduleReply : list){
            scheduleReply.setIsDeleted(true);
        }
        scheduleReplyRepository.saveAll(list);
    }
}
