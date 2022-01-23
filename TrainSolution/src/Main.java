import trainUtils.*;
import utils.Writer;
import solve.Solver;
import grading.Grader;

public class Main {
	
	final static String DATA_DIR = "./assets/in/";
	final static String OUT_DIR = "./assets/out/";

	public static void main(String[] args) {
		//test1();
		test2();
		
	}
	
	public static void test1() {
		Train[] t = null;
		try {
			t = TrainSchedule.parseFile(DATA_DIR + "refSchedule.csv");
		} catch(Exception e) {
			System.out.println("directory problem");
		}
		
		Grader g = new Grader(DATA_DIR + "passengersREF.csv");
		System.out.println(g.grade(t));
	}
	
	public static void test2() {
		Solver s = new Solver(DATA_DIR + "passengersREF.csv");

		TrainType[] types = { 
				TrainType.L8, TrainType.L8, TrainType.L8, TrainType.L8,
				TrainType.L8, TrainType.L8, TrainType.L8, TrainType.L8,
				TrainType.L8, TrainType.L8, TrainType.L8, TrainType.L8,
				TrainType.L4, TrainType.L4, TrainType.L4, TrainType.L4
				};
		int[] starts = { 
				0, 10, 15, 20, 
				30, 40, 50, 60, 
				70, 80, 90, 105,
				130, 150, 160, 180
				};
		Grader g = new Grader(DATA_DIR + "passengersREF.csv");
		Train[] ans = s.solve(types, starts);
		
		System.out.println(g.grade(ans));
		Writer.write(ans, OUT_DIR + "newSchedule1.csv" );
	}
}


