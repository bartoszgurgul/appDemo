package andrzej.appdemo.utilities;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserUtilities {
		
	
	private UserUtilities() {
		
	}
	public static String getLoggerUser() {
		String userName = null;
		
		// przechwycenie kontekstu - z springSecurity 
		// w jego wnetrzu powinny yc dane o zalogownym userze
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(!(auth instanceof AnonymousAuthenticationToken)) {
			userName = auth.getName();
		}
		
		return userName;
	}

}
