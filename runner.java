import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;



public class runner {
	static int DATASET_SIZE;
	static int ITERATIONS;
	static int MELODY_LENGTH = 4*4*4+1; 	//4 bars, 4/4 16th as the smallest divison, plus a down beat
	static int[][] chordTones = {{0,4,7},{5,9,0},{2,5,9},{2,7,11,5},{0,4}};
	static int[] diatonicTones = {0,2,4,5,7,9,11};
	static String log = "[0";
	static String TIMESTAMP = "";

	//melody values are just midi
	//-1 = rest
	//init an cool dataset of RANDOM :D (size? 100 or something to start)
	static ArrayList<Melody> dataset = new ArrayList<>();

	public static void main(String[] args) {
		DATASET_SIZE = Integer.parseInt(args[0]);
		ITERATIONS = Integer.parseInt(args[1]);
		TIMESTAMP = args[2];
		Comparator<Melody> comparator = new MelodyComp();

		for(int i = 0; i < DATASET_SIZE; i ++){
			dataset.add(new Melody(MELODY_LENGTH));
			dataset.get(i).fitness = rank(dataset.get(i));
		}
		//k. That inits our dataset now.
		
		System.out.println("Initial Dataset");
		
		//now we do everything.
		for(int i = 0; i < ITERATIONS; i ++){
       		PriorityQueue<Melody> queue = new PriorityQueue<Melody>(DATASET_SIZE, comparator);
			
			//rank every melody, put it in a queue
			for(int j = 0; j <DATASET_SIZE; j ++){
				queue.add(dataset.get(j));
			}
			
			//clear the old dataset, about to make a new one
			dataset.clear();

			//"breed" the best melodies
			if(Math.random()>.5){queue.poll();}
			//50% chance of discarding the most fit
			//prevents case where the top tier are paird mating with eachother not chaning

			for(int j = 0; j <DATASET_SIZE/4; j ++){
				Melody curA = queue.poll();
				Melody curB = queue.poll();
				Melody childA = Melody.breed(curA, curB);
				Melody childB = Melody.breed(curA, curB);
				Melody childC = Melody.breed(curA, curB);
				dataset.add(childA);
				dataset.add(childB);
				dataset.add(childC);
			}

			//Add back in the less-good melodies
			while(dataset.size() < DATASET_SIZE){
				dataset.add(queue.poll());
			}
			//do something about mutating them
			for(int j = 0; j <DATASET_SIZE; j ++){
				dataset.set(j,Melody.mutate(dataset.get(j)));
				dataset.get(j).fitness = rank(dataset.get(j));
			}
			vitals(i);
		}

		for(int i = 0; i <DATASET_SIZE; i++){
			dataset.get(i).fitness = rank(dataset.get(i));
		}

		Collections.sort(dataset,comparator);
		System.out.println("Finished, final Dataset");
		printDataset("out/"+TIMESTAMP);
	}


	public static Double rank(Melody m){
		double rank = 0;

		for(int i = 0; i < m.melody.size(); i++){
			int cur = m.melody.get(i);
			
			//if note is "do" to start
			if(cur%12 == 0 && i == 0){
				rank +=300;
			}
			//if note is "do" to finish
			if(cur%12 == 0 && i == m.melody.size()-1){
				rank +=300;
			}
			//if note is part of the chord on strong beat
			int chordIndex = i/16;
			if(i%4 == 0){
				for(int j = 0; j<chordTones[chordIndex].length; j++) {
					if(cur%12 == chordTones[chordIndex][j]){
						rank += 50;
					}
				}
			}

			//if the note is part of the chord at all
			for(int j = 0; j<chordTones[chordIndex].length; j++) {
				if(cur%12 == chordTones[chordIndex][j]){
					rank += 10;
				}
			}

			//if it is diatonic
			for(int j = 0; j< diatonicTones.length; j ++){
				if(cur%12 == diatonicTones[j]){
					rank += 30;
				}
			}

			if(i!=0){	//comparisons to previous notes

				//if note is a repeated note
				if(cur == m.melody.get(i-1)){rank += 10;}

				//if note changes on a new beat
				if(i % 4 == 0 && cur != m.melody.get(i-1)){rank += 30;}

				//if note changes from the previous strong beat
				if(i % 4 == 0 && cur != m.melody.get(i-4)){rank += 30;}

				//if note changes on an 8th note
				if(cur != m.melody.get(i-1) && i % 2 == 0){rank += 20;}
				
				//if note changes does NOT change on a 16th note
				if(cur == m.melody.get(i-1) && i % 2 == 1){rank += 10;}

				//if note is stepwise from one before
				if(Math.abs(cur-m.melody.get(i-1)) <3){rank += 20;}
				
				//if note is a huge from one before
				if(Math.abs(cur-m.melody.get(i-1)) >8){rank -= 90;}

				//if note is a TRITONE D:
				if(Math.abs(cur-m.melody.get(i-1))%12==6){rank -= 100;}

			
			}
			//if note is in a reasonable range (g3 - e5)
			if(cur > 42 && cur < 65) {rank += 5;}
		}

		//System.out.println(rank);
		return rank;
	}

	private static void vitals(int index){
		double average = 0;
		for(int i = 0; i < DATASET_SIZE; i ++){
			double cur = dataset.get(i).fitness;
			average += cur;
		}
		average = average / DATASET_SIZE;
		log+= ", "+average;
	}

	private static void outToFiles(String status){
		try {
			PrintWriter writer = new PrintWriter(status+"/dataset.txt", "UTF-8");
			for(int i = 0; i < DATASET_SIZE; i ++){
		 		writer.println(dataset.get(i));
			}
			writer.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
			System.out.println("I guess we give up now...");
		}
		try {
			PrintWriter writer = new PrintWriter(status+"/stats.csv", "UTF-8");
			writer.println(log+"]");
			writer.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
			System.out.println("I guess we give up now...");
		}
		Melody best = dataset.get(0);
		try {
			PrintWriter writer = new PrintWriter(status+"/melody.ly", "UTF-8");
			writer.println("\\header{title = \"Grown Melody\"}\n\n\\score{\n\\absolute {");
			writer.println(best.toLilypond());
			writer.println("\n}\n\\midi{}\n\\layout{}\n}");
			writer.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
			System.out.println("I guess we give up now...");
		}
	}

	private static void printDataset(String status){
		double average = 0;
		double max = 0;
		double min = Integer.MAX_VALUE;
		for(int i = 0; i < DATASET_SIZE; i ++){
			double cur = dataset.get(i).fitness;
			average += cur;
			if(cur> max) max = cur;
			if(cur< min) min = cur;
		}
		average = average / DATASET_SIZE;

		System.out.println("Average Fitness: "+average);
		System.out.println("Min Fitness: "+min);
		System.out.println("Max Fitness: "+max);
		outToFiles(status);

	}
}
