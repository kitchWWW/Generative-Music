import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;


public class MovementTwo {

	public static ArrayList<ArrayList<TempNote>> generate(
		int totalParts,
		ArrayList<ArrayList<Integer>> possibleNotes,
		ArrayList<ArrayList<TempNote>> mvmtOne
		){

		ArrayList<ArrayList<TempNote>> mvmtTwo = new ArrayList<>();
		
		ArrayList<ArrayList<Integer>> chords = new ArrayList<>();
		ArrayList<Integer> chord = new ArrayList<>();



		boolean goodToGo = false;
		while(!goodToGo){

			chords = new ArrayList<>();
			for(int ind = 0; ind < 5; ind++){
				chord = new ArrayList<>();
				for(int j =0; j <mvmtOne.size(); j ++){
					chord.add(
						ThreadLocalRandom.current().nextInt(0, 4));
				}
				chords.add(chord);
			}
			chords.add(new ArrayList<>(Arrays.asList(2,2,2,2)));

			goodToGo = true;


			for(int i = 0; i <mvmtOne.size(); i++){
				boolean[] found = {false, false, false,false};
				for(int j = 0; j <chords.size(); j ++){
					found[chords.get(j).get(i)] = true;
				}

				if(!(found[0]&&found[1]&&found[2]&&found[3])){
					goodToGo = false;
				}
			}
		}


		for(int i = 0; i< totalParts; i++){
			mvmtTwo.add(new ArrayList<TempNote>());
		}

		//Add music here:
		for(int bigI = 0; bigI <= totalParts; bigI++){
			for(int j = 0; j < totalParts; j++){
				for(int i = 0; i < chords.size(); i++){
					if(j<totalParts-bigI){
						mvmtTwo.get(j).add(new TempNote(chords.get(i).get(j),8));
						mvmtTwo.get(j).add(new TempNote("\\<"));

						mvmtTwo.get(j).add(new TempNote("~"));

						mvmtTwo.get(j).add(new TempNote(chords.get(i).get(j),8));
						mvmtTwo.get(j).add(new TempNote("\\>"));

					}else{
						int index = getGoodIndex(mvmtOne,j,chords.get(i).get(j));
						ArrayList<TempNote> exerpt = TempNote.getExerpt(mvmtOne.get(j),index,8);
						exerpt.add(new TempNote(mvmtOne.get(j).get(index).tempValue,8));
						exerpt.add(1,new TempNote("("));
						exerpt.add(1,new TempNote("\\<"));
						exerpt.add(new TempNote("\\>"));
						exerpt.add(new TempNote(")"));
						if(j==totalParts-bigI && i==0){
							exerpt.add(1, new TempNote("^\"solo\" \\mf"));
						}
						if(j==totalParts-bigI+1 && i==0){
							exerpt.add(1, new TempNote("^\"accompanying\" \\p"));
						}
						mvmtTwo.get(j).addAll(exerpt);
					}
				}
				mvmtTwo.get(j).add(new TempNote("\n"));
			}
		}

		int[] startsOfMovement = new int[4];

		for(int vi = 0; vi < totalParts; vi ++){
			mvmtTwo.get(vi).add(1, new TempNote("\n \\p"));
			mvmtTwo.get(vi).add(1, new TempNote("^\"Like Breathing\""));
			mvmtTwo.get(vi).add(0, new TempNote("\\time 2/2 "));
			mvmtTwo.get(vi).add(0, new TempNote("\\tempo \"Lento\" 2 = 35"));
			mvmtTwo.get(vi).add(new TempNote("\n \\bar\"||\" \n \\break \n"));
			mvmtTwo.get(vi).add(new TempNote("\\tempo \"Allegro\" 4 = 120"));
			startsOfMovement[vi] = mvmtTwo.get(vi).size();
		}
		//Chord 0,1,0,2,0,3,4,5
		//This determines who is the soloist for each of the different chord sections
		goodToGo = false;
		ArrayList<Integer> soloist = new ArrayList<>();
		while(!goodToGo){
			soloist = new ArrayList<>();
			for(int i = 0; i < chords.size(); i++){
				soloist.add(i%totalParts);
			}
			Collections.shuffle(soloist);
			goodToGo = true;
			for(int i = 1; i < soloist.size(); i ++){
				if(soloist.get(i)==soloist.get(0)){
					goodToGo = false;
				}
			}	
		}
		

		//make the big "zero theme" with everyone acting: 4 measure, x, x, cont
		ArrayList<ArrayList<TempNote>> zeroTheme = new ArrayList<>();
		//Actually keep this one because we need to use it lots


		for(int vi= 0; vi< totalParts; vi++){//voice index, or insturment
			int index = getGoodIndex(mvmtOne,vi,chords.get(0).get(vi));
			int solo = soloist.get(0);
			zeroTheme.add(new ArrayList<>());
			zeroTheme.get(vi).addAll(TempNote.getExerpt(mvmtOne.get(vi),index,8));
			zeroTheme.get(vi).addAll(TempNote.getExerpt(mvmtOne.get(vi),index,8));
			
			if(vi == solo){
				zeroTheme.get(vi).addAll(TempNote.getExerpt(mvmtOne.get(vi),index,16));
			}else{
				zeroTheme.get(vi).add(new TempNote(chords.get(0).get(vi),4));
				zeroTheme.get(vi).add(new TempNote(-1,4));
				zeroTheme.get(vi).add(new TempNote(-1,8));
			}
			int soloIndex = getGoodIndex(mvmtOne,solo,chords.get(0).get(solo));

			zeroTheme.get(vi).addAll(TempNote.getExerpt(mvmtOne.get(solo),soloIndex,16));
			zeroTheme.get(vi).addAll(TempNote.getExerpt(mvmtOne.get(solo),soloIndex,16));
			
			if(vi == solo){
				zeroTheme.get(vi).addAll(TempNote.getExerpt(mvmtOne.get(vi),index,32));
			}else{
				zeroTheme.get(vi).add(new TempNote(chords.get(0).get(vi),4));
				zeroTheme.get(vi).add(new TempNote(-1,4));
				zeroTheme.get(vi).add(new TempNote(chords.get(0).get(vi),4));
				zeroTheme.get(vi).add(new TempNote(-1,4));
				zeroTheme.get(vi).addAll(TempNote.getExerpt(mvmtOne.get(solo),soloIndex,16));
			}
			mvmtTwo.get(vi).addAll(zeroTheme.get(vi));
		}

		//the first theme
		for(int vi= 0; vi< totalParts; vi++){//voice index, or insturment
			int index = getGoodIndex(mvmtOne,vi,chords.get(1).get(vi));
			int solo = soloist.get(1);
			mvmtTwo.get(vi).addAll(TempNote.getExerpt(mvmtOne.get(vi),index,8));
			mvmtTwo.get(vi).addAll(TempNote.getExerpt(mvmtOne.get(vi),index,8));
			
			if(vi == solo){
				mvmtTwo.get(vi).addAll(TempNote.getExerpt(mvmtOne.get(vi),index,64));
			}else if(vi == ((solo+1) % totalParts)) {
				for(int i = 0; i < 64/2; i++){
					mvmtTwo.get(vi).add(new TempNote(chords.get(1).get(vi),2));
				}
			}else{
				for(int i = 0; i < 4; i ++){
					mvmtTwo.get(vi).add(new TempNote(chords.get(1).get(vi),4));
					mvmtTwo.get(vi).add(new TempNote(-1,4));
					mvmtTwo.get(vi).add(new TempNote(-1,8));					
				}
			}
			mvmtTwo.get(vi).addAll(TempNote.getExerpt(mvmtOne.get(vi),index,16));

		}

		//short re-visit to zero theme
		for(int vi = 0; vi < totalParts; vi ++){
			mvmtTwo.get(vi).addAll(TempNote.getExerpt(zeroTheme.get(vi),0,48));
		}

		//now for the 2nd theme
		for(int vi= 0; vi< totalParts; vi++){//voice index, or insturment
			int index = getGoodIndex(mvmtOne,vi,chords.get(2).get(vi));
			int solo = soloist.get(2);
			if(vi == solo){
				mvmtTwo.get(vi).addAll(TempNote.getExerpt(mvmtOne.get(vi),index,8));
				mvmtTwo.get(vi).addAll(TempNote.getExerpt(mvmtOne.get(vi),index,72));
				mvmtTwo.get(vi).addAll(TempNote.getExerpt(mvmtOne.get(vi),index,8));
				mvmtTwo.get(vi).addAll(TempNote.getExerpt(mvmtOne.get(vi),index,8));
			}else{
				for(int i = 0; i < 3; i ++){
					mvmtTwo.get(vi).addAll(TempNote.getExerpt(mvmtOne.get(vi),index,8));
					mvmtTwo.get(vi).add(new TempNote(chords.get(1).get(vi),4));
					mvmtTwo.get(vi).add(new TempNote(-1,4));
					mvmtTwo.get(vi).add(new TempNote(chords.get(1).get(vi),4));
					mvmtTwo.get(vi).add(new TempNote(-1,4));
					mvmtTwo.get(vi).add(new TempNote(chords.get(1).get(vi),4));
					mvmtTwo.get(vi).add(new TempNote(-1,4));					
				}
			}
		}

		//short re-visit to zero theme
		for(int vi = 0; vi < totalParts; vi ++){
			mvmtTwo.get(vi).addAll(TempNote.getExerpt(zeroTheme.get(vi),0,24));
			mvmtTwo.get(vi).addAll(TempNote.getExerpt(zeroTheme.get(soloist.get(0)),0,8));
		}



		//now for the 3rd theme
		for(int vi= 0; vi< totalParts; vi++){//voice index, or insturment
			int index = getGoodIndex(mvmtOne,vi,chords.get(3).get(vi));
			int solo = soloist.get(3);
			if(vi == solo){
				mvmtTwo.get(vi).addAll(TempNote.getExerpt(mvmtOne.get(vi),index,16));
				mvmtTwo.get(vi).addAll(TempNote.getExerpt(mvmtOne.get(vi),index,16));
			}else{
				mvmtTwo.get(vi).addAll(TempNote.getExerpt(mvmtOne.get(vi),index,8));
				mvmtTwo.get(vi).add(new TempNote(chords.get(3).get(vi),4));
				mvmtTwo.get(vi).add(new TempNote(-1,4));
				mvmtTwo.get(vi).addAll(TempNote.getExerpt(mvmtOne.get(vi),index,8));
				mvmtTwo.get(vi).add(new TempNote(chords.get(3).get(vi),4));
				mvmtTwo.get(vi).add(new TempNote(-1,4));
			}
		}

		//now for the 4rd theme
		for(int vi= 0; vi< totalParts; vi++){//voice index, or insturment
			int index = getGoodIndex(mvmtOne,vi,chords.get(4).get(vi));
			int solo = soloist.get(4);
			if(vi == solo){
				mvmtTwo.get(vi).addAll(TempNote.getExerpt(mvmtOne.get(vi),index,16));
				mvmtTwo.get(vi).addAll(TempNote.getExerpt(mvmtOne.get(vi),index,16));
			}else{
				mvmtTwo.get(vi).addAll(TempNote.getExerpt(mvmtOne.get(vi),index,8));
				mvmtTwo.get(vi).add(new TempNote(chords.get(4).get(vi),4));
				mvmtTwo.get(vi).add(new TempNote(-1,4));
				mvmtTwo.get(vi).addAll(TempNote.getExerpt(mvmtOne.get(vi),index,8));
				mvmtTwo.get(vi).add(new TempNote(chords.get(4).get(vi),4));
				mvmtTwo.get(vi).add(new TempNote(-1,4));
			}
		}

		for(int vi= 0; vi< totalParts; vi++){//voice index, or insturment
			int index = getGoodIndex(mvmtOne,vi,chords.get(5).get(vi));
			int solo = soloist.get(5);
			if(vi == solo){
				mvmtTwo.get(vi).addAll(TempNote.getExerpt(mvmtOne.get(vi),index,64));
			}else{
				mvmtTwo.get(vi).add(new TempNote(chords.get(5).get(vi),4));
				mvmtTwo.get(vi).add(new TempNote(-1,4));
				mvmtTwo.get(vi).add(new TempNote(-1,8));
				mvmtTwo.get(vi).add(new TempNote(-1,16));
				mvmtTwo.get(vi).add(new TempNote(-1,16));
				mvmtTwo.get(vi).addAll(TempNote.getExerpt(mvmtOne.get(vi),index,16));

			}
		}

		//THE END!!!
		for(int vi = 0; vi < totalParts; vi ++){
			int solo = soloist.get(0);

			mvmtTwo.get(vi).addAll(TempNote.getExerpt(zeroTheme.get(vi),0,16));
			mvmtTwo.get(vi).addAll(TempNote.getExerpt(zeroTheme.get(soloist.get(0)),0,4));
			mvmtTwo.get(vi).addAll(TempNote.getExerpt(zeroTheme.get(soloist.get(0)),0,4));
			mvmtTwo.get(vi).addAll(TempNote.getExerpt(zeroTheme.get(soloist.get(0)),0,4));
			mvmtTwo.get(vi).addAll(TempNote.getExerpt(zeroTheme.get(soloist.get(0)),0,4));
			if(vi == solo){
				mvmtTwo.get(vi).addAll(TempNote.getExerpt(zeroTheme.get(vi),0,32));
				mvmtTwo.get(vi).add(new TempNote(1,4));

			}else{
				mvmtTwo.get(vi).add(new TempNote(chords.get(5).get(vi),4));
				mvmtTwo.get(vi).add(new TempNote(-1,4));
				mvmtTwo.get(vi).add(new TempNote(-1,8));
				mvmtTwo.get(vi).add(new TempNote(-1,16));
				mvmtTwo.get(vi).add(new TempNote(1,4));
			}
		}

		for(int vi = 0; vi<totalParts; vi ++){
			mvmtTwo.get(vi).add(startsOfMovement[vi]+1,new TempNote("\\f"));
		}


		return mvmtTwo;
	}

	private static int getGoodIndex(ArrayList<ArrayList<TempNote>> mvmtOne, int vi, int pitch ){
		int index = TempNote.find(mvmtOne.get(vi), pitch);
		if(mvmtOne.get(vi).get(index).duration==1){
			if(!(mvmtOne.get(vi).get(index+1).duration==1)){
				index++;
			}
		}
		return index;
	}

}