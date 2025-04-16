package dao;

import java.sql.Connection;

import javax.naming.InitialContext;
import javax.sql.DataSource;

public class Dao {
    // 🔹 静的なデータソース（1回だけ取得して使い回す）
    private DataSource ds;

    /**
     * 📡 データベースへのコネクション取得メソッド
     *
     * JNDI経由で `DataSource` を取得し、DB接続を返す。
     * 2回目以降はキャッシュされた `ds` を再利用。
     *
     * @return データベースの接続（Connection）
     * @throws Exception 接続取得時の例外
     */
    protected Connection getConnection() throws Exception {
        // 初回のみDataSource取得
        if (ds == null) {
            InitialContext ic = new InitialContext();
            ds = (DataSource) ic.lookup("java:comp/env/jdbc/seisaku");
        }
        return ds.getConnection();
    }
}
