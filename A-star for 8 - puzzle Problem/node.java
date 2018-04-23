
public class node {
	int [][]nodeData= new int[3][3];
	int heuristicValue;
	int cost;
	node ParentLink;
	node(int [][]nodeData){
		for(int i=0;i<3;i++){
			for(int j=0;j<3;j++){
				this.nodeData[i][j]= nodeData[i][j];
				
			}
		}
		cost =0;
		heuristicValue=0;
		this.ParentLink=null;
		}
	
	public node getParentLink() {
		return ParentLink;
	}

	public void setParentLink(node parentLink) {
		ParentLink = parentLink;
	}
	public int[][] getNodeData() {
		return nodeData;
	}
	public void setNodeData(int[][] nodeData) {
		this.nodeData = nodeData;
	}
	public int getHeuristicValue() {
		return heuristicValue;
	}
	public void setHeuristicValue(int heuristicValue) {
		this.heuristicValue = heuristicValue;
	}
	public int getCost() {
		return cost;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}
	
	
	}
	