package appdemo.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import appdemo.entity.Role;
import appdemo.entity.User;
import appdemo.repository.AdminRepository;
import appdemo.repository.RoleRepository;

@Service("adminService")
@Transactional
public class AdminServiceImpl implements AdminService {

	private static final Logger LOG = LoggerFactory.getLogger(AdminServiceImpl.class);
	
	@Autowired
	private AdminRepository adminRepository;
	
	@Autowired
	private JpaContext jpaContext;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public Page<User> findAll(Pageable pageable) {
		LOG.info("Wywołanie AdminService");
		return adminRepository.findAll(pageable);
	}

	@Override
	public User findUserById(int id) {
		User user = adminRepository.findUserById(id);
		return user;
	}

	@Override
	public void updateUser(int userId, int nrRoli, int active) {
		adminRepository.updateActiveUser(active, userId);
		adminRepository.updateRoleUser(nrRoli, userId);
		
	}

	@Override
	public Page<User> findAllUser(String param, Pageable pageable) {
		
		return adminRepository.findAllUser(param, pageable);
	}

	@Override
	public void insertInBatch(List<User> userList) {
		/**	pokazujemy z która encja mamny doczynienia 
		 * 
		 */
		EntityManager em = jpaContext.getEntityManagerByManagedType(User.class);
		
		
		
		for (int i = 0; i < userList.size(); i ++) {
			User user = userList.get(i);
			
			Role role = roleRepository.findByRole("ROLE_USER");
			user.setRoles(new HashSet<Role>(Arrays.asList(role)));
			user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
			// tutaj mamy przygotowana porcje danych 
			
			em.persist(user);
			if(i % 50 == 0 && i > 0) {
				em.flush();
				em.clear();
				System.out.println("**** Załadowano " + i + " rekordów z " + userList.size() );
			}
		}
		
	}
	
	public void saveAll(List<User> userList) {
		for (int i = 0; i < userList.size(); i++) {
            Role role = roleRepository.findByRole("ROLE_USER");
            userList.get(i).setRoles(new HashSet<Role>(Arrays.asList(role)));
			userList.get(i).setPassword(bCryptPasswordEncoder.encode(userList.get(i).getPassword()));
		}
		adminRepository.saveAll(userList);
}

	@Override
	public void deleteUserById(int id) {
		LOG.info(" Start AdminServiceImpl.deleteUserById >> parametr " + id);
		
		LOG.debug("Wywołanie AdminService.deleteUserFromUserRole > param " + id);
		adminRepository.deleteUserFromUserRole(id);
		LOG.debug("Wywołanie AdminService.deleteUserFromUser > param " + id);
		adminRepository.deleteUserFromUser(id);
		
		
		
	}
}
