package com.treyzania.bfncc;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class BrainfunCC {

	public static String file = "";
	public static String outputName = "";
	
	public static boolean quiet = false;
	public static boolean useGCC = false;
	public static String gccOutput = "";
	
	public static void main(String[] args) {			
		
		// Catch
		if (args.length < 1) {
			System.err.println("Usage: java -jar bfncc.jar <filename> [-O<outputfile>] [-X[executable]");
			System.err.println("Note: The -X trigger only works on Linux, with GCC installed.");
			System.exit(-1);
		}
		
		// Parse the arguments.
		for (int i = 0; i < args.length; i++) {
			
			String arg = args[i];
			
			if (arg.charAt(0) == '-') { // It's a flag!
				
				char key = arg.charAt(1);
				String value = arg.substring(2);
				
				// Add more if necessary.
				
				if (key == 'O') {
					outputName = value;
				} else if (key == 'q') {
					quiet = true;
				} else if (key == 'X') {
					useGCC = true;
					gccOutput = value;
				}
				
			} else {
				file = arg;
			}
			
		}
		
		String title = file.split("\\.")[0];
		if (outputName.isEmpty()) outputName = title + ".c";
		if (useGCC && gccOutput.isEmpty()) gccOutput = title;
		
		if (!quiet) {
			System.out.println("Brainfun C Compiler for 0.2.4");
			System.out.println("File: " + file + " -> " + outputName);
		}
		
		// Init.
		FunScript fs = new FunScript(file);
		FunCompiler fc = new FunCompiler(fs);
		
		// Process.
		String program = fc.compile();
		
		// Output.
		File output = new File(outputName);
		FileOutputStream fos = null;
		
		String os = System.getProperty("os.name").toLowerCase();
		
		try {
			
			if (!output.exists()) output.createNewFile();
			
			fos = new FileOutputStream(output);
			fos.write(program.getBytes());
			
			fos.close();
			
		} catch (IOException ioe) {
			
			System.err.println("So close, but we at least finished compiling!  We just can't write to the output.");
			ioe.printStackTrace();
			System.exit(-1);
			
		}
		
		if (useGCC) {
			
			if (os.contains("linux")) {
				
				int gccResult = 0;
				boolean gccFinished = false;
				
				Process gcc;
				
				try {
					
					gcc = Runtime.getRuntime().exec("gcc -o " + gccOutput + " " + outputName);
					gccResult = gcc.waitFor();
					
					gccFinished = true;
					
				} catch (Exception e) {
					System.err.println("Something went wrong with GCC.");
					e.printStackTrace();
				}
				
				if (gccFinished && gccResult != 0) {
					System.err.println("GCC exited with "
							+ Integer.toString(gccResult)
							+ ".  Try compiling manually for details.");
				}
				
			} else {
				System.err.println("You must be using Linux to immediately compile via GCC.");
			}
			
		}
		
		if (!quiet) System.out.println("Done!");
		
	}
	
	public static String downloadURL(URL url) {
		
		InputStream is = null;
		
		try {
			is = url.openStream();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
		return is != null ? emptyStream(is) : "";
		
	}
	
	public static String emptyStream(InputStream is) {
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		int nRead = 0;
		byte[] dataBuf = new byte[16384];
		
		//System.out.println(is);
		
		try {
			
			while ((nRead = is.read(dataBuf, 0, dataBuf.length)) != -1) {
				baos.write(dataBuf, 0, nRead);
			}
			
			baos.flush();
			
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
		try {
			
			if (is != null) is.close();
			baos.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
		return new String(baos.toByteArray());
		
	}
	
}
