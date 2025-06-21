package nuevo.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EnableJpaRepositories("nuevo.demo.repository") // Asegura el escaneo de repositorios
@EntityScan("nuevo.demo.model") // Escanea las entidades
public class RunPW {
    public static void main(String[] args) {
        SpringApplication.run(RunPW.class, args);
    }
}