package com.skyvault05.remindme.controller;

import com.skyvault05.remindme.config.security.dto.SessionUser;
import com.skyvault05.remindme.domain.User;
import com.skyvault05.remindme.dto.UserDto;
import com.skyvault05.remindme.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
public class TestController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/test")
    public String hello(){
        return "hello";
    }

    @GetMapping("/session")
    public String getSession(HttpServletRequest req){
        HttpSession session = req.getSession();
        System.out.println(session.getId()+":"+session.getAttributeNames()+":"+session.getAttribute("OAuthAttributes"));
        return session.getId();
    }

    @GetMapping("/login")
    public String login(){
        return "/oauth2/authorization/google";
    }

    @PostMapping("/v1/insertusertest")
    public UserDto insertUserTest(UserDto userDTO){
        System.out.println(userDTO.getUserId()+":"+userDTO.getUserEmail()+":"+ userDTO.getUserName());
        return userDTO;
    }

    @PostMapping("/v1/usertest")
    public User userTest(UserDto userDTO){
        System.out.println(userDTO.getUserId()+":"+userDTO.getUserEmail()+":"+ userDTO.getUserName());
        User user = userRepository.findById(3L).orElse(null);
        return user;
    }

    @PostMapping("/v1/gettest")
    public User gettingUser(@RequestBody User user){
//        System.out.println(user.getUserId()+":"+user.getUserEmail()+":"+ user.getUserName());
        System.out.println(user.getUserId());
        return user;
    }

    @GetMapping("/v1/tokentest")
    public String userinfo(HttpServletRequest request, OAuth2AuthenticationToken authentication,
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



        return "userinfo";
    }
}
