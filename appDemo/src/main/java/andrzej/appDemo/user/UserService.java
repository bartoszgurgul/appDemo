package andrzej.appDemo.user;

public interface UserService {
	public User findUserByEmail(String email);
	public void saveUser(User user);

}
