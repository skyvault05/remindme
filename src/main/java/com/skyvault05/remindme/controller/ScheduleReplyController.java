package com.skyvault05.remindme.controller;

import com.skyvault05.remindme.dto.ScheduleReplyDto;
import com.skyvault05.remindme.service.ScheduleReplyService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping(path = "${api}/scheduleReply")
@RequiredArgsConstructor
@Slf4j
public class ScheduleReplyController {
    private final ScheduleReplyService scheduleReplyService;

    @Operation(summary = "ScheduleReply 저장")
    @PostMapping("/storeScheduleReply")
    public ScheduleReplyDto storeScheduleReply(@RequestBody ScheduleReplyDto scheduleReplyDto){
        return scheduleReplyService.storeScheduleReply(scheduleReplyDto);
    }

    @Operation(summary = "내가 작성한 ScheduleReply 리스트")
    @GetMapping("/getMyScheduleReplies")
    public List<ScheduleReplyDto> getMyScheduleReplies(HttpSession session){
        return scheduleReplyService.getMyScheduleReplies(session);
    }

    @Operation(summary = "ScheduleReply 삭제")
    @DeleteMapping("/deleteScheduleReply/{id}")
    public void deleteScheduleReply(@PathVariable("id") Long id, HttpSession session){
        scheduleReplyService.deleteScheduleReply(id, session);
    }
}
