package andrzej.appDemo.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Określny sposób dostepu do zasobów
 * @author BartoszGurgul
 *
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	/**
	 * to klasa która koduje i rozkoduje hasla
	 */
	@Autowired
	private BCryptPasswordEncoder bcp;
	
	/**
	 * dostep do bazy danych 
	 */
	@Autowired
	private DataSource ds;
	
	/**
	 * uzytkownik hasło i czy jest aktywny 
	 */
	@Value("${spring.queries.users-query}")
	private String usersQuery;
	
	/**
	 *  rola - okresla poziom udostepnienia zasobów 
	 */
	@Value("${spring.queries.roles-query}")
	private String rolesQuery;
	
	/**
	 * wyglada na to jest to konfiguracja logowania w bazie danych 
	 * autentykacja loguje sie za pomoca metody dns jdbc autentykacyjnego 
	 * querujac metoda userByUsernameQuery arg - usersQuery który zawiera login itp itd( aktywnosc ) 
	 * potem querje ametoda authoriesByUsernameQUery role 
	 * data source to dostep do bazy 
	 * i wysyla na konc do tego wszystkiego hasło 
	 * @throws Exception 
	 */
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
		.jdbcAuthentication()
		.usersByUsernameQuery(usersQuery)
		.authoritiesByUsernameQuery(rolesQuery)
		.dataSource(ds)
		.passwordEncoder(bcp);
	}
	/**
	 * sa to zasady ktore musza byc spelnione w przypadku wywolywania logowania do strony 
	 * httpSec - spring security - odpowiedzialny za bezpieczenstwo wywolania na stronie 
	 * prawdza roli 
	 * 
	 * authorizeRequests - to autoryzacja przy róznych wywoalaniach 
	 * i tak dla przykładu na samym poczatku 
	 * antmach .. permitAll jest dosteona dla kazdego 
	 * 
	 * jezeli bedzie zadanie gdzie bedzie admin - musimy sprawdzic role czy jest to 
	 * osoba z uprawnieniami admin
	 * 
	 * dalej linijka 
	 * anyRequest() mowi ze wszystko co nie zostało wczesniej uchwycone 
	 * musi byc potwierdzone autentykacja - tylko dla zalogowanych uzytkowników .authenticated()
	 * 
	 * 
	 * dalej okrelsamy formularz logownaia 
	 * formLogin()
	 * dalej wywołujemy strone loginPage
	 * co wiecej strona / metoda która finalnie przekieruje nas do strony loginPahe 
	 * zostanie wywołana kiedy bedziemy chceli uzyc URL który nie zostal wyzej wymieniony 
	 * albo nie mamy admina a chcemy cos adminiowego wywołać 
	 *  
	 *  jezeli logowanie bedzie niepoprawne wywolane bedzie 
	 *  failureURL bedzie wywolana strona login?error= true ale to chyba do wywoalania nie
	 *  poprawnego URL
	 *  
	 *  jezeli logowanie sie uda wywolujemuy 
	 *  defaultSuccecURL na strone "/" jezeli jego logownaie sie uda 
	 *  czyli poprawny email +  hasło 
	 *  
	 *   and logout reaquest macher - odpalamy antrequies macthec niszczy sesje i wrzuca nas na strone 
	 *   logout 
	 *   
	 *   ostatnia linika to wyjatek kiedy nie mamy uprawnien 
	 *   a gdzies chcemy sie wwbic mamy exceptionHandling wiec lecimy w /denied
	 */
	protected void configure(HttpSecurity httpSec) throws Exception {
		httpSec.authorizeRequests()
		.antMatchers("/").permitAll()
		.antMatchers("/login").permitAll()
		.antMatchers("/register").permitAll()
		.antMatchers("/adduser").permitAll()
		.antMatchers("/admin").hasAuthority("ROLE_ADMIN")
		.anyRequest().authenticated()
		.and().csrf().disable()
		.formLogin()
		.loginPage("/login")
		.failureUrl("/login?error=true")
		.defaultSuccessUrl("/").usernameParameter("email")
		.passwordParameter("password")
		.and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		.logoutSuccessUrl("/")
		.and().exceptionHandling().accessDeniedPage("/denied");
	}
	
	/**
	 * nie bedzie wymagac autentykacji w przypadku dosteou do tych katalogów 
	 * czyli całe programistyczne pierdoły 
	 */
	public void configure(WebSecurity webSec) {
		webSec.ignoring()
		.antMatchers("/resources/**"
				, "/statics/**", "/css/**", "/js/**", "/images/**", "/incl/**" );
	}
}
