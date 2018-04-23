import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Stack;

public class AstarProblemNew {
	public static int numberOfNodeGenrated=0;
	public static int numberOfNodeExpanded=0;
	
	AstarProblemNew(){
	root= null;	
}
	
public static PriorityQueue<node> queue ;
public node goalStateNode=null;
public static boolean isInitialStateisFinal=false;
public static Stack<node> stack = new Stack<node>();
public static ArrayList<node> expandedNode = new ArrayList<node>();
public static node root ;
public static int [][]initialState = new int[3][3]; 
public static int [][]goalState = new int[3][3]; 

	public static void main(String[] args) {
		AstarProblemNew Astar = new AstarProblemNew();
		queue = new PriorityQueue<>(heuristiccomparator);
		Astar.inputFromUser();
		if(!isInitialStateisFinal){
		Astar.traversethequeue();	
		}else{
			System.out.println("YOUR INITAIL STATE IS GOAL STATE");
		}
	}
	/*
	 * Take a input from user	
	 */
	public  void inputFromUser(){
		Scanner in = new Scanner(System.in);
		System.out.println("Enter the initial state array");
		for(int i=0;i<3;i++){
			for(int j=0;j<3;j++){
				System.out.println("Enter the" +i+j+" position number");
				initialState[i][j]= in.nextInt();
			}
		}
		System.out.println("Enter the goal state array");
		for(int l=0;l<3;l++){
			for(int m=0;m<3;m++){
				System.out.println("Enter the" +l+m+" position number");
				goalState[l][m]= in.nextInt();
			}
		}
		root= new node(initialState);
		root.heuristicValue = calculateheuristicfuction(initialState);
		if(root.heuristicValue ==0)
			isInitialStateisFinal = true;
		queue.offer(root);
		numberOfNodeGenrated++;
	}

/*
 * traverse the priority Queue	
 */
public void traversethequeue(){
node currentnode;	
boolean flag=false;
	if(queue.size() ==0){
	System.out.println("Queuse is empty");
	return;
	}else{
		System.out.println("**************************GENRATED NODE*******************************");
		while((currentnode=queue.poll())!=null){
			flag = expandNode(currentnode);
			numberOfNodeExpanded++;
			if(flag == true){
				break;
			}
			expandedNode.add(currentnode);
		}
		printNodeData(currentnode);
		numberOfNodeGenrated++;
		node node=goalStateNode;
		int depthOfSolution =node.getCost();
		while(node!=null){
			stack.push(node);
			node = node.getParentLink();
		}
		System.out.println("***************************SOLUTION TREE************************");
		while(stack.size()!=0){
			node = stack.pop();
			printNodeData(node);
		}
		System.out.println("*************************************************");
		System.out.println("NUMBER OF NODE TRAVERSED " + numberOfNodeGenrated);
		System.out.println("NUMBER OF NODE EXPANDED " + numberOfNodeExpanded);
		System.out.println("SOLUTION DEPTH IS  " + depthOfSolution);
		System.out.println("*************************************************");	
	}
}

/*
 * Comparator function for priority Queue	
 */
public static Comparator<node> heuristiccomparator = new Comparator<node>() {
	@Override
	public int compare(node node1, node node2) {
		return	(int) ((node1.getHeuristicValue()+node1.getCost()) - (node2.getHeuristicValue()+node2.getCost()));
	}
};
/*
 * This function Calculate heuristic value
 * input -2D array, Output - heuristic function value	
 */
public int calculateheuristicfuction(int [][] nodeData){
	int sumOfmanhattenDistance=0,value=0;
	for(int i=0;i<3;i++){
		for(int j=0;j<3;j++){
			if(nodeData[i][j]!=0){
				value = nodeData[i][j];
				
				for(int k=0;k<3;k++){
					for(int l=0;l<3;l++){
						if(goalState[k][l]==value){
						sumOfmanhattenDistance+=Math.abs(l-j)+Math.abs(k-i);	
						k=3;
						l=3;
						}
					}
				}
			}
		}
		
	}
	return sumOfmanhattenDistance;
}

/*
 * This function check whether the generated child node is alreday present in a priority Queue
 * input -node, Output - costOfNode g(n) of node which is present in priority Queue and if not present return 0 	
 */
public static int isnodeisRepeted(node node){
int flag=0;	
int costOfNode=0;
for(node d:queue)	{
	flag =0;
	if(node.getHeuristicValue() == d.getHeuristicValue()){
		for(int i=0;i<3;i++){
			for(int j=0;j<3;j++){
				if(node.getNodeData()[i][j] != d.getNodeData()[i][j]){
					flag=1;
				}
			}
		}
		if(flag==0){
			costOfNode = d.getCost();
			return costOfNode;
		}
	}
}
return costOfNode;			
}
/*
 * This function check whether the generated child node is alreday Expanded or not.
 * input -node, Output - Flag(boolean) return true if already expanded otherwise return false  	
 */
public static boolean isNodeExpanded(node node){
int flag=0;	
for(node d:expandedNode)	{
	flag =0;
	if(node.getHeuristicValue() == d.getHeuristicValue()){
		for(int i=0;i<3;i++){
			for(int j=0;j<3;j++){
				if(node.getNodeData()[i][j] != d.getNodeData()[i][j]){
					flag=1;
				}
			}
		}
		if(flag==0){
			return true;
			}
		
		}
	}
return false;		
}
/*
 * This function print the node data
 * input -node  	
 */
public static void printNodeData(node nodeInfo){
	
	System.out.println("g(n)= "+nodeInfo.getCost() +" "+"h(n)= " +nodeInfo.getHeuristicValue());	
	for(int i=0;i<3;i++){
		for(int j=0;j<3;j++){
		System.out.print(nodeInfo.getNodeData()[i][j] + " ");
		}
		System.out.println();	
	}
}
/*
 * This function check whether the genrated child node is alreday present in a priority Queue or already expanded
 * input -array list of generated child node
 */
public static void isChildRepeated(ArrayList<node> child){
	ArrayList<node> list = new ArrayList<node>();
	for(node childNode:child){
		numberOfNodeGenrated++;
		int costOfNode =isnodeisRepeted(childNode);
		boolean isNodeExpanded =false;
		isNodeExpanded = isNodeExpanded(childNode);
		if(costOfNode!=0 && !isNodeExpanded){
			if(costOfNode>childNode.getCost()){
				node node;	
				while((node =queue.poll())!=null){
					int flag =0;
					if(node.getCost() == costOfNode){
						for(int k=0;k<3;k++){
							for(int l=0;l<3;l++){
								if(node.getNodeData()[k][l] != childNode.getNodeData()[k][l]){
									flag=1;
								}
							}
						}
						if(flag==1){
							list.add(node);
						}else{
							list.add(childNode);
						}
						
					}else{
						list.add(node);
					}
					
				}
				for(node node1:list){
					queue.offer(node1);
				}
			}
			
		}else if(costOfNode ==0 && !isNodeExpanded){
			queue.offer(childNode);
		}
	}						
}
/*
 * This function expand the node
 * input -node, Output - return true if goal state found in expanded nodes	
 */
	public  boolean expandNode(node curent){
		boolean isGoalNode = false;
		node childnode1 = new node(curent.getNodeData());
		node childnode2 = new node(curent.getNodeData());
		node childnode3 = new node(curent.getNodeData());
		node childnode4 = new node(curent.getNodeData());
		ArrayList<node> child = new ArrayList<node>();
	
		for(int i=0;i<3;i++){
			for(int j=0;j<3;j++){
				if(curent.nodeData[i][j] == 0){
					if((i==0 && j==0)){
						int newI= Math.abs(i-1);
						childnode1.nodeData[i][j]=curent.nodeData[newI][j];
						childnode1.nodeData[newI][j] =0;
						childnode1.cost = curent.cost+1;
						childnode1.heuristicValue = calculateheuristicfuction(childnode1.nodeData);	
						childnode1.ParentLink =curent;
							if(childnode1.heuristicValue ==0){
								isGoalNode =true;
								goalStateNode =childnode1;
								printNodeData(childnode1);
								return isGoalNode;
							}
						childnode2.nodeData[i][j]=curent.nodeData[i][j+1];
						childnode2.nodeData[i][j+1] =0;
						childnode2.cost = curent.cost+1;
						childnode2.heuristicValue = calculateheuristicfuction(childnode2.nodeData);
						childnode2.ParentLink =curent;
						if(childnode2.heuristicValue ==0){
							isGoalNode =true;
							goalStateNode =childnode2;
							printNodeData(childnode2);
							return isGoalNode;

						}
						printNodeData(childnode1);
						printNodeData(childnode2);
						child.add(childnode1);
						child.add(childnode2);
						isChildRepeated(child);
					}else if((i==0 && j==2)){
						int newJ = Math.abs(j-1);
						
						childnode1.nodeData[i][j]=curent.nodeData[i][newJ];
						childnode1.nodeData[i][newJ] =0;
						childnode1.cost = curent.cost+1;
						childnode1.heuristicValue = calculateheuristicfuction(childnode1.nodeData);		
						childnode1.ParentLink =curent;
							if(childnode1.heuristicValue ==0){
								isGoalNode =true;
								goalStateNode =childnode1;
								printNodeData(childnode1);
								return isGoalNode;
							}
						childnode2.nodeData[i][j]=curent.nodeData[i+1][j];
						childnode2.nodeData[i+1][j] =0;
						childnode2.cost = curent.cost+1;
						childnode2.heuristicValue = calculateheuristicfuction(childnode2.nodeData);
						childnode2.ParentLink =curent;
						
						if(childnode2.heuristicValue ==0){
							isGoalNode =true;
							goalStateNode =childnode2;
							printNodeData(childnode2);
							return isGoalNode;

						}
						child.add(childnode1);
						child.add(childnode2);
						printNodeData(childnode1);
						printNodeData(childnode2);
						isChildRepeated(child);
					}else if((i==2 && j==2)){
						int newI= Math.abs(i-1);
						int newJ = Math.abs(j-1);
						
						childnode1.nodeData[i][j]=curent.nodeData[newI][j];
						childnode1.nodeData[newI][j] =0;
						childnode1.cost = curent.cost+1;
						childnode1.heuristicValue = calculateheuristicfuction(childnode1.nodeData);	
						childnode1.ParentLink =curent;
							if(childnode1.heuristicValue ==0){
								isGoalNode =true;
								goalStateNode =childnode1;
								printNodeData(childnode1);
								return isGoalNode;
							}
						childnode2.nodeData[i][j]=curent.nodeData[i][newJ];
						childnode2.nodeData[i][newJ] =0;
						childnode2.cost = curent.cost+1;
						childnode2.heuristicValue = calculateheuristicfuction(childnode2.nodeData);
						childnode2.ParentLink =curent;
						if(childnode2.heuristicValue ==0){
							isGoalNode =true;
							goalStateNode =childnode2;
							printNodeData(childnode2);
							return isGoalNode;
						}
						child.add(childnode1);
						child.add(childnode2);
						printNodeData(childnode1);
						printNodeData(childnode2);
						isChildRepeated(child);
					}else if((i==2 && j==0)){
						int newI= Math.abs(i-1);
						
						childnode1.nodeData[i][j]=curent.nodeData[newI][j];
						childnode1.nodeData[newI][j] =0;
						childnode1.cost = curent.cost+1;
						childnode1.heuristicValue = calculateheuristicfuction(childnode1.nodeData);		
						childnode1.ParentLink =curent;
							if(childnode1.heuristicValue ==0){
								isGoalNode =true;
								goalStateNode =childnode1;
								printNodeData(childnode1);
								return isGoalNode;
							}
						childnode2.nodeData[i][j]=curent.nodeData[i][j+1];
						childnode2.nodeData[i][j+1] =0;
						childnode2.cost = curent.cost+1;
						childnode2.heuristicValue = calculateheuristicfuction(childnode2.nodeData);
						childnode2.ParentLink =curent;
						if(childnode2.heuristicValue ==0){
							isGoalNode =true;
							goalStateNode =childnode2;
							printNodeData(childnode2);
							return isGoalNode;

						}
						child.add(childnode1);
						child.add(childnode2);
						printNodeData(childnode2);
						printNodeData(childnode2);
						isChildRepeated(child);

						
					}else if((i==1 && j==0)){
						
						childnode1.nodeData[i][j]=curent.nodeData[i][j+1];
						childnode1.nodeData[i][j+1] =0;
						childnode1.cost = curent.cost+1;
						childnode1.heuristicValue = calculateheuristicfuction(childnode1.nodeData);
						childnode1.ParentLink =curent;
						if(childnode1.heuristicValue ==0){
							isGoalNode =true;
							goalStateNode =childnode1;
							printNodeData(childnode1);
							return isGoalNode;
						}
						childnode2.nodeData[i][j]=curent.nodeData[Math.abs(i-1)][j];
						childnode2.nodeData[Math.abs(i-1)][j] =0;
						childnode2.cost = curent.cost+1;
						childnode2.heuristicValue = calculateheuristicfuction(childnode2.nodeData);
						childnode2.ParentLink =curent;
						if(childnode2.heuristicValue ==0){
							isGoalNode =true;
							goalStateNode =childnode2;
							printNodeData(childnode2);
							return isGoalNode;
						}
						childnode3.nodeData[i][j]=curent.nodeData[i+1][j];
						childnode3.nodeData[i+1][j] =0;
						childnode3.cost = curent.cost+1;
						childnode3.heuristicValue = calculateheuristicfuction(childnode3.nodeData);
						childnode3.ParentLink =curent;
						if(childnode3.heuristicValue ==0){
							isGoalNode =true;
							goalStateNode =childnode3;
							printNodeData(childnode3);
							return isGoalNode;
						}
						
						child.add(childnode1);
						child.add(childnode2);
						child.add(childnode3);
						printNodeData(childnode1);
						printNodeData(childnode2);
						printNodeData(childnode3);
						isChildRepeated(child);
					}else if((i==0 && j==1)){
						
						childnode1.nodeData[i][j]=curent.nodeData[i][j+1];
						childnode1.nodeData[i][j+1] =0;
						childnode1.cost = curent.cost+1;
						childnode1.heuristicValue = calculateheuristicfuction(childnode1.nodeData);
						childnode1.ParentLink =curent;
						if(childnode1.heuristicValue ==0){
							isGoalNode =true;
							goalStateNode =childnode1;
							printNodeData(childnode1);
							return isGoalNode;
						}
						
						childnode2.nodeData[i][j]=curent.nodeData[i][Math.abs(j-1)];
						childnode2.nodeData[i][Math.abs(j-1)] =0;
						childnode2.cost = curent.cost+1;
						childnode2.heuristicValue = calculateheuristicfuction(childnode2.nodeData);
						childnode2.ParentLink =curent;
						if(childnode2.heuristicValue ==0){
							isGoalNode =true;
							goalStateNode =childnode2;
							printNodeData(childnode2);
							return isGoalNode;
						}
						childnode3.nodeData[i][j]=curent.nodeData[i+1][j];
						childnode3.nodeData[i+1][j] =0;
						childnode3.cost = curent.cost+1;
						childnode3.heuristicValue = calculateheuristicfuction(childnode3.nodeData);
						childnode3.ParentLink =curent;
						if(childnode3.heuristicValue ==0){
							isGoalNode =true;
							goalStateNode =childnode3;
							printNodeData(childnode3);
							return isGoalNode;
						}
						child.add(childnode1);
						child.add(childnode2);
						child.add(childnode3);
						printNodeData(childnode1);
						printNodeData(childnode2);
						printNodeData(childnode3);
						isChildRepeated(child);
					}else if((i==1 && j==2)){
						
						childnode1.nodeData[i][j]=curent.nodeData[Math.abs(i-1)][j];
						childnode1.nodeData[Math.abs(i-1)][j] =0;
						childnode1.cost = curent.cost+1;
						childnode1.heuristicValue = calculateheuristicfuction(childnode1.nodeData);
						childnode1.ParentLink =curent;
						if(childnode1.heuristicValue ==0){
							isGoalNode =true;
							goalStateNode =childnode1;
							printNodeData(childnode1);
							return isGoalNode;
						}
						
						childnode2.nodeData[i][j]=curent.nodeData[i][Math.abs(j-1)];
						childnode2.nodeData[i][Math.abs(j-1)] =0;
						childnode2.cost = curent.cost+1;
						childnode2.heuristicValue = calculateheuristicfuction(childnode2.nodeData);
						childnode2.ParentLink =curent;
						if(childnode2.heuristicValue ==0){
							isGoalNode =true;
							goalStateNode =childnode2;
							printNodeData(childnode2);
							return isGoalNode;
						}
						childnode3.nodeData[i][j]=curent.nodeData[i+1][j];
						childnode3.nodeData[i+1][j] =0;
						childnode3.cost = curent.cost+1;
						childnode3.heuristicValue = calculateheuristicfuction(childnode3.nodeData);
						childnode3.ParentLink =curent;
						if(childnode3.heuristicValue ==0){
							isGoalNode =true;
							goalStateNode =childnode3;
							printNodeData(childnode3);
							return isGoalNode;
						}
						
						child.add(childnode1);
						child.add(childnode2);
						child.add(childnode3);
						printNodeData(childnode1);
						printNodeData(childnode2);
						printNodeData(childnode3);
						isChildRepeated(child);
					}
					else if((i==2 && j==1)){
						
						childnode1.nodeData[i][j]=curent.nodeData[i][j+1];
						childnode1.nodeData[i][j+1] =0;
						childnode1.cost = curent.cost+1;
						childnode1.heuristicValue = calculateheuristicfuction(childnode1.nodeData);
						childnode1.ParentLink =curent;
						if(childnode1.heuristicValue ==0){
							isGoalNode =true;
							goalStateNode =childnode1;
							printNodeData(childnode1);
							return isGoalNode;
						}
						
						childnode2.nodeData[i][j]=curent.nodeData[i][Math.abs(j-1)];
						childnode2.nodeData[i][Math.abs(j-1)] =0;
						childnode2.cost = curent.cost+1;
						childnode2.heuristicValue = calculateheuristicfuction(childnode2.nodeData);
						childnode2.ParentLink =curent;
						if(childnode2.heuristicValue ==0){
							isGoalNode =true;
							goalStateNode =childnode2;
							printNodeData(childnode2);
							return isGoalNode;
						}
						childnode3.nodeData[i][j]=curent.nodeData[Math.abs(i-1)][j];
						childnode3.nodeData[Math.abs(i-1)][j] =0;
						childnode3.cost = curent.cost+1;
						childnode3.heuristicValue = calculateheuristicfuction(childnode3.nodeData);
						childnode3.ParentLink =curent;
						if(childnode3.heuristicValue ==0){
							isGoalNode =true;
							goalStateNode =childnode3;
							printNodeData(childnode3);
							return isGoalNode;
						}
						
						child.add(childnode1);
						child.add(childnode2);
						child.add(childnode3);
						printNodeData(childnode1);
						printNodeData(childnode2);
						printNodeData(childnode3);
						isChildRepeated(child);
							
						}
						else{
						
						childnode1.nodeData[i][j]=curent.nodeData[i+1][j];
						childnode1.nodeData[i+1][j] =0;
						childnode1.cost = curent.cost+1;
						childnode1.heuristicValue = calculateheuristicfuction(childnode1.nodeData);
						childnode1.ParentLink =curent;
						if(childnode1.heuristicValue ==0){
							isGoalNode =true;
							goalStateNode =childnode1;
							printNodeData(childnode1);
							return isGoalNode;
						}
						childnode2.nodeData[i][j]=curent.nodeData[Math.abs(i-1)][j];
						childnode2.nodeData[Math.abs(i-1)][j] =0;
						childnode2.heuristicValue = calculateheuristicfuction(childnode2.nodeData);
						childnode2.cost = curent.cost+1;
						childnode2.ParentLink =curent;
						if(childnode2.heuristicValue ==0){
							isGoalNode =true;
							goalStateNode =childnode2;
							printNodeData(childnode2);
							return isGoalNode;
						}
						childnode3.nodeData[i][j]=curent.nodeData[i][j+1];
						childnode3.nodeData[i][j+1]=0;
						childnode3.cost = curent.cost+1;
						childnode3.heuristicValue = calculateheuristicfuction(childnode3.nodeData);
						childnode3.ParentLink =curent;
						if(childnode3.heuristicValue ==0){
							isGoalNode =true;
							goalStateNode =childnode3;
							printNodeData(childnode3);
							return isGoalNode;
						}
						childnode4.nodeData[i][j]=curent.nodeData[i][Math.abs(j-1)];
						childnode4.nodeData[i][Math.abs(j-1)]=0;
						childnode4.cost = curent.cost+1;
						childnode4.heuristicValue = calculateheuristicfuction(childnode4.nodeData);
						childnode4.ParentLink =curent;
						if(childnode4.heuristicValue ==0){
							isGoalNode =true;
							goalStateNode =childnode4;
							printNodeData(childnode4);
							return isGoalNode;
						}
						
						child.add(childnode1);
						child.add(childnode2);
						child.add(childnode3);
						child.add(childnode4);
						printNodeData(childnode1);
						printNodeData(childnode2);
						printNodeData(childnode3);
						printNodeData(childnode4);
						isChildRepeated(child);
							
					}
						
				}
				
			}
		}
		return isGoalNode;
	}
}
