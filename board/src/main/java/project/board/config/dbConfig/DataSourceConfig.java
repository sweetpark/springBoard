package project.board.config.dbConfig;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import project.board.common.constant.ConnectionConst;

import javax.sql.DataSource;

public class DataSourceConfig {

    public static DataSource dataSource(){
        HikariConfig config = new HikariConfig();

        config.setJdbcUrl(ConnectionConst.URL);
        config.setUsername(ConnectionConst.USERNAME);
        config.setPassword(ConnectionConst.PASSWORD);

        config.setMaximumPoolSize(10);
        config.setMinimumIdle(5);
        config.setIdleTimeout(600000);
        config.setConnectionTimeout(30000);
        config.setMaxLifetime(1800000);
        config.setConnectionTestQuery("select 1");

        return new HikariDataSource(config);
    }
}
