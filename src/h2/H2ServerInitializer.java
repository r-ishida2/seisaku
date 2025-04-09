package h2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.h2.tools.Server;

@WebListener
public class H2ServerInitializer implements ServletContextListener {
    private static Server server; // H2サーバーインスタンス
    private static final String JDBC_URL = "jdbc:h2:tcp://localhost:9092/~/book"; // H2DBのパス
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        synchronized (H2ServerInitializer.class) {
            boolean isGlobalRunning = isH2RunningGlobally();
            boolean isLocalRunning = isH2Running();

            // H2の起動状態をコンソールに出力
            System.out.println("H2ServerInitializer 起動チェック");
            System.out.println("isH2RunningGlobally() = " + isGlobalRunning);
            System.out.println("isH2Running() (JDBC接続) = " + isLocalRunning);

            // どちらかが true なら、新しく起動しない
            if (isGlobalRunning || isLocalRunning) {
                System.out.println("H2 データベースは既に起動しています（" + JDBC_URL + "）。新しく起動しません。");
                return;
            }

            try {
                System.out.println("H2 データベースを起動します...");
                server = Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9092").start();
                System.setProperty("H2_STARTED", "true"); // グローバル変数をセット
                System.out.println("H2 データベースが起動しました！ ポート: 9092");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        stopH2Server();
    }

    private void stopH2Server() {
        synchronized (H2ServerInitializer.class) {
            if (server != null) {
                System.out.println("H2 データベースを停止します...");
                server.stop();
                System.clearProperty("H2_STARTED"); // グローバル変数をクリア
                System.out.println("H2 データベースが停止しました。");
                server = null;
            }
        }
    }

    /**
     * システムプロパティを使って、H2がグローバルに起動しているか確認
     */
    private boolean isH2RunningGlobally() {
        return "true".equals(System.getProperty("H2_STARTED"));
    }

    /**
     * JDBC接続で、H2がローカルで起動しているか確認
     */
    private boolean isH2Running() {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, USER, PASSWORD)) {
            return true; // H2 に接続できれば起動中
        } catch (SQLException e) {
            return false; // H2 に接続できなければ未起動
        }
    }
}
