package com.springboot2.test5.service;

import java.util.List;

import com.springboot2.test5.model.User;

public interface IndexService {
	public String login(String name);
	public List<User> findUserList();
	public User queryUserById(String id); 
	public User updateUser(User user);
	public int insertUser(User user);
	public int deleteUser(String id);
	public List<User> queryUserBy(User user);
}
