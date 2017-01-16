// StringLengthComparator.java
import java.util.Comparator;

public class MelodyComp implements Comparator<Melody>{
	public int compare(Melody x, Melody y){
		if(x.fitness == y.fitness){return 0;}
		return (x.fitness<y.fitness) ? 1 : -1;
	}
}