package com.example.mongocrud.utils;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiJavaFile;

public class DtoUtils {
    private DtoUtils() {
    }

    public static void copyFields(PsiClass aClass, PsiJavaFile javaFile) {
        for (PsiField field : aClass.getAllFields()) {
            PsiClass psiClass = javaFile.getClasses()[0];
            PsiElementFactory elementFactory = PsiElementFactory.getInstance(aClass.getProject());
            PsiField cField = elementFactory.createFieldFromText("private " + field.getType().getCanonicalText() + " " + field.getName() + ";", null);
            aClass.add(cField);
            psiClass.add(field);
        }
    }
}
