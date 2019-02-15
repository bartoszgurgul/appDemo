/**
 * 
 */
package andrzej.appDemo.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import andrzej.appDemo.Entity.User;

/**
 * @author BartoszGurgul
 *
 * jpaRepository to interfajs przyjmujacy dwa argumenty 
 * user - obiekt na ktorym pracujemy 
 * integer - to oznaczenie klucza głównego 
 * 
 * adnotacja repository - czego ma szukac
 */
@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Integer> {
	
	public User findByEmail(String email);

	@Modifying
	@Query("UPDATE User u SET u.password = :newPassword WHERE u.email= :email")
	public void updateUserPassword(@Param("newPassword") String password, @Param("email") String email);
	
}
