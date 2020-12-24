package at.uibk.dps.ee.visualization.process;

import javax.swing.JPanel;

import org.opt4j.viewer.Viewport;
import org.opt4j.viewer.Widget;

import com.google.inject.Inject;

import at.uibk.dps.ee.model.graph.EnactmentGraph;
import at.uibk.dps.ee.model.graph.EnactmentGraphProvider;

/**
 * The widget to display the state of the enactment.
 * 
 * @author Fedor Smirnov
 *
 */
public class EnactmentProcessWidget implements Widget{

	protected final EnactmentGraph enactmentGraph;
	protected GraphPanelProcess panel;
	
	@Inject
	public EnactmentProcessWidget(EnactmentGraphProvider graphProvider) {
		this.enactmentGraph = graphProvider.getEnactmentGraph();
		this.panel = new GraphPanelProcess(enactmentGraph);
	}
	
	@Override
	public JPanel getPanel() {
		return panel;
	}

	@Override
	public void init(Viewport viewport) {
		// Nothing to do here
	}
}
