package sag.is;

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
