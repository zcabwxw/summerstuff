package comp0012.main;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.bcel.classfile.*;
import org.apache.bcel.generic.*;
import org.apache.bcel.util.InstructionFinder;

public class Patterns {
    public static Instruction ldcFold(Number first, Number second, Instruction instruction, ConstantPoolGen cpGen){
        if (instruction instanceof IADD) return new LDC(cpGen.addInteger(first.intValue() + second.intValue()));
        else if (instruction instanceof ISUB) return new LDC(cpGen.addInteger(first.intValue() - second.intValue()));
        else if (instruction instanceof IMUL) return new LDC(cpGen.addInteger(first.intValue() * second.intValue()));
        else if (instruction instanceof IDIV) return new LDC(cpGen.addInteger(first.intValue() / second.intValue()));
        else if (instruction instanceof IREM) return new LDC(cpGen.addInteger(first.intValue() % second.intValue()));
        else if (instruction instanceof LADD) return new LDC(cpGen.addLong(first.longValue() + second.longValue()));
        else if (instruction instanceof LSUB) return new LDC(cpGen.addLong(first.longValue() - second.longValue()));
        else if (instruction instanceof LMUL) return new LDC(cpGen.addLong(first.longValue() * second.longValue()));
        else if (instruction instanceof LDIV) return new LDC(cpGen.addLong(first.longValue() / second.longValue()));
        else if (instruction instanceof LREM) return new LDC(cpGen.addLong(first.longValue() % second.longValue()));
        else if (instruction instanceof FADD) return new LDC(cpGen.addFloat(first.floatValue() + second.floatValue()));
        else if (instruction instanceof FSUB) return new LDC(cpGen.addFloat(first.floatValue() - second.floatValue()));
        else if (instruction instanceof FMUL) return new LDC(cpGen.addFloat(first.floatValue() * second.floatValue()));
        else if (instruction instanceof FDIV) return new LDC(cpGen.addFloat(first.floatValue() / second.floatValue()));
        else if (instruction instanceof FREM) return new LDC(cpGen.addFloat(first.floatValue() % second.floatValue()));
        else if (instruction instanceof DADD) return new LDC(cpGen.addDouble(first.doubleValue() + second.doubleValue()));
        else if (instruction instanceof DSUB) return new LDC(cpGen.addDouble(first.doubleValue() - second.doubleValue()));
        else if (instruction instanceof DMUL) return new LDC(cpGen.addDouble(first.doubleValue() * second.doubleValue()));
        else if (instruction instanceof DDIV) return new LDC(cpGen.addDouble(first.doubleValue() / second.doubleValue()));
        else if (instruction instanceof DREM) return new LDC(cpGen.addDouble(first.doubleValue() % second.doubleValue()));
        else return null;
    }

    public static Number constVal(InstructionHandle instruction_handle, ConstantPoolGen constpoolgen) {
        Number value;
        if (instruction_handle.getInstruction() instanceof LDC) {
            value = (Number) ((LDC) instruction_handle.getInstruction()).getValue(constpoolgen);
        } else if (instruction_handle.getInstruction() instanceof LDC2_W) {
            value = ((LDC2_W) instruction_handle.getInstruction()).getValue(constpoolgen);
        } else if (instruction_handle.getInstruction() instanceof ConstantPushInstruction) {
            value = ((ConstantPushInstruction) instruction_handle.getInstruction()).getValue();
        } else {
            throw new RuntimeException();
        }
        return value;
    }

    public static boolean isConstantInstruction(Instruction instruction) {
        return (instruction instanceof LDC || instruction instanceof LDC_W || instruction instanceof LDC2_W || instruction instanceof ConstantPushInstruction);
    }
}
