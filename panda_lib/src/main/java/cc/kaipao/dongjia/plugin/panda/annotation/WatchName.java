package cc.kaipao.dongjia.plugin.panda.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * <pre>
 * 慈竹杰标记的类，打点时会忽视类名，使用指定的名字
 * </pre>
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface WatchName {

    String value();
}
