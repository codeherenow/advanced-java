package com.codeherenow.bookstore.common;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author Ragunath Jawahar <www.codeherenow.com>
 */
@Target({FIELD, METHOD})
@Retention(RUNTIME)
public @interface Column {

    /*
     * In Oracle's JVM {@link Class#getDeclaredFields()} returns the fields in the
     * order they were declared. But you shouldn't rely on this.
     *
     * Different VM implementations may have different behaviors. For instance, the Dalvik VM
     * in Android returns the fields in alphabetical order.
     */
    public int index();

    /*
     * Making this field optional is based on the assumption that we will not be using a obfuscation
     * tool such as ProGuard. If ProGuard is used, the fields will be renamed and the retrieved column
     * names (derived from the fields) will have the new field name given by ProGuard. Under circumstances
     * where you can't rely on the field name, it's essential to set this attribute.
     */
    public String name() default "$null";
}
