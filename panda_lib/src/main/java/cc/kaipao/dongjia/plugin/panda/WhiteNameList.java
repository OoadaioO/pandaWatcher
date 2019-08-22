package cc.kaipao.dongjia.plugin.panda;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import androidx.annotation.Keep;

@Keep
class WhiteNameList {

    private static final List<String> classWhiteList = new ArrayList<>();
    private static final List<String> packageWhiteList = new ArrayList<>();

    static {

        classWhiteList.add("androidx.lifecycle.HolderFragment.class");
        classWhiteList.add("androidx.lifecycle.LifecycleDispatcher$DestructionReportFragment.class");

        packageWhiteList.add("android.arch.lifecycle");
        packageWhiteList.add("com.bumptech.glide");
        packageWhiteList.add("com.growingio.android");
    }

    static void addClassToWhiteList(String clazzName) {
        classWhiteList.add(clazzName);
    }

    static void addPackageToWhiteList(String packageName) {
        packageWhiteList.add(packageName);
    }

    static boolean contain(String clazzName) {

        if (classWhiteList.contains(clazzName)) {
            return true;
        }else{
            Iterator<String> it = packageWhiteList.iterator();
            while (it.hasNext()) {
                String packageName = it.next();
                if (clazzName.startsWith(packageName)) {
                    return true;
                }
            }
        }
        return false;
    }
}
