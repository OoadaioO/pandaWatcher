package cc.kaipao.dongjia.plugin;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 *
 */
public class OnCreateMethodVisitor extends MethodVisitor implements Opcodes {

    private String className;

    public OnCreateMethodVisitor(MethodVisitor mv,String className) {
        super(Opcodes.ASM6, mv);
        this.className = className;
    }


    @Override
    public void visitCode() {
        visitVarInsn(ALOAD, 0);
        visitMethodInsn(INVOKEVIRTUAL, className, "getLifecycle", "()Landroidx/lifecycle/Lifecycle;", false);
        visitTypeInsn(NEW, "cc/kaipao/dongjia/plugin/panda/WatcherLifecycleObserver");
        visitInsn(DUP);
        visitVarInsn(ALOAD, 0);
        visitMethodInsn(INVOKEVIRTUAL, "java/lang/Object", "getClass", "()Ljava/lang/Class;", false);
        visitMethodInsn(INVOKEVIRTUAL, "java/lang/Class", "getName", "()Ljava/lang/String;", false);
        visitMethodInsn(INVOKESPECIAL, "cc/kaipao/dongjia/plugin/panda/WatcherLifecycleObserver", "<init>", "(Ljava/lang/String;)V", false);
        visitMethodInsn(INVOKEVIRTUAL, "androidx/lifecycle/Lifecycle", "addObserver", "(Landroidx/lifecycle/LifecycleObserver;)V", false);

        super.visitCode();
    }
}
