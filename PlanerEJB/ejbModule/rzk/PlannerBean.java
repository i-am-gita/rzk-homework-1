package rzk;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import model.Event;
import model.EventType;
import model.User;

/**
 * Session Bean implementation class PlannerBean
 */
@Stateful
@LocalBean
public class PlannerBean implements PlannerBeanRemote {

	@PersistenceContext
	EntityManager em;
	
	User loggedUser;
	
    /**
     * Default constructor. 
     */
    public PlannerBean() {
        // TODO Auto-generated constructor stub
    }

	@Override
	public String login(String user, String pass) {
		Query q = em.createQuery("SELECT u FROM User u WHERE u.email LIKE :user AND u.password LIKE :pass");
		q.setParameter("user", user);
		q.setParameter("pass", pass);
		List<User> users = q.getResultList();
		System.out.println(users.size());
		try {
			loggedUser = users.get(0);
			return  users.get(0).getEmail();
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return "";
	}

	@Override
	public boolean createEvent(String description, Date fromDate, Date toDate, int eventTypeID) {
		if(description != null && fromDate != null && toDate != null) {
			Query q = em.createQuery("SELECT e FROM EventType e WHERE e.id = :id");
			q.setParameter("id", eventTypeID);
			EventType et = (EventType) q.getSingleResult();
			
			
			Event newEvent = new Event();
			newEvent.setDescription(description);
			newEvent.setFromDate(fromDate);
			newEvent.setToDate(toDate);
			newEvent.setEventType(et);
			newEvent.setUser(loggedUser);
			em.persist(newEvent);
			
			return true;
		}
		return false;
	}

	@Override
	public List<EventType> getTypes() {
		Query q = em.createQuery("SELECT e FROM EventType e");
		List<EventType> eventTypes = q.getResultList();
		try {
			return  eventTypes;
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return null;
	}

	@Override
	public List<Event> searchEvents(Date date) {
		
		//Stavljanje granice za datum jer ako u upitu stavimo uslov fromDate > selectedDate to obuhvata sve datume nakon tog, a to ne zelimo
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DAY_OF_MONTH, 1);
		Date dateLimit = c.getTime();
		
		Query q = em.createQuery("SELECT e FROM Event e WHERE e.fromDate > :selectedDate AND e.user = :loggedUser AND e.fromDate < :limitDate");
		q.setParameter("selectedDate", date);
		q.setParameter("loggedUser", loggedUser);
		q.setParameter("limitDate", dateLimit);
		
		List<Event> events = q.getResultList();
		return events;
	}
	
	@PostConstruct
	public void connect(){
		System.out.println("PlannerBean has been created!");
	}
	
	@PreDestroy
	public void cleanup(){
		System.out.println("PlannerBean has been destroyed!");
	}

}
