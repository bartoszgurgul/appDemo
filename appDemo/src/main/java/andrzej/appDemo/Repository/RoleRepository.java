/**
 * 
 */
package andrzej.appDemo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import andrzej.appDemo.Entity.Role;

/**
 * @author BartoszGurgul
 *
 */
@Repository("roleRepository")
public interface RoleRepository extends JpaRepository<Role, Integer> {
	
	public Role findByRole(String role);
}
