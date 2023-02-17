package com.skyvault05.remindme.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.skyvault05.remindme.config.security.dto.SessionUser;
import com.skyvault05.remindme.domain.Schedule;
import com.skyvault05.remindme.domain.ScheduleMember;
import com.skyvault05.remindme.domain.User;
import com.skyvault05.remindme.dto.ScheduleDto;
import com.skyvault05.remindme.dto.SimpleUserDto;
import com.skyvault05.remindme.repository.ScheduleReplyRepository;
import com.skyvault05.remindme.utils.cloud.S3Upload;
import com.skyvault05.remindme.utils.exceptions.ScheduleNotFoundException;
import com.skyvault05.remindme.utils.exceptions.UserNotMatchedException;
import com.skyvault05.remindme.utils.mapper.ScheduleMapper;
import com.skyvault05.remindme.repository.ScheduleMemberRepository;
import com.skyvault05.remindme.repository.ScheduleRepository;
import com.skyvault05.remindme.repository.UserRepository;
import com.skyvault05.remindme.utils.exceptions.UserNotFoundException;
import com.skyvault05.remindme.utils.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class ScheduleService{
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;
    private final ScheduleMemberRepository scheduleMemberRepository;
    private final ScheduleReplyService scheduleReplyService;
    private final ScheduleMemberService scheduleMemberService;
    private final ScheduleMapper scheduleMapper;
    private final UserMapper userMapper;
    private final AmazonS3 amazonS3;
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    private S3Upload s3Upload;

    @Transactional
    public ScheduleDto storeSchedule(ScheduleDto scheduleDto) throws IOException {
        Schedule schedule = scheduleMapper.dtoToEntity(scheduleDto);

        scheduleRepository.save(schedule);
        scheduleMemberService.addMyself(schedule);
        scheduleMemberService.addMembers(schedule, scheduleDto.getMembers());

        ScheduleDto newScheduleDto = scheduleMapper.entityToDto(schedule);
        List<ScheduleMember> scheduleMemberList = scheduleMemberRepository.findAllBySchedule(schedule.getId());
        List<SimpleUserDto> simpleUserDtoList = userMapper.scheduleMemberListToSimpleDtoList(scheduleMemberList);
        newScheduleDto.setMembers(simpleUserDtoList);

        log.info("스케쥴: " + schedule.getId() + ", 유저: " + schedule.getId() + ", 스케쥴저장: " + schedule.getTitle() + " : " + schedule.getDescription());
        return newScheduleDto;
    }

    public List<ScheduleDto> getSchedules(HttpSession session){
        List<ScheduleDto> myScheduleDtos = new LinkedList<>();

        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findById(principal.getId()).orElseThrow(() -> new UserNotFoundException("세션에 유저 정보가 없습니다"));

        List<ScheduleMember> scheduleMemberList = scheduleMemberRepository.findAllByMember(user.getId());

        Set<ScheduleDto> scheduleDtoSet = new HashSet<>();
        for(ScheduleMember scheduleMember : scheduleMemberList){
            Schedule schedule = scheduleRepository.findByIdAndIsDeleted(scheduleMember.getSchedule(), false).orElse(null);
            if(schedule == null) continue;
            ScheduleDto scheduleDto = scheduleMapper.entityToDto(schedule);
            scheduleDtoSet.add(scheduleDto);
        }
        myScheduleDtos.addAll(scheduleDtoSet);

        return myScheduleDtos;
    }

    public List<ScheduleDto> getMySchedules(HttpSession session) {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findById(principal.getId()).orElseThrow(() -> new UserNotFoundException("세션에 유저 정보가 없습니다"));

        List<Schedule> schedules = scheduleRepository.findAllByUserAndIsDeleted(user.getId(), false);
        List<ScheduleDto> scheduleDtos = scheduleMapper.entityListToDtoList(schedules);

        return scheduleDtos;
    }
    @Transactional
    public Boolean deleteSchedule(Long scheduleId, HttpSession session) {
        Schedule schedule = scheduleRepository.findByIdAndIsDeleted(scheduleId, false).orElseThrow(() -> new ScheduleNotFoundException("삭제하려는 스케쥴을 찾을 수 없습니다."));
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findById(principal.getId()).orElseThrow(() -> new UserNotFoundException("세션에 유저 정보가 없습니다"));

        if(!schedule.getUser().equals(user.getId())) {
            throw new UserNotMatchedException("삭제하려는 스케쥴의 작성자가 아닙니다.");
        } else {
            schedule.setIsDeleted(true);
            scheduleRepository.save(schedule);
            scheduleReplyService.deleteScheduleReplies(schedule.getScheduleReplies());
            scheduleMemberService.deleteScheduleMemebers(schedule.getMembers());

            log.info("스케쥴: " + schedule.getId() + ", 유저: " + schedule.getUser() + "스케쥴 삭제: " + schedule.getTitle() + " : " + schedule.getDescription());
            return true;
        }
    }

    public String uploadThumbnail(MultipartFile multipartFile) throws IOException {
        String s3FileName = UUID.randomUUID() + "-" + multipartFile.getOriginalFilename();
        String ext = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf(".")+1);
        String contentType = ext == "jpg" ? "image/jpeg" : "image/" + ext;

        ObjectMetadata objMeta = new ObjectMetadata();
        objMeta.setContentLength(multipartFile.getInputStream().available());
        objMeta.setContentType(contentType);

        amazonS3.putObject(bucket, s3FileName, multipartFile.getInputStream(), objMeta);

//        return amazonS3.getUrl(bucket, s3FileName).toString();
        return amazonS3.getUrl(bucket, s3FileName).toString().replace("https", "http");
    }

    public ScheduleDto getSchedule(Long scheduleId) {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findById(principal.getId()).orElseThrow(() -> new UserNotFoundException("세션에 유저 정보가 없습니다"));

        Schedule nullSchedule = Schedule.builder().user(user.getId()).build();
        if(scheduleId == null) return scheduleMapper.entityToDto(nullSchedule);

        Schedule schedule = scheduleRepository.findById(scheduleId).orElse(nullSchedule);

        return scheduleMapper.entityToDto(schedule);
    }
}
