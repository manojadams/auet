package sag.is;

//service node containing: 
//service type, service complexity and service size
public class SvcNode {
	public String nodeType;
	public long nodeSize;
	public String nodeComplexty;
	public String nodePath;
	public SvcNode(String nodeType, long nodeSize, String nodeComplexity, String nodePath){
		this.nodeType = nodeType;
		this.nodeSize = nodeSize;
		this.nodeComplexty = nodeComplexity;
		this.nodePath = nodePath;
	}
}
