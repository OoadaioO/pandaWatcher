package cc.kaipao.dongjia.plugin

import com.android.build.api.transform.*
import com.android.build.gradle.AppExtension
import com.android.build.gradle.internal.pipeline.TransformManager
import org.apache.commons.compress.utils.IOUtils
import org.apache.commons.io.FileUtils
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.internal.impldep.org.apache.commons.codec.digest.DigestUtils
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.ClassWriter

import java.util.jar.JarEntry
import java.util.jar.JarFile
import java.util.jar.JarOutputStream
import java.util.zip.ZipEntry

public class WatcherTransform extends Transform implements Plugin<Project> {

    public static final List<String> TARGET_LIST = Arrays.asList(
            "androidx/fragment/app/Fragment.class",
            "androidx/fragment/app/FragmentActivity.class",
            "androidx/viewpager/widget/ViewPager.class"
    )

    @Override
    void apply(Project project) {

        def android = project.extensions.getByType(AppExtension)
        android.registerTransform(this)

    }

    @Override
    String getName() {
        return "PandaWatcher"
    }

    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    @Override
    boolean isIncremental() {
        return false
    }


    @Override
    void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {

        // 不支持增量编译，删除之前的输出
        TransformOutputProvider outputProvider = transformInvocation.outputProvider
        if (outputProvider != null) {
            outputProvider.deleteAll()
        }


        transformInvocation.inputs.each { TransformInput input ->

            // 文件类型input遍历
            input.directoryInputs.each { DirectoryInput directoryInput ->

                HashMap<String, File> modifyMap = new HashMap<>()

                File dir = directoryInput.file;
                if (dir.isDirectory()) {

                    // 循环遍历文件
                    dir.eachFileRecurse { File file ->
                        File modifiedFile = modifyClassFile(dir, file, transformInvocation.context.temporaryDir)
                        if (modifiedFile != null) {
                            modifyMap.put(file.absolutePath.replace(dir.absolutePath, ""), modifiedFile)
                        }
                    }
                }

                def dest = transformInvocation.outputProvider.getContentLocation(directoryInput.name, directoryInput.contentTypes,
                        directoryInput.scopes, Format.DIRECTORY)
                FileUtils.copyFile(dir, dest)

                modifyMap.entrySet().each { Map.Entry<String, File> entry ->
                    File target = new File(dest.absolutePath + entry.getKey())
                    if (target.exists()) {
                        target.delete()
                    }
                    println "watcher#replace:"+target.absolutePath
                    FileUtils.copyFile(entry.getValue(), target)
                    entry.getValue().delete()
                }

            }

            // jar类型input遍历
            input.jarInputs.each { JarInput jarInput ->

                // 重命名，避免输出jar包重名
                def jarName = jarInput.name
                def md5Name = DigestUtils.md5Hex(jarInput.file.getAbsolutePath())
                if (jarName.endsWith(".jar")) {
                    jarName = jarName.substring(0, jarName.length() - 4)
                }

                JarFile jarFile = new JarFile(jarInput.file)
                // 临时输出目录
                File tmpFile = new File(transformInvocation.context.temporaryDir, md5Name + jarName)

                // 清除缓存
                if (tmpFile.exists()) {
                    tmpFile.delete()
                }

                JarOutputStream jarOutputStream = new JarOutputStream(new FileOutputStream(tmpFile))
                Enumeration<JarEntry> enumeration = jarFile.entries()
                while (enumeration.hasMoreElements()) {
                    JarEntry jarEntry = enumeration.nextElement()
                    String entryName = jarEntry.getName()
                    ZipEntry zipEntry = new ZipEntry(entryName)
                    InputStream inputStream = jarFile.getInputStream(zipEntry)
                    byte[] originalBytes = IOUtils.toByteArray(inputStream)
                    jarOutputStream.putNextEntry(zipEntry)
                    if (isTargetClass(entryName)) {
                        println "watcher#modify jar entry:" + entryName
                        byte[] modifyCodeBytes = modifyClass(array)
                        jarOutputStream.write(modifyCodeBytes)
                    } else {
                        jarOutputStream.write(originalBytes)
                    }

                }

                jarOutputStream.close()
                jarFile.close()


                // 获取输出目录
                File dest = transformInvocation.outputProvider.getContentLocation(jarName + md5Name,
                        jarInput.contentTypes,
                        jarInput.scopes,
                        Format.JAR)
                FileUtils.copyFile(tmpFile, dest)
                tmpFile.delete()
            }
        }
    }

    private File modifyClassFile(File dir, File classFile, File tempDir) {

        if (isTargetClass(classFile.name)) {
            String className = classFile.absolutePath.replace(dir.absolutePath + File.separator, "")
            File modifiedFile = new File(tempDir, className)

            println "watcher#modify class:" + modifiedFile.absolutePath

            // 删除缓存
            if (modifiedFile.exists()) {
                modifiedFile.delete()
            }
            modifiedFile.createNewFile()


            byte[] data = modifyClass(classFile.bytes)
            FileOutputStream fos = new FileOutputStream(modifiedFile)
            fos.write(data)
            fos.close()

            return modifiedFile
        }

        return null;
    }

    private boolean isTargetClass(String name) {
        return TARGET_LIST.contains(name)
    }

    private byte[] modifyClass(byte[] bytes) {
        ClassReader classReader = new ClassReader(bytes)
        ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS)
        ClassVisitor visitor = new WatcherClassVisitor(classWriter)
        classReader.accept(visitor, ClassReader.EXPAND_FRAMES)
        return classWriter.toByteArray()
    }


}