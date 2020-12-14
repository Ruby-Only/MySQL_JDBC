package com.aizhixin.jdbc2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 以答题时间和用户id为条件查询每日答题统计表信息。
 * 
 * @author liu.wenlong
 *
 */
public class Find6 {
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
			String queryParam2 = "2019";

			String query_sql = "SELECT `ID`,`USER_ID` ,`CORRECT_COUNT` ,`WRONG_COUNT` ,`TOTAL_COUNT`,CURRENT_SCORE,DAY_SOCARE, `ANSWER_DATE` FROM `day_answer` as q where 1=1 ";
			if (null != queryParam1 && !"".equals(queryParam1)) {
				query_sql += " and q.`USER_ID` ='" + queryParam1 + "' ";
			}
			if (null != queryParam2 && !"".equals(queryParam2)) {
				query_sql += " and q.`ANSWER_DATE` like '%" + queryParam2 + "%' ";
			}

			// 执行SQL语句并返回结果
			ResultSet rs = stmt.executeQuery(query_sql);

			// 循环输出查询结果
			while (rs.next()) {

				String id = rs.getString("ID");
				String a = rs.getString("USER_ID");
				String b = rs.getString("CORRECT_COUNT");
				String c = rs.getString("WRONG_COUNT");
				String d = rs.getString("TOTAL_COUNT");
				String e = rs.getString("CURRENT_SCORE");
				String f = rs.getString("DAY_SOCARE");

				// 输出查询字段结果
				System.out.println("-----------------------");
				System.out.println("ID: " + id);
				System.out.println("USER_ID: " + a);
				System.out.println("CURRENT_SCORE: " + b);
				System.out.println("TOTAL_CORRECR_ANSWER: " + c);
				System.out.println("TOTAL_WRONG_ANSWER:" + d);
				System.out.println("TOTAL_ANSWER:" + e);
				System.out.println("DAY_SOCARE" + f);

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
