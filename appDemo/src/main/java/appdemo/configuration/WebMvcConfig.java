package appdemo.configuration;

import javax.servlet.MultipartConfigElement;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * nasza strona jest według odelu MVC - model view controller 
 * takze dla tych powodów musimy teraz stworzyc konfiguracje 
 * @author BartoszGurgul
 *
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
		
	/**
	 * tworzymy hasło 
	 * @return
	 */
	@Bean
	public BCryptPasswordEncoder pwdEncrypt() {
		BCryptPasswordEncoder bcp = new BCryptPasswordEncoder();
		return bcp;
	}

	@Bean 
	public MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		factory.setMaxFileSize("5MB");
		factory.setMaxRequestSize("5MB");
		return factory.createMultipartConfig();
	}


}
