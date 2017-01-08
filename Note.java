import java.util.ArrayList;


public class Note {

	static String[] noteNames = {"c","cis","d","dis","e","f","fis","g","gis","a","ais","b"};

	int midi;
	String lilypond;
	int duration;//in 16th notes
	String pitch;

	public static int dur(ArrayList<Note> notes){
		int sum = 0;
		for(int i = 0; i < notes.size(); i++){
			sum += notes.get(i).duration;
		}
		return sum;
	}

	public static String toString(ArrayList<Note> notes){
		String ret = "";
		for(Note n : notes){
			ret = ret + n;
		}
		return ret;
	}

	public Note(int pitchValue, int dur, String message){
		midi = pitchValue;
		if(dur>16 || dur <1){
			dur = 16;
			System.out.println("invalid NOTE duration, using whole note");
		}
		duration = dur;
		if(midi == -1){
			pitch = "r";
		}else{
			pitch = noteNames[midi%12];
			int octave = midi/12;
	 		octave -= 4;
	 		while(octave!=0){
	 			if(octave>0){
	 				pitch+="'";
	 				octave--;
	 			}else{
	 				pitch+=",";
	 				octave++;
	 			}
	 		}
		}
 		String[] lengths = {" error ","16","8","8.","4",
 			"4 ~"+pitch+"16","4.","4..","2",
 			"2 ~"+pitch+"16","2 ~"+pitch+"8","2 ~"+pitch+"8.","2.",
 			"2. ~"+pitch+"16","2..","2...","1"};
 		lilypond =pitch+lengths[duration];
 		if(message!=null){
			lilypond = lilypond + message;
 		}
	}
	
	public Note(String message){
		lilypond = message;
	}

	public String toString(){
		return lilypond+" ";
	}
	public int dur(){
		return duration;
	}
}