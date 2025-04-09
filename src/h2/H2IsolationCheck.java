package h2;

import java.sql.Connection;
import java.sql.DriverManager;

public class H2IsolationCheck {
    public static void main(String[] args) throws Exception {
        Connection con = DriverManager.getConnection("jdbc:h2:~/test", "sa", "");
        int isolationLevel = con.getTransactionIsolation();

        switch (isolationLevel) {
            case Connection.TRANSACTION_READ_UNCOMMITTED:
                System.out.println("トランザクション分離レベル: READ UNCOMMITTED");
                break;
            case Connection.TRANSACTION_READ_COMMITTED:
                System.out.println("トランザクション分離レベル: READ COMMITTED");
                break;
            case Connection.TRANSACTION_REPEATABLE_READ:
                System.out.println("トランザクション分離レベル: REPEATABLE READ");
                break;
            case Connection.TRANSACTION_SERIALIZABLE:
                System.out.println("トランザクション分離レベル: SERIALIZABLE");
                break;
            default:
                System.out.println("未知のトランザクション分離レベル");
        }

        con.close();
    }
}
