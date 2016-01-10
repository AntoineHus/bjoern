package exporters.structures.interpretations;

import java.util.LinkedList;
import java.util.List;

import exporters.nodeStore.Node;
import exporters.nodeStore.NodeTypes;
import exporters.structures.edges.DirectedEdge;


public class Function extends Node
{

	FunctionContent content;
	List<DirectedEdge> edges = new LinkedList<DirectedEdge>();

	private String name = "";

	public Function(long addr)
	{
		content = new FunctionContent(addr);
		setType(NodeTypes.FUNCTION);
		setAddr(addr);
	}

	public FunctionContent getContent()
	{
		return content;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return name;
	}

	public void setContent(FunctionContent content)
	{
		this.content = content;
	}

	public void addEdge(DirectedEdge edge)
	{
		edges.add(edge);
	}

	public List<DirectedEdge> getEdges()
	{
		return edges;
	}

	public void deleteEdges()
	{
		edges = null;
	}

	public void deleteContent()
	{
		content = null;
	}

}
