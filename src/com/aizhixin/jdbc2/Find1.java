package com.aizhixin.jdbc2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

/**
 * 以opeanId为条件查询用户信息。
 * 
 * @author liu.wenlong
 *
 */
public class Find1 {

	private static final String URL = "jdbc:mysql://localhost:3306/hello_mysql?"
			+ "useUnicode=true&characterEncoding=utf8&" + "useSSL=false&serverTimezone=Hongkong";

	private static final String USER = "root";

	private static final String PASSWORD = "liuwenlong";

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		queryByParams();
	}

	private static void queryByParams() {
		Statement stmt = null;
		Connection conn = null;
		try {
			// 注册MySQL的JDBC驱动
			Class.forName("com.mysql.cj.jdbc.Driver");
			// 获取数据库连接
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			// 创建statement对象
			stmt = conn.createStatement();
			// 查询参数
			String queryParam1 = "110";
			String query_sql = "SELECT `ID`,`OPENID`,HEAD,NAME,COUNTRY,PROVINCE,CITY,CREAT_TIME,BAIDU_TOKEN FROM `user_message` as q where 1=1 ";
			if (null != queryParam1 && !"".equals(queryParam1)) {
				query_sql += " and q.`OPENID` ='" + queryParam1 + "' ";
			}
			// 执行SQL语句并返回结果
			ResultSet rs = stmt.executeQuery(query_sql);

			// 循环输出查询结果
			while (rs.next()) {
				String id = rs.getString("ID");
				String openid = rs.getString("OPENID");
				String head = rs.getString("HEAD");
				String name = rs.getString("NAME");
				String country = rs.getString("COUNTRY");
				String province = rs.getString("PROVINCE");
				String city = rs.getString("CITY");
				Timestamp createdDate1 = rs.getTimestamp("CREAT_TIME");
				String token = rs.getString("BAIDU_TOKEN");
				// 输出查询字段结果
				System.out.println("-----------------------");
				System.out.println("ID: " + id);
				System.out.println("OPENID: " + openid);
				System.out.println("HEAD: " + head);
				System.out.println("NAME: " + name);
				System.out.println("COUNTRY:" + country);
				System.out.println("PROVINCE:" + province);
				System.out.println("CITY:" + city);
				System.out.println("createdDate1: " + createdDate1);
				System.out.println("BAIDU_TOKEN:" + token);
			}
			// 释放查询结果
			rs.close();
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 关闭资源
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
			}
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
	}

}
