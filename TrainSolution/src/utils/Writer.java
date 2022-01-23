package utils;
import java.io.FileWriter;
import java.io.IOException;

import trainUtils.*;

public class Writer {
	public static void write(Train[] t, String filename) {
		try {
			FileWriter csv = new FileWriter(filename);
			csv.append("TrainNum,TrainType,A_ArrivalTime,A_AvailCap,"
					+ "A_Boarding,B_ArrivalTime,B_AvailCap,B_Boarding,"
					+ "C_ArrivalTime,C_AvailCap,C_Boarding,U_Arrival,"
					+ "U_AvailCap,U_Offloading\n");
			for (int i = 0; i < t.length; i++) {
				Train cur = t[i];
				int bCap = cur.type.cap - cur.aBoard;
				int cCap = bCap - cur.bBoard;
				int uCap = cCap - cur.cBoard;
				int uArr = cur.aArr + 37;
				int uOff = cur.type.cap - uCap;
				
				csv.append(Integer.toString(i+1) + ",");
				if (cur.type == TrainType.L4) {
					csv.append("L4,");
				} else {
					csv.append("L8,");
				}
				csv.append(
				Parse.parseTime(cur.aArr) + "," + Integer.toString(cur.type.cap) + "," + Integer.toString(cur.aBoard) + "," +
				Parse.parseTime(cur.bArr) + "," + Integer.toString(bCap) + "," + Integer.toString(cur.bBoard) + "," + 
				Parse.parseTime(cur.cArr) + "," + Integer.toString(cCap) + "," + Integer.toString(cur.cBoard) + "," + 
				Parse.parseTime(uArr)     + "," + Integer.toString(uCap) + "," + Integer.toString(uOff)
				);
				csv.append('\n');
			}
			csv.flush();
			csv.close();
			System.out.println("done!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

class Parse {
	public static String parseTime(int t) {
		int a = t/60;
		int b = t%60;
		return Integer.toString(a) + ":" + Integer.toString(b);
	}
}
