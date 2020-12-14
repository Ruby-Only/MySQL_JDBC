package com.aizhixin.jdbc1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.UUID;

/**
 * 对每日答题统计表新增数据
 * 
 * @author liu.wenlong
 *
 */
public class AddDay {

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

			String insert_sql = "INSERT INTO `day_answer` (`ID`,`USER_ID` ,`CORRECT_COUNT` ,`WRONG_COUNT` ,`TOTAL_COUNT`,CURRENT_SCORE,DAY_SOCARE, `ANSWER_DATE`) VALUES (?, ?, ?, ?, ? , ? , ?, ?);";
			pstm = conn.prepareStatement(insert_sql);
			String insertId = UUID.randomUUID().toString();// UUID.randomUUID().toString()是javaJDK提供的一个自动生成主键的方法。
			System.out.println("insertId : " + insertId);
			pstm.setString(1, insertId);
			pstm.setString(2, "119");
			pstm.setString(3, "15 ");
			pstm.setString(4, "5");
			pstm.setString(5, "30");
			pstm.setString(6, "30");
			pstm.setString(7, "40");
			long current_time = System.currentTimeMillis();
			pstm.setTimestamp(8, new Timestamp(current_time));
			pstm.executeUpdate();
			// 手动提交事务
			conn.commit();
			System.out.println("添加成功");
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
