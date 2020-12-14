package com.aizhixin.jdbc4;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * .以用户id为条件对答题汇总统计表对应数据的正确答题数，错误答题数，总答题数，当前总累计得分进行修改。
 * 
 * @author liu
 *
 */
public class Update5 {
	// 数据库连接url
	private static final String URL = "jdbc:mysql://localhost:3306/hello_mysql_db?"
			+ "useUnicode=true&characterEncoding=utf8&" + "useSSL=false&serverTimezone=Hongkong";
	// 数据库用户名
	private static final String USER = "root";
	// 数据库密码
	private static final String PASSWORD = "liuwenlong";

	private static Connection getConnection() throws ClassNotFoundException, SQLException {
		Connection conn;
		// 注册MySQL的JDBC驱动
		Class.forName("com.mysql.cj.jdbc.Driver");
		// 获取数据库连接
		conn = DriverManager.getConnection(URL, USER, PASSWORD);
		return conn;
	}

	private static void closeConnection(Connection conn) {
		try {
			if (conn != null)
				conn.close();
		} catch (SQLException se) {
			se.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement pstm = null;

		try {
			// 获取数据库连接
			conn = getConnection();
			// 关闭自动提交事务
			conn.setAutoCommit(false);

			String insert_sql = "UPDATE `total_count` SET TOTAL_CORRRCT_COUNT = ?,TOTAL_WORING_COUNT=?,TOTAL_COUNT = ?,CURRENT_SCORE = ? WHERE ID = ?;";

			pstm = conn.prepareStatement(insert_sql);
			pstm.setString(1, "150");
			pstm.setString(2, "80");
			pstm.setString(3, "230");
			pstm.setString(4, "300");
			pstm.setString(5, "5");
			System.out.println("修改完成！");
			pstm.executeUpdate();
			// 手动提交事务
			conn.commit();
		} catch (SQLException se) {
			try {
				conn.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			se.printStackTrace();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			// 关闭资源
			try {
				if (pstm != null)
					pstm.close();
			} catch (SQLException se1) {
			}
			closeConnection(conn);
		}
	}
}
