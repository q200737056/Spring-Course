package com.springboot2.test2.service.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot2.test2.mapper.IndexDao;
import com.springboot2.test2.model.User;
import com.springboot2.test2.service.IndexService;
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
	/**
	 * 示例：加个事务
	 * 默认只有发生RuntimeException和Error，才会回滚
	 * 如果发生了SQLException 不会回滚的
	 * 所以要指定回滚异常，如下
	 */
	@Override
	@Transactional(rollbackFor = {SQLException.class})
	public int insertUser(User user){
		return this.indexDao.insertUser(user);
	}
	@Override
	public int deleteUser(String id){
		return this.indexDao.deleteUser(id);
	}
}
