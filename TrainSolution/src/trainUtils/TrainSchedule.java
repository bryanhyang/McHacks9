package trainUtils;
import java.io.*;

public class TrainSchedule {
	public static Train[] parseFile(String file) throws FileNotFoundException, IOException {
		Train[] t = new Train[16];
		String row;
	
		int i = 0;
		BufferedReader csvReader = new BufferedReader(new FileReader(file));
		csvReader.readLine(); //clear column headings
		while ((row = csvReader.readLine()) != null) {
		    String[] d = row.split(",");
		    TrainType cur;
		    if (d[1] == "L4") {
		    	cur = TrainType.L4;
		    } else {
		    	cur = TrainType.L8;
		    }
		    t[i] = new Train(cur, 
		    		CurTime.parseTime(d[2]), Integer.parseInt(d[4]),
		    		CurTime.parseTime(d[5]), Integer.parseInt(d[7]),
		    		CurTime.parseTime(d[8]), Integer.parseInt(d[10]) 
		    );
		    i++;
		}
		csvReader.close();
		return t;
	}
	
}

class CurTime {
	public static short parseTime(String s) {
		//parses string of format h:ss and returns it converted to minutes since 7:00
		String[] split = s.split(":");
		return (short) ((short) (Integer.parseInt(split[0]) - 7)*60 + Integer.parseInt(split[1]));
	}
}
