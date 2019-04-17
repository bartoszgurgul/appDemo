package appdemo.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import appdemo.entity.Role;
import appdemo.entity.User;
import appdemo.repository.RoleRepository;
import appdemo.repository.UserRepository;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	/*
	 * ta metoda zwraca nam calego obiekt usera wyszukanego po email 
	 * dzieki tej metodzie mozemy sobie pracowac na te
	 * (non-Javadoc)
	 * @see andrzej.appDemo.user.UserService#findUserByEmail(java.lang.String)
	 */
	@Override
	public User findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public void saveUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setActive(1);
		
		Role role = roleRepository.findByRole("ROLE_ADMIN");
		//TODO tutaj powinno być poprawione
		user.setRoles(new HashSet<Role>(Arrays.asList(role)));

		userRepository.save(user);
	}

	@Override
	public void updateUserPassword(String newPassword, String email) {
		userRepository.updateUserPassword(bCryptPasswordEncoder.encode(newPassword), email);
		
	}

	@Override
	public void updateUserProfile(String newName, String lastName, String newEmail, Integer id) {
		userRepository.updateUserProfile(newName, lastName, newEmail, id);
		
	}
	/**
	 * ta metoda jest dostepna domyślnie w implementacji JpaRepository
	 */
	@Override
	public List<User> findAll() {
		List<User> userList = userRepository.findAll();
		
		return userList;
	}

}
