package trainUtils;

public class Train {
	public TrainType type;
	public int aArr, bArr, cArr, aBoard, bBoard, cBoard;
	
	public Train(TrainType type, int aArr, int aBoard, int bArr, int bBoard, int cArr, int cBoard) {
		this.type = type;
		this.aArr = aArr;
		this.bArr = bArr;
		this.cArr = cArr;
		this.aBoard = aBoard;
		this.bBoard = bBoard;
		this.cBoard = cBoard;
	}
	
}
