package com.example.mongocrud.utils;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.PsiShortNamesCache;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ClassImportUtils {
    public static Optional<PsiClass> findClassGlobal(String className, Project project) {
        final int idx = className.lastIndexOf(".");
        if (idx == -1)
            return Optional.empty();
        String packageName = className.substring(0, idx);
        String name = className.substring(idx + 1);
        PsiShortNamesCache namesCache = PsiShortNamesCache.getInstance(project);
        PsiClass[] classes = namesCache.getClassesByName(name, GlobalSearchScope.allScope(project));
        return Arrays.stream(classes)
                .filter(filterClassesByPackageName(packageName))
                .findFirst();

    }
    public static List<Boolean> importClasses(Map<String, String> map , PsiClass aClass, PsiJavaFile javaFile) {
        javaFile.importClass(aClass);
        return map.values().stream()
                .map(s -> ClassImportUtils.findClassGlobal(s, aClass.getProject()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(javaFile::importClass)
                .collect(Collectors.toList());
    }

    @NotNull
    private static Predicate<PsiClass> filterClassesByPackageName(String packageName) {
        return aClass -> {
            PsiJavaFile javaFile = (PsiJavaFile) aClass.getContainingFile();
            return javaFile.getPackageName().equals(packageName);
        };
    }
}
