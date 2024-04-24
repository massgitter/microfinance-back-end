package et.com.act.microfinance;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@EnableScheduling
@SecurityScheme(name = "microservice", scheme = "bearer",bearerFormat = "JWT",type = SecuritySchemeType.HTTP, in = SecuritySchemeIn.HEADER)
public class MicrofinanceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicrofinanceApplication.class, args);
    }

}
