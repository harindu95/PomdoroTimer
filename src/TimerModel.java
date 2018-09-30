
public class TimerModel {
	private int hours = 0;
	private int mins = 0;
	private int secs = 0;
	TimerModel(int h, int m, int s){
		setHours(h);
		setMins(m);
		setSecs(s);
	}
	
	void setHours(int h) {
		if (h>=0)
			hours = h;
	}
	
	void setMins(int m) {
		if(m >=0 && m < 60) {
			mins = m;
		}
	}
	
	void setSecs(int s) {
		if(s >=0 && s < 60) {
			secs = s;
		}
	}
	
	void decrementSecond() {
		if(secs > 0) {
			secs--;
		}else {
			if(mins > 0) {
				mins--;
				secs = 60;
				secs--;
			}else {
				if(hours > 0 ) {
					hours--;
					mins = 60;
					mins--;
					secs = 60;
					secs--;
				}else {
					throw new RuntimeException("Timer is done!");
				}
			}
		}
	}
	
	int getHours() {
		return hours;
	}
	
	int getMins() {
		return mins;
	}
	
	int getSecs() {
		return secs;
	}
	
	public String toString() {
		return String.format("%02d:%02d:%02d", hours, mins, secs);
	}
	
}
