package robot.controllers.banter;

import java.io.IOException;

public class VoiceController {
	private static VoiceController __instance = null;

	public static VoiceController getInstance() {
		if (__instance == null) {
			__instance = new VoiceController();
		}
		return __instance;
	}

	public VoiceController() {

	}

	public synchronized void speak(String sayWhat) {
		try {
			sayWhat = sayWhat.replaceAll("&", " ");
			sayWhat = sayWhat.replaceAll("\"", " ");
			ProcessBuilder pb = new ProcessBuilder("bash", "-c",
					"espeak -v en-us \"" + sayWhat + "\"");
			// pb.redirectErrorStream(true); //Outputs to stderr in-case of
			// Error
			Process shell = null;
			try {
				shell = pb.start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
