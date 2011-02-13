package com.naveen.test.bytecode;

import com.naveen.test.annotation.Loggable;
import javassist.*;

import java.io.File;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: naveen
 * Date: 13/2/11
 * Time: 2:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class PostCompileByteCodeEnhancer {
    private int baseClassPathLength;
    private String baseClassPath;

    public static void main(String[] args) throws NotFoundException, CannotCompileException, IOException, ClassNotFoundException {
        final ClassPool pool = ClassPool.getDefault();
        System.out.println("args = " + args[0]);
        pool.appendClassPath(args[0]);

        File file = new File(args[0]);
        PostCompileByteCodeEnhancer postCompileByteCodeEnhancer = new PostCompileByteCodeEnhancer();
        postCompileByteCodeEnhancer.setBaseClassPathLength(args[0].length());
        postCompileByteCodeEnhancer.setBaseClassPath(args[0]);
        postCompileByteCodeEnhancer.getFileAndInstrument(file, pool);
    }

    public void getFileAndInstrument(File file, ClassPool classPool) throws IOException, NotFoundException, CannotCompileException, ClassNotFoundException {
        File[] files = file.listFiles();
        for (File readFiles : files) {
            if (readFiles.isDirectory())
                getFileAndInstrument(readFiles, classPool);
            else {
                String canonicalPath = readFiles.getCanonicalPath().intern();
                String fullyQualifiedClassName = canonicalPath.substring(getBaseClassPathLength() + 1, canonicalPath.lastIndexOf(".class".intern())).
                        intern().replaceAll(System.getProperty("file.separator"), ".").intern();
                CtClass compileTimeClass = classPool.get(fullyQualifiedClassName);
                if (!compileTimeClass.isInterface()) {
                    Object[] annotations = compileTimeClass.getAnnotations();
                    for (Object annotation : annotations) {
                        if (annotation instanceof Loggable) {
                            CtMethod[] declaredMethods = compileTimeClass.getDeclaredMethods();
                            for (CtMethod declaredMethod : declaredMethods) {
                                declaredMethod.addLocalVariable("startMs", CtClass.longType);
                                declaredMethod.insertBefore("startMs = System.currentTimeMillis();");
                                declaredMethod.insertAfter("{final long endMs = System.currentTimeMillis();" +
                                        "System.out.println(\"Executed in ms: \" + (endMs-startMs));}");
                            }
                            compileTimeClass.writeFile(getBaseClassPath());
                        }
                    }
                } else {
                    compileTimeClass.detach();
                }
            }
        }
    }

    public int getBaseClassPathLength() {
        return baseClassPathLength;
    }

    public void setBaseClassPathLength(int baseClassPathLength) {
        this.baseClassPathLength = baseClassPathLength;
    }

    public String getBaseClassPath() {
        return baseClassPath;
    }

    public void setBaseClassPath(String baseClassPath) {
        this.baseClassPath = baseClassPath;
    }
}
