package andrzej.appdemo.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import andrzej.appdemo.entity.User;


@Repository("adminRepository")
public interface AdminRepository extends JpaRepository<User, Integer> {

	User findUserById(int id);
	
	@Modifying
	@Query(value = "UPDATE User u SET u.active = :active WHERE u.id = :user_id")
	void updateActiveUser(@Param("active") int active, @Param("user_id") int user_id);
	
	
	/**
	 * Tablica do której piszemy zapytanie nie posiada swojego odpowiednika w 
	 * enciki przez co Hibernate sobie tutaj nie porodzi 
	 * piszemy surowego SQL i dodajemy paramter nativeQuery. Jednak bez wsparcia Hibernate 
	 * nie jest to polecane 
	 * @param role_id
	 * @param user_id
	 */
	@Modifying
	@Query(value = "UPDATE user_role r SET r.role_id = :role_id "
			+ "WHERE r.user_id = :id", nativeQuery=true)
	void updateRoleUser(@Param("role_id") int role_id, @Param("id") int id);
	
	@Query(value = "SELECT * from User u WHERE "
			+ "u.name LIKE %:param% "
			+ "OR u.last_name LIKE %:param% "
			+ "OR u.email LIKE %:param%", nativeQuery = true)
	Page<User> findAllUser(@Param("param") String param, Pageable pageable);
}
