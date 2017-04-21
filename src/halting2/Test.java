package halting2;

import java.math.BigInteger;

public class Test {

	public static void main(String[] args) {
		char sp = ' ';
		System.out.println(pad(Integer.toBinaryString((int) sp)));
		
		String bin = "001001100";
		int asInt = Integer.parseInt(bin, 2);
		System.out.println(asInt);
		char c = (char)asInt;
		System.out.println(c);
		
		String bin2 = "001100001000100000001100010001100011";
		System.out.println(Long.parseLong(bin2, 2));
		
//		BigInteger b = new BigInteger(bin2,2);
//		System.out.println(b.toString(10));
		
		System.out.println(EncodeDecode.encode(bin2, true));
		
		

	}
	public static String pad(String s) {
		while(s.length() < 8) {
				s = "0" + s;
		}
		return s;
	}

}
