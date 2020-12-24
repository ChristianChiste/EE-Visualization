package at.uibk.dps.ee.visualization.utils;

import at.uibk.dps.ee.model.graph.EnactmentGraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import net.sf.opendse.model.Communication;
import net.sf.opendse.model.Dependency;
import net.sf.opendse.model.Element;
import net.sf.opendse.model.Task;

/**
 * Static container for the methods used when drawing enactment graphs.
 * 
 * @author Fedor Smirnov
 */
public final class UtilsVizGraph {

	/**
	 * No constructor.
	 */
	private UtilsVizGraph() {
	}

	/**
	 * Generates an enactment graph which is used for the visualization (adjust the
	 * method if the displayed graph shall differ from the actual enactment graph).
	 * 
	 * @param original the original graph
	 * @return an enactment graph which is used for the visualization
	 */
	public static EnactmentGraph generateGraphToDraw(final EnactmentGraph original) {
		EnactmentGraph result = new EnactmentGraph();
		// add all vertices
		for (Task task : original) {
			result.addVertex((Task) copy(task));
		}
		// add all edges
		for (Dependency dep : original.getEdges()) {
			Dependency copyDep = (Dependency) copy(dep);
			Task srcOrigin = original.getSource(dep);
			Task dstOrigin = original.getDest(dep);
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
	protected static Element copy(Element original) {
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
		result.setParent(original);
		return result;
	}
}
