package com.springboot2.test4.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.springboot2.test4.dao.IndexDao;
import com.springboot2.test4.model.User;
import com.springboot2.test4.service.IndexService;
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
	 * 使用缓存 ，通过修改、删除后查询 观察打印,是否调用了方法
	 * @Cacheable:会先查询是否已经有缓存，有会使用缓存，没有则会执行方法并缓存。
	 * value就是ehcache.xml配置的缓存名；
	 * 当然也可以在类上统一配置，方法上可以省略，@CacheConfig(cacheNames ={"user"})
	 * 此处的key是使用的spEL表达式,这边使用了固定user字符串前缀,Spring Cache提供了一些默认的SpEL上下文数据
	 * #root.methodName 当前被调用的方法名   简写methodName
	 * #root.targetClass 当前被调用的目标对象的类  简写targetClass
	 * #root.target 当前被调用的目标对象实例  简写target
	 * #result 方法执行后的返回值
	 * #参数名 方法参数值 ， 也可以 #p索引  来访问参数值,如#p0=#id
	 * unless 当条件成立的话不放入缓存
	 * condition 当条件成立的话放入缓存
	 */
	@Cacheable(value="user",key="'user'+#id",unless = "#result eq null")
	@Override
	public User queryUserById(String id){
		System.out.println("去查询数据库。。。");
		return this.indexDao.queryUserById(Integer.valueOf(id));
	}
	
	@Override
	public int insertUser(User user){
		return this.indexDao.insertUser(user);
	}
	/**
	 * @CachePut:更新缓存
	 * 需要注意的是 value和 key必须与要更新或清空的缓存相同，也就是与@Cacheable 相同
	 * 还有 缓存对象要相同 ，比如上面@Cacheable缓存了User(就是方法返回对象)，@CachePut也必须缓存User
	 * 
	 */
	@CachePut(value="user",key="'user'+#user.id")
	@Override
	public User updateUser(User user){
		 this.indexDao.updateUser(user);
		 return this.indexDao.queryUserById(Integer.valueOf(user.getId()));
	}
	/**
	 * @CacheEvict: 清除缓存
	 * allEntries属性 设置true时，会清空全部;
	 * beforeInvocation属性 设置true时，在方法执行前就清空，默认为 false
	 * 需要注意的是 value和 key必须与要更新或清空的缓存相同，也就是与@Cacheable 相同
	 */
	@CacheEvict(value="user",key="'user'+#id")
	@Override
	public int deleteUser(String id){
		return this.indexDao.deleteUser(id);
	}
}
