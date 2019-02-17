package andrzej.appDemo.service;



import java.util.List;

import andrzej.appDemo.Entity.User;

public interface UserService {
	public User findUserByEmail(String email);

	public void saveUser(User user);

	public void updateUserPassword(String newPassword, String email);

	public void updateUserProfile(String newName, String lastName, String newEmail, Integer id);

	public List<User> findAll();
}
