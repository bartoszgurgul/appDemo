package appdemo.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import appdemo.entity.User;
import appdemo.service.AdminService;
import appdemo.utilities.UserUtilities;


/** 
 * zapisywanie to POST 
 * pobieranie GET
 * usuwanie delete
 * @author bgurgul
 *
 */
@Controller
public class AdminPageController {

	private static final Logger LOG = LoggerFactory.getLogger(MainPageController.class);
	private static int ELEMENTS = 5;
	
	@Autowired
	private AdminService adminService;

	@Autowired
	private MessageSource messageSource;
	
	
	@GET
	@RequestMapping(value = "/admin")
	@Secured(value = { "ROLE_ADMIN" })
	public String showAdminPage() {
		LOG.info("-----  Admin page opening  -----");
		return "admin/admin";

	}

	@GET
	@RequestMapping(value = "/admin/users/{page}")
	@Secured(value = "ROLE_ADMIN")
	public String openAdminAllUsersPage(@PathVariable("page") int page,  Model model) {
		System.out.println("getAllUsersPageable >>");
		Page<User> pages = getAllUsersPageable(page - 1, false, null);
		int totalPages = pages.getTotalPages();
		int currentPage = pages.getNumber();
		
		// aktualna zawartosc strony 
		List<User> userList = pages.getContent();
		
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("currentPage", currentPage+1);
		model.addAttribute("userList", userList);
		model.addAttribute("recordStartCounter", currentPage*ELEMENTS);
		return "admin/users";
	}

	@GET
	@RequestMapping( value = "/admin/users/edit/{id}")
	@Secured( value = "ROLE_ADMIN")
	public String getUserToEdit(Model model, @PathVariable("id") int id) {
		
		
		
		User user = adminService.findUserById(id);
		
		Map<Integer, String> roleMap = prepareRoleMap();
		
		Map<Integer, String> activityMap = prepareActivityMap();
		int role = user.getRoles().iterator().next().getId();
		user.setNrRoli(role);
		model.addAttribute("roleMap", roleMap);
		model.addAttribute("activityMap", activityMap);
		model.addAttribute("user", user);
		return "admin/useredit" ;
	}
	
	@POST
	@Secured(value="ROLE_ADMIN")
	@RequestMapping(value = "/admin/updateuser/{id}")
	public String updateUser(@PathVariable("id") int id, User user) {
		int nrRoli = user.getNrRoli();
		int czyAktywny = user.getActive();
		
		adminService.updateUser(id, nrRoli, czyAktywny);
		
		return "redirect:/admin/users/1";
	}
	
	@GET
	@RequestMapping(value = "/admin/users/search/{searchString}/{page}")
	@Secured(value = "ROLE_ADMIN")
	public String openSearchUserPage(@PathVariable("searchString") String searchString, 
									@PathVariable("page") int page,
									Model model) {
		Page<User> pages = getAllUsersPageable(page-1, true, searchString);
		int totalPages = pages.getTotalPages();
		int currentPage = pages.getNumber();
		List<User> userList = pages.getContent();
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("userList", userList);
		model.addAttribute("recordStartCounter", currentPage * ELEMENTS);
		model.addAttribute("searchWord", searchString);
		
		return "admin/usersearch";
	}

	@GET
	@RequestMapping(value = "/admin/users/importusers")
	public String showUploadPageFromXML(Model model) {
		return "admin/importusers";
	}
	
	@POST
	@RequestMapping(value ="/admin/users/upload")
	@Secured(value = "ROLE_ADMIN")
	public String importUsersFromXML(@RequestParam("filename") MultipartFile mFile) {
		
		String uploadDir = System.getProperty("user.dir") + "/uploads";
		
		File file;
		try {
			file = new File(uploadDir);
			if(!file.exists()) {
				file.mkdir();
			}
			
			Path fileAndPath = Paths.get(uploadDir, mFile.getOriginalFilename());
			Files.write(fileAndPath, mFile.getBytes());
			file = new File(fileAndPath.toString());
			List<User> userList = UserUtilities.userDataLoader(file);
			//adminService.insertInBatch(userList);
			adminService.saveAll(userList);
			file.delete();
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		return "redirect:/admin/users/1";
	}

	@DELETE
	@RequestMapping(value = "/admin/users/delete/{id}")
	@Secured(value = "ROLE_ADMIN")
	public String deleteUser(@PathVariable("id") int id) {
		LOG.info("Wywołanie AdminPageController.deleteUser");
		System.out.println("Start deleteUser");
		adminService.deleteUserById(id);
		return "redirect:/admin/users/1";
		
	}
	
	
	private Map<Integer, String> prepareRoleMap() {
		Locale locale = Locale.getDefault();
		Map<Integer, String> roleMap = new HashMap<>();
		roleMap.put(1, messageSource.getMessage("word.admin", null, locale));
		roleMap.put(2, messageSource.getMessage("word.user", null, locale));
		return roleMap;
	}
	
	private Map<Integer, String> prepareActivityMap() {
		Locale locale = Locale.getDefault();
		Map<Integer, String> activityMap = new HashMap<>();
		activityMap.put(1, messageSource.getMessage("word.tak", null, locale));
		activityMap.put(2, messageSource.getMessage("word.nie", null, locale));
		return activityMap;
	}

	private Page<User> getAllUsersPageable(int page, boolean isSearching, String param){
		Page<User> pages;
		if(!isSearching) {
			
			pages = adminService.findAll(PageRequest.of(page, ELEMENTS));
			
		}else {
			pages = adminService.findAllUser(param, PageRequest.of(page, ELEMENTS));
		}
		
		for (User users : pages) {
			
			int numerRoli = users.getRoles().iterator().next().getId();
			users.setNrRoli(numerRoli);
		}
		return pages;
	}
}
