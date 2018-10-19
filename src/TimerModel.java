
public class TimerModel {

	private int mins = 0;
	private int secs = 0;

	TimerModel(int m, int s) {

		setMins(m);
		setSecs(s);
	}

	void setMins(int m) {
		if (m >= 0) {
			mins = m;
		}
	}

	void setSecs(int s) {
		if (s >= 0 && s < 60) {
			secs = s;
		}
	}

	void decrementSecond() throws TimerDone {
		if (secs > 0) {
			secs--;
		} else {
			if (mins > 0) {
				mins--;
				secs = 60;
				secs--;
			} else {
				throw new TimerDone();
			}
		}
	}

	int getMins() {
		return mins;
	}

	int getSecs() {
		return secs;
	}

	public String toString() {
		return String.format("%02d:%02d", mins, secs);
	}

	void reset() {

		mins = 0;
		secs = 0;
	}

	void setTime(int m, int s) {
		setMins(m);
		setSecs(s);
	}

	public boolean isDone() {
		if ((secs + mins) == 0)
			return true;
		else
			return false;
	}

	public boolean isEqual(int m, int s) {
		if (m != mins || s != secs) {
			return false;
		}
		return true;
	}

}

class TimerDone extends Exception {

	TimerDone() {
		super("Time is done.");
	}
}
