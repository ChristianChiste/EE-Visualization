package at.uibk.dps.ee.visualization.model;

import static org.junit.Assert.*;

import org.junit.Test;

import at.uibk.dps.ee.model.graph.EnactmentGraph;
import net.sf.opendse.model.Communication;
import net.sf.opendse.model.Dependency;
import net.sf.opendse.model.Element;
import net.sf.opendse.model.Resource;
import net.sf.opendse.model.Task;

import static org.mockito.Mockito.mock;

public class GraphPanelFormatModelTest {

	@Test
	public void testElementCopyOkay() {
		EnactmentGraph mockGraph = mock(EnactmentGraph.class);
		GraphPanelFormatModel tested = new GraphPanelFormatModel(mockGraph);
		
		Task task = new Task("t");
		task.setAttribute("attrT", 1);
		
		Communication comm = new Communication("c");
		comm.setAttribute("commT", "bla");
		
		Dependency dep = new Dependency("dep");
		dep.setAttribute("attrD", 3.2);
		
		Element result1 = tested.copy(task);
		Element result2 = tested.copy(comm);
		Element result3 = tested.copy(dep);
		
		assertTrue(result1 instanceof Task);
		assertTrue(result2 instanceof Communication);
		assertTrue(result3 instanceof Dependency);
		
		assertEquals(1, (int) result1.getAttribute("attrT"));
		assertEquals("bla", result2.getAttribute("commT"));
		assertEquals(3.2, result3.getAttribute("attrD"), 0.0);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testElementCopyUnknown() {
		EnactmentGraph mockGraph = mock(EnactmentGraph.class);
		GraphPanelFormatModel tested = new GraphPanelFormatModel(mockGraph);
		Resource res = new Resource("res");
		tested.copy(res);
	}
}
