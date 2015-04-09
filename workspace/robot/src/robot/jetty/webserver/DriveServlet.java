package robot.jetty.webserver;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import robot.controllers.drive.DriveController;

public class DriveServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	public DriveServlet(){
    	
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
    	String direction=request.getParameter("direction");
    	processDriveRequest(direction);
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println("session=" + request.getSession(true).getId());
    }
	private void processDriveRequest(String direction) {
		if(direction==null){
			return;
		}
    	if(direction.equals("forward")){
    		DriveController.getInstance().forward();
    	}
    	else if(direction.equals("reverse")){
    		DriveController.getInstance().reverse();
        }
    	else if(direction.equals("left")){
    		DriveController.getInstance().left();
        }
    	else if(direction.equals("right")){
    		DriveController.getInstance().right();
        }
    	else if(direction.equals("stop")){
    		DriveController.getInstance().allOff();
        }
	}
}
