package inmemorycompiler1;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import java.util.Arrays;

/**
 * Created by trung on 5/3/15.
 */
public class InMemoryJavaCompiler {
	
    static JavaCompiler javac = ToolProvider.getSystemJavaCompiler();

    public static Class<?> compile(String className, String sourceCodeInText) throws Exception {
        SourceCode sourceCode = new SourceCode(className, sourceCodeInText);
        CompiledCode compiledCode = new CompiledCode(className);
        Iterable<? extends JavaFileObject> compilationUnits = Arrays.asList(sourceCode);
        DynamicClassLoader cl = new DynamicClassLoader(ClassLoader.getSystemClassLoader());
        StandardJavaFileManager sjfm = javac.getStandardFileManager(null, null, null);
        ExtendedStandardJavaFileManager fileManager = new ExtendedStandardJavaFileManager(sjfm, compiledCode, cl);
        JavaCompiler.CompilationTask task = javac.getTask(null, fileManager, null, null, null, compilationUnits);
        boolean result = task.call();
        if(result) 
        	return cl.loadClass(className);
        else
        	return null;
    }
}