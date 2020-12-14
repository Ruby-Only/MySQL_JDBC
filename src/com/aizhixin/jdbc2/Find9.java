package com.aizhixin.jdbc2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 查询答题汇总统计表总分排名从高到底在第3-5名的得分信息和用户信息。
 * 
 * @author liu.wenlong
 *
 */
public class Find9 {
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
			String query_sql = "SELECT `ID`,`USER_ID` ,`CURRENT_SCORE` ,`TOTAL_CORRECR_ANSWER` ,`TOTAL_WRONG_ANSWER`, `TOTAL_ANSWER` FROM `total_answer` as q where 1=1 ";
			query_sql += " order by `CURRENT_SCORE` desc limit 0,3";
			// 执行SQL语句并返回结果
			ResultSet rs = stmt.executeQuery(query_sql);
			// 循环输出查询结果
			while (rs.next()) {
				String id = rs.getString("ID");
				String a = rs.getString("USER_ID");
				String b = rs.getString("CURRENT_SCORE");
				String c = rs.getString("TOTAL_CORRECR_ANSWER");
				String d = rs.getString("TOTAL_WRONG_ANSWER");
				String e = rs.getString("TOTAL_ANSWER");
				// 输出查询字段结果
				System.out.println("-----------------------");
				System.out.println("ID: " + id);
				System.out.println("USER_ID: " + a);
				System.out.println("CURRENT_SCORE: " + b);
				System.out.println("TOTAL_CORRECR_ANSWER: " + c);
				System.out.println("TOTAL_WRONG_ANSWER:" + d);
				System.out.println("TOTAL_ANSWER:" + e);

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
