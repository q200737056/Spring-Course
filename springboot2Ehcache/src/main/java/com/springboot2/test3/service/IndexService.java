package com.springboot2.test3.service;

import java.util.List;

import com.springboot2.test3.model.User;

public interface IndexService {
	public String login(String name);
	public List<User> findUserList();
	public List<User> queryUserById(String id); 
	public int updateUser(User user);
	public int insertUser(User user);
	public int deleteUser(String id);
}
