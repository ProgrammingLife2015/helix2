package tudelft.ti2806.pl3.util;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Test for the Hashable Collection
 * Created by Kasper on 21-5-2015.
 */
public class HashableCollectionTest {

	private HashableCollection<Integer> hashableCollection;

	@Test
	public void testEquals() {
		Collection<Integer> collection = new ArrayList<>();
		collection.add(5);
		collection.add(4);
		collection.add(3);

		Collection<Integer> collection2 = new ArrayList<>();
		collection2.add(4);
		collection2.add(3);

		hashableCollection = new HashableCollection<>(collection);
		HashableCollection<Integer> nullHashCollection = new HashableCollection<>(null);

		assertTrue(hashableCollection.equals(hashableCollection));
		assertFalse(hashableCollection.equals(null));
		assertFalse(hashableCollection.equals(new Object()));
		assertTrue(nullHashCollection.equals(new HashableCollection<Integer>(null)));
		assertFalse(nullHashCollection.equals(new HashableCollection<>(collection)));
		assertFalse(hashableCollection.equals(new HashableCollection<Integer>(null)));
		assertFalse(hashableCollection.equals(new HashableCollection<>(collection2)));

		assertTrue(hashableCollection.equals(new HashableCollection<>(collection)));

	}

	@Test
	public void testHashCode() {
		Collection<Integer> collection = new ArrayList<>();
		collection.add(5);
		collection.add(4);
		collection.add(3);

		hashableCollection = new HashableCollection<>(collection);
		assertEquals(hashableCollection.hashCode(), 34754);
	}
}
