package at.uibk.dps.ee.visualization.control;

import javax.swing.JButton;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.opt4j.core.config.Icons;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import at.uibk.dps.ee.control.command.Control;
import at.uibk.dps.ee.core.ControlStateListener;
import at.uibk.dps.ee.core.EnactmentState;
import at.uibk.dps.ee.core.exception.StopException;

/**
 * The control buttons enable starting, pausing, and resuming the enactment.
 * 
 * @author Fedor Smirnov
 *
 */
@Singleton
public class ControlButtons implements ControlStateListener {

  protected final Control control;

  protected JButton play;
  protected JButton pause;
  protected JButton stop;

  @Inject
  public ControlButtons(Control control) {
    this.control = control;
    control.addListener(this);
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (ClassNotFoundException | UnsupportedLookAndFeelException | IllegalAccessException
        | InstantiationException exc) {
      throw new IllegalStateException(
          "Exception thrown when setting the look & feel of the control buttons.");
    }

    construct();
  }

  /**
   * Constructs the buttons
   */
  protected final void construct() {
    play = new JButton("", Icons.getIcon(Icons.CONTROL_START));
    pause = new JButton("", Icons.getIcon(Icons.CONTROL_PAUSE));
    stop = new JButton("", Icons.getIcon(Icons.CONTROL_STOP));

    play.setToolTipText("Start");
    pause.setToolTipText("Pause");
    stop.setToolTipText("Stop");

    play.setFocusable(false);
    pause.setFocusable(false);
    stop.setFocusable(false);

    play.addActionListener(e -> {
      control.play();
    });
    pause.addActionListener(e -> {
      control.pause();
    });

    stop.addActionListener(e -> {
      control.stop();
    });
  }

  public JButton getPlay() {
    return play;
  }

  public JButton getPause() {
    return pause;
  }

  public JButton getStop() {
    return stop;
  }

  @Override
  public void reactToStateChange(EnactmentState previousState, EnactmentState currentState)
      throws StopException {
    if (!control.isInit() || control.getEnactmentState().equals(EnactmentState.RUNNING)) {
      pause.setEnabled(true);
      play.setEnabled(false);
    } else if (control.getEnactmentState().equals(EnactmentState.PAUSED)) {
      pause.setEnabled(false);
      play.setEnabled(true);
    } else if (control.getEnactmentState().equals(EnactmentState.STOPPED)) {
      play.setEnabled(false);
      pause.setEnabled(false);
      stop.setEnabled(false);
    } else {
      throw new IllegalArgumentException(
          "Cannot yet handle anything besides running, pausing, and stopping.");
    }
  }
}
