package robot.jetty.webserver;

import javax.servlet.http.HttpServlet;

public class ServoController extends HttpServlet
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static ServoController __instance=null;
	public static ServoController getInstance(){
		if(__instance==null){
			__instance=new ServoController();
		}
		return __instance;
	}
	
	public void startMotorTest() throws Exception{
		        System.out.println("<--Pi4J--> GPIO Control Example ... started.");

		        System.out.println("wiring pi setup");
	}
}
