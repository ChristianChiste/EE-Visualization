package at.uibk.dps.ee.visualization.process;

import java.awt.Color;

import at.uibk.dps.ee.model.graph.EnactmentGraph;
import at.uibk.dps.ee.visualization.constants.GraphAppearance;
import at.uibk.dps.ee.visualization.model.GraphPanelFormatModel;
import at.uibk.dps.ee.visualization.utils.UtilsVizGraph;
import net.sf.opendse.model.Edge;
import net.sf.opendse.model.Graph;
import net.sf.opendse.model.Node;

/**
 * 
 * The {@link GraphPanelFormalProcess} defines how the enactment process is
 * visualized by the {@link GraphPanelProcess}.
 * 
 * @author Fedor Smirnov
 *
 */
public class GraphPanelFormalProcess extends GraphPanelFormatModel {

	public GraphPanelFormalProcess(EnactmentGraph originalGraph) {
		super(originalGraph);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Graph<Node, Edge> getGraph() {
		Graph<?, ?> result = UtilsVizGraph.generateGraphToDraw(originalGraph);
		return (Graph<Node, Edge>) result;
	}

	@Override
	public Color getColor(Node node) {
		return GraphAppearance.getColorProcess(node);
	}
}
