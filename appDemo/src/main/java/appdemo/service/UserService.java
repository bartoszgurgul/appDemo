package appdemo.service;



import java.util.List;

import appdemo.entity.User;

public interface UserService {
	public User findUserByEmail(String email);

	public void saveUser(User user);

	public void updateUserPassword(String newPassword, String email);

	public void updateUserProfile(String newName, String lastName, String newEmail, Integer id);

	public List<User> findAll();
	
	public void updateActivation(int activeCode, String activationCode);
}
