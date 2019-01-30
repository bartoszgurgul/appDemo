package andrzej.appDemo;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * Jest to klasa do inicjalizacji aplikacji 
 * pokazuje spirinkowi ze konfiguruhje strone 
 * 
 * W klasie AppDemoApplication dzieki adnotacji ComponentScan - znajdzie adnotacjie 
 * Configuration.
 * @author BartoszGurgul
 *
 */
@Configurable
public class ServletInitializer extends SpringBootServletInitializer {

	/**
	 * Buduje nam aplikacje - zwraca zrodło 
	 * 
	 */
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(AppDemoApplication.class);
	}
}
