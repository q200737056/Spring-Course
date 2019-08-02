package com.springboot2.test2.mapper;

import java.util.List;

import com.springboot2.test2.model.User;

public interface IndexDao {
	public String login(String name);
	public List<User> findUserList();
	public int insertUser(User user);
	public int deleteUser(String id);
}
