import java.util.ArrayList;
import java.util.Arrays;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class runner {

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

//		possibleNotes.add(new ArrayList<>(Arrays.asList(69,72,74,76)));
//		possibleNotes.add(new ArrayList<>(Arrays.asList(64,65,67,69)));
//		possibleNotes.add(new ArrayList<>(Arrays.asList(53,55,57,59)));
//		possibleNotes.add(new ArrayList<>(Arrays.asList(45,48,52,53)));



		String  timeStamp = args[0];




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
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("I guess we give up now...");
		}
	}

}
