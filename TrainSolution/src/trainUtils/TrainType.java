package trainUtils;

public enum TrainType {
	L4(200), L8(400);
	public int cap;
	private TrainType(int s) { this.cap = s;}
}
