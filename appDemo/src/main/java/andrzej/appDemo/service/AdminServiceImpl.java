package andrzej.appDemo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import andrzej.appDemo.Entity.User;
import andrzej.appDemo.Repository.AdminRepository;

@Service("adminService")
@Transactional
public class AdminServiceImpl implements AdminService {

	private static final Logger LOG = LoggerFactory.getLogger(AdminServiceImpl.class);
	
	@Autowired
	private AdminRepository adminRepository;
	
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
}
