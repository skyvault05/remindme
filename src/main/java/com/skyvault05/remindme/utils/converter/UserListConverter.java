package com.skyvault05.remindme.utils.converter;

import com.skyvault05.remindme.domain.User;
import com.skyvault05.remindme.dto.UserInListDto;
import com.skyvault05.remindme.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.LinkedList;
import java.util.List;

@Converter
@RequiredArgsConstructor
public class UserListConverter implements AttributeConverter<List<UserInListDto>, String> {
    private static final String SPLIT_CHAR=",";

    private final UserRepository userRepository;


    @Override
    public String convertToDatabaseColumn(List<UserInListDto> attribute) {
        if (attribute.isEmpty()) return null;

        List<String> stringList = new LinkedList<>();
        for(UserInListDto user : attribute){
            stringList.add(user.getUserId().toString());
        }

        return String.join(SPLIT_CHAR, stringList);
    }

    @Override
    public List<UserInListDto> convertToEntityAttribute(String dbData) {
        if (dbData == null) return new LinkedList<>();

        String[] userIdArray = dbData.split(",");
        List<UserInListDto> userList = new LinkedList<>();

        for(String userId : userIdArray) {
            User tempUser = userRepository.findById(Long.parseLong(userId)).orElse(null);
            UserInListDto tempUserInListDto =
                    UserInListDto.builder()
                            .userId(tempUser.getUserId())
                            .userName(tempUser.getUserName())
                            .userEmail(tempUser.getUserEmail())
                            .userPicture(tempUser.getUserPicture())
                            .build();
            userList.add(tempUserInListDto);
        }

        return userList;
    }
}

