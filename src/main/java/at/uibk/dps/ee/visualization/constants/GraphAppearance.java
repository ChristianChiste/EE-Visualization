package at.uibk.dps.ee.visualization.constants;

import java.awt.Color;

import at.uibk.dps.ee.core.enactable.Enactable.State;
import at.uibk.dps.ee.model.properties.PropertyServiceData;
import at.uibk.dps.ee.model.properties.PropertyServiceFunction;
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
	protected static final Color colorFunctionModel = Graphics.GREEN;
	protected static final Color colorFunctionWaiting = Graphics.GRAY;
	protected static final Color colorFunctionReady = Graphics.YELLOW;
	protected static final Color colorFunctionRunning = Graphics.BLUE;
	protected static final Color colorFunctionFinished = Graphics.GREEN;

	protected static final Color colorDataModel = Graphics.BLUE;
	protected static final Color colorDataUnavailable = Graphics.GRAY;
	protected static final Color colorDataAvailable = Graphics.GREEN;

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
	 * Returns the color for the given node when draws as model visualization.
	 * 
	 * @param node the given node
	 * @return the color for the given node.
	 */
	public static final Color getColorModel(Node node) {
		if (node instanceof Communication) {
			return colorDataModel;
		} else if (node instanceof Task) {
			return colorFunctionModel;
		} else {
			throw new IllegalArgumentException("Unknown type of node: " + node.getId());
		}
	}

	/**
	 * Returns the color for the given node when draws as process visualization.
	 * 
	 * @param node the given node
	 * @return the color for the given node.
	 */
	public static final Color getColorProcess(Node node) {
		if (node instanceof Communication) {
			// data node
			return PropertyServiceData.isDataAvailable((Task) node) ? colorDataAvailable : colorDataUnavailable;
		} else if (node instanceof Task) {
			// function node
			State state = PropertyServiceFunction.getEnactableState((Task) node);
			switch (state) {
			case WAITING:
				return colorFunctionWaiting;
			case RUNNING:
				return colorFunctionRunning;
			case READY:
				return colorFunctionReady;
			case FINISHED:
				return colorFunctionFinished;
			default:
				throw new IllegalStateException("Unexpected enactable state " + state.name());
			}
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
