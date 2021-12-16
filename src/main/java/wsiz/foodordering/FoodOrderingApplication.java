package wsiz.foodordering;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;
import wsiz.foodordering.infrastructure.SwaggerConfiguration;

@SpringBootApplication
@EnableScheduling
@Import(SwaggerConfiguration.class)
public class FoodOrderingApplication {

	public static void main(String[] args) {
		SpringApplication.run(FoodOrderingApplication.class, args);
	}

}
