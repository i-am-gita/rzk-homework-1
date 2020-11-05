package rzk;

import javax.ejb.Remote;

@Remote
public interface AccountBeanRemote {

	public String register(String firstname, String lastname, String email, String pass);

}
