package com.skyvault05.remindme.utils.converter;

import com.skyvault05.remindme.domain.User;
import com.skyvault05.remindme.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Converter
@RequiredArgsConstructor
public class UserListConverter implements AttributeConverter<List<User>, String> {
    private static final String SPLIT_CHAR=",";

    private final UserRepository userRepository;


    @Override
    public String convertToDatabaseColumn(List<User> attribute) {
        if (attribute.isEmpty()) return null;

        List<String> stringList = new LinkedList<>();
        for(User user : attribute){
            stringList.add(user.getUserId().toString());
        }

        return String.join(SPLIT_CHAR, stringList);
    }

    @Override
    public List<User> convertToEntityAttribute(String dbData) {
        if (dbData == null) return new LinkedList<>();

        String[] userIdArray = dbData.split(",");
        List<User> userList = new LinkedList<>();

        for(String userId : userIdArray) {
            User tempUser = userRepository.findById(Long.parseLong(userId)).orElse(null);
            userList.add(tempUser);
        }

        return userList;
    }
}
