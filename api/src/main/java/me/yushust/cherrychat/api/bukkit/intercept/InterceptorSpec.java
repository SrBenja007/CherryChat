package me.yushust.cherrychat.api.bukkit.intercept;

import org.bukkit.plugin.Plugin;

import java.lang.annotation.*;

/**
 * Annotation for specifying interceptor
 * data like the holder plugin and the
 * interceptor name (unique)
 * @see MessageInterceptor
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface InterceptorSpec {

  /** The holder plugin */
  Class<? extends Plugin> plugin();

  /** The interceptor name */
  String name();

}
