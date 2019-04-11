package appdemo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import appdemo.entity.User;

public interface AdminService {
	
	Page<User> findAll(Pageable pageAble);
	User findUserById(int id);
	void updateUser(int userId, int nrRoli, int active);
	Page<User> findAllUser(String param, Pageable pageable);
}
