package at.uibk.dps.ee.visualization.model;

import java.awt.Color;
import java.awt.Shape;

import at.uibk.dps.ee.model.graph.EnactmentGraph;
import at.uibk.dps.ee.visualization.constants.GraphAppearance;
import at.uibk.dps.ee.visualization.constants.GraphAppearance.EGNodeShape;
import at.uibk.dps.ee.visualization.utils.UtilsVizGraph;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.DirectedGraph;
import net.sf.opendse.model.Edge;
import net.sf.opendse.model.Graph;
import net.sf.opendse.model.Node;
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
		this.graphToDraw = UtilsVizGraph.generateGraphToDraw(originalGraph);
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
		return GraphAppearance.getColorModel(node);
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
		switch (shapeEnum) {
		case Ellipse: {
			return shapes.getEllipse(node);
		}
		case Rectangle: {
			return shapes.getRectangle(node);
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + shapeEnum.name());
		}
	}
}
