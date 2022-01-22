package trainUtils;

public class Train {
	public TrainType type;
	public short aArr, bArr, cArr, aBoard, bBoard, cBoard;
	
	public Train(TrainType type, short aArr, short aBoard, short bArr, short bBoard, short cArr, short cBoard) {
		this.type = type;
		this.aArr = aArr;
		this.bArr = bArr;
		this.cArr = cArr;
		this.aBoard = aBoard;
		this.bBoard = bBoard;
		this.cBoard = cBoard;
	}
	
}
