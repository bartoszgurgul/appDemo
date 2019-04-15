package appdemo.utilities;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import appdemo.entity.User;

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
	
	public static List<User> userDataLoader(File file){
		
		List<User> userList = new ArrayList<>();
		
		try {
			DocumentBuilderFactory dbFactor = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactor.newDocumentBuilder();
			Document doc = dBuilder.parse(file);
			
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("user");
			for(int i = 0;i < nList.getLength();i++) {
				Node n = nList.item(i);
				if(n.getNodeType() == Node.ELEMENT_NODE) {
					Element e = (Element) n;
					
					User u = new User();
					u.setName(e.getElementsByTagName("name").item(0).getTextContent());
					u.setEmail(e.getElementsByTagName("email").item(0).getTextContent());
					u.setActive(
						Integer.valueOf(
								e.getElementsByTagName("active")
									.item(0)
									.getTextContent()));	
					u.setLastName(e.getElementsByTagName("lastname")
							.item(0).getTextContent());
					u.setNrRoli(Integer.valueOf(e.getElementsByTagName("nrroli")
							.item(0)
							.getTextContent()));
					u.setPassword(e.getElementsByTagName("password")
							.item(0)
							.getTextContent());
					userList.add(u);
					
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
			
		
		
		
		return userList;
	}

}
