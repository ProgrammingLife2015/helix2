package tudelft.ti2806.pl3.util;

import org.hamcrest.core.IsNot;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import tudelft.ti2806.pl3.data.Genome;
import tudelft.ti2806.pl3.data.graph.node.DataNode;
import tudelft.ti2806.pl3.testutil.UtilTest;
import tudelft.ti2806.pl3.visualization.wrapper.NodeWrapper;
import tudelft.ti2806.pl3.visualization.wrapper.operation.WrapperOperation;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class OrderedListUtilTest {
	private static List<List<NodeWrapper>> listsToCombine;
	private static List<NodeWrapper[]> ordersToTest;
	
	/**
	 * Run before each test.
	 * 
	 * @throws Exception
	 *             when an exception is thrown
	 */
	@Before
	public void setUp() throws Exception {
		listsToCombine = new ArrayList<List<NodeWrapper>>(5);
		ordersToTest = new ArrayList<NodeWrapper[]>(5);
		List<NodeWrapper> list = new ArrayList<NodeWrapper>(3);
		list.add(new TestWrapper("A"));
		list.add(new TestWrapper("B"));
		list.add(new TestWrapper("C"));
		listsToCombine.add(list);
		ordersToTest.add(list.toArray(new NodeWrapper[3]));
		list = new ArrayList<NodeWrapper>(4);
		list.add(new TestWrapper("A"));
		list.add(new TestWrapper("X"));
		list.add(new TestWrapper("C"));
		list.add(new TestWrapper("D"));
		listsToCombine.add(list);
		ordersToTest.add(list.toArray(new NodeWrapper[4]));
		list = new ArrayList<NodeWrapper>(3);
		list.add(new TestWrapper("E"));
		list.add(new TestWrapper("F"));
		list.add(new TestWrapper("G"));
		listsToCombine.add(list);
		ordersToTest.add(list.toArray(new NodeWrapper[3]));
		list = new ArrayList<NodeWrapper>(3);
		list.add(new TestWrapper("A"));
		list.add(new TestWrapper("F"));
		list.add(new TestWrapper("D"));
		listsToCombine.add(list);
		ordersToTest.add(list.toArray(new NodeWrapper[3]));
		list = new ArrayList<NodeWrapper>(1);
		list.add(new TestWrapper("P"));
		listsToCombine.add(list);
		ordersToTest.add(list.toArray(new NodeWrapper[1]));
	}
	
	@Test
	public void mergeSuccesTest() {
		List<NodeWrapper> list = OrderedListUtil
				.mergeOrderedLists(listsToCombine);
		ArrayOrderMatcher matcher = new ArrayOrderMatcher(
				list.toArray(new NodeWrapper[8]));
		for (NodeWrapper[] order : ordersToTest) {
			Assert.assertThat(order, matcher);
		}
		// Test for the Matcher itself
		Assert.assertThat(new NodeWrapper[] { new TestWrapper("B"),
				new TestWrapper("A") }, new IsNot<NodeWrapper[]>(matcher));
	}
	
	@Test
	public void mergeFailTest() {
		List<NodeWrapper> list = new ArrayList<NodeWrapper>(2);
		list.add(new TestWrapper("D"));
		list.add(new TestWrapper("A"));
		listsToCombine.add(list);
		Assert.assertNull(OrderedListUtil.mergeOrderedLists(listsToCombine));
	}
	
	@Test
	public void utilConstructorTest() throws NoSuchMethodException,
			IllegalAccessException, InvocationTargetException,
			InstantiationException {
		new UtilTest(OrderedListUtil.class).testConstructorIsPrivate();
	}
	
	private static class TestWrapper extends NodeWrapper {
		private String name;
		
		private TestWrapper(String name) {
			this.name = name;
		}
		
		@Override
		public String toString() {
			return name;
		}
		
		@Override
		public long getXStart() {
			return 0;
		}
		
		@Override
		public long getXEnd() {
			return 0;
		}
		
		@Override
		public long getWidth() {
			return 0;
		}
		
		@Override
		public String getIdString() {
			return null;
		}
		
		@Override
		public Set<Genome> getGenome() {
			return new HashSet<Genome>();
		}
		
		@Override
		public void calculate(WrapperOperation wrapperSequencer,
				NodeWrapper container) {
		}

		@Override
		public void collectDataNodes(List<DataNode> list) {

		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			return result;
		}
		
		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			TestWrapper other = (TestWrapper) obj;
			if (name == null) {
				if (other.name != null) {
					return false;
				}
			} else if (!name.equals(other.name)) {
				return false;
			}
			return true;
		}

	}
}
