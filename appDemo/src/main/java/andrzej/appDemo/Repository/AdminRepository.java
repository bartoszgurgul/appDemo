package andrzej.appDemo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import andrzej.appDemo.Entity.User;


@Repository("adminRepository")
public interface AdminRepository extends JpaRepository<User, Integer> {

	User findUserById(int id);
	
}
