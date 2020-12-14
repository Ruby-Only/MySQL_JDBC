package com.aizhixin.jdbc4;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 根据题目id对题目表题目内容和正确答案进行修改。
 * 
 * @author liu.wenlong
 *
 */
public class Update2 {
	// 数据库连接url
	private static final String URL = "jdbc:mysql://localhost:3306/hello_mysql?"
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

			String insert_sql = "UPDATE `t_question` SET CONTENT = ? ,ANSWER = ?  WHERE `ID` = ?;";
			pstm = conn.prepareStatement(insert_sql);
			pstm.setString(1, "10 - 10 = ?");
			pstm.setString(2, "B");
			pstm.setString(3, "9");
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
