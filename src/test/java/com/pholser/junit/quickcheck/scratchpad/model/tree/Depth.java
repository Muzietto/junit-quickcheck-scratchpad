package com.pholser.junit.quickcheck.scratchpad.model.tree;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.pholser.junit.quickcheck.generator.GeneratorConfiguration;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

@Target({ PARAMETER, FIELD, ANNOTATION_TYPE, TYPE_USE })
@Retention(RUNTIME)
@GeneratorConfiguration
public @interface Depth {
    int min() default 0;
    int max();
}
