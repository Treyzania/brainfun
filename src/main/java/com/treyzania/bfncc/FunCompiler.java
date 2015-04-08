package com.treyzania.bfncc;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import com.treyzania.bfncc.commands.*;

public class FunCompiler {

	public static ArrayList<Command> commands;
	public static SpecialHandler commentHandler;
	
	public FunScript script;
	public StringBuilder cCode;
	public Command lastCommand;
	
	public int currentTabLevel = 0;
	public int commandsTotal = 0;
	private boolean alreadyCompiled;
	
	public boolean endReached = false;
	
	public FunCompiler(FunScript fs) {
		
		this.script = fs;
		this.cCode = new StringBuilder();
		
	}
	
	public String compile() {
		
		if (alreadyCompiled) return cCode.toString();
		
		// #defines, various methods, etc.
		this.addInit();
		
		InputStream is = null;
		
		try {
			is = new FileInputStream(script.compiling);
		} catch (IOException ioe) {
			ioe.printStackTrace();
			System.exit(-1);
		}
		
		boolean done = false;
		long count = 0;
		
		while (!done) {
			
			int last = -1;
			try {
				last = is.read();
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println("Blah.");
			}
			
			//System.out.println("Char read: " + last);
			
			if (last != -1) {
				
				char c = (char) last;
				
				Command exec = getCommandForChar(c);
				
				if (exec != null) {
					exec.add(this, c, is); // All there is to it!
					
					if (exec != commentHandler) {
						count++; // Comments shouldn't increase the symbol count.
						this.lastCommand = exec;
					}
					
				}
				
			} else {
				// End reached.  Do something.
				done = true;
			}
			
		}
		
		// main(void)'s return, etc.
		this.addClose();
		
		if (!BrainfunCC.quiet) System.out.println("Symbols: " + count);
		
		// Full program here.
		alreadyCompiled = true;
		return cCode.toString();
		
	}
	
	private static Command getCommandForChar(char c) {
		
		Command ret = null;
		
		for (Command com : commands) {
			
			//System.out.println("Checking " + com + " against " + c + ".");
			
			boolean ok = false;
			
			for (char test : com.getChars()) {
				
				if (c == test) {
					
					//if (!BrainfunCC.quiet) System.out.println("Found something for \'" + c + "\'!");
					
					ret = com;
					break;
					
				}
				
			}
			
			if (ok) break;
			
		}
		
		return ret;
		
	}
	
	public void appendFile(String path) {
		
		String[] lines = BrainfunCC.emptyStream(ClassLoader.getSystemClassLoader().getResourceAsStream(path))
				.split("\n");
		
		for (String line : lines) this.appendLine(line);
		
		// Always have an empty line after a file.  For spacing.
		//this.addEmptyLine();
		
	}
	
	public void appendLine(String line) {
		
		for (int i = 0; i < this.currentTabLevel; i++) {
			cCode.append("\t"); // Tabs, because C.
		}
		
		cCode.append(line + "\n");
		
	}
	
	public void addEmptyLine() {
		this.appendLine("");
	}
	
	private final void addInit() {
		
		// Basic definitions.
		appendLine("#define STACKSIZE 65536");
		appendLine("#define DATASIZE 65536");
		
		// More code.
		appendFile("snips/init_premain.csnip");
		appendFile("snips/init_main.csnip");
		
		// :)
		this.currentTabLevel = 1;
		
	}
	
	private final void addClose() {
		
		this.currentTabLevel = 0;
		
		// This file is already fit with tabs.
		appendFile("snips/finalizer.csnip");
		
	}
	
	static {
		
		// Init.
		commands = new ArrayList<Command>();
		
		// Basic commands. (For pure brainfuck.)
		commands.add(new CommandMovePointerRight());
		commands.add(new CommandMovePointerLeft());
		commands.add(new CommandIncPointed());
		commands.add(new CommandDecPointed());
		commands.add(new CommandStdin());
		commands.add(new CommandStdout());
		commands.add(new CommandLoopBegin());
		commands.add(new CommandLoopEnd());
		
		// Stack commands.
		commands.add(new CommandPush());
		commands.add(new CommandPop());
		commands.add(new CommandDrop());
		
		// Register stuff.
		commands.add(new CommandGetRegister('A'));
		commands.add(new CommandGetRegister('B'));
		commands.add(new CommandGetRegister('C'));
		commands.add(new CommandGetRegister('D'));
		commands.add(new CommandSetRegister('a'));
		commands.add(new CommandSetRegister('b'));
		commands.add(new CommandSetRegister('c'));
		commands.add(new CommandSetRegister('d'));
		commands.add(new CommandPushRegs());
		commands.add(new CommandPopRegs());
		
		// Bitwise operators.
		commands.add(new CommandAND());
		commands.add(new CommandOR());
		commands.add(new CommandXOR());
		commands.add(new CommandNOT());
		
		// Misc.
		commands.add(new CommandSetByte());
		commands.add(new CommandQuit());
		commands.add(new CommandNullify());
		commands.add(new CommandString());
		commentHandler = new SpecialHandler('#', '\n'); commands.add(commentHandler);
		commands.add(new SpecialHandler('$', '$'));
		
	}
	
}
