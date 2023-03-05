package com.crcl.mongocrud.utils;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.PsiShortNamesCache;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ClassImportUtils {

    public static Optional<PsiClass> findClassGlobal(String className, Project project) {
        final int idx = className.lastIndexOf(".");
        if (idx == -1)
            return Optional.empty();
        final String packageName = className.substring(0, idx);
        final String name = className.substring(idx + 1);
        final PsiShortNamesCache namesCache = PsiShortNamesCache.getInstance(project);
        final PsiClass[] classes = namesCache.getClassesByName(name, GlobalSearchScope.allScope(project));
        return Arrays.stream(classes)
                .filter(filterClassesByPackageName(packageName))
                .findFirst();

    }

    public static List<Boolean> importClasses(Map<String, Object> map,
                                              Project project,
                                              PsiJavaFile javaFile) {
        return map.values().stream()
                .map(findOrGetPsiClass(project))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(javaFile::importClass)
                .collect(Collectors.toList());
    }

    private static Function<Object, Optional<PsiClass>> findOrGetPsiClass(Project project) {
        return object -> {
            if (object instanceof PsiClass)
                return Optional.of((PsiClass) object);
            return ClassImportUtils.findClassGlobal((String) object, project);
        };
    }

    private static Predicate<PsiClass> filterClassesByPackageName(String packageName) {
        return aClass -> {
            final PsiJavaFile javaFile = (PsiJavaFile) aClass.getContainingFile();
            return javaFile.getPackageName().equals(packageName);
        };
    }
}
