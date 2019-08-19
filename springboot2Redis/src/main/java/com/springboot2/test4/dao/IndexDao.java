package com.springboot2.test4.dao;

import java.util.List;

import com.springboot2.test4.model.User;

public interface IndexDao {
	public String login(String name);
	public List<User> findUserList();
	public User queryUserById(int id); 
	public int insertUser(User user);
	public int updateUser(User user);
	public int deleteUser(String id);
}
