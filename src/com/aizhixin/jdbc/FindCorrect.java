package com.aizhixin.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * 以用户id和是否答题正确（正确）为条件查询每日答题记录表统计出当前用户答题正确数最高的三个知识领域。
 * @author liu
 *
 */
public class FindCorrect {
	private static final String URL = "jdbc:mysql://localhost:3306/hello_mysql?"
			+ "useUnicode=true&characterEncoding=utf8&"
			+ "useSSL=false&serverTimezone=Hongkong";

	private static final String USER = "root";

	private static final String PASSWORD = "liuwenlong";

	public FindCorrect() {

	}

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
			//查询参数
			String queryParam1 = "02";
			String queryParam2 = "true";
			
			String query_sql = "SELECT COUNT(*) AS total_num, `KNOWLEDGE_AREA` FROM `answer_record` as q where 1=1   ";
			if(null != queryParam1 && !"".equals(queryParam1)) {
				query_sql += " and q.`USER_ID` ='"+queryParam1+"' "; 
			}
			if(null != queryParam2 && !"".equals(queryParam2)) {
				query_sql += " and q.`JUDGE` ='"+queryParam2+"' "; 
			}

			query_sql += "GROUP BY KNOWLEDGE_AREA order by `KNOWLEDGE_AREA` desc limit 0,3";
			// 执行SQL语句并返回结果
			ResultSet rs = stmt.executeQuery(query_sql);

			// 循环输出查询结果
		while (rs.next()) {

				String knowledgeArea = rs.getString("KNOWLEDGE_AREA");
				System.out.println("knowledgeArea: " + knowledgeArea);

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
