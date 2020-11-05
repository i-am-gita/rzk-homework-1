package rzk;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import model.User;

/**
 * Session Bean implementation class AccountBean
 */
@Stateless
@LocalBean
public class AccountBean implements AccountBeanRemote {
	
	@PersistenceContext
	EntityManager em;

    /**
     * Default constructor. 
     */
    public AccountBean() {
        // TODO Auto-generated constructor stub
    }
    

    @Override
	public String register(String firstName, String lastName, String email, String pass) {
		if(firstName != null && lastName != null && email != null && pass != null) {
			User newUser = new User();
			newUser.setEmail(email);
			newUser.setFirstName(firstName);
			newUser.setLastName(lastName);
			newUser.setPassword(pass);
			em.persist(newUser);
			return "Registration successful";
		}
		return null;
	}
    
    @PostConstruct
	public void connect(){
		System.out.println("AccountBean has been created!");
	}
	
	@PreDestroy
	public void cleanup(){
		System.out.println("AccountBean has been destroyed!");
	}

}
