package at.uibk.dps.ee.visualization.process;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import at.uibk.dps.ee.model.graph.EnactmentGraph;
import net.sf.opendse.visualization.ElementSelection;
import net.sf.opendse.visualization.GraphPanel;

/**
 * The panel for painting the enactment state (the enactment graph, the states
 * of the nodes, etc.)
 * 
 * @author Fedor Smirnov
 */
public class GraphPanelProcess extends JPanel{

	private static final long serialVersionUID = 1L;
	
	protected final EnactmentGraph graph;
	
	protected GraphPanel graphPanel = null;
	
	public GraphPanelProcess(EnactmentGraph graph) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (UnsupportedLookAndFeelException | IllegalAccessException | InstantiationException
				| ClassNotFoundException lfExc) {
			throw new IllegalStateException("Exception during the set of the Look & Feel.");
		}
		this.setLayout(new BorderLayout());
		this.graph = graph;
		createGraphPanel();
	}
	
	protected void createGraphPanel() {
		if (this.graphPanel != null) {
			this.remove(graphPanel);
		}
		graphPanel = new GraphPanel(new GraphPanelFormatProcess(graph), new ElementSelection());
		this.add(graphPanel);
		revalidate();
		repaint();
	}
	
	public void update() {
		System.out.println("created");
		createGraphPanel();
	}
}
