package com.skyvault05.remindme.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skyvault05.remindme.dto.UserDto;
import com.skyvault05.remindme.utils.mapper.UserMapper;
import com.skyvault05.remindme.repository.FriendRepository;
import com.skyvault05.remindme.repository.UserRepository;
import com.skyvault05.remindme.utils.enums.UserRole;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UserTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private FriendRepository friendRepository;

    @Test
    public void userForTest(){
        for(long i=1; i < 10 ; i++){
            User user = User.builder()
                    .email("email@email.com" + i)
                    .name("name" + i)
                    .picture("pic" + i)
                    .role(UserRole.USER)
                    .build();

            userRepository.save(user);
        }
    }

    @Test
    public void friendsForTest(){
        for(long j=1; j<9; j++){
            User user = userRepository.findById(j).orElse(null);
            User friendUser = userRepository.findById(j+1).orElse(null);
            Friend friend = Friend
                    .builder()
                    .user(user.getId())
                    .friend(friendUser.getId())
                    .build();

            friendRepository.save(friend);
        }
    }

    @Test
    @WithMockUser(roles = "USER")
    public void saveUser(){
        User user = User.builder()
                .email("email@email.com")
                .name("name")
                .picture("pic")
                .build();
        userRepository.save(user);
    }

    @Test
    public void updateUser(){
        User user = userRepository.findById(1L).orElse(null);
        user.setName("updateUserName");
        userRepository.save(user);
    }

    @Test
    public void deleteUser(){
        User user = userRepository.findById(6L).orElse(null);
        userRepository.delete(user);
    }

    @Test
    public void readUser(){
        User user = userRepository.findById(3L).orElse(null);
        System.out.println(user.getFriends());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void postTest() throws Exception{
        MultiValueMap<String, String> info = new LinkedMultiValueMap<>();
        User user = userRepository.findById(3L).orElse(null);
        user.getCreatedDate();
        UserDto userDto = userMapper.entityToDto(user);

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
