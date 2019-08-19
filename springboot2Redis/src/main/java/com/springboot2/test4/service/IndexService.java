package com.springboot2.test4.service;

import java.util.List;

import com.springboot2.test4.model.User;

public interface IndexService {
	public String login(String name);
	public List<User> findUserList();
	public User queryUserById(String id); 
	public User updateUser(User user);
	public int insertUser(User user);
	public int deleteUser(String id);
}
