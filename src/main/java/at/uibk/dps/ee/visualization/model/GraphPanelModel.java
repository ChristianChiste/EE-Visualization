package at.uibk.dps.ee.visualization.model;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import at.uibk.dps.ee.model.graph.EnactmentGraph;
import net.sf.opendse.visualization.ElementSelection;
import net.sf.opendse.visualization.GraphPanel;

/**
 * Panel for the visualization of the {@link EnactmentGraph}.
 * 
 * @author Fedor Smirnov
 *
 */
public class GraphPanelModel extends JPanel {

	private static final long serialVersionUID = 1L;

	protected GraphPanel graphPanel = null;

	/**
	 * Standard constructor
	 * 
	 * @param graph the graph to visualize
	 */
	public GraphPanelModel(final EnactmentGraph graph) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (UnsupportedLookAndFeelException | IllegalAccessException | InstantiationException
				| ClassNotFoundException lfExc) {
			throw new IllegalStateException("Exception during the set of the Look & Feel.");
		}
		setEnactmentGraph(graph);
	}

	/**
	 * Repaints the enactment graph (called on initial opening, updates, and
	 * changes).
	 * 
	 * @param enactmentGraph the enactment graph to visualize
	 */
	protected void setEnactmentGraph(final EnactmentGraph enactmentGraph) {
		if (graphPanel != null) {
			this.remove(graphPanel);
		}
		ElementSelection selection = new ElementSelection();
		graphPanel = new GraphPanel(new GraphPanelFormatModel(enactmentGraph), selection);
		this.setLayout(new BorderLayout());
		this.add(graphPanel);
		revalidate();
		repaint();
	}
}
