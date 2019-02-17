package andrzej.appDemo.mainController;

import java.util.List;

import javax.ws.rs.GET;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import andrzej.appDemo.Entity.User;
import andrzej.appDemo.service.AdminService;
import andrzej.appDemo.service.UserService;

@Controller
public class AdminPageController {

	private static final Logger LOG = LoggerFactory.getLogger(MainPageController.class);

	@Autowired
	private AdminService adminService;

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
		Page<User> pages = getAllUsersPageable(page);
		int totalPages = pages.getTotalPages();
		int currentPage = pages.getNumber();
		
		// aktualna zawartosc strony 
		List<User> userList = pages.getContent();
		
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("currentPage", currentPage+1);
		model.addAttribute("userList", userList);
		return "admin/users";
	}

	
	private Page<User> getAllUsersPageable(int page){
		int elementsOnPage = 5;
		Page<User> pages = adminService.findAll(PageRequest.of(page, elementsOnPage));
				for (User users : pages) {
					int numerRoli = users.getRoles().iterator().next().getId();

					users.setNrRoli(numerRoli);

				}
		return pages;
	}
}
