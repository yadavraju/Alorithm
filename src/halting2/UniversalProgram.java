package halting2;

import java.lang.reflect.Method;
import java.math.BigInteger;
import java.util.logging.Logger;

/**
 * This class is a "universal java program" in the following sense:
 * 
 * 1. A "normalized" Java class is one that has public class declaration
 * and a public method declaration for which the method accepts
 * a BigInteger argument and returns an Integer value.
 * 
 * 2. UniversalProgram will:
 *   a. accept a binary string encoding a normalized Java class 
 *       and also a BigInteger argument
 *   b. verify that the binary string does encode a (compilable) 
 *      Java class (but will not verify that the Java class
 *      is normalized)
 *   c. compile the class to produce a Class file for the class
 *   d. create an instance of the class
 *   e. invoke the first public method (using reflection), 
 *       passing in the BigInteger input argument
 *   f. return the output of the method on this input
 *   
 *
 */
public class UniversalProgram {
	private static final Logger LOG = Logger.getGlobal();
	/**
	 *@param binString - a binary string encoding a normalized 
	 *Java class  
	 *@param - input is an integer argument, to be accepted by 
	 *the method of the encoded class. 
	 */
	public Integer runProgram(String binString, BigInteger input) {
		EncodingTester et = new EncodingTester();
		//verifies that binString is a compilable Java class
		boolean compilable = et.test(binString);
		Integer retVal = null;
		if(compilable) {
			//loads compiled code as class with class loader
			Class<?> cl = et.getCompiledClass();
			try {
				//uses reflection to identify the method for this class
				String functionName = Util.readFunctionName(binString);
				Method method = cl.getMethod(functionName, BigInteger.class);
				//invokes the program's method on a new instance of this class, passing in input integer
				retVal = (Integer)method.invoke(cl.newInstance(), input);
			} catch(Exception e) {
				e.printStackTrace();
			}
		} else {
			LOG.warning("Input program did not compile");
		}
		return retVal;	
	}

	public static void main(String[] args) {
		UniversalProgram u = new UniversalProgram();
		System.out.println(SampleCode.SAMPLE);
		System.out.println(u.runProgram(EncodeDecode.encode(SampleCode.SAMPLE),
							BigInteger.valueOf(5)));
	}
}
