package com.skyvault05.remindme.domain;

import com.skyvault05.remindme.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UserTest {
    @Autowired
    UserRepository userRepository;

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
}
