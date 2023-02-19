package kingmc.common.environment;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(RuntimeResources.class)
public @interface RuntimeResource {
    String value();
    String hash();
    String name() default "";
    String tag() default "";
    boolean zip() default false;

}