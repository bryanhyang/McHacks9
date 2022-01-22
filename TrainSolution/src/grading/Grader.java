package grading;
import java.io.FileReader;
import java.util.ArrayDeque;
import java.util.Queue;

import com.opencsv.CSVReader;

import trainUtils.Train;

public class Grader {
	
	private int[][] pArrival = new int[19][3]; //number of passengers arriving at [10min interval][station]
	
	public Grader(String passengerCSV) {
		try{
			FileReader file = new FileReader(passengerCSV);
			CSVReader read = new CSVReader(file);
			String line[];
			int i = 0;
			while((line = read.readNext()) != null){
				int j = 0;
				for (String cell : line){
					this.pArrival[i][j] =  Integer.parseInt(cell);
					j+=1;
				}
				i+=1;
			}
		} catch(Exception e){
			System.out.println("file/directory not found");
		}
	}
	
	public String grade(Train[] schedule) {
		//O(4600)
		//TODO rather than store the passengers as an individual time, store the nubmer of passengers, keep track of arrival time, and subtract.
		int curTime = 0, timeWaited = 0;
		Queue<Integer> a = new ArrayDeque<>(), b = new ArrayDeque<>(), c = new ArrayDeque<>();
		for (int i = 0; i < pArrival.length; i++) {
			for (int j = 0; j < pArrival[i][0]; j++) {
				a.add(i*10);
			}
			for (int j = 0; j < pArrival[i][1]; j++) {
				b.add(i*10);
			}
			for (int j = 0; j < pArrival[i][2]; j++) {
				c.add(i*10);
			}
		}
		//iterate through both pArrival and schedule, 
		for (int i = 0; i < schedule.length; i++) {
			Train cur = schedule[i];
			for (int j = 0; j < schedule[i].aBoard; j++) {
				int wait = schedule[i].aArr - a.poll();
				assert wait >= 0;
				timeWaited += wait;
			}
			for (int j = 0; j < schedule[i].bBoard; j++) {
				int wait = schedule[i].bArr - b.poll();
				assert wait >= 0;
				timeWaited += wait;
			}
			for (int j = 0; j < schedule[i].cBoard; j++) {
				int wait = schedule[i].cArr - c.poll();
				assert wait >= 0;
				timeWaited += wait;
			}
		}
		
		assert a.isEmpty();
		assert b.isEmpty();
		assert c.isEmpty();
		
		double avgTimeWaited = timeWaited/4600.;
		String ans = "";
		ans += Integer.toString((int) avgTimeWaited) + ":" + Integer.toString( (int)((avgTimeWaited - (int) avgTimeWaited)*60));
		
		return ans;
	}
}
