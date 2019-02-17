package andrzej.appDemo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import andrzej.appDemo.Entity.User;

public interface AdminService {
	
	Page<User> findAll(Pageable pageAble);

}
