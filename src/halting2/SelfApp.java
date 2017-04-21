package halting2;

import halting2.SampleCode;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import javax.tools.ToolProvider;

/** 
 * If the halting function is computable, this SelfApp function apply
 * must also be computable. 
 * 
 * This SelfApp is a normalized Java program -- it is a single public method
 * that takes a single BigInteger argument, and all the supporting classes
 * have been placed inside SelfApp as static nested classes.
 * 
 * Since SelfApp is a normalized Java program, we can find its 
 * code and execute the apply method on its own code --
 * and ask whether, in this case, the apply method halts. This question
 * leads to a paradox: 
 *    the apply method halts in this case if and only if it does not halt.
 * 
 *
 */
public class SelfApp {
	private static final Logger LOG = Logger.getGlobal();
	/**
	 * Returns 1 if HaltingCalculator returns 0
	 * Goes into endless loop if HaltingCalculator returns 1
	 * @param binString
	 * @return LOG.info("Begin endless loop");
	 */

	public Integer apply(java.math.BigInteger code) {
		String program = EncodeDecode.decode(code);
		//need to convert to binary string encoding
		String binString = EncodeDecode.encode(program);
		HaltingCalculator halting = new HaltingCalculator(binString);
		Integer result = halting.halts(code);
		if(result.intValue() == 1) {
			while(true){;}
		}
		return 1;
	}

	public static void main(String[] args) {
		//trying out UniversalProgram on SampleCode, 5
		UniversalProgram u = new UniversalProgram();
		System.out.println(SampleCode.SAMPLE);
		System.out.println(u.runProgram(EncodeDecode.encode(SampleCode.SAMPLE), BigInteger.valueOf(5)));
		
		//trying out HaltingCalculator on SampleCode, 5
		HaltingCalculator calc = new HaltingCalculator(EncodeDecode.encode(SampleCode.SAMPLE));
		BigInteger input = BigInteger.valueOf(11);
		System.out.println("Test Code:\n" + SampleCode.SAMPLE);
		System.out.println("Does this program halt on input " + input.intValue() + "?\n" + (calc.halts(input) == 1));
		
		//test self-app
		SelfApp sa = new SelfApp();
		String binStr = EncodeDecode.encode(SampleCode.SAMPLE);
		BigInteger bigInt = new BigInteger(binStr,2);
		//this will go into an endless loop -- the sample code will halt, so SelfApp will not terminate
		sa.apply(bigInt);
		System.out.println(Util.encodingOfSelfApp());
	}
	
	
	
	
	static public class EncodeDecode {
		private static final int WORD_LENGTH = 8;

		public static String encode(String s) {
			char[] chars = s.toCharArray();
			StringBuilder output = new StringBuilder();
			for(char c : chars) {
				output.append(asLength9Binary(c));
			}
			return output.toString();
			
		}
		
		public static String decode(String binStr) {
			if(binStr.length() % WORD_LENGTH != 0) 
				throw new IllegalArgumentException("Input string must have length a multiple of "  + WORD_LENGTH);
			try {
				new BigInteger(binStr,2);
			} catch(NumberFormatException e) {
				throw new IllegalArgumentException("Input string must consist of 0s and 1s only");
			}
			int start = 0;
			int end = WORD_LENGTH;
			int steps = binStr.length()/WORD_LENGTH;
			StringBuilder sb = new StringBuilder();
			for(int i = 0; i < steps; ++i) {
				sb.append(convertToChar(nextBlock(binStr, start, end)));
				start += WORD_LENGTH;
				end +=WORD_LENGTH;
			}
			return sb.toString();
		}
		public static String decode(java.math.BigInteger num) {
			String binaryStr = num.toString(2);
			int len = binaryStr.length();
			int nextMultiple =(len % WORD_LENGTH == 0) 
					              ? len 
					              : len + (WORD_LENGTH - (len % WORD_LENGTH));
			return decode(pad(num.toString(2), nextMultiple));
		}
		private static String nextBlock(String binStr, int start, int end) {
			return binStr.substring(start, end);
		}
		private static String convertToChar(String block) {
			return "" + (char) Integer.parseInt(block, 2);
		}
		private static String asLength9Binary(char c) {
			return pad(Integer.toBinaryString((int) c));
		}
		public static String pad(String s, int nextMultiple) {
			while(s.length() < nextMultiple) {
					s = "0" + s;
			}
			return s;
		}
		public static String pad(String s) {
			while(s.length() < WORD_LENGTH) {
					s = "0" + s;
			}
			return s;
		}
	}
	
	static public class EncodingTester {
		static {//C:\Program Files\Java\jdk1.8.0_25
			System.setProperty("java.home", "C:\\Program Files\\Java\\jdk1.8.0_40\\jre");
		}
		private Class<?> compiledClass;
		public Class<?> getCompiledClass() {
			return compiledClass;
		}
		
		public boolean test(String binStr) {
			
			String javaProgram = null;
			try {
				javaProgram = EncodeDecode.decode(binStr);
			} catch(IllegalArgumentException e) {
				LOG.severe("Decoding input string failed: " + e.getMessage());
				return false;
			}
			try {
				JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
				String className = Util.readClassName(binStr);
				compiledClass = 
						InMemoryJavaCompiler.compile(className, javaProgram);
				
			} catch(Exception e) {
				e.printStackTrace();
			}
			return true;
		}
	}
	static class HaltingCalculator {
		private String encodedProgram;
		public HaltingCalculator(String encodedProgram) {
			validateArgument(encodedProgram);
			this.encodedProgram = encodedProgram;
		}
		
		/**
		 * Returns 1 if program halts on given input, 0 otherwise
		 */
		public int halts(java.math.BigInteger input) {
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
	}
	
	static public class UniversalProgram {
		private static final Logger LOG = Logger.getGlobal();
		public Integer runProgram(String binString, java.math.BigInteger input) {
			EncodingTester et = new EncodingTester();
			boolean compilable = et.test(binString);
			Integer retVal = null;
			if(compilable) {
				Class<?> cl = et.getCompiledClass();
				try {
					String functionName = Util.readFunctionName(binString);
					java.lang.reflect.Method method = cl.getMethod(functionName, java.math.BigInteger.class);
					retVal = (Integer)method.invoke(cl.newInstance(), input);
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
			return retVal;	
		}
	}
	
	static class Util {
		public static String readClassName(String binString) {
			String javaProgram = EncodeDecode.decode(binString);
			String searchStr = "public class ";
			String space = " ";
			int start = javaProgram.indexOf(searchStr) + searchStr.length();
			int end = javaProgram.indexOf(space, start);
			return javaProgram.substring(start, end);
		}
		public static String readFunctionName(String binString) {
			String javaProgram = EncodeDecode.decode(binString);
			String searchStr = "public Integer ";
			String space = "(";
			int start = javaProgram.indexOf(searchStr) + searchStr.length();
			int end = javaProgram.indexOf(space, start);
			return javaProgram.substring(start, end);
		}
		/* The logic for this is correct, but the encoding is too big to fit into memory */
		public static String encodingOfSelfApp() {
			String path = System.getProperty("user.dir") + "\\halting3\\SelfApp.java";
			String retVal = null;
			File f = new File(path);
			try {
				FileReader fr = new FileReader(f);
				BufferedReader buf = new BufferedReader(fr);
				Stream<String> lines = buf.lines();
				String program = lines.collect(Collectors.joining("\n"));
				retVal = EncodeDecode.encode(program);
				System.out.println(retVal);
				//System.out.println(Util.readClassName(retVal));
				//System.out.println(Util.readFunctionName(retVal));
			} catch(Exception e) {
				e.printStackTrace();
			}
			return retVal;		
		}
	}
	
	static class CompiledCode extends SimpleJavaFileObject {
	    private ByteArrayOutputStream baos = new ByteArrayOutputStream();

	    public CompiledCode(String className) throws Exception {
	        super(new URI(className), Kind.CLASS);
	    }

	    @Override
	    public OutputStream openOutputStream() throws IOException {
	        return baos;
	    }

	    public byte[] getByteCode() {
	        return baos.toByteArray();
	    }
	}
	
	static class DynamicClassLoader extends ClassLoader {

	    private Map<String, CompiledCode> customCompiledCode = new HashMap<>();

	    public DynamicClassLoader(ClassLoader parent) {
	        super(parent);
	    }

	    public void setCode(CompiledCode cc) {
	        customCompiledCode.put(cc.getName(), cc);
	    }

	    @Override
	    protected Class<?> findClass(String name) throws ClassNotFoundException {
	        CompiledCode cc = customCompiledCode.get(name);
	        if (cc == null) {
	            return super.findClass(name);
	        }
	        byte[] byteCode = cc.getByteCode();
	        return defineClass(name, byteCode, 0, byteCode.length);
	    }
	}
	
	static class ExtendedStandardJavaFileManager extends ForwardingJavaFileManager<JavaFileManager> {

	    private CompiledCode compiledCode;
	    private DynamicClassLoader cl;

	    /**
	     * Creates a new instance of ForwardingJavaFileManager.
	     *
	     * @param fileManager delegate to this file manager
	     * @param cl
	     */
	    protected ExtendedStandardJavaFileManager(JavaFileManager fileManager, CompiledCode compiledCode, DynamicClassLoader cl) {
	        super(fileManager);
	        this.compiledCode = compiledCode;
	        this.cl = cl;
	        this.cl.setCode(compiledCode);
	    }

	    @Override
	    public JavaFileObject getJavaFileForOutput(JavaFileManager.Location location, String className, JavaFileObject.Kind kind, FileObject sibling) throws IOException {
	        return compiledCode;
	    }

	    @Override
	    public ClassLoader getClassLoader(JavaFileManager.Location location) {
	        return cl;
	    }
	}
	
	static class InMemoryJavaCompiler {
	    static JavaCompiler javac = ToolProvider.getSystemJavaCompiler();

	    public static Class<?> compile(String className, String sourceCodeInText) throws Exception {
	        SourceCode sourceCode = new SourceCode(className, sourceCodeInText);
	        CompiledCode compiledCode = new CompiledCode(className);
	        Iterable<? extends JavaFileObject> compilationUnits = Arrays.asList(sourceCode);
	        DynamicClassLoader cl = new DynamicClassLoader(ClassLoader.getSystemClassLoader());
	        ExtendedStandardJavaFileManager fileManager = new ExtendedStandardJavaFileManager(javac.getStandardFileManager(null, null, null), compiledCode, cl);
	        JavaCompiler.CompilationTask task = javac.getTask(null, fileManager, null, null, null, compilationUnits);
	        boolean result = task.call();
	        return cl.loadClass(className);
	    }
	}
	
	static class SourceCode extends SimpleJavaFileObject {
	    private String contents = null;

	    public SourceCode(String className, String contents) throws Exception {
	        super(URI.create("string:///" + className.replace('.', '/') + Kind.SOURCE.extension), Kind.SOURCE);
	        this.contents = contents;
	    }

	    public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
	        return contents;
	    }
	}
	
	
	
	
}
