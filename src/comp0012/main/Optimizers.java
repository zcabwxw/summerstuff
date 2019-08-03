package comp0012.main;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.bcel.classfile.*;
import org.apache.bcel.generic.*;
import org.apache.bcel.util.InstructionFinder;

public class Optimizers {
    public static void simpleFolding(InstructionList il, ConstantPoolGen cpGen) {
        InstructionFinder instructionFinder = new InstructionFinder(il);
        String regex = "PushInstruction PushInstruction ArithmeticInstruction";
        for (Iterator iterator = instructionFinder.search(regex); iterator.hasNext(); ) {
            InstructionHandle[] found = (InstructionHandle[]) iterator.next();

            Instruction firstVar = found[0].getInstruction();
            Instruction secondVar = found[1].getInstruction();
            Instruction operation = found[2].getInstruction();

            if (!(Patterns.isConstantInstruction(firstVar) && Patterns.isConstantInstruction(secondVar))) continue;

            Number firstNumber = Patterns.constVal(found[0], cpGen);
            System.out.println(firstNumber);
            Number secondNumber = Patterns.constVal(found[1], cpGen);
            System.out.println(secondNumber);

            if (firstNumber == null || secondNumber == null) continue;

            Instruction newInstruction = Patterns.ldcFold(firstNumber, secondNumber, operation, cpGen);

            System.out.println("New Instruction = " + newInstruction.toString());

            if (newInstruction == null) continue;

            found[0].setInstruction(newInstruction);
            try {
                il.delete(found[1]);
                il.delete(found[2]);
            } catch (TargetLostException e) {
                e.printStackTrace();
            }
        }
    }


}
