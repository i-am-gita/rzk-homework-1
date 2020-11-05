package web;

import java.io.IOException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import rzk.AccountBeanRemote;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
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

				AccountBeanRemote bean = (AccountBeanRemote) request.getSession().getAttribute("bean");
				if (bean == null) {
					try {
						InitialContext ic = new InitialContext();
						bean = (AccountBeanRemote) ic
								.lookup("ejb:PlanerEAR/PlanerEJB/AccountBean!rzk.AccountBeanRemote?stateless");

						request.getSession().setAttribute("bean", bean);
					} catch (NamingException e) {
						e.printStackTrace();
					}
				}

				String firstname = request.getParameter("firstname");
				String lastname = request.getParameter("lastname");
				String email = request.getParameter("email");
				String pass = request.getParameter("password");
				String registrationStatus = bean.register(firstname, lastname, email, pass);
				if (registrationStatus == null) {
					request.getRequestDispatcher("error.jsp").forward(request, response);
				} else {
					request.getRequestDispatcher("index.jsp").forward(request, response);
				}
	}

}
