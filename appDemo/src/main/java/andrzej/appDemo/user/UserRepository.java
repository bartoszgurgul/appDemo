/**
 * 
 */
package andrzej.appDemo.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

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
}
