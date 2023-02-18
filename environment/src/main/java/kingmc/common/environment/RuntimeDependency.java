package kingmc.common.environment;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(RuntimeDependencies.class)
public @interface RuntimeDependency {
    String value();
    String test() default "";
    String repository() default "https://maven.aliyun.com/repository/central";
    boolean transitive() default true;
    boolean ignoreOptional() default true;
    boolean ignoreException() default false;
    DependencyScope[] scopes() default {DependencyScope.RUNTIME, DependencyScope.COMPILE};
    String[] relocate() default {};
    boolean initiative() default false;
}