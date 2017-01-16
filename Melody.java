import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;


public class Melody{


	static double MUT_BUMP_PRB = .01;
	static double MUT_NEWNOTE_PRB = .001;
	static double MUT_JOIN_PRB = .01;
	static double BREED_SWAP_PRB = .005;

	static String[] noteNames = {"c","cis","d","dis","e","f","fis","g","gis","a","ais","b"};


	ArrayList<Integer> melody;
	Double fitness;
	
	public Melody(int MELODY_LENGTH) {
		melody = new ArrayList<>();
		for(int i = 0; i < MELODY_LENGTH; i ++){
			melody.add(0 + (int)(Math.random() * 127));
		}
		fitness = -1.0;
	}


	public String toString(){
		return fitness +" " +melody;
	}

	public String toLilypond(){

		String ret = "";
		for(int i = 0; i < melody.size(); ){

	 		ret+=" "+noteNames[melody.get(i)%12];

	 		for(int j = 0; j < melody.get(i)/12 - 3; j ++){
				ret+="'";
			}
			int length = 0;
			for(int j = i; j<melody.size() &&
				melody.get(j) == melody.get(i)
				&& !(j%4 ==0 && length > 0); j ++){
				length++;
			}
			if(length > 4){length = 4;}
			String[] lengths = {"32","16","8","8.","4"};
			ret+=""+lengths[length];
			i+=length;
		}
		ret = ret.substring(0, ret.length() - 2);
		ret +="4 \\bar\"|.\"";
		return ret;
	}

	public static Melody breed(Melody a, Melody b){
		ArrayList<Integer> possibleSwapSpots = new ArrayList<>();
		for(int i = 0; i < a.melody.size(); i ++){
			if(Math.abs(a.melody.get(i) - b.melody.get(i))<5){
				possibleSwapSpots.add(i);
			}
		}
		int swapSpot = -1;
		if(possibleSwapSpots.isEmpty()){
			swapSpot = ThreadLocalRandom.current().nextInt(0, a.melody.size());
			swapSpot = (swapSpot/4)*4;
		}else{
			swapSpot = possibleSwapSpots.get(ThreadLocalRandom.current().nextInt(0, possibleSwapSpots.size()));
		}
		Melody ret = new Melody(a.melody.size());

		Boolean fromA = Math.random()>.5;
		for(int i = 0 ; i <a.melody.size(); i ++){
			ret.melody.set(i,(fromA)?a.melody.get(i): b.melody.get(i));
			if(i == swapSpot){
				fromA = ! fromA;
			}
		}
		return ret;
	}

	public static Melody mutate(Melody m){
		for(int i = 0; i <m.melody.size(); i ++){
			int newNote = m.melody.get(i);
			if(Math.random()<MUT_BUMP_PRB){
				newNote = newNote -3 + (int)(Math.random()*6);
			}
			if(Math.random()<MUT_NEWNOTE_PRB){
				newNote = 42+(int)(Math.random() * 24);
			}
			if(i > 1){
				if(Math.random()<MUT_JOIN_PRB){
					newNote = m.melody.get(i-1);
				}				
			}
			m.melody.set(i,newNote);
		}
		return m;
	}

}
