/**
 * 
 */
package andrzej.appDemo.user;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

/**
 * Tworzymy encie ktore służą za konfiguacje dla hibernate aby ten mógł sam sobie 
 * tworzyc sql 
 * Aby to sie stalo - ta klasa musi mu wytłumaczyć co jest w danej bazie itp 
 * 
 * Entity daje znac hibernatowi ze ta klasa odpowiada tablicy w bazie danych 
 * dzieki adnotacji table name hibernate bedzie wiedziec z jaką bazą ma doczynienia
 * 
 * 
 * wzorzec do tablic w sql 
 * - rozpisujemy zmienne / kolumy 
 * - rozpisujemy klucz głowny 
 * - rozpisujemy łaczenie z inna tablica 
 * @author BartoszGurgul
 *
 */
@Entity
@Table(name = "user")
public class User {
	
	// Tworzymy kolumny w bazie danych 
	// adnotacja id wskazujemy co jest kluczem głównym ktory musi byc autoinkrementowane
	// generate value wstazuje strategie - generateType.auto - czyli autoinkrementacja wystepuje w bazie 
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_id")
	private int id;
	
	// nazwa w bazie - oraz wartosc nie moze byc nullem 
	// @email - walidacja emaila 
	@Column(name = "email")
	@NotNull
	private String email;
	
	/**
	 * takie małe walidacje 
	 * @Length(min = 5, message = "Hasło za słabe")
	 */
	@Column(name = "password")
	@NotNull
	private String password;
	
	@Column(name = "name")
	@NotNull
	private String name;
	
	@Column(name = "last_name")
	@NotNull
	private String lastName;
	
	@Column(name = "active")
	@NotNull
	private int active;
	
	// kolekcja do encji roli 
	/**
	 * many to many okreslmy relacje wiele do wielu 
	 * lączymy tablice z user role - nie musimy tworzyc jej encji poniewaz tablica zajmuje sie tylko 
	 * przetrzymywaniem klucz - sama w sobie nie przechowuje zadnej tresci 
	 * 
	 */
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable( 
			name = "user_role", 
			joinColumns = @JoinColumn(name = "user_id")
			, inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles;

	/**
	 * posłuży nam żeby sie dopwiedziec czy jestemy w trybie edycji czy odczytu
	 * pole nie wysyepuje w bazie danych - przez cos - bez adnotacji Transient 
	 * dzieki temu hibernate ominie to podczas łaczenia z baza danych 
	 * heheh transient to slowo kluczone z serializajci ;) 
	 * 
	 */
	@Transient
	private String operacja;
	
	
	/**
	 * @return the operacja
	 */
	public String getOperacja() {
		return operacja;
	}

	/**
	 * @param operacja the operacja to set
	 */
	public void setOperacja(String operacja) {
		this.operacja = operacja;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the active
	 */
	public int getActive() {
		return active;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(int active) {
		this.active = active;
	}

	/**
	 * @return the roles
	 */
	public Set<Role> getRoles() {
		return roles;
	}

	/**
	 * @param roles the roles to set
	 */
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + active;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + id;
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((operacja == null) ? 0 : operacja.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((roles == null) ? 0 : roles.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (active != other.active)
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (id != other.id)
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (operacja == null) {
			if (other.operacja != null)
				return false;
		} else if (!operacja.equals(other.operacja))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (roles == null) {
			if (other.roles != null)
				return false;
		} else if (!roles.equals(other.roles))
			return false;
		return true;
	}

	
	
	

}
