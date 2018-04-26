8 PUZZLE PROBLEM

8 Puzzle problem is to reach goal state from initial state, where 1 element in the 9 elements is represented by blank.

8 Puzzle FORMULATION:

1.	Calculate Manhatton distance (h)
2.	Calculate cost to visit the node (g)
3.	Select Node with lowest f = (g+ h) value.
4.	Traversal of tree. 

State Space Representation

public class Node {
	int [][]nodeData= new int[3][3];

	int heuristicValue;

	int cost;

	node ParentLink;
}

Each Node represents
1.	state : 3*3 matrix
2.	cost : Cost to reach the state
3.	Heuristic Value : Heuristic function . Manhattan Heuristic is used
4.	Node parent : a state to save the reference of parent node.
 
Global Variables for A Star Implementation

public class Astar {

public static PriorityQueue<node> queue ;
public node goalStateNode=null;
public static boolean isInitialStateisFinal=false;
public static Stack<node> stack = new Stack<node>();
public static ArrayList<node> expandedNode = new ArrayList<node>();
public static node root ;
public static int [][]initialState = new int[3][3]; 
public static int [][]goalState = new int[3][3]; 
 }




1.	initialState : Node to store the initial state
2.	goalState : Node to store the goal state
3.	root : node which is storing initial state i.e. root element.
4.	queue : A priority queue to add a nodes which are generated only once
5.	expandedNode : List of Nodes generated and expanded.
6.	isInitialStateisFinal : Flag is indicate that whether initial state is final state.
7.	goalStateNode : saved the state of goal node. 
8.	stack : used to store the nodes from goal state to final state

Functions/Procedures

1) inputFromUser -take the initial and final state from user.
2) traversetheQueue – traverse the priority queue. 
3)heuristiccomparator -It is a comparator function for priority queue to sort it by f(n)
4)calculateHeuristicfunction -This function calculate the h(n) and return it.
5)isnodeRepeated :This function check whether the generated node is already present in the priority queue
6)isNodeExpanded -check whether the generated node is already expanded
7)printNodeData- it print the data of node.
8)isChildRepeated-check whether the child node is already present in priority queue or already expanded
9)expandNode – This function generate the child node.

Program structure and flow
1)Set the initial state and final state for 8-puzzle problem
2)Assign initial state as a root node and insert it in priority queue.
3)Check if the root node is a goal state or not. If yes, then exit the program.
4)while goal state not found repeat the following steps
4.a) pop the element from priority queue and pass it to expand.
4.b) Generate it’s child.
4.c) for each child repeat the following steps
4.c.a) check whether the child is already generated or not
4.c.b) if the node is already expanded then do nothing else check whether it is already present in priority queue
4.c.c) If present then check f(n) of new generated node < f(n) of node which is already present in priority queue.
4.c.d) if f(n)new<f(n)Old then discard old ndoe and replace it with new node.
4.d) return true if the goal state found
5)print the sequence.
