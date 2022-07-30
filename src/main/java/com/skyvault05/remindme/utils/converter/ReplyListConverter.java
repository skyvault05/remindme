package com.skyvault05.remindme.utils.converter;

import com.skyvault05.remindme.domain.ScheduleReply;
import com.skyvault05.remindme.repository.ScheduleReplyRepository;
import lombok.RequiredArgsConstructor;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.LinkedList;
import java.util.List;

@Converter
@RequiredArgsConstructor
public class ReplyListConverter implements AttributeConverter<List<ScheduleReply>, String> {
    private static final String SPLIT_CHAR=",";

    private final ScheduleReplyRepository scheduleReplyRepository;


    @Override
    public String convertToDatabaseColumn(List<ScheduleReply> attribute) {
        if (attribute.isEmpty()) return null;

        List<String> stringList = new LinkedList<>();
        for(ScheduleReply ScheduleReply : attribute){
            stringList.add(ScheduleReply.getScheduleReplyId().toString());
        }

        return String.join(SPLIT_CHAR, stringList);
    }

    @Override
    public List<ScheduleReply> convertToEntityAttribute(String dbData) {
        if (dbData == null) return new LinkedList<>();

        String[] replyIdArray = dbData.split(",");
        List<ScheduleReply> scheduleReplyList = new LinkedList<>();

        for(String replyId : replyIdArray) {
            ScheduleReply tempScheduleReply = scheduleReplyRepository.findById(Long.parseLong(replyId)).orElse(null);
            scheduleReplyList.add(tempScheduleReply);
        }

        return scheduleReplyList;
    }
}

