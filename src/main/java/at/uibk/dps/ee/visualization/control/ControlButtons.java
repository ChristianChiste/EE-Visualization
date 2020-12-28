package at.uibk.dps.ee.visualization.control;

import javax.swing.JButton;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.opt4j.core.config.Icons;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import at.uibk.dps.ee.control.command.Control;
import at.uibk.dps.ee.core.EnactmentState;

/**
 * The control buttons enable starting, pausing, and resuming the enactment.
 * 
 * @author Fedor Smirnov
 *
 */
@Singleton
public class ControlButtons {

	protected final Control control;

	protected JButton play;
	protected JButton pause;

	@Inject
	public ControlButtons(Control control) {
		this.control = control;
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | UnsupportedLookAndFeelException | IllegalAccessException
				| InstantiationException exc) {
			throw new IllegalStateException("Exception thrown when setting the look & feel of the control buttons.");
		}

		construct();
		update();
	}

	/**
	 * Constructs the buttons
	 */
	protected final void construct() {
		play = new JButton("", Icons.getIcon(Icons.CONTROL_START));
		pause = new JButton("", Icons.getIcon(Icons.CONTROL_PAUSE));

		play.setToolTipText("Start");
		pause.setToolTipText("Pause");

		play.setFocusable(false);
		pause.setFocusable(false);


		play.addActionListener(e -> {
			control.play();
			update();
		});
		pause.addActionListener(e -> {
			control.pause();
			update();
		});
	}

	public JButton getPlay() {
		return play;
	}

	public JButton getPause() {
		return pause;
	}

	/**
	 * Updates the status of the buttons (en-/disabled depending on the control
	 * state)
	 */
	protected void update() {
		if (!control.isInit() || control.getEnactmentState().equals(EnactmentState.RUNNING)) {
			pause.setEnabled(true);
			play.setEnabled(false);
		} else if (control.getEnactmentState().equals(EnactmentState.PAUSED)) {
			pause.setEnabled(false);
			play.setEnabled(true);
		} else {
			throw new IllegalArgumentException("Cannot handle anything besides running and paused yet.");
		}
	}
}