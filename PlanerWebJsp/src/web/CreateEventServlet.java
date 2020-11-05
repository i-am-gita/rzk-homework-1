package web;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import rzk.PlannerBeanRemote;

/**
 * Servlet implementation class CreateEventServlet
 */
@WebServlet("/CreateEventServlet")
public class CreateEventServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateEventServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date fromDate;
		Date toDate;
		try {
			fromDate = format.parse(request.getParameter("fromDate") + " " + request.getParameter("fromTime"));
			toDate = format.parse(request.getParameter("toDate") + " " + request.getParameter("toTime"));
			String description = request.getParameter("description");
			int eventTypeID = Integer.parseInt(request.getParameter("eventType"));
			
			boolean eventCreationStatus = bean.createEvent(description, fromDate, toDate, eventTypeID);
			if (!eventCreationStatus) {
				request.getRequestDispatcher("error.jsp").forward(request, response);
			} else {
				request.getRequestDispatcher("nova-stranica.jsp").forward(request, response);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

}
