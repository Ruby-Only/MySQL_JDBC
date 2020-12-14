import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.UUID;

public class MySQLDemo1 {

	//数据库连接url
	private static final String URL = "jdbc:mysql://localhost:3306/hello_mysql?"
			+ "useUnicode=true&characterEncoding=utf8&"
			+ "useSSL=false&serverTimezone=Hongkong";
	//数据库用户名
	private static final String USER = "root";
    //数据库密码
	private static final String PASSWORD = "liuwenlong";

	private static Connection getConnection() throws ClassNotFoundException,
			SQLException {
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
			//获取数据库连接
			conn = getConnection();
			// 关闭自动提交事务
			conn.setAutoCommit(false);

			String insert_sql = "INSERT INTO `t_question` (`ID`,`CONTENT`,`ANSWER`,`KNOWLEDGE_AREA`,`CREATED_DATE`) VALUES (?, ?, ?, ?, ?);";
			pstm = conn.prepareStatement(insert_sql);
			String insertId = UUID.randomUUID().toString();
			System.out.println("insertId : " + insertId);
			pstm.setString(1, insertId);
			pstm.setString(2, "题目内容111111");
			pstm.setString(3, "答案");
			pstm.setString(4, "1");
			long current_time = System.currentTimeMillis();
			 java.sql.Date date = new java.sql.Date(current_time);
			 pstm.setDate(5, date);
//			pstm.setTimestamp(5, new Timestamp(current_time));
			 
			pstm.executeUpdate();

			String insertOption_sql = "INSERT INTO `t_question_option` (`OPTION_CONTENT`,`OPTION`,`QUESTION_ID`) VALUES (?, ?, ?);";
			pstm_option = conn.prepareStatement(insertOption_sql);
			pstm_option.setString(1, "选项内容");
			pstm_option.setString(2, "A");
			pstm_option.setString(3, insertId);
			//新增,修改,删除数据都使用executeUpdate
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
