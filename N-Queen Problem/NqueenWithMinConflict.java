import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class NqueenWithMinConflict {
	static int size;
	static boolean flag=false;
	//static int numberOfStepChange=0;
	static int numberOfRestart=0;
	static Random random[];
	static Random random1 = new Random();
	static int[]answer;
	public static void main(String[] args) {		
		Scanner in = new Scanner(System.in);
	//	System.out.println("Enter the dimention of matrix");
		size= 1000;//in.nextInt();
		answer=new int[size];
		random = new Random[size];
		for(int i=0;i<size;i++){
			Random random1 = new Random();
			random[i]=random1;
			answer[i] =getRandomValue(random[i]);
		}
		hillClimbing(answer);
	}
	
	public static int getRandomValue(Random random){
		
		return random.nextInt(size);
	}

	public static void hillClimbing(int[] array) {
		ArrayList<Integer> duplicates = new ArrayList<Integer>();
		int numberOfStepChange=0;
		while(true){
			int prevheuritsicValue =0;
			duplicates.clear();
			for(int rowNumber=0;rowNumber<size;rowNumber++){
				int nextHeuristic =getHeuristicForParticularQueen(rowNumber, array);
				if(prevheuritsicValue ==nextHeuristic){
					duplicates.add(rowNumber);
				}else if(prevheuritsicValue<nextHeuristic){
					duplicates.clear();
					prevheuritsicValue =nextHeuristic;
					duplicates.add(rowNumber);
				}
				
			}
			if(prevheuritsicValue==0){
				for(int i=0;i<size;i++){
					System.out.println(array[i]);
				}
				for(int i=0;i<size;i++){
					for(int j=0;j<size;j++){
						if(array[i]!=j){
							System.out.print(0+" ");
						}
						else
						{
							System.out.print("x ");
						}
					}
					System.out.println();
				}
				System.out.println("**************number of steps"+numberOfStepChange);
				return;
			}
			
				int MaxConflictow =
					duplicates.get(random1.nextInt(duplicates.size()));
	
			duplicates.clear();
			int nextHeuristic =size+1;
			for(int columnNumber=0;columnNumber<size;columnNumber++){
					if(array[MaxConflictow]!=columnNumber){				
						array[MaxConflictow]=columnNumber;
						int nextNewHeuristic =getHeuristicForParticularQueen(MaxConflictow, array);
						if(nextNewHeuristic<nextHeuristic){
							duplicates.clear();
							duplicates.add(columnNumber);
							nextHeuristic =nextNewHeuristic;
							//newColumn=columnNumber;
						}
						if(nextNewHeuristic==nextHeuristic){
							duplicates.add(columnNumber);
						}
					}
			}
			if(duplicates.size()!=0){
				array[MaxConflictow] =
                        duplicates.get(random1.nextInt(duplicates.size()));		
				}
			numberOfStepChange++;
			  if (numberOfStepChange == size * 2) {
                 System.out.println("Trying too long... start over");
				  for(int i=0;i<size;i++){
						array[i] =getRandomValue(random[i]);
					}
                  numberOfStepChange=0;
              }
		}
	
}

	private static int  getHeuristicForParticularQueen(int rowNumber,int []array) {
		int heuriSticValue=0;
					
					for(int nextI=0;nextI<size;nextI++){
						if(nextI!=rowNumber){
						if(array[nextI] ==array[rowNumber] || (Math.abs(nextI-rowNumber) ==Math.abs(array[nextI]-array[rowNumber]))){
							heuriSticValue++;
							}
						}
					}
				
				return heuriSticValue;
		}

	
	private static int  getHeuristic(int[] array) {
		int heuriSticValue=0;
				for(int i=0;i<size;i++){
					for(int nextI=i+1;nextI<size;nextI++){
						if(array[i] ==array[nextI] || (Math.abs(i-nextI) ==Math.abs(array[i]-array[nextI]))){
							heuriSticValue++;
						}
					}
				}
				return heuriSticValue;
		}
	}
