package h2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class H2Init {
    // H2データベースのファイル保存パス
	private static final String JDBC_URL = "jdbc:h2:~/book"; //
	private static final String USER = "sa";
	private static final String PASSWORD = "";

	public static void main(String[] args) {
	    try (Connection conn = DriverManager.getConnection(JDBC_URL, USER, PASSWORD)) {
	        System.out.println("H2データベースに接続しました。");

	        // SQLファイルを実行
	        executeSqlFile(conn, "src/resources/product.sql");
	        executeSqlFile(conn, "src/resources/customer.sql");
	        executeSqlFile(conn, "src/resources/purchase.sql");

	        System.out.println("データベースの初期化が完了しました！");
	    } catch (SQLException | IOException e) {
	        e.printStackTrace();
	    }
	}

    private static void executeSqlFile(Connection conn, String filePath) throws SQLException, IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath));
             Statement stmt = conn.createStatement()) {

            String line;
            StringBuilder sql = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                sql.append(line).append(" ");
                if (line.trim().endsWith(";")) { // ";" が来たら実行
                    stmt.execute(sql.toString());
                    sql.setLength(0); // バッファをクリア
                }
            }

            System.out.println(filePath + " のSQLを実行しました。");
        }
    }
}
