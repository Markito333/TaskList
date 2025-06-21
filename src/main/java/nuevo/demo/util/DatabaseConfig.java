package nuevo.demo.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.sql.DataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;

@Configuration
public class DatabaseConfig {
    
    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder.create()
            .url("jdbc:postgresql://" + System.getenv("PGHOST") + ":" + 
                 System.getenv("PGPORT") + "/" + 
                 System.getenv("PGDATABASE") + "?sslmode=require")
            .username(System.getenv("PGUSER"))
            .password(System.getenv("PGPASSWORD"))
            .driverClassName("org.postgresql.Driver")
            .build();
    }
}