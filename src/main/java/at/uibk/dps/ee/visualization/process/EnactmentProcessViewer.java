package at.uibk.dps.ee.visualization.process;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

import org.opt4j.core.start.Constant;
import org.opt4j.viewer.Viewport;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import at.uibk.dps.ee.core.enactable.Enactable;
import at.uibk.dps.ee.core.enactable.Enactable.State;
import at.uibk.dps.ee.visualization.constants.ConstantsVisulization;
import at.uibk.dps.ee.core.enactable.EnactableStateListener;
import at.uibk.dps.ee.core.enactable.EnactmentStateListener;

/**
 * The {@link EnactmentProcessViewer} is used to visualize the enactment
 * process.
 * 
 * @author Fedor Smirnov
 *
 */
@Singleton
public class EnactmentProcessViewer implements EnactableStateListener, EnactmentStateListener {

	// config attributes
	protected final boolean closeOnStop;

	// graphic elements
	protected JFrame frame = null; // the overall window
	protected final Viewport viewPort; // the viewport for the visualizations

	protected final EnactmentProcessWidget widget;

	@Inject
	public EnactmentProcessViewer(Viewport viewport, EnactmentProcessWidget enactmentWidget,
			@Constant(namespace = EnactmentProcessViewer.class, value = "closeOnTerminate") boolean closeOnStop) {
		this.closeOnStop = closeOnStop;
		this.viewPort = viewport;
		this.widget = enactmentWidget;
	}

	@Override
	public void enactmentStarted() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | UnsupportedLookAndFeelException | IllegalAccessException
				| InstantiationException exc) {
			throw new IllegalStateException("Exception thrown when setting the look & feel of the enactment viewer.");
		}
		EnactmentProcessViewer.this.frame = new JFrame();
		final JFrame frame = EnactmentProcessViewer.this.frame;

		// TODO Adjust when we have a logo (preferably Apollo)
		// ImageIcon logo = Icons.getIcon(Icons.OPT4J);
		// frame.setIconImage(logo.getImage());
		frame.setTitle(ConstantsVisulization.ProcessViewerTitle);
		frame.setLayout(new BorderLayout());
		frame.setPreferredSize(new Dimension(800, 600));

		viewPort.init();
		frame.add(viewPort.get(), BorderLayout.CENTER);

		// TODO we could add an actual termination of the enactment if the window is
		// closed (as well as add the option for a termination)

		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		viewPort.addWidget(widget);
	}

	@Override
	public void enactmentTerminated() {
		if (closeOnStop && frame != null) {
			frame.dispose();
		}
	}

	@Override
	public void enactableStateChanged(Enactable enactable, State previousState, State currentState) {
		if (frame != null) {
			frame.repaint();
		}
	}
}
