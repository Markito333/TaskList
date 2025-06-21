package nuevo.demo.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import javax.sql.DataSource;

@Configuration
public class RailwayDatabaseConfig {

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        
        // 1. Intenta con JDBC_DATABASE_URL (formato completo de Railway)
        String jdbcUrl = System.getenv("JDBC_DATABASE_URL");
        
        // 2. Si no existe, construye la URL con variables individuales
        if (jdbcUrl == null || jdbcUrl.isEmpty()) {
            jdbcUrl = String.format(
                "jdbc:postgresql://%s:%s/%s?sslmode=require&ssl=true",
                System.getenv("DB_HOST"),
                System.getenv("DB_PORT"),
                System.getenv("DB_NAME")
            );
        }
        
        dataSource.setUrl(jdbcUrl);
        dataSource.setUsername(System.getenv("DB_USER"));
        dataSource.setPassword(System.getenv("DB_PASSWORD"));
        dataSource.setDriverClassName("org.postgresql.Driver");
        
        return dataSource;
    }
}