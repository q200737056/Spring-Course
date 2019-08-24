package com.springboot2.test5.dao;

import java.util.List;

import com.springboot2.test5.model.User;

public interface IndexDao {
	public String login(String name);
	public List<User> findUserList();
	public User queryUserById(int id); 
	public List<User> queryUserBy(User user); 
	public int insertUser(User user);
	public int updateUser(User user);
	public int deleteUser(String id);
}
