package com.example.mongocrud.utils;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.codeStyle.CodeStyleManager;

public class PsiCommonUtils {

    public static PsiClass getPsiJavaTargetClass(PsiJavaFile psiFile) {
        return psiFile.getClasses()[0];
    }

    public static void format(PsiElement psiElement, Project project) {
        CodeStyleManager.getInstance(project).reformat(psiElement);
    }

    public static String getPackageName(PsiJavaFile psiFile, String folderName) {
        String[] parts = psiFile.getPackageName().split("\\.");
        String[] copy = new String[parts.length];
        if (parts.length - 1 >= 0) {
            System.arraycopy(parts, 0, copy, 0, parts.length - 1);
            copy[parts.length - 1] = folderName;
            return String.join(".", copy);
        }
        return folderName;
    }
}
