package com.homeene.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RestController;

import com.homeene.service.UserService;

@RestController
public class UserController {

	@Resource
	private UserService userService;
	
}
