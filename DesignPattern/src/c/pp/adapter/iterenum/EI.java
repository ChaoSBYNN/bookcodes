package c.pp.adapter.iterenum;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Vector;

public class EI {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Vector<String> vector = new Vector<String>(Arrays.asList(args));
		Enumeration<String> enumeration = vector.elements();
		while (enumeration.hasMoreElements()) {
			System.out.println(enumeration.nextElement());
		}
		Iterator<String> iterator = vector.iterator();
		while (iterator.hasNext()) {
			System.out.println(iterator.next());
		}

	}

}
