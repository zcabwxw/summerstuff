package comp0012.main;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.bcel.classfile.*;
import org.apache.bcel.generic.ClassGen;
import org.apache.bcel.generic.ConstantPoolGen;
import org.apache.bcel.generic.InstructionHandle;
import org.apache.bcel.generic.InstructionList;
import org.apache.bcel.util.InstructionFinder;
import org.apache.bcel.generic.MethodGen;
import org.apache.bcel.generic.TargetLostException;



public class ConstantFolder
{
	ClassParser parser = null;
	ClassGen gen = null;

	JavaClass original = null;
	JavaClass optimized = null;

	public ConstantFolder(String classFilePath)
	{
		try{
			this.parser = new ClassParser(classFilePath);
			this.original = this.parser.parse();
			this.gen = new ClassGen(this.original);
		} catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void optimize()
	{
		ClassGen cgen = new ClassGen(original);
		ConstantPoolGen cpgen = cgen.getConstantPool();
		cgen.setMajor(50);
		Method[] methods = cgen.getMethods();
		for(int i = 0; i<methods.length;i++){
			System.out.println("Current Method :" + methods[i]);
			optimizeMethod(methods[i],cgen,cpgen);
		}
		this.optimized = cgen.getJavaClass();
	}

	public void optimizeMethod(Method iniMethod, ClassGen cgen, ConstantPoolGen cpgen){
		MethodGen mGen = new MethodGen(iniMethod,cgen.getClassName(),cpgen);
		InstructionList iList = mGen.getInstructionList();
		ConstantPoolGen methodCpGen = mGen.getConstantPool();
		System.out.println("-------Simple Folding Test-------");
		Optimizers.simpleFolding(iList,methodCpGen);
		mGen.setMaxStack();
		mGen.setMaxLocals();
		Method newMethod = mGen.getMethod();
		cgen.replaceMethod(iniMethod,newMethod);
	}

	
	public void write(String optimisedFilePath)
	{
		this.optimize();

		try {
			FileOutputStream out = new FileOutputStream(new File(optimisedFilePath));
			this.optimized.dump(out);
		} catch (FileNotFoundException e) {
			// Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// Auto-generated catch block
			e.printStackTrace();
		}
	}
}