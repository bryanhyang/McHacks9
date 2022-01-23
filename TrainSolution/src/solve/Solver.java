package solve;

import java.io.FileReader;
import java.util.ArrayDeque;
import java.util.Queue;

import com.opencsv.CSVReader;

import trainUtils.Train;
import trainUtils.TrainType;

public class Solver {
	private int[][] pArrival = new int[19][3]; //number of passengers arriving at [10min interval][station]
	
	public Solver(String passengerCSV) {
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
			read.close();
		} catch(Exception e){
			System.out.println("file/directory not found");
		}
	}
	
	public Train[] solve(TrainType[] types, int[] arrivals) {
		assert types.length == arrivals.length; //must be equal
		int acc = 0;
		
		Queue<Integer> qa = new ArrayDeque<>(), qb = new ArrayDeque<>(), qc = new ArrayDeque<>();
		for (int i = 0; i < pArrival.length; i++) {
			for (int j = 0; j < pArrival[i][0]; j++) {
				qa.add(i*10);
			}
			for (int j = 0; j < pArrival[i][1]; j++) {
				qb.add(i*10);
			}
			for (int j = 0; j < pArrival[i][2]; j++) {
				qc.add(i*10);
			}
		}
		
		Train[] schedule = new Train[types.length];
		for (int i = 0; i < types.length; i++) {
			int a = 0, b = 0, c = 0, pop = 0;
			int cap = types[i].cap;
			int aArr = arrivals[i];
			int bArr = aArr + 11;
			int cArr = aArr + 23;
			
			while (pop < cap) {
				int sa = 0, sb = 0, sc = 0; //score, ie wait time accounting for delay train takes in reaching station
				boolean ba = false, bb = false, bc = false;
				
				if (!qa.isEmpty() && qa.peek() <= aArr + 3) {
					sa = aArr - qa.peek();
					sa = sa > 0?sa:0; //must be >= 0
					ba = true;
				}
				if (!qb.isEmpty() && qb.peek() <= bArr + 3) {
					sb = bArr - qb.peek();
					sb = sa > 0?sb:0;
					bb = true;
				}
				if (!qc.isEmpty() && qc.peek() <= cArr + 3) {
					sc = cArr - qc.peek();
					sc = sc > 0?sc:0;
					bc = true;
				}
				if (!(ba || bb || bc)) break;
				int max = Math.max(sa, Math.max(sc, sb));
				acc += max;
				if (max == sa && ba) {
					a++;
					qa.poll();
				} else if (max == sb && bb) {
					b++;
					qb.poll();
				} else {
					c++;
					qc.poll();
				}
				pop++;
			}
			schedule[i] = new Train(types[i], aArr, a, bArr, b, cArr, c);
		}
	
		String ans = "";
		double avg = acc/4600.;
		ans += Integer.toString((int) avg) + ":" + Integer.toString( (int)((avg - (int) avg)*60));

		System.out.println(ans);
		return schedule;
	}
	
}
