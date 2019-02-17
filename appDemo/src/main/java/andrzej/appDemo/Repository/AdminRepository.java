package andrzej.appDemo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import andrzej.appDemo.Entity.User;

public interface AdminRepository extends JpaRepository<User, Integer> {
	
}
