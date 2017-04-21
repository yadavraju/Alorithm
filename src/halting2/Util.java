package halting2;

public class Util {
	/** Converts a code for a java program to a java program and then reads class name.
	 *   Does not check if binString is a code for a java progam 
	 *   Assumption: there is a space after the class name
	 */
	public static String readClassName(String binString) {
		String javaProgram = EncodeDecode.decode(binString);
		String searchStr = "public class ";
		String space = " ";
		if(javaProgram.indexOf(searchStr) == -1) {
			throw new IllegalArgumentException("Java class must be declared public");
		}
		int start = javaProgram.indexOf(searchStr) + searchStr.length();
		int end = javaProgram.indexOf(space, start);
		return javaProgram.substring(start, end);
	}
	/** Converts a code for a java program to a java program and then reads first method name 
	 *  Assumption: there is no space between function name and character '('
	 */
	public static String readFunctionName(String binString) {
		String javaProgram = EncodeDecode.decode(binString);
		String searchStr = "public Integer ";
		String space = "(";
		int start = javaProgram.indexOf(searchStr) + searchStr.length();
		int end = javaProgram.indexOf(space, start);
		return javaProgram.substring(start, end);
	}
	
	public static void main(String[] args) {
		String binString = EncodeDecode.encode(SampleCode.SAMPLE);
		System.out.println("Class name: " + readClassName(binString));
		System.out.println("Function name: " + readFunctionName(binString));
		
	}
}
