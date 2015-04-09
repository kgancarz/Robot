package robot.controllers.drive;

import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;

import com.ftdi.BitModes;
import com.ftdi.FTD2XXException;
import com.ftdi.FTDevice;
import com.ftdi.Parity;
import com.ftdi.StopBits;
import com.ftdi.WordLength;

public class DriveController extends HttpServlet {

	public static final long MOTOR_TIMER_LENGTH = 400;

	private static final long serialVersionUID = 1L;
	private static DriveController __instance = null;
	private long startTime = 0;
	private FTDevice device = null;
	private DriveState currentState = DriveState.STOP;

	public static DriveController getInstance() {
		if (__instance == null) {
			__instance = new DriveController();
		}
		return __instance;
	}
	
	public DriveController(){
		loadFTDIDevice();
	}

	public synchronized void allOff() {
		System.out.println("Drive Controller: STOP");

		boolean relayStates[] = { false, false, false, false, false, false,
				false, false };
		setRelayStates(relayStates);
		setCurrentState(DriveState.STOP);
	}

	public synchronized DriveState getCurrentState() {
		return currentState;
	}

	public synchronized void setCurrentState(DriveState state) {
		this.currentState = state;
	}

	public synchronized void setRelayStates(boolean[] relayStates) {
		if(device==null){
			loadFTDIDevice();
		}
		int value = 0;
		for (int i = 0; i < relayStates.length; i++) {
			if (relayStates[i])
				value += (1 << i);
		}
		try {
			device.open();
			device.write(value);
			device.close();
		} catch (FTD2XXException e) {
			e.printStackTrace();
		}
	}

	public synchronized void forward() {
		System.out.println("Drive Controller: FORWARD");

		boolean relayStates[] = { true, false, true, false, false, false,
				false, false };
		if (!getCurrentState().equals(DriveState.FORWARD)) {
			allOff();
			setRelayStates(relayStates);
			setCurrentState(DriveState.FORWARD);
		}
		long currentTime = System.currentTimeMillis();
		new DriveController.DriveTimer(currentTime);
	}

	public synchronized void reverse() {
		System.out.println("Drive Controller: REVERSE");

		boolean relayStates[] = { false, true, false, true, false, false,
				false, false };
		if (!getCurrentState().equals(DriveState.REVERSE)) {
			allOff();
			setRelayStates(relayStates);
			setCurrentState(DriveState.REVERSE);
		}
		long currentTime = System.currentTimeMillis();
		new DriveController.DriveTimer(currentTime);
	}

	public synchronized void left() {
		System.out.println("Drive Controller: LEFT");

		boolean relayStates[] = { true, false, false, true, false, false,
				false, false };
		if (!getCurrentState().equals(DriveState.LEFT)) {
			allOff();
			setRelayStates(relayStates);
			setCurrentState(DriveState.LEFT);
		}
		long currentTime = System.currentTimeMillis();
		new DriveController.DriveTimer(currentTime);

	}

	public synchronized void right() {
		System.out.println("Drive Controller: RIGHT");

		boolean relayStates[] = { false, true, true, false, false, false,
				false, false };
		if (!getCurrentState().equals(DriveState.RIGHT)) {
			allOff();
			setRelayStates(relayStates);
			setCurrentState(DriveState.RIGHT);
		}
		long currentTime = System.currentTimeMillis();
		new DriveController.DriveTimer(currentTime);

	}

	public synchronized void loadFTDIDevice() {
		try {
			removeUSBKernelModules();
			closeFTDIDevice();
			List<FTDevice> fTDevices;
			fTDevices = FTDevice.getDevices();
			for (FTDevice device : fTDevices) {
				System.out.println("fTDevice:" + device);
				System.out.println("fTDevice.DevType:" + device.getDevType());
				System.out.println("fTDevice.DevID:" + device.getDevID());
				System.out.println("fTDevice.DevLocationID:"
						+ device.getDevLocationID());

				device.open();
				device.setBaudRate(921600);
				device.setDataCharacteristics(WordLength.BITS_8,
						StopBits.STOP_BITS_1, Parity.PARITY_NONE);
				// fTDevice.setTimeouts(1000, 1000);

				device.setBitMode((byte) 0xFF, BitModes.BITMODE_SYNC_BITBANG);
				this.device = device;
			}

		} catch (FTD2XXException ex) {
			ex.printStackTrace();
		}
	}

	private synchronized void removeUSBKernelModules() {
		ProcessBuilder pb = new ProcessBuilder("bash", "-c", "sudo rmmod ftdi_sio");
		//pb.redirectErrorStream(true); //Outputs to stderr in-case of Error
		Process shell = null;
		try {
			shell = pb.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
		ProcessBuilder pb2 = new ProcessBuilder("bash", "-c", "sudo rmmod usbserial");
		//pb2.redirectErrorStream(true); //Outputs to stderr in-case of Error
		Process shell2 = null;
		try {
			shell2 = pb2.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public synchronized void closeFTDIDevice() {
		try {
			if(device!=null){
				device.close();
			}

		} catch (FTD2XXException ex) {
			ex.printStackTrace();
		}
	}

	public synchronized void startMotorTest() throws Exception {
		// System.out.println(fTDevice.getBitMode());
		forward();
		Thread.sleep(200);
		reverse();
		Thread.sleep(200);
		left();
		Thread.sleep(200);
		right();
		Thread.sleep(200);
		allOff();
	}

	public synchronized void setMotorStartTime(long currentTime) {
		this.startTime = currentTime;
	}

	public synchronized long getMotorStartTime() {
		return this.startTime;
	}

	public static class DriveTimer extends Timer {
		private final long timeStarted;

		public DriveTimer(long startTime) {
			this.timeStarted = startTime;
			DriveController.getInstance().setMotorStartTime(this.timeStarted);
			this.schedule(new TimerTask() {
				@Override
				public void run() {
					if (DriveController.getInstance().getMotorStartTime() == timeStarted) {
						DriveController.getInstance().allOff();
					}
				}

			}, MOTOR_TIMER_LENGTH);
		}
	}

	public enum DriveState {
		FORWARD, REVERSE, LEFT, RIGHT, STOP
	}
}
