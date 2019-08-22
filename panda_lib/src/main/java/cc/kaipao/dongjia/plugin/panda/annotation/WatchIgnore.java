package cc.kaipao.dongjia.plugin.panda.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 此注解标记的activity或者fragment不会进行生命周期打点
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface WatchIgnore {
}
