/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.metamodel.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import junit.framework.TestCase;

public class CollectionUtilsTest extends TestCase {

	public void testArray1() throws Exception {
		String[] result = CollectionUtils.array(new String[] { "foo", "bar" },
				"hello", "world");
		assertEquals("[foo, bar, hello, world]", Arrays.toString(result));
	}

	public void testArray2() throws Exception {
		Object[] existingArray = new Object[] { 'c' };
		Object[] result = CollectionUtils.array(existingArray, "foo", 1, "bar");

		assertEquals("[c, foo, 1, bar]", Arrays.toString(result));
	}

	public void testConcat() throws Exception {
		List<String> list1 = new ArrayList<String>();
		list1.add("hello");
		list1.add("hello");
		list1.add("world");
		List<String> list2 = new ArrayList<String>();
		list2.add("howdy");
		list2.add("world");
		List<String> list3 = new ArrayList<String>();
		list3.add("hi");
		list3.add("world");

		List<String> result = CollectionUtils.concat(true, list1, list2, list3);
		assertEquals("[hello, world, howdy, hi]", result.toString());
		assertEquals(4, result.size());

		result = CollectionUtils.concat(false, list1, list2, list3);
		assertEquals("[hello, hello, world, howdy, world, hi, world]",
				result.toString());
		assertEquals(7, result.size());
	}

	public void testMap() throws Exception {
		List<String> strings = new ArrayList<String>();
		strings.add("hi");
		strings.add("world");

		List<Integer> ints = CollectionUtils.map(strings,
				new Func<String, Integer>() {
					@Override
					public Integer eval(String arg) {
						return arg.length();
					}
				});
		assertEquals("[2, 5]", ints.toString());
	}

	public void testFilter() throws Exception {
		List<String> list = new ArrayList<String>();
		list.add("foo");
		list.add("bar");
		list.add("3");
		list.add("2");

		list = CollectionUtils.filter(list, new Predicate<String>() {
			@Override
			public Boolean eval(String arg) {
				return arg.length() > 1;
			}
		});

		assertEquals(2, list.size());
		assertEquals("[foo, bar]", list.toString());
	}

	public void testArrayRemove() throws Exception {
		String[] arr = new String[] { "a", "b", "c", "d", "e" };
		arr = CollectionUtils.arrayRemove(arr, "c");
		assertEquals("[a, b, d, e]", Arrays.toString(arr));

		arr = CollectionUtils.arrayRemove(arr, "e");
		assertEquals("[a, b, d]", Arrays.toString(arr));

		arr = CollectionUtils.arrayRemove(arr, "e");
		assertEquals("[a, b, d]", Arrays.toString(arr));

		arr = CollectionUtils.arrayRemove(arr, "a");
		assertEquals("[b, d]", Arrays.toString(arr));
	}

	public void testToList() throws Exception {
		assertTrue(CollectionUtils.toList(null).isEmpty());
		assertEquals("[foo]", CollectionUtils.toList("foo").toString());
		assertEquals("[foo, bar]",
				CollectionUtils.toList(new String[] { "foo", "bar" })
						.toString());

		List<Integer> ints = Arrays.asList(1, 2, 3);
		assertSame(ints, CollectionUtils.toList(ints));

		assertEquals("[1, 2, 3]", CollectionUtils.toList(ints.iterator())
				.toString());
		assertEquals("[1, 2, 3]",
				CollectionUtils.toList(new HashSet<Integer>(ints)).toString());
	}
}
