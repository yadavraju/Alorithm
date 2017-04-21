package lecture_10.subsetsum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class ConstantsLinkedList {

	@SuppressWarnings("serial")
	public
	static final LinkedList<Integer> NULL = new LinkedList<Integer>() {
		{
			add(-1);
		}
	};

	public static final LinkedList<Integer> EMPTY_SET = new LinkedList<>();
	@SuppressWarnings("serial")
	public static final LinkedList<Integer> ZERO_ELEMENT = new LinkedList<Integer>() {
		{
			add(0);
		}
	};

}
