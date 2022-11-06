package com.example.mongocrud.utils;

import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiFile;

import java.util.Objects;
import java.util.Optional;

public class PsiDirectoryUtils {
    private PsiDirectoryUtils() {
    }

    public static PsiDirectory getOrCreateSubDirectory(PsiDirectory parentDirectory, String subDirectoryName, Project project) {
        return Optional.ofNullable(parentDirectory.findSubdirectory(subDirectoryName)).orElseGet(() -> {
            Runnable r = () -> parentDirectory.createSubdirectory(subDirectoryName);
            WriteCommandAction.runWriteCommandAction(project, r);
            return parentDirectory.findSubdirectory(subDirectoryName);
        });
    }

    public static PsiDirectory buildTargetDirectory(PsiFile psiFile, PsiDirectory parentDirectory, String dirName) {
        return PsiDirectoryUtils.getOrCreateSubDirectory(Objects.requireNonNull(parentDirectory),
                dirName,
                psiFile.getProject());
    }

    public static PsiDirectory getParentDirectory(PsiFile psiFile) {
        return Objects.requireNonNull(psiFile)
                .getContainingDirectory()
                .getParentDirectory();
    }
}
