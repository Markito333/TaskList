package nuevo.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;


@SpringBootApplication
@ServletComponentScan
public class RunPW {

    public static void main(String[] args) {

        SpringApplication.run(RunPW.class, args);
    }  

} 