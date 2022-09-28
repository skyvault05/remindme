package com.skyvault05.remindme.utils.mapper;

import com.skyvault05.remindme.config.security.dto.SessionUser;
import com.skyvault05.remindme.domain.Schedule;
import com.skyvault05.remindme.domain.ScheduleReply;
import com.skyvault05.remindme.domain.User;
import com.skyvault05.remindme.dto.ScheduleDto;
import com.skyvault05.remindme.dto.ScheduleReplyDto;
import com.skyvault05.remindme.dto.UserDto;
import com.skyvault05.remindme.repository.ScheduleReplyRepository;
import com.skyvault05.remindme.repository.UserRepository;
import com.skyvault05.remindme.utils.exceptions.ScheduleReplyNotFoundException;
import com.skyvault05.remindme.utils.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.LinkedList;
import java.util.List;

@Component
public class ScheduleReplyMapper {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ScheduleReplyRepository scheduleReplyRepository;
    @Autowired
    private ScheduleReplyMapper scheduleReplyMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ScheduleMapper scheduleMapper;
    @Autowired
    private HttpSession httpSession;

    @Transactional
    public ScheduleReply dtoToEntity(ScheduleReplyDto scheduleReplyDto){
        ScheduleReply scheduleReply = (scheduleReplyDto.getId() != null) ?
                scheduleReplyRepository.findById(scheduleReplyDto.getId()).orElseThrow(() -> new ScheduleReplyNotFoundException("해당 댓글을 찾을 수 없습니다.")) : new ScheduleReply();

        if(scheduleReplyDto.getUser() != null){
            scheduleReply.setUser(userMapper.dtoToEntity(scheduleReplyDto.getUser()));
        }else {
            SessionUser sessionUser = (SessionUser) httpSession.getAttribute("user");
            User user = userRepository.findById(sessionUser.getId()).orElseThrow(() -> new UserNotFoundException("세션에 유저 정보가 없습니다"));

            scheduleReply.setUser(user);
        }
        if(scheduleReplyDto.getSchedule() != null) scheduleReply.setSchedule(scheduleMapper.dtoToEntity(scheduleReplyDto.getSchedule()));
        if(scheduleReplyDto.getDescription() != null) scheduleReply.setDescription(scheduleReplyDto.getDescription());
        if(scheduleReplyDto.getStatus() != null) scheduleReply.setStatus(scheduleReplyDto.getStatus());

        return scheduleReply;
    }

    public ScheduleReplyDto entityToDto(ScheduleReply scheduleReply){
        UserDto userDto = userMapper.entityToDto(scheduleReply.getUser());

        return ScheduleReplyDto
                .builder()
                .id(scheduleReply.getId())
                .user(userDto)
                .schedule(scheduleMapper.entityToDto(scheduleReply.getSchedule()))
                .description(scheduleReply.getDescription())
                .createdDate(scheduleReply.getCreatedDate())
                .modifiedDate(scheduleReply.getModifiedDate())
                .status(scheduleReply.getStatus())
                .build();
    }

    public List<ScheduleReplyDto> entityListToDtoList(List<ScheduleReply> list){
        if(list == null)return null;

        List<ScheduleReplyDto> newList = new LinkedList<>();

        for(ScheduleReply scheduleReply : list){
            ScheduleDto scheduleDto = ScheduleDto
                    .builder()
                    .id(scheduleReply.getSchedule().getId())
                    .user(userMapper.entityToDto(scheduleReply.getSchedule().getUser()))
                    .title(scheduleReply.getSchedule().getTitle())
                    .thumbnail(scheduleReply.getSchedule().getThumbnail())
                    .startDate(scheduleReply.getSchedule().getStartDate())
                    .endDate(scheduleReply.getSchedule().getEndDate())
                    .intervalType(scheduleReply.getSchedule().getIntervalType())
                    .intervalValue(scheduleReply.getSchedule().getIntervalValue())
                    .status(scheduleReply.getSchedule().getStatus())
                    .build();

            scheduleDto.deleteUnnecessaryFields();

            ScheduleReplyDto scheduleReplyDto = ScheduleReplyDto
                    .builder()
                    .id(scheduleReply.getId())
                    .user(userMapper.entityToDto(scheduleReply.getUser()))
                    .schedule(scheduleDto)
                    .description(scheduleReply.getDescription())
                    .createdDate(scheduleReply.getCreatedDate())
                    .modifiedDate(scheduleReply.getModifiedDate())
                    .status(scheduleReply.getStatus())
                    .build();

            scheduleReplyDto.deleteUnnecessaryFields();


            newList.add(scheduleReplyDto);
        }

        return newList;
    }

    public List<ScheduleReply> dtoListToEntityList(List<ScheduleReplyDto> scheduleReplyDto){
        List<ScheduleReply> newList = new LinkedList<>();

        for(ScheduleReplyDto dto : scheduleReplyDto){
            ScheduleReply scheduleReply = scheduleReplyRepository.findById(dto.getId()).orElseThrow(() -> new ScheduleReplyNotFoundException("해당 댓글을 찾을 수 없습니다."));
            if(scheduleReply != null) newList.add(scheduleReply);
        }

        return newList;
    }
}
