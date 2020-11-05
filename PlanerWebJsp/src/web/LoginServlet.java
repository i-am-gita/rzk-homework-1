package web;

import java.io.IOException;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import rzk.PlannerBean;
import rzk.PlannerBeanRemote;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*
	 * private Context initialContext;
	 * 
	 * private Context getInitialContext() throws NamingException { if
	 * (initialContext == null) { initialContext = new InitialContext(); }
	 * return initialContext; }
	 * 
	 * private String getLookupName() { final String appName = "PlanerEar";
	 * final String moduleName = "PlanerEJB"; final String distinctName = "";
	 * final String beanName = PlannerBean.class.getSimpleName(); final String
	 * interfaceName = PlannerBeanRemote.class.getName(); String lookUp = "ejb:"
	 * + appName + "/" + moduleName + "/" + distinctName + "/" + beanName + "!"
	 * + interfaceName + "?stateful"; return lookUp; }
	 * 
	 * private PlannerBeanRemote doLookup() { Context context = null;
	 * PlannerBeanRemote hbr = null; try { context = getInitialContext(); String
	 * lookUpName = getLookupName(); hbr =
	 * (PlannerBeanRemote)context.lookup(lookUpName); } catch (Exception e) {
	 * e.printStackTrace(); }
	 * 
	 * return hbr; }
	 */
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// PlannerBeanRemote pbr = doLookup();
		// moze onako kao sto je sve zakomentarisano, ili kao sto je ovde dole
		// uradjeno

		PlannerBeanRemote bean = (PlannerBeanRemote) request.getSession().getAttribute("plannerBean");
		if (bean == null) {
			try {
				InitialContext ic = new InitialContext();
				bean = (PlannerBeanRemote) ic
						.lookup("ejb:PlanerEAR/PlanerEJB/PlannerBean!rzk.PlannerBeanRemote?stateful");

				request.getSession().setAttribute("plannerBean", bean);
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		String username = request.getParameter("username");
		String pass = request.getParameter("password");
		String email = bean.login(username, pass);
		if (email.equals("")) {
			request.getRequestDispatcher("error.jsp").forward(request, response);
		} else {
			request.setAttribute("bean", bean);
			request.setAttribute("email", email);
			
			request.setAttribute("eventTypes", bean.getTypes());
			
			//nakon toga se u ostalim servletima, koriti 
			//BeanRemoteInterface bri = (BeanRemoteInterface) request.getAttribute("bean");
			//gde je BeanRemoteInterface samo primer kako nam se zove RemoteInterface
			//isto tako dobavljamo npr. email
			//String mail = (String) request.getAttribute("email");
			request.getRequestDispatcher("nova-stranica.jsp").forward(request, response);
		}

	}

}
