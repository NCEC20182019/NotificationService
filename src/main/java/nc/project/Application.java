package nc.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.core.annotation.Order;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication(scanBasePackages = "nc.project")
@EnableSwagger2
@EnableOAuth2Sso
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
