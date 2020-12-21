package at.uibk.dps.ee.visualization.model;

import java.awt.Color;
import java.awt.Shape;

import at.uibk.dps.ee.model.graph.EnactmentGraph;
import at.uibk.dps.ee.visualization.constants.GraphAppearance;
import at.uibk.dps.ee.visualization.constants.GraphAppearance.EGNodeShape;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import net.sf.opendse.model.Communication;
import net.sf.opendse.model.Dependency;
import net.sf.opendse.model.Edge;
import net.sf.opendse.model.Element;
import net.sf.opendse.model.Graph;
import net.sf.opendse.model.Node;
import net.sf.opendse.model.Task;
import net.sf.opendse.visualization.AbstractGraphPanelFormat;
import net.sf.opendse.visualization.Graphics;
import net.sf.opendse.visualization.LocalEdge;
import net.sf.opendse.visualization.algorithm.DistanceFlowLayout;

/**
 * The {@link GraphPanelFormatModel} defines how an {@link EnactmentGraph} is
 * visualized by the {@link GraphPanelModel}.
 * 
 * @author Fedor Smirnov
 *
 */
public class GraphPanelFormatModel extends AbstractGraphPanelFormat {

	protected final EnactmentGraph originalGraph;
	protected final EnactmentGraph graphToDraw;

	public GraphPanelFormatModel(EnactmentGraph originalGraph) {
		this.originalGraph = originalGraph;
		this.graphToDraw = generateGraphToDraw(originalGraph);
	}

	/**
	 * Creates the graph which is used for the visualization. Alter the method if
	 * the visualization shall display anything which deviates from the way it is
	 * implemented in the actual {@link EnactmentGraph}.
	 * 
	 * @param originalGraph the original {@link EnactmentGraph}
	 * @return the graph which is actually drawn
	 */
	protected EnactmentGraph generateGraphToDraw(final EnactmentGraph originalGraph) {
		EnactmentGraph result = new EnactmentGraph();
		// add all vertices
		for (Task task : originalGraph) {
			result.addVertex((Task) copy(task));
		}
		// add all edges
		for (Dependency dep : originalGraph.getEdges()) {
			Dependency copyDep = (Dependency) copy(dep);
			Task srcOrigin = originalGraph.getSource(dep);
			Task dstOrigin = originalGraph.getDest(dep);
			Task srcCopy = result.getVertex(srcOrigin.getId());
			Task dstCopy = result.getVertex(dstOrigin.getId());
			result.addEdge(copyDep, srcCopy, dstCopy, EdgeType.DIRECTED);
		}
		return result;
	}

	/**
	 * Returns a copy of the given element, identical in everything but the
	 * reference.
	 * 
	 * @param original the original element
	 * @return the element copy
	 */
	protected Element copy(Element original) {
		// make the object
		Element result = null;
		if (original instanceof Communication) {
			result = new Communication(original.getId());
		} else if (original instanceof Task) {
			result = new Task(original.getId());
		} else if (original instanceof Dependency) {
			result = new Dependency(original.getId());
		} else {
			throw new IllegalArgumentException("Unknown element type for element " + original.getId());
		}
		// copy the attributes
		for (String attrName : original.getAttributeNames()) {
			result.setAttribute(attrName, original.getAttribute(attrName));
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Graph<Node, Edge> getGraph() {
		Graph<?, ?> result = graphToDraw;
		return (Graph<Node, Edge>) result;
	}

	@Override
	public Layout<Node, LocalEdge> getLayout(DirectedGraph<Node, LocalEdge> graph) {
		final Layout<Node, LocalEdge> result = new DistanceFlowLayout<Node, LocalEdge>(graph);
		return result;
	}

	@Override
	public Color getColor(Edge edge) {
		return Graphics.BLACK;
	}

	@Override
	public Shape getShape(Node node) {
		return getShapeForEnum(GraphAppearance.getShape(node), node);
	}

	@Override
	public int getSize(Node node) {
		return GraphAppearance.getSize(node);
	}
	
	@Override
	public Color getColor(Node node) {
		return GraphAppearance.getColor(node);
	}

	@Override
	public boolean isActive(Node node) {
		return true;
	}

	@Override
	public boolean isActive(Edge edge, Node n0, Node n1) {
		return true;
	}

	/**
	 * Returns a shape object for the enum used by the configuration class.
	 * 
	 * @param shapeEnum the shape enum
	 * @param node      the node with the corresponding shape
	 * @return the shape object
	 */
	protected Shape getShapeForEnum(EGNodeShape shapeEnum, Node node) {
		return switch (shapeEnum) {
		case Ellipse: {
			yield shapes.getEllipse(node);
		}
		case Rectangle: {
			yield shapes.getRectangle(node);
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + shapeEnum.name());
		};
	}
}
