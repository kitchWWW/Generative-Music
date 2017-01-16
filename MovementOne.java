import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;


public class MovementOne {

	public static ArrayList<ArrayList<TempNote>> generate(int totalParts){

		ArrayList<ArrayList<TempNote>> mvmtOne = new ArrayList<>();
		
		for(int i = 0; i< totalParts; i++){
			mvmtOne.add(new ArrayList<TempNote>());
		}
		//Add all the notes to make it music
		int totalLength = 16*32-4;

		for(int i = 0; i< totalParts; i ++){

			for(int j = 0; j < 2*i; j ++){
				mvmtOne.get(i).add(new TempNote(-1,16));
			}
			while(TempNote.dur(mvmtOne.get(i))<totalLength){
				double progress = TempNote.dur(mvmtOne.get(i))/(double) totalLength;
				if(Math.random()*2< progress){
					mvmtOne.get(i).add(new TempNote(0,1,"("));
					mvmtOne.get(i).add(new TempNote(1,1,")"));
				}else{
					mvmtOne.get(i).add(new TempNote(1,2,"-."));
				}
				if(Math.random()>.5){
					mvmtOne.get(i).add(new TempNote(1,2,"-."));
				}
				if(Math.random()*2< progress){
					mvmtOne.get(i).add(new TempNote(3,1,"("));
					mvmtOne.get(i).add(new TempNote(2,1,")"));
				}else{
					mvmtOne.get(i).add(new TempNote(2,2,"-."));
				}if(Math.random()>.5){
					mvmtOne.get(i).add(new TempNote(2,2,"-."));
				}
			}
			
			mvmtOne.get(i).get(i*2).message +="\\mp ^\"Like Raindrops\" ";

			while(TempNote.dur(mvmtOne.get(i))>totalLength){
				mvmtOne.get(i).remove(mvmtOne.get(i).size()-1);
			}
		}
		//add Harmonic Shifts
		int index = ThreadLocalRandom.current().nextInt(2*16*4, totalLength/2);
		index = (index/8) * 8;
		for(int i = 0; i <totalParts; i ++){
			TempNote.get(mvmtOne.get(i),index).message +="\\mp";
			TempNote.insert(mvmtOne.get(i),new TempNote(1,4),index);
			TempNote.insert(mvmtOne.get(i),new TempNote(2,4,"\\mf"),index);
		}
		index = ThreadLocalRandom.current().nextInt(index+16, totalLength-16*8);
		index = (index/8) * 8;
		for(int i = 0; i <totalParts; i ++){
			TempNote.get(mvmtOne.get(i),index).message +="\\mp";
			TempNote.insert(mvmtOne.get(i),new TempNote(3,4),index);
			TempNote.insert(mvmtOne.get(i),new TempNote(2,4,"\\mf"),index);
		}
		index = ThreadLocalRandom.current().nextInt(totalLength-16*8, totalLength-1);
		index = (index/16) * 16;
		for(int i = 0; i <totalParts; i ++){
			TempNote.get(mvmtOne.get(i),index).message +="\\sp";
			TempNote.insert(mvmtOne.get(i),new TempNote(3,1,""),index);
			TempNote.insert(mvmtOne.get(i),new TempNote(2,1),index);
			TempNote.insert(mvmtOne.get(i),new TempNote(0,1),index);
			TempNote.insert(mvmtOne.get(i),new TempNote(1,1),index);

			TempNote.insert(mvmtOne.get(i),new TempNote(2,8),index);
			TempNote.insert(mvmtOne.get(i),new TempNote(1,8,"\\f\\<"),index);
		}

		//add Ending note
		for(int i = 0; i <totalParts; i ++){
			mvmtOne.get(i).add(new TempNote(1,4));
			mvmtOne.get(i).add(new TempNote(-1,4));
			mvmtOne.get(i).add(new TempNote(-1,8));
			mvmtOne.get(i).add(0, new TempNote("\\tempo \"Adagio\" 4 = 60"));

		}

		return mvmtOne;
	}

}