package com.skyvault05.remindme.controller;

import com.skyvault05.remindme.config.security.dto.SessionUser;
import com.skyvault05.remindme.domain.User;
import com.skyvault05.remindme.dto.ScheduleDto;
import com.skyvault05.remindme.dto.UserDto;
import com.skyvault05.remindme.utils.mapper.UserMapper;
import com.skyvault05.remindme.repository.UserRepository;
import com.skyvault05.remindme.service.TestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@Slf4j
@RequiredArgsConstructor
public class TestController {
    private final HttpSession httpSession;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final TestService testService;


    @GetMapping("/test")
    public String hello(ModelAndView mav){
        return "hello";
    }

    @PostMapping("/v1/addScheduleTest")
    public ScheduleDto addScheduleTest(@RequestBody ScheduleDto scheduleDto){
        System.out.println(scheduleDto);
        return scheduleDto;
    }


    @GetMapping("/session")
    public String getSession(HttpServletRequest req){
        HttpSession session = req.getSession();
        System.out.println(session.getId()+":"+session.getAttributeNames()+":"+session.getAttribute("OAuthAttributes"));
        return session.getId();
    }

    @GetMapping("/userDtoTest")
    public UserDto userDtoTest(){
        return userMapper.entityToDto(userRepository.findById(1L).orElse(null));
    }

    @GetMapping("/login")
    public String login(){
        return "/oauth2/authorization/google";
    }

    @PostMapping("/v1/insertusertest")
    public UserDto insertUserTest(UserDto userDTO){
        System.out.println(userDTO.getId()+":"+userDTO.getEmail()+":"+ userDTO.getName());
        return userDTO;
    }

    @PostMapping("/v1/usertest")
    public User userTest(UserDto userDTO){
        System.out.println(userDTO.getId()+":"+userDTO.getEmail()+":"+ userDTO.getName());
        User user = userRepository.findById(3L).orElse(null);
        return user;
    }

    @PostMapping("/v1/gettest")
    public User gettingUser(@RequestBody User user){
//        System.out.println(user.getUserId()+":"+user.getUserEmail()+":"+ user.getUserName());
        System.out.println(user.getId());
        return user;
    }

    @GetMapping("/v1/tokentest")
    public User userinfo(HttpServletRequest request, OAuth2AuthenticationToken authentication,
                           Model model, @RegisteredOAuth2AuthorizedClient("google") OAuth2AuthorizedClient authorizedClien,
                           OAuth2AccessTokenResponse oAuth2AccessTokenResponse, HttpSession httpSession) {
        OAuth2AccessToken accessToken = authorizedClien.getAccessToken();
        System.out.println(accessToken.getTokenType().toString());
        System.out.println(accessToken.getScopes());
        System.out.println(accessToken.getExpiresAt());
//        System.out.println("refresh token: "+authorizedClien.getRefreshToken().getTokenValue());
        System.out.println(accessToken.getTokenValue());
        System.out.println(authentication.getCredentials()+"???");
        SessionUser user = (SessionUser) request.getSession().getAttribute("user");
        System.out.println(user.getEmail());
        System.out.println(authentication.getCredentials());
        for (Object obj : authentication.getPrincipal().getAttributes().keySet()) {
            System.out.println((String) obj);
        }

//        Long obj = Long.valueOf(httpSession.getAttribute("expiresIn").toString());
//        System.out.println("expiresIn(testController): "+obj);

//        DefaultOAuth2User ud = (DefaultOAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        System.out.println(ud.getAttributes().keySet());
//        System.out.println(ud.getAuthorities());

//        for(Object obj : oAuth2AccessTokenResponse.getAdditionalParameters().keySet()){
//            System.out.println(obj.toString());
//        }
        log.info("hi");

        User returnUser = userRepository.findById(4L).orElse(null);


        return returnUser;
    }

    @GetMapping("/causeerror")
    void causeError(){
        testService.throwError();
    }

    @GetMapping("/sessiontest")
    public SessionUser sessionTest(){

        SessionUser sessionUser = (SessionUser) httpSession.getAttribute("user");
        System.out.println(sessionUser.getId());
        return sessionUser;
    }

}
