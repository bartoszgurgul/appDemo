package appdemo.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import appdemo.entity.User;

public interface AdminService {
	
	Page<User> findAll(Pageable pageAble);
	User findUserById(int id);
	void updateUser(int userId, int nrRoli, int active);
	Page<User> findAllUser(String param, Pageable pageable);
	void insertInBatch(List<User> userList);
	void saveAll(List<User> userList);
	void deleteUserById(int id);
}
