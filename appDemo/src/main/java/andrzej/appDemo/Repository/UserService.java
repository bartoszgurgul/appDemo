package andrzej.appDemo.Repository;

import andrzej.appDemo.Entity.User;

public interface UserService {
	public User findUserByEmail(String email);
	public void saveUser(User user);
	public void updateUserPassword(String newPassword, String email);

}
