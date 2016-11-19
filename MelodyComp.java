// StringLengthComparator.java
import java.util.Comparator;

public class MelodyComp implements Comparator<Melody>{
	public int compare(Melody x, Melody y){
		return (x.fitness<y.fitness) ? 1 : -1;
	}
}