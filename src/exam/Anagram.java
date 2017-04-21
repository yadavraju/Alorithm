package exam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

public class Anagram {

	public static void anaGram(String[] s) {
		HashMap<String, List<String>> hashMap = new LinkedHashMap();
		List<String> temp;
		
		for (int i = 0; i < s.length; i++) {
			char[] s1AsChar = s[i].toCharArray();
			Arrays.sort(s1AsChar);
			String t = String.valueOf(s1AsChar);
			if (hashMap.containsKey(t)) {
				hashMap.get(t).add(s[i]);
			}else {
				temp = new ArrayList<>();
				temp.add(s[i]);
				hashMap.put(t, temp);
			}
		}
		System.out.println(hashMap);
		Set<String> keySet = hashMap.keySet();
		List<String> list = new ArrayList<String>();
		for (String string : keySet) {
			list.addAll(hashMap.get(string));
		}
		System.out.println(list);

	}

	public static void main(String[] args) {

		// TODO Auto-generated method stub
		String[] s = { "ham", "friend", "mah", "tree", "tooth", "rete", "ahm", "hammer" };
		anaGram(s);
		// then the output would be
		//
		// [ham, mah, ahm, friend, tree, rete, tooth, hammer]

	}

}
