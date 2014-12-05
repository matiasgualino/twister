package ar.edu.itba.geb.compiler.java.core;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import ar.edu.itba.geb.compiler.java.jasmin.BytecodeCreator;
import ar.edu.itba.geb.compiler.java.symboltable.ClassDescriptor;
import ar.edu.itba.geb.compiler.java.symboltable.SymbolTable;
import ar.edu.itba.geb.compiler.java.symboltable.SymbolTableBuilderVisitor;
import ar.edu.itba.geb.compiler.java.syntaxtree.Program;
import ar.edu.itba.geb.compiler.java.visitors.TypeVisitorImpl;
import java_cup.runtime.Symbol;

public class GEBHelper {
	static String fileName;
	static AnalizadorLexico lexer;
	static AnalizadorSintactico parser_obj;
	static String jasmin_path = new File("").getAbsolutePath().toString()
			+ "/src/jasmin.jar";

	public static void accept() {
		try {
			Symbol sy;
			sy = parser_obj.parse();
			System.out.println("Codigo aceptado!");
			System.out.println("Root Node: " + sy.value);
		} catch (IOException e) {
			System.err.println("ERROR: Unable to open file: " + fileName);
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
	}

	public static void generate() {
		System.out.println("\n*** Generando ***\n");
		String flexDir = new File("").getAbsolutePath().toString()
				+ "/src/main/java/ar/edu/itba/geb/compiler/jflex/";
		String archLexico = flexDir + "Parser.flex";
		String archSintactico = new File("").getAbsolutePath().toString()
				+ "/src/main/java/ar/edu/itba/geb/compiler/cup/Parser.cup";

		String[] alexico = { archLexico };
		String[] asintactico = { "-parser", "AnalizadorSintactico",
				archSintactico };
		jflex.Main.main(alexico);
		try {
			java_cup.Main.main(asintactico);
		} catch (Exception ex) {
			Logger.getLogger(GEBHelper.class.getName()).log(Level.SEVERE, null,
					ex);
		}
		// movemos los archivos generados
		boolean mvAL = moverArch(flexDir + "AnalizadorLexico.java");
		boolean mvAS = moverArch("AnalizadorSintactico.java");
		boolean mvSym = moverArch("sym.java");
		if (mvAL && mvAS && mvSym) {
			System.exit(0);
		}
		System.out.println("Generado!");
	}

	private static boolean moverArch(String archNombre) {
		boolean efectuado = false;
		File arch = new File(archNombre);
		if (arch.exists()) {
			System.out.println("\n*** Moviendo " + arch + " \n***");
			String nuevoDir = new File("").getAbsolutePath().toString()
					+ "/src/main/java/ar/edu/itba/geb/compiler/java/core/"
					+ arch.getName();
			File archViejo = new File(nuevoDir);
			archViejo.delete();

			if (arch.renameTo(new File(nuevoDir))) {
				System.out.println("\n*** Generado " + archNombre + "***\n");
				efectuado = true;
			} else {
				System.out.println("\n*** No movido " + archNombre + " ***\n");
			}

		} else {
			System.out.println("\n*** Codigo no existente ***\n");
		}
		return efectuado;
	}

	public static void initialize(String _fileName) {
		try {
			File file = new File(_fileName);
			Reader reader = new InputStreamReader(new ByteArrayInputStream(
					Files.readAllBytes(file.toPath())), "UTF-8");
			lexer = new AnalizadorLexico(reader);
			fileName = _fileName;
			parser_obj = new AnalizadorSintactico(new AnalizadorLexico(
					new InputStreamReader(new FileInputStream(file))));

		} catch (UnsupportedEncodingException ignore) {
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void tokens() throws Exception {
		System.out.println("Armando la lista de tokens...");
		ArrayList<String> list = new ArrayList<String>();

		try {
			String token;
			do {
				token = sym.terminalNames[lexer.next_token().sym];
				list.add(token);
			} while (!token.equals("EOF"));
		} catch (IOException e) {
			throw new Exception(e.getMessage());
		}
		System.out.println(list);
	}

	public static void prepare() throws Exception {
		SymbolTableBuilderVisitor symtBuilder = new SymbolTableBuilderVisitor();

		Program root = (Program) parser_obj.parse().value;

		System.out.println("Armando la tabla de simbolos...");
		root.accept(symtBuilder);

		System.out.println("Verificando los tipos de datos...");
		TypeVisitorImpl typeVisitor = new TypeVisitorImpl();
		root.accept(typeVisitor);

		System.out.println("Generando Jasmin...\n");

		BytecodeCreator visitor = new BytecodeCreator();
		root.accept(visitor);
	}

	public static void run() throws Exception {

		prepare();

		ArrayList<String> l = new ArrayList<String>();

		for (ClassDescriptor c : SymbolTable.getInstance()
				.getClassDescriptors()) {
			String classN = c.getName();

			l.add(classN);

			String jasmin_class = classN + ".jasmin";

			String cmd = "java -jar %s -g %s";
			cmd = String.format(cmd, jasmin_path, jasmin_class);
			System.out.println(cmd);

			Process proc = Runtime.getRuntime().exec(cmd);

			String stdout = readInputStream(proc.getInputStream());
			String stderr = readInputStream(proc.getErrorStream());

			if (!stdout.isEmpty())
				System.out.println(stdout);

			if (!stderr.isEmpty())
				System.out.println(stderr);
		}

		try {
			for (String cls : l) {

				String command = "java -cp "
						+ new File("").getAbsolutePath().toString() + " " + cls;

				System.out.println("Ejecucion del .class generado por Jasmin ("
						+ command + ")");
				System.out.println();
				Process proc = Runtime.getRuntime().exec(command);
				String stdout = readInputStream(proc.getInputStream());
				String stderr = readInputStream(proc.getErrorStream());

				if (!stdout.isEmpty())
					System.out.println(stdout);

				if (!stderr.isEmpty())
					System.out.println(stderr);
			}
		} catch (IOException e) {
			System.err.println("IOException raised: " + e.getMessage());
		}
	}

	private static String readInputStream(InputStream stream) throws Exception {
		Reader r = new InputStreamReader(stream, "ASCII");

		int n;
		StringBuffer sb = new StringBuffer(200);
		char[] buff = new char[100];

		while ((n = r.read(buff)) != -1) {
			sb.append(buff, 0, n);
		}

		return sb.toString();
	}

	public static void byteCodes() throws Exception {
		prepare();
		for (ClassDescriptor c : SymbolTable.getInstance()
				.getClassDescriptors()) {
			String classN = c.getName();

			String jasmin_class = classN + ".jasmin";
			
			byte[] encoded = Files.readAllBytes(Paths.get(new File("").getAbsolutePath().toString() + "/" + jasmin_class));
			String code = new String(encoded, StandardCharsets.UTF_8);
			
			System.out.println("Bytecode para " + classN);
			System.out.println(code);
		}
	}

}
