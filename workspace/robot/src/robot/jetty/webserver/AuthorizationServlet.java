package robot.jetty.webserver;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthorizationServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	public AuthorizationServlet(){
    	
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println("<h1>"+"It's ALIVE!"+"</h1>");
        response.getWriter().println("session=" + request.getSession(true).getId());
    }
}
