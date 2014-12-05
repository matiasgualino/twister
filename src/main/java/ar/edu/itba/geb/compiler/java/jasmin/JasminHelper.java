package ar.edu.itba.geb.compiler.java.jasmin;

// Clase obtenida de http://files.dbruhn.de/compilerpraktikum/src/edu/kit/compilerpraktikum/libfirm/



import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import ar.edu.itba.geb.compiler.jasmin.ClassFile;

public class JasminHelper {

    private static class JasminException extends RuntimeException {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public JasminException(Throwable cause) {
            super(cause);
        }
    }

    public static void createByteCode(String className, String srcIn,
            OutputStream outStream) throws JasminException {
        ClassFile classFile = new ClassFile();

        StringReader srcReader = new StringReader(srcIn);

        try {
            classFile.readJasmin(srcReader, className, true);
            classFile.write(outStream);
        } catch (IOException e) {
            throw new JasminException(e);
        } catch (Exception e) {
            throw new JasminException(e);
        }
    }


    private static class ByteClassLoader extends ClassLoader {

        private final Map<String, byte[]> classes;
        private ByteClassLoader(Map<String, byte[]> classes) {
            this.classes = classes;
        }

        @Override
        protected Class<?> findClass(String name)
        throws ClassNotFoundException {

            if (!classes.containsKey(name)) {
                return super.findClass(name);
            }

            byte[] data = classes.get(name);

            return defineClass(name, data, 0, data.length);
        }

    }

   public static Entry<String, String> executeMainMethod(Method m) {
        // StdOut sichern und umbiegen
        PrintStream orgOut = System.out;
        PrintStream orgErr = System.err;

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(bos, true);
        ByteArrayOutputStream bosErr = new ByteArrayOutputStream();
        PrintStream err = new PrintStream(bosErr, true);
        System.setOut(out);
        System.setErr(err);

        try {
            m.invoke(null, (Object)new String[0]);
        } catch(InvocationTargetException e) {
            e.getCause().printStackTrace(out);
        } catch (IllegalArgumentException e) {
        	Logger.getLogger(JasminHelper.class.getName()).log(Level.SEVERE, "Couldn't call main method:", e);
        } catch (IllegalAccessException e) {
        	Logger.getLogger(JasminHelper.class.getName()).log(Level.SEVERE, "Couldn't access main method:", e);
        }

        System.setOut(orgOut);
        System.setErr(orgErr);

        String sOut = new String(bos.toByteArray());
        String sErr = new String(bosErr.toByteArray());

        return new AbstractMap.SimpleEntry<String,String>(sOut, sErr);
    }

    public static Map<String, Entry<String,String>> runByteCode(Map<String,
            byte[]> classes) {
        ClassLoader cl = new ByteClassLoader(classes);

        Map<String, Entry<String,String>> re =
            new HashMap<String, Entry<String,String>>();

        for (String className : classes.keySet()) {
        	Logger.getLogger(JasminHelper.class.getName()).log(Level.INFO, "ClassLoading {}", className);
            Class<?> clazz;
            try {
                clazz = cl.loadClass(className);
            } catch (ClassNotFoundException e) {
            	Logger.getLogger(JasminHelper.class.getName()).log(Level.INFO, "Loading of class '" + className + "' failed: {}", e);
                throw new JasminException(e);
            }

            try {
                Method m = clazz.getMethod("main", (new String[0]).getClass());
                Logger.getLogger(JasminHelper.class.getName()).log(Level.INFO, "Found Main method {}", m);
                Entry<String, String> e = executeMainMethod(m);
                Logger.getLogger(JasminHelper.class.getName()).log(Level.INFO, "Result of our compiled src is\n"+e);
                re.put(className, e);
            } catch (SecurityException e1) {
            	Logger.getLogger(JasminHelper.class.getName()).log(Level.SEVERE, "Failed to execute Main-Method", e1);
                throw new RuntimeException(e1);
            } catch (NoSuchMethodException e1) {
            }
        }


        return re;
    }
}
