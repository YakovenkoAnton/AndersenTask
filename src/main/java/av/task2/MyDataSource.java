package av.task2;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class MyDataSource {

    private static HikariConfig config = new HikariConfig();
    public static HikariDataSource ds;

    static {
        config.setJdbcUrl( "jdbc:postgresql://localhost:5432/postgres" );
        config.setUsername( "postgres" );
        config.setPassword( "postgres" );
        config.addDataSourceProperty( "cachePrepStmts" , "true" );
        config.addDataSourceProperty( "prepStmtCacheSize" , "250" );
        config.addDataSourceProperty( "prepStmtCacheSqlLimit" , "2048" );
        ds = new HikariDataSource( config );
    }

    private MyDataSource() {}

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
}