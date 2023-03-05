package com.crcl.mongocrud.utils;

import com.intellij.psi.PsiClass;

import java.util.Arrays;

import static java.util.Objects.nonNull;

public class AnnotationUtils {
    private AnnotationUtils() {
    }

    public static boolean hasRequiredAnnotations(PsiClass aClass, String... s) {
        return Arrays.stream(s).allMatch(s1 -> nonNull(aClass.getAnnotation(s1)));
    }
}
