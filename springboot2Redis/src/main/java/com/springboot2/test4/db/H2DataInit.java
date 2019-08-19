package com.springboot2.test4.db;

import java.sql.Connection;
import java.sql.Statement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.alibaba.druid.pool.DruidDataSource;
@Component
public class H2DataInit implements ApplicationRunner {
	@Autowired
	DruidDataSource druidDataSource;
	//初始化h2 数据库表和数据
	@Override
	public void run(ApplicationArguments args) throws Exception {
	
		 String sql1 = "CREATE TABLE TEST_USER(id INT NOT NULL AUTO_INCREMENT, "
		 		+ "name VARCHAR(30),password VARCHAR(30), email VARCHAR(100),"
		 		+ "PRIMARY KEY (id))";
		 String sql2="INSERT INTO test_user (id, name, password, email) "
		 		+ "VALUES (1, 'admin', 'admin', '1111@qq.com')";
		 String sql3="INSERT INTO test_user (id, name, password, email) "
		 		+ "VALUES (2, 'hehe', 'hehe', '119@qq.com')";
		 Connection connection = null;
		 Statement stmt = null;
		 
		 try {
			connection = druidDataSource.getConnection();
			stmt = connection.createStatement();
			stmt.addBatch(sql1);
			stmt.addBatch(sql2);
			stmt.addBatch(sql3);
			stmt.executeBatch();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
                 stmt.close();
                 connection.close();
             } catch (Exception e2) {
                 e2.printStackTrace();
             }
			System.out.println("初始化表数据完成。。。");
		}
		  
	}

}
