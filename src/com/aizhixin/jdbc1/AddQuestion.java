package com.aizhixin.jdbc1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.UUID;

/**
 * 在同一个事务中对题目表和题目选项表新增数据
 * 
 * @author liu.wenlong
 *
 */
public class AddQuestion {
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
		Connection conn = null;
		PreparedStatement pstm = null;
		PreparedStatement pstm_option = null;
		try {
			// 获取数据库连接
			conn = getConnection();
			// 关闭自动提交事务
			conn.setAutoCommit(false);

			// 为题目添加内容
			String insert_sql = "INSERT INTO `t_question` (`ID`,`CONTENT`,`ANSWER`,`KNOWLEDGE_AREA`,`CREATED_DATE`) VALUES (?, ?, ?, ?, ?);";
			pstm = conn.prepareStatement(insert_sql);
			String insertId = UUID.randomUUID().toString();
			System.out.println("insertId : " + insertId);
			pstm.setString(1, insertId);
			pstm.setString(2, "光如何传播？");
			pstm.setString(3, "A");
			pstm.setString(4, "物理");
			long current_time = System.currentTimeMillis();
			pstm.setTimestamp(5, new Timestamp(current_time));
			pstm.executeUpdate();

			// 为选项添加内容
			String insertOption_sql = "INSERT INTO `t_question_option` (`ID`,`OPTION`,`OPTION_CONTENT`,`QUESTION_ID`) VALUES (?, ?, ?, ?);";
			pstm_option = conn.prepareStatement(insertOption_sql);
			pstm_option.setString(1, "25");
			pstm_option.setString(2, "A");
			pstm_option.setString(3, "直线");
			pstm_option.setString(4, insertId);
			pstm_option.executeUpdate();
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
			try {
				if (pstm_option != null)
					pstm_option.close();
			} catch (SQLException se2) {
			}
			closeConnection(conn);
		}
	}
}
