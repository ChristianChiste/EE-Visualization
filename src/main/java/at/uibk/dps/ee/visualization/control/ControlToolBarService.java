package at.uibk.dps.ee.visualization.control;

import javax.swing.JToolBar;

import org.opt4j.viewer.ToolBarService;

import com.google.inject.Inject;

/**
 * The service for the buttons controlling the running, pausing, and resuming of
 * the enactment process.
 * 
 * @author Fedor Smirnov
 *
 */
public class ControlToolBarService implements ToolBarService {

	protected final ControlButtons controlButtons;

	@Inject
	public ControlToolBarService(ControlButtons controlButtons) {
		this.controlButtons = controlButtons;
	}

	@Override
	public JToolBar getToolBar() {
		JToolBar toolbar = new JToolBar("Control");
		toolbar.add(controlButtons.getPlay());
		toolbar.add(controlButtons.getPause());
		toolbar.add(controlButtons.getStop());
		return toolbar;
	}
}