package com.example.mongocrud.utils;

import com.intellij.lang.Language;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.psi.*;
import com.intellij.psi.codeStyle.CodeStyleManager;
import java.util.Optional;

import static com.intellij.debugger.ui.impl.watch.MessageDescriptor.INFORMATION;
import static java.util.Objects.nonNull;

public class PsiUtils {
    public static void format(PsiElement psiElement, Project project) {
        CodeStyleManager.getInstance(project).reformat(psiElement);
    }

    public static PsiDirectory getOrCreateSubDirectory(PsiDirectory parentDirectory, String subDirectoryName, Project project) {
        return Optional.ofNullable(parentDirectory.findSubdirectory(subDirectoryName)).orElseGet(() -> {
            Runnable r = () -> parentDirectory.createSubdirectory(subDirectoryName);
            WriteCommandAction.runWriteCommandAction(project, r);
            return parentDirectory.findSubdirectory(subDirectoryName);
        });
    }

    public static void validateFileLanguage(PsiFile psiFile, Language language) {
        if (nonNull(psiFile) && psiFile.getLanguage() != language) {
            displayInfoDialog(psiFile.getProject(), "the selected file is not a java file");
        }
    }

    public static void displayInfoDialog(Project project, String message) {
        Messages.showMessageDialog(project, message, String.valueOf(INFORMATION), Messages.getInformationIcon());
    }

    public static PsiClass getPsiJavaTargetClass(PsiJavaFile psiFile) {
        return psiFile.getClasses()[0];
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
