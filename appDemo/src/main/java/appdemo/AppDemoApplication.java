package appdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
public class AppDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppDemoApplication.class, args);
	}

}

