import trainUtils.*;
import grading.Grader;

public class Main {
	
	final static String DATA_DIR = "./assets/in/";

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		test1();
	}
	
	public static void test1() {
		Train[] t = null;
		try {
			t = TrainSchedule.parseFile(DATA_DIR + "refSchedule.csv");
		} catch(Exception e) {
			System.out.println("directory problem");
		}
		
		Grader g = new Grader(DATA_DIR + "passengers.csv");
		System.out.println(g.grade(t));
		
	}
}
