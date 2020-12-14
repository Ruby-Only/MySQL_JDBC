package com.aizhixin.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 查询答题汇总统计表得分最高的三个用户的得分信息。
 * @author liu.wenlong
 *
 */
public class FindScore {

	private static final String URL = "jdbc:mysql://localhost:3306/hello_mysql?"
			+ "useUnicode=true&characterEncoding=utf8&" + "useSSL=false&serverTimezone=Hongkong";

	private static final String USER = "root";

	private static final String PASSWORD = "liuwenlong";

	public FindScore() {

	}

	public static void main(String[] args) {
		queryByParam();
		// queryByParams();
	}

	/**
	 * 一个查询参数
	 */
	private static void queryByParam() {
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
			String queryParam = "";
			String query_sql = "SELECT q.`ID`,q.`USER_ID`,q.`CURRENT_SCORE`,q.`TOTAL_CORRECR_ANSWER`,q.`TOTAL_WRONG_ANSWER` ,q.`TOTAL_ANSWER` FROM `total_answer` as q ";
			if (null != queryParam && !"".equals(queryParam)) {
				query_sql += " where q.`CURRENT_SCORE` ='" + queryParam + " ";
			}
			query_sql += " order by `CURRENT_SCORE` desc limit 0,3";
			// 执行SQL语句并返回结果
			ResultSet rs = stmt.executeQuery(query_sql);

			// 循环输出查询结果
			while (rs.next()) {
				String id = rs.getString("ID");
				String user_id = rs.getString("USER_ID");
				String current = rs.getString("CURRENT_SCORE");
				// String knowledgeArea = rs.getString("KNOWLEDGE_AREA");
				String correct = rs.getString("TOTAL_CORRECR_ANSWER");
				String wrong = rs.getString("TOTAL_WRONG_ANSWER");
				String total = rs.getString("TOTAL_ANSWER");

				// 输出查询字段结果
				System.out.println("-----------------------");
				System.out.println("ID: " + id);
				System.out.println("USER_ID: " + user_id);
				System.out.println("CURRENT_SCORE: " + current);
				// System.out.println("knowledgeArea: " + knowledgeArea);
				System.out.println("TOTAL_CORRECR_ANSWER" + correct);
				System.out.println("TOTAL_WRONG_ANSWER" + wrong);
				System.out.println("TOTAL_ANSWER" + total);

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
