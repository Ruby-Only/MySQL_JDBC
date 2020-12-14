import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.util.Date;

public class MySQLDemo {

	private static final String URL = "jdbc:mysql://localhost:3306/hello_mysql?"
			+ "useUnicode=true&characterEncoding=utf8&"
			+ "useSSL=false&serverTimezone=Hongkong";

	private static final String USER = "root";

	private static final String PASSWORD = "liuwenlong";

	public MySQLDemo() {

	}

	public static void main(String[] args) {
		Statement stmt = null;
		Connection conn = null;
		try {
			// 注册MySQL的JDBC驱动
			Class.forName("com.mysql.cj.jdbc.Driver");
			// 获取数据库连接
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			// 创建statement对象
			stmt = conn.createStatement();
			String query_sql = "SELECT q.`ID`,q.`CONTENT`,q.`ANSWER`,q.`KNOWLEDGE_AREA`,q.`CREATED_DATE` FROM `t_question` as q order by `CREATED_DATE` desc limit 0,1;";
			// 执行SQL语句并返回结果
			ResultSet rs = stmt.executeQuery(query_sql);

			// 循环输出查询结果
			while (rs.next()) {
				String id = rs.getString("ID");
				String content = rs.getString("CONTENT");
				String answer = rs.getString("ANSWER");
				String knowledgeArea = rs.getString("KNOWLEDGE_AREA");
				Date createdDate = rs.getDate("CREATED_DATE");
				Date createdDate1 = rs.getTimestamp("CREATED_DATE");
				// 输出查询字段结果
				System.out.println("-----------------------");
				System.out.println("ID: " + id);
				System.out.println("CONTENT: " + content);
				System.out.println("ANSWER: " + answer);
				System.out.println("knowledgeArea: " + knowledgeArea);
				System.out.println("createdDate: " + createdDate);
				System.out.println("createdDate1: " + createdDate1);
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
