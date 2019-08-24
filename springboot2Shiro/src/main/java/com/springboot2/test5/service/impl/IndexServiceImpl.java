package com.springboot2.test5.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot2.test5.dao.IndexDao;
import com.springboot2.test5.model.User;
import com.springboot2.test5.service.IndexService;
@Service
public class IndexServiceImpl implements IndexService {
	@Autowired
	private IndexDao indexDao;
	@Override
	public String login(String name){
		return this.indexDao.login(name);
	}
	@Override
	public List<User> findUserList(){
		return this.indexDao.findUserList();
	}
	@Override
	public User queryUserById(String id){
		return this.indexDao.queryUserById(Integer.valueOf(id));
	}
	
	@Override
	public int insertUser(User user){
		return this.indexDao.insertUser(user);
	}
	
	@Override
	public User updateUser(User user){
		 this.indexDao.updateUser(user);
		 return user;
	}
	
	@Override
	public int deleteUser(String id){
		return this.indexDao.deleteUser(id);
	}
	@Override
	public List<User> queryUserBy(User user){
		return this.indexDao.queryUserBy(user);
	}
}
