package clarity.gay.modules.api;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ModuleInfo {
    String name();
    String description()    default "";
    Category category();
    int bind()              default 0;
    boolean startup()       default false;
}