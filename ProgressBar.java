

public class ProgressBar {
	
	static int width = 0;

	public ProgressBar (int wide) {
		width = wide;
	}

	public void update(double progress){
		if(progress>1 || progress < 0){
			return;
		}
		//between 0 - 1
		System.out.println(progress);
		System.out.println(width);
		String s = "\r|";
		for(int i = 0; i < (progress*width); i ++){
			s+="*";
		}
		s+="|";
		if(progress != 0){
			for(int i = 0; i<(1/progress)*width; i++){
				s+=" ";
			}			
		}else{
			for(int i = 0; i<width; i++){
				s+=" ";
			}
		}
		s+="|";
		System.out.printf(s);
	}
	public void done(){
		System.out.println("\nDone!");
	}
}