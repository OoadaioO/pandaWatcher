package cc.kaipao.dongjia.plugin;


import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 *
 */
public class SetAdapterMethodVisitor extends MethodVisitor implements Opcodes {

    private String className;

    public SetAdapterMethodVisitor(String className, MethodVisitor methodVisitor) {
        super(Opcodes.ASM6, methodVisitor);
        this.className = className;
    }


    @Override
    public void visitCode() {
        visitVarInsn(ALOAD, 0);
        visitTypeInsn(NEW, "cc/kaipao/dongjia/plugin/panda/WatcherOnAdapterChangeListener");
        visitInsn(DUP);
        visitMethodInsn(INVOKESPECIAL, "cc/kaipao/dongjia/plugin/panda/WatcherOnAdapterChangeListener", "<init>", "()V", false);
        visitMethodInsn(INVOKEVIRTUAL, className, "addOnAdapterChangeListener", "(Landroidx/viewpager/widget/ViewPager$OnAdapterChangeListener;)V", false);
        super.visitCode();

    }
}
