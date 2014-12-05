package ar.edu.itba.geb.compiler.java.core;

import ar.edu.itba.geb.compiler.java.jasmin.BytecodeCreator;
import ar.edu.itba.geb.compiler.java.symboltable.ClassDescriptor;
import ar.edu.itba.geb.compiler.java.symboltable.SymbolTable;
import ar.edu.itba.geb.compiler.java.symboltable.SymbolTableBuilderVisitor;
import ar.edu.itba.geb.compiler.java.syntaxtree.*;
import ar.edu.itba.geb.compiler.java.visitors.DepthFirstVisitor;
import ar.edu.itba.geb.compiler.java.visitors.TypeVisitorImpl;
import java_cup.*;
import java_cup.runtime.*;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		String subCommand = args[0];

		if (subCommand.equals("generate")) {
			GEBHelper.generate();
			return;
		}

		File file = new File(args[1]);

		if (!file.isFile()) {
			System.err.println("El archivo no existe!");
			System.exit(1);
		}

		try {
			
			GEBHelper.initialize(args[1]);
			
			if (subCommand.equals("accept")) {
				GEBHelper.accept();
			} else if (subCommand.equals("run")) {
				GEBHelper.run();
			} else if (subCommand.equals("tokens")) {
				GEBHelper.tokens();
			} else if (subCommand.equals("bytecode")) {
				GEBHelper.byteCodes();
			} else {
				System.err.println("Comando invalido: " + subCommand);
			}
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
	}

	

}
