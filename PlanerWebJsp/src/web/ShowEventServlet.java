package web;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Event;
import rzk.PlannerBeanRemote;

/**
 * Servlet implementation class ShowEventServlet
 */
@WebServlet("/ShowEventServlet")
public class ShowEventServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowEventServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PlannerBeanRemote bean = (PlannerBeanRemote) request.getSession().getAttribute("plannerBean");
		if (bean == null) {
			try {
				InitialContext ic = new InitialContext();
				bean = (PlannerBeanRemote) ic
						.lookup("ejb:PlanerEAR/PlanerEJB/PlannerBean!rzk.PlannerBeanRemote?statefull");

				request.getSession().setAttribute("plannerBean", bean);
			} catch (NamingException e) {
				e.printStackTrace();
			}
		}
		//KOJI FORMAT
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date selectedDate;
		try {
			//Da li ovde treba da se doda vreme 00:00
			selectedDate = format.parse(request.getParameter("eventsDate"));
			List<Event> eventResults = bean.searchEvents(selectedDate);
			if(eventResults == null) {
				request.getRequestDispatcher("error.jsp").forward(request, response);
			}else if(eventResults.isEmpty()){
				request.setAttribute("searchStatus", "There are no events for selected date!");
				request.getRequestDispatcher("nova-stranica.jsp").forward(request, response);
			}else {
				request.setAttribute("eventResults", eventResults);
				request.getRequestDispatcher("nova-stranica.jsp").forward(request, response);
			}
			
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
