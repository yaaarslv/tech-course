package catApplications;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "entities")
@EnableJpaRepositories(basePackages = "catRepositories")
public class CatMicroserviceApplication {
    public static void main(String[] args) {
        SpringApplication.run(CatMicroserviceApplication.class, args);
    }
}
