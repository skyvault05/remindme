package com.skyvault05.remindme.controller;

import com.skyvault05.remindme.domain.ScheduleMember;
import com.skyvault05.remindme.dto.ScheduleDto;
import com.skyvault05.remindme.dto.ScheduleReplyDto;
import com.skyvault05.remindme.dto.SimpleUserDto;
import com.skyvault05.remindme.service.ScheduleService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path = "${api}/schedule")
@RequiredArgsConstructor
@Slf4j
public class ScheduleController {
    private final ScheduleService scheduleService;

    @Operation(summary = "Schedule 추가 및 업데이트")
    @PostMapping("/storeSchedule")
    public ScheduleDto storeSchedule(@RequestBody ScheduleDto scheduleDto) {
        ScheduleDto newScheduleDto = scheduleService.storeSchedule(scheduleDto);

        return newScheduleDto;
    }

    @Operation(summary = "Thumbnail 이미지 업로드")
    @PostMapping("/uploadThumbnail")
    @ResponseBody
    public String uploadThumbnail(@RequestBody MultipartFile thumbnail) throws IOException {
        return scheduleService.uploadThumbnail(thumbnail);
    }

    @Operation(summary = "내가 포함된 Schedule 리스트")
    @GetMapping("/getSchedules")
    public List<ScheduleDto> getSchedules(HttpSession session){
        return scheduleService.getSchedules(session);
    }

    @Operation(summary = "내가 등록한 Schedule 리스트")
    @GetMapping("/getMySchedules")
    public List<ScheduleDto> getMySchedules(HttpSession session){
        return scheduleService.getMySchedules(session);
    }

    @Operation(summary = "Schedule 삭제")
    @DeleteMapping("/deleteSchedule/{scheduleId}")
    public Boolean deleteSchedule(@PathVariable Long scheduleId, HttpSession session){
        return scheduleService.deleteSchedule(scheduleId, session);
    }
}
