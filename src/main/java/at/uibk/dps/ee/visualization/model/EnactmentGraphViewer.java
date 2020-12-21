package at.uibk.dps.ee.visualization.model;

import java.awt.BorderLayout;
import java.awt.Frame;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import at.uibk.dps.ee.model.graph.EnactmentGraph;
import at.uibk.dps.ee.visualization.constants.ConstantsVisulization;

/**
 * Class offering a static method to visulize a given {@link EnactmentGraph}.
 * 
 * @author Fedor Smirnov
 *
 */
public final class EnactmentGraphViewer {

	private EnactmentGraphViewer() {
	}

	/**
	 * Visualizes the given {@link EnactmentGraph}.
	 * 
	 * @param enactmentGraph the graph to visulize
	 */
	public static void view(EnactmentGraph enactmentGraph) {
		final JFrame frame = new JFrame();
		final GraphPanelModel panel = new GraphPanelModel(enactmentGraph);
		frame.setLayout(new BorderLayout());
		frame.add(panel);
		frame.setExtendedState(Frame.MAXIMIZED_BOTH);
		frame.setTitle(ConstantsVisulization.ModelViewerTitle);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
}
