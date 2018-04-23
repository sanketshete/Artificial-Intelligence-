import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class NqueenProblem {
	static int size;
	static int numberOfStepChange=0;
	static int numberOfRestart=0;
	static Random random[];
	static int[]answer;
	public static void main(String[] args) {		
		Scanner in = new Scanner(System.in);
		System.out.println("Enter the dimention of matrix");
		size= 50;//in.nextInt();
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
		int heuritsicValue= getHeuristic(array);
		if(heuritsicValue ==0){
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
			System.out.println("******Total number of steps for climbing" +numberOfStepChange);
			System.out.println("*****Total number of Restart" +numberOfRestart);
			System.exit(0);
		}else{
			int newRow =size+1;
			int newColumn =size+1;
			int prevHeuristic=heuritsicValue;
			for(int rowNumber=0;rowNumber<size;rowNumber++){
				int prevCoulmn =array[rowNumber];
				for(int columnNum=0;columnNum<size;columnNum++){
					if(prevCoulmn!=columnNum){				
						array[rowNumber]=columnNum;
						int nextNewHeuristic =getHeuristic(array);
						if(nextNewHeuristic<prevHeuristic){
							newRow=rowNumber;
							prevHeuristic =nextNewHeuristic;
							newColumn=columnNum;
							//hillClimbing(array);
						}
					}
				}
				array[rowNumber]=prevCoulmn;
				}
			
			if(newRow!=(size+1) && newColumn!=(size+1)){
				array[newRow] = newColumn;
				numberOfStepChange++;
				hillClimbing(array);
				
			}else{
				System.out.println("restarting the state");
				for(int i=0;i<size;i++){
					answer[i] =getRandomValue(random[i]);
				}
				numberOfRestart++;
				hillClimbing(answer);
			}
		}
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
