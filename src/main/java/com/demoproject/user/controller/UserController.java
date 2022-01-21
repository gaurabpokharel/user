package com.demoproject.user.controller;

import com.demoproject.user.VO.ResponseTemplateVO;
import com.demoproject.user.entity.User;
import com.demoproject.user.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/saveUser")
    @ApiOperation(value = "this api is used to save user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully post the user details"),
            @ApiResponse(code = 500, message = "Unable to post the user")
    })
    private User saveUser(@RequestBody User user) throws Exception {
        log.info("Inside the saveUser of User Controller");
        return userService.saveUser(user);
    }

    @GetMapping("/findUserById/{id}")
    @ApiOperation(value = "this api is used to find user by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully search the user"),
            @ApiResponse(code = 500, message = "Unable to search the user")
    })
    private User findUserById(@PathVariable("id") long userId) throws Exception
    {
        log.info("Inside findUserById of user Controller");
        return  userService.findUserById(userId);
    }

    @GetMapping("/findUserWithBlog/{id}")
    @ApiOperation(value = "this api is used to find user with blogs")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully search the user with blog content"),
            @ApiResponse(code = 500, message = "Unable to search the user")
    })
    public ResponseTemplateVO getUserWithBlog(@PathVariable("id") Long userId) throws Exception
    {
        log.info("Inside getUserWithBlog of user Controller");
        return userService.getUserWithBlog(userId);

    }




}
