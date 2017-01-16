import java.util.ArrayList;
import java.util.Arrays;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class runner {
<<<<<<< HEAD
	static int DATASET_SIZE;
	static int ITERATIONS;
	static int RANK_STARTDO = 300;
	static int RANK_ENDDO = 300;
	static int RANK_SECOND_TO_LAST = 300;
	static int RANK_CHORD_TONE_STRONG=100;
	static int RANK_CHORD_TONE_GEN=30;
	static int RANK_DIATONIC_EVER=2400;
	static int RANK_STEPWISE = 100;
	static int RANK_CHORDAL_LEAP=20;
	static int RANK_LEAP_PUNISH_R1 = 150;
	static int RANK_LEAP_PUNISH_R2 = 250;
	static int RANK_LEAP_PUNISH_R3 = 250;
	static int RANK_LEAP_PUNISH_R4 = 1000;
	static int RANK_LEAP_PUNISH_TRI = 500;
	static int RANK_CHANGE_ON_STRONG = 200;
	static int RANK_CHANGE_FROM_PREV_STRONG = 200;
	static int RANK_QUARTER_NOTE_REWARD = 200;
	static int RANK_DOUBLE_EIGHT_REWARD = 200;
	static int RANK_RANGE_REWARD = 500;


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

		RANK_STARTDO = Integer.parseInt(args[3]);
		RANK_ENDDO = Integer.parseInt(args[4]);
		RANK_SECOND_TO_LAST = Integer.parseInt(args[5]);
		RANK_CHORD_TONE_STRONG=Integer.parseInt(args[6]);
		RANK_CHORD_TONE_GEN=Integer.parseInt(args[7]);
		RANK_DIATONIC_EVER=Integer.parseInt(args[8]);
		RANK_STEPWISE = Integer.parseInt(args[9]);
		RANK_CHORDAL_LEAP=Integer.parseInt(args[10]);
		RANK_LEAP_PUNISH_R1 = Integer.parseInt(args[11]);
		RANK_LEAP_PUNISH_R2 = Integer.parseInt(args[12]);
		RANK_LEAP_PUNISH_R3 = Integer.parseInt(args[13]);
		RANK_LEAP_PUNISH_R4 = Integer.parseInt(args[14]);
		RANK_LEAP_PUNISH_TRI = Integer.parseInt(args[15]);
		RANK_CHANGE_ON_STRONG = Integer.parseInt(args[16]);
		RANK_CHANGE_FROM_PREV_STRONG = Integer.parseInt(args[17]);
		RANK_QUARTER_NOTE_REWARD = Integer.parseInt(args[18]);
		RANK_DOUBLE_EIGHT_REWARD = Integer.parseInt(args[19]);
		RANK_RANGE_REWARD = Integer.parseInt(args[20]);

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
			//prevents case where the top tier are paird mating with eachother not changing

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
		try{
			Collections.sort(dataset,comparator);	
		}catch(Exception e){
			e.printStackTrace();
			System.out.println(dataset);
			return;
		}
		System.out.println("Finished, final Dataset");
		printDataset("out/"+TIMESTAMP);
	}
=======

	ArrayList<String> clefs;

	public static void main(String[] args) {
		//Following should be moved to be taken as arguments
		int totalParts = 4;
		ArrayList<ArrayList<Integer>> possibleNotes = new ArrayList<>();
		ArrayList<String> clefs = new ArrayList<>();
		ArrayList<String> names = new ArrayList<>(Arrays.asList("Oboe","Marimba","Guitar","Marimba"));

//		ArrayList<String> names = new ArrayList<>(Arrays.asList("Violin 1","Violin 2","Viola","Cello"));
		

		 possibleNotes.add(new ArrayList<>(Arrays.asList(71,72,74,76)));
		 possibleNotes.add(new ArrayList<>(Arrays.asList(65,67,71,72)));
		 possibleNotes.add(new ArrayList<>(Arrays.asList(62,64,67,69)));
		 possibleNotes.add(new ArrayList<>(Arrays.asList(59,60,64,65)));

		// possibleNotes.add(new ArrayList<>(Arrays.asList(66,69,70,71)));
		// possibleNotes.add(new ArrayList<>(Arrays.asList(61,63,65,67)));
		// possibleNotes.add(new ArrayList<>(Arrays.asList(50,52,56,58)));
		// possibleNotes.add(new ArrayList<>(Arrays.asList(44,46,49,51)));
>>>>>>> c6d17761ac022d3cefbefd3478310c505024793e

//		possibleNotes.add(new ArrayList<>(Arrays.asList(69,72,74,76)));
//		possibleNotes.add(new ArrayList<>(Arrays.asList(64,65,67,69)));
//		possibleNotes.add(new ArrayList<>(Arrays.asList(53,55,57,59)));
//		possibleNotes.add(new ArrayList<>(Arrays.asList(45,48,52,53)));

<<<<<<< HEAD
	public static Double rank(Melody m){
		double rank = 0;

		for(int i = 0; i < m.melody.size(); i++){
			int cur = m.melody.get(i);
			
			//if note is "do" to start
			//RANK_STARTDO
			if(cur%12 == 0 && i == 0){
				rank +=RANK_STARTDO;
			}
			//if note is "do" to finish
			//RANK_ENDDO
			if(cur%12 == 0 && i == m.melody.size()-1){
				rank +=RANK_ENDDO;
			}

			//if note is "ti" or "ray" second to last
			//RANK_SECOND_TO_LAST
			if((cur%12 == 2 || cur%12==11) && i == m.melody.size()-2){
				rank +=RANK_SECOND_TO_LAST;
			}

			//if note is part of the chord on strong beat
			//RANK_CHORD_TONE_STRONG
			int chordIndex = i/16;
			if(i%4 == 0){
				for(int j = 0; j<chordTones[chordIndex].length; j++) {
					if(cur%12 == chordTones[chordIndex][j]){
						rank += RANK_CHORD_TONE_STRONG;
					}
				}
			}

			//if the note is part of the chord at all
			//RANK_CHORD_TONE_GEN
			for(int j = 0; j<chordTones[chordIndex].length; j++) {
				if(cur%12 == chordTones[chordIndex][j]){
					rank += RANK_CHORD_TONE_GEN;
				}
			}

			//if it is diatonic
			//RANK_DIATONIC_EVER
			for(int j = 0; j< diatonicTones.length; j ++){
				if(cur%12 == diatonicTones[j]){
					rank += RANK_DIATONIC_EVER;
				}
			}
			//melodic leaps and stepwise motion
			int pvi = prevNote(i,m);
			if(pvi !=-1){	//comparisons to previous notes
				//if note is stepwise from one before

				//RANK_STEPWISE
				if(Math.abs(cur-m.melody.get(pvi)) <3){rank += RANK_STEPWISE;}
				else{
					for(int j = 0; j<chordTones[chordIndex].length; j++) {
						if(cur%12 == chordTones[chordIndex][j]){
							//RANK_CHORDAL_LEAP
							rank+= RANK_CHORDAL_LEAP;
						}
					}
				}
				//if note is a leap from one before
				//RANK_PUNISH_R1
				if(Math.abs(cur-m.melody.get(pvi)) > 4){rank -= RANK_LEAP_PUNISH_R1;}

				//if note is a huge from one before
				//RANK_PUNISH_R2
				if(Math.abs(cur-m.melody.get(pvi)) >8){rank -= RANK_LEAP_PUNISH_R2;}

				//if note is a huge from one before
				//RANK_PUNISH_R3
				if(Math.abs(cur-m.melody.get(pvi)) >11){rank -= RANK_LEAP_PUNISH_R3;}

				//if note is a huge from one before
				//RANK_PUNISH_R4
				if(Math.abs(cur-m.melody.get(pvi)) >14){rank -= RANK_LEAP_PUNISH_R4;}

				//if note is a TRITONE D:
				//RANK_PUNISH_TRI
				if(Math.abs(cur-m.melody.get(pvi))%12==6){rank -= RANK_LEAP_PUNISH_TRI;}
			
			}
			//now all the rhythm
			if(i%4 == 0){
				if(i!=0){
					//if note changes on a new beat
					//RANK_CHANGE_ON_STRONG
					if(cur != m.melody.get(i-1)){rank += RANK_CHANGE_ON_STRONG;}

					//if note changes from the previous strong beat
					//RANK_CHANGE_FROM_PREV_STRONG
					if(cur != m.melody.get(i-4)){rank += RANK_CHANGE_FROM_PREV_STRONG;}
				}
				if(i!=m.melody.size()-1){
					//if rhythm is 4th
					//RANK_QUARTER_NOTE_REWARD
					if(m.melody.get(i)==m.melody.get(i+1) &&
						m.melody.get(i+1)==m.melody.get(i+2) &&
						m.melody.get(i+2)==m.melody.get(i+3)){
						rank+=RANK_QUARTER_NOTE_REWARD;
					}
					//if rhythm is 8th 8th
					//RANK_DOUBLE_EIGHT_REWARD
					if(m.melody.get(i)==m.melody.get(i+1) &&
						m.melody.get(i+1)!=m.melody.get(i+2) &&
						m.melody.get(i+2)==m.melody.get(i+3)){
						rank+=RANK_DOUBLE_EIGHT_REWARD;
					}				
				}
			}
			//if note is in a reasonable range (g3 - e5)
			//RANK_RANGE_REWARD
			if(cur > 42 && cur < 65) {rank += 5;}else{rank-=RANK_RANGE_REWARD;}
		}
=======
>>>>>>> c6d17761ac022d3cefbefd3478310c505024793e


<<<<<<< HEAD
	private static int prevNote(int index, Melody m){
		int ret = index;
		while(ret>0){
			ret--;
			if(m.melody.get(ret)!=m.melody.get(index)){
				return ret;
			}
		}
		return -1;
	}

	private static void vitals(int index){
		double average = 0;
		double max = Integer.MIN_VALUE;
		for(int i = 0; i < DATASET_SIZE; i ++){
			double cur = dataset.get(i).fitness;
			average += cur;
			if(cur>max){
				max = cur;
			}
		}
		average = average / DATASET_SIZE;
		log+= ", "+max;
	}
=======
		String  timeStamp = args[0];
>>>>>>> c6d17761ac022d3cefbefd3478310c505024793e




		ArrayList<ArrayList<TempNote>> oneTemp = MovementOne.generate(totalParts);
		//ArrayList<String> oneParts = tempNotesToParts(oneTemp,possibleNotes);		
		//buildParts(timeStamp,"I", oneParts,names);

		ArrayList<ArrayList<TempNote>> twoTemp = MovementTwo.generate(totalParts,possibleNotes,TempNote.strip(oneTemp));
		//ArrayList<String> twoParts = tempNotesToParts(twoTemp,possibleNotes);		
		//buildParts(timeStamp,"II", twoParts,names);
		

		for(int i = 0; i < totalParts; i ++){
			oneTemp.get(i).add(new TempNote("\\bar\"||\" \n \\break \n "));
			oneTemp.get(i).addAll(twoTemp.get(i));
		}
		ArrayList<String> finalParts = tempNotesToParts(oneTemp,possibleNotes);		
		//System.out.println(finalParts);
		//System.out.println(oneTemp);
		buildParts(timeStamp,"III", finalParts,names);


	}
	private static ArrayList<String> tempNotesToParts(ArrayList<ArrayList<TempNote>> tempNotes,
		ArrayList<ArrayList<Integer>> possibleNotes){
		ArrayList<String> ret = new ArrayList<>();
		for(int i = 0; i < tempNotes.size(); i ++){
			//swap out the temp notes for real ones, then use toString to make it a strin
			ret.add(Note.toString(TempNote.swap(tempNotes.get(i),possibleNotes.get(i))));
		}
		return ret;
	}	

	private static void buildParts(String timeStamp, String title,
		ArrayList<String> parts, ArrayList<String> names){
		try {
<<<<<<< HEAD
			PrintWriter writer = new PrintWriter(status+"/melody.ly", "UTF-8");
			writer.println("#(set-global-staff-size 25)");
			writer.println("\\header{tagline = \" \"}\n\n\\score{\n\\absolute {");
			writer.println("\\override Score.BarNumber.break-visibility = ##(#f #f #f) ");
			writer.println(best.toLilypond());
			writer.println("\n}\n\\midi{}\n\\layout{}\n}");
=======

			String res = "";

			File file = new File("templates/Temp1.ly");

	        Scanner sc = new Scanner(file);

	        while (sc.hasNextLine()) {
	            String i = sc.nextLine();
	            //System.out.println(i);
	            if(i.startsWith("%part")){
	            	i = i.substring(5);
	            	int index = Integer.parseInt(i);
	            	res+= parts.get(index) +"\n";
	            }else if(i.startsWith("%name")){
	            	i = i.substring(5);
	            	int index = Integer.parseInt(i);
	            	res+= names.get(index) +"\n";
	            }else if(i.startsWith("%timeStamp")){
	            	res+= timeStamp +"\n";
	            }
	            else{
	            	res+=i + "\n";
	            }
	        }
        	sc.close();
			PrintWriter writer = new PrintWriter("out/"+timeStamp+"/"+title+".ly", "UTF-8");
			writer.println(res);
>>>>>>> c6d17761ac022d3cefbefd3478310c505024793e
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("I guess we give up now...");
		}
	}

}
