import java.util.ArrayList;

public class TempNote {

	public int tempValue;
	public int duration;//in 16th notes
	public String message;

	public static int dur(ArrayList<TempNote> notes){
		int sum = 0;
		for(int i = 0; i < notes.size(); i++){
			sum += notes.get(i).duration;
		}
		return sum;
	}

	public static ArrayList<Note> swap(ArrayList<TempNote> notes, ArrayList<Integer> possibleNotes){
		ArrayList<Note> ret = new ArrayList<>();
		for(TempNote tn: notes){
			Note nn;
			if(tn.tempValue==-1){
				nn = new Note(-1, tn.dur(),"");
			}else if (tn.tempValue == -2){
				nn = new Note(tn.message);
			}else{
				nn = new Note(possibleNotes.get(tn.tempValue), tn.dur(), tn.message);
			}
			ret.add(nn);
		}
		return ret;
	}

	public static ArrayList<TempNote> insert(ArrayList<TempNote> notes, TempNote note, int index){
		int dur = 0;
		int addSpot = 0;
		while (dur < index){
			dur += notes.get(addSpot).dur();
			addSpot++;
		}
		notes.add(addSpot, note);			
		return notes;
	}

	//returns note at a particular index from the start
	public static TempNote get(ArrayList<TempNote> notes, int index){
		int dur = 0;
		int i = 0;
		while (dur < index){
			dur += notes.get(i).dur();
			i++;
		}
		return notes.get(i);
	}

	//returns the actual index, not a duration from start
	public static int find(ArrayList<TempNote> notes, int tempVal){
		for(int i = 0; i < notes.size(); i ++){
			if(notes.get(i).tempValue == tempVal){
				return i;
			}
		}
		return -1;
	}

	public static ArrayList<ArrayList<TempNote>> strip(ArrayList<ArrayList<TempNote>> notes){
		ArrayList<ArrayList<TempNote>> ret = new ArrayList<>();
		for(ArrayList<TempNote> line : notes){
			ArrayList<TempNote> cleanLine = new ArrayList<>();
			for(TempNote n : line){
				cleanLine.add(new TempNote(n.tempValue, n.duration));
			}
			ret.add(cleanLine);
		}
		return ret;
	}


	//takes an actual index, not duration from start
	public static ArrayList<TempNote> getExerpt(ArrayList<TempNote> notes, int startIndex, int excerptDuration){
		int dur = 0;
		int i = startIndex;
		ArrayList<TempNote> ret = new ArrayList<>();
		while (dur < excerptDuration){
			ret.add(notes.get(i));
			dur += notes.get(i).duration;
			i++;
		}
		if(dur>excerptDuration){
			ret.get(ret.size()-1).duration = dur - excerptDuration;
		}
		return ret;
	}

	public TempNote(int temp, int dur){
		tempValue = temp;
		if(dur>16 || dur <0){
			System.out.println("invalid TEMP duration, using whole note");
			System.out.println(temp + " "+ dur+".");
			dur = 16;
			Thread.dumpStack();
		}
		duration = dur;
		message = "";
	}

	public TempNote(int temp, int dur, String articulation){
		tempValue = temp;
		if(dur>16 || dur <0){
			System.out.println("invalid TEMP duration, using whole note");
			System.out.println(temp + " "+ dur+" "+articulation);
			dur = 16;
		}
		duration = dur;
		message = articulation;
	}
	
	public TempNote(String lilypond){
		tempValue = -2;
		duration = 0;
		message = lilypond;
	}

	public int dur(){
		return duration;
	}

	public String toString(){
		return "("+tempValue+","+duration+")";
	}
}