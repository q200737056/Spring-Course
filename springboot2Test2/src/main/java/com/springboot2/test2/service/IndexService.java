package com.springboot2.test2.service;

import java.util.List;

import com.springboot2.test2.model.User;

public interface IndexService {
	public String login(String name);
	public List<User> findUserList();
	public int insertUser(User user);
	public int deleteUser(String id);
}
