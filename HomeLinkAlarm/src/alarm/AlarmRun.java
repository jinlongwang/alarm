package alarm;

import util.DBUtil;
import util.Time;

public class AlarmRun {
	public static void main(String[] args) throws Exception{
		AlarmThread alarm = new AlarmThread("homelink");
		alarm.start();
	}

}
