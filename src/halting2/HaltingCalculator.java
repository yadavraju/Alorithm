package halting2;

import java.math.BigInteger;

/**
 * This is an attempt to solve the Halting Problem. Since there
 * is no algorithmic solution, this attempt fails, but it shows 
 * how a reasonable attempt to solve the problem would look.
 * 
 *
 */
public class HaltingCalculator {
	private String encodedProgram;
	public HaltingCalculator(String encodedProgram) {
		validateArgument(encodedProgram);
		this.encodedProgram = encodedProgram;
	}
	
	/**
	 * Returns 1 if program halts on given input, 0 otherwise
	 */
	public int halts(BigInteger input) {
		UniversalProgram up = new UniversalProgram();
		try {
			up.runProgram(encodedProgram, input);
			return 1;
		} catch(Throwable t) {
			return 0;
		}
	}
	
	private void validateArgument(String program) {  
		EncodingTester et = new EncodingTester();
		//verifies that binString is a compilable Java class
		boolean compilable = et.test(program);
		if(!compilable) {
			throw new IllegalArgumentException("Input string does not encode a compilable Java program.");
		}
	}
	public static void main(String[] args) {
		HaltingCalculator calc = new HaltingCalculator(EncodeDecode.encode(SampleCode.SAMPLE));
		BigInteger input = BigInteger.valueOf(11);
		System.out.println("Test Code:\n" + SampleCode.SAMPLE);
		System.out.println("Does this program halt on input " + input.intValue() + "?\n" + (calc.halts(input) == 1));
	}
}
