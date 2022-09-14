package com.skyvault05.remindme.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skyvault05.remindme.dto.UserDto;
import com.skyvault05.remindme.mapper.UserMapper;
import com.skyvault05.remindme.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.servlet.http.Cookie;
import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@RequiredArgsConstructor
public class UserTest {
    private UserRepository userRepository;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private UserMapper userMapper;

    @Test
    public void userForTest(){
        for(long i=1; i < 10 ; i++){
            User user = User.builder()
                    .userEmail("email@email.com" + i)
                    .userName("name" + i)
                    .userPicture("pic" + i)
                    .userRole(UserRole.USER)
                    .build();
            for(long j=0; j<i; j++){
                User friend = userRepository.findById(j).orElse(null);

                if(friend != null) user.getUserFriend().add(userMapper.userToUserInListDto(friend));
            }
            userRepository.save(user);
        }
    }

    @Test
    @WithMockUser(roles = "USER")
    public void saveUser(){
        User user = User.builder()
                .userEmail("email@email.com")
                .userName("name")
                .userPicture("pic")
                .userRole(UserRole.USER)
                .build();
        userRepository.save(user);
    }

    @Test
    public void updateUser(){
        User user = userRepository.findById(1L).orElse(null);
        user.setUserName("updateUserName");
        userRepository.save(user);
    }

    @Test
    public void deleteUser(){
        User user = userRepository.findById(6L).orElse(null);
        userRepository.delete(user);
    }

    @Test
    public void readUser(){
        User user = userRepository.findById(1L).orElse(null);
        System.out.println(user.getUserFriend());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void postTest() throws Exception{
        MultiValueMap<String, String> info = new LinkedMultiValueMap<>();
        User user = userRepository.findById(3L).orElse(null);
        user.getCreatedDate();
        UserDto userDto = userMapper.userToUserDto(user);

        String content = objectMapper.writeValueAsString(userDto);

        mockMvc.perform(post("/v1/gettest")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void addFriendTest() throws Exception{
        mockMvc.perform(post("/v1/addFriend/name1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(""))
                .andExpect(status().isOk())
                .andDo(print());
    }

}
