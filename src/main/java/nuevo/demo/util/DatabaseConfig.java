package nuevo.demo.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        
        // Usa JDBC_DATABASE_URL si existe, si no, construye la URL
        String jdbcUrl = System.getenv("JDBC_DATABASE_URL");
        if (jdbcUrl == null || jdbcUrl.isEmpty()) {
            jdbcUrl = String.format("jdbc:postgresql://%s:%s/%s?sslmode=require&ssl=true",
                System.getenv("PGHOST"),
                System.getenv("PGPORT"),
                System.getenv("PGDATABASE"));
        }
        
        dataSource.setUrl(jdbcUrl);
        dataSource.setUsername(System.getenv("PGUSER"));
        dataSource.setPassword(System.getenv("PGPASSWORD"));
        
        return dataSource;
    }
}