package cc.kaipao.dongjia.plugin;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 *
 */
public class WatcherClassVisitor extends ClassVisitor implements Opcodes {

    private String className;

    public WatcherClassVisitor(ClassVisitor cv) {
        super(Opcodes.ASM6, cv);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        this.className = name;
        super.visit(version, access, name, signature, superName, interfaces);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
        if (mv != null) {
            if ("androidx/fragment/app/Fragment".equals(className) && "onCreate".equals(name)) {
                return new OnCreateMethodVisitor(mv, this.className);
            }
            else if ("androidx/fragment/app/FragmentActivity".equals(className) && "onCreate".equals(name)) {
                return new OnCreateMethodVisitor(mv, this.className);
            }
            else if ("androidx/viewpager/widget/ViewPager".equals(this.className) && "setAdapter".equals(name)) {
                return new SetAdapterMethodVisitor(className, mv);
            }
        }
        return mv;
    }
}
