package at.uibk.dps.ee.visualization.constants;

import java.awt.Color;

import net.sf.opendse.model.Communication;
import net.sf.opendse.model.Node;
import net.sf.opendse.model.Task;
import net.sf.opendse.visualization.Graphics;

/**
 * Static method container for the configuration of the appearance of the graph
 * visualizations.
 * 
 * @author Fedor Smirnov
 *
 */
public final class GraphAppearance {

	/**
	 * The node shapes used for the visualization of enactment graphs.
	 * 
	 * @author Fedor Smirnov
	 *
	 */
	public enum EGNodeShape {
		Ellipse, Rectangle
	}

	// shapes
	protected static final EGNodeShape shapeFunction = EGNodeShape.Ellipse;
	protected static final EGNodeShape shapeData = EGNodeShape.Rectangle;

	// colors
	protected static final Color colorFunction = Graphics.GREEN;
	protected static final Color colorData = Graphics.BLUE;

	// sizes
	protected static final int sizeFunction = 20;
	protected static final int sizeData = 15;

	/**
	 * No constructor.
	 */
	private GraphAppearance() {
	}

	/**
	 * Returns the shape enum for the given node.
	 * 
	 * @param node the given node
	 * @return the shape enum for the given node
	 */
	public static final EGNodeShape getShape(Node node) {
		if (node instanceof Communication) {
			return shapeData;
		} else if (node instanceof Task) {
			return shapeFunction;
		} else {
			throw new IllegalArgumentException("Unknown type of node: " + node.getId());
		}
	}
	
	/**
	 * Returns the color for the given node.
	 * 
	 * @param node the given node
	 * @return the color for the given node.
	 */
	public static final Color getColor(Node node) {
		if (node instanceof Communication) {
			return colorData;
		} else if (node instanceof Task) {
			return colorFunction;
		} else {
			throw new IllegalArgumentException("Unknown type of node: " + node.getId());
		}
	}

	/**
	 * Returns the size of the given node.
	 * 
	 * @param node the given node
	 * @return the size of the given node
	 */
	public static final int getSize(Node node) {
		if (node instanceof Communication) {
			return sizeData;
		} else if (node instanceof Task) {
			return sizeFunction;
		} else {
			throw new IllegalArgumentException("Unknown type of node: " + node.getId());
		}
	}
}
