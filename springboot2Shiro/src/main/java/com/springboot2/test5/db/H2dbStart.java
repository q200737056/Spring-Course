package com.springboot2.test5.db;

import java.sql.SQLException;

import org.h2.tools.Server;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
@Component
@Order(1)
public class H2dbStart implements ApplicationRunner  {

	@Override
	public void run(ApplicationArguments args) throws Exception {
		// TODO Auto-generated method stub
		try {  
            System.out.println("正在启动h2数据库...");
            
            Server.createTcpServer().start(); 
            System.out.println("h2数据库启动成功...");
        } catch (SQLException e) {  
            System.out.println("启动h2数据库出错：" + e.toString());  
            e.printStackTrace();  
           throw new RuntimeException(e);  
        }  
	}

	


}
