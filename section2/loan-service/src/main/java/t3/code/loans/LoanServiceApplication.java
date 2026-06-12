package t3.code.loans;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
//@ComponentScan(basePackages = "t3.code.base.controller")
//@EnableJpaRepositories(basePackages = "t3.code.base.repository")
//@EntityScan(basePackages = "t3.code.base.entity")
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@OpenAPIDefinition(
        info = @Info(
                title = "Loans microservice REST API Documentation",
                version = "v1",
                description = "T3 Loans microservice REST API Documentation",
                contact = @Contact(
                        name = "Trần Tấn Tài",
                        email = "tai05122001@gmail.com"
                ),
                license = @License(
                        name = "Apache 2.0",
                        url = "https://trantantaiportfolio.lovable.app/"
                )
        ),
        externalDocs = @ExternalDocumentation(
                description = "T3 Loans microservice REST API Documentation",
                url = "https://trantantaiportfolio.lovable.app/"
        )
)
public class LoanServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoanServiceApplication.class, args);
    }

}
