package com.example.mongocrud.processors.impl;

import com.example.mongocrud.utils.ClassImportUtils;
import com.example.mongocrud.processors.RepositoryProcessor;
import com.intellij.ide.highlighter.JavaFileType;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.codeStyle.CodeStyleManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class MongoRepositoryProcessorImpl implements RepositoryProcessor {

    private static final Map<String, String> MAP = new HashMap<>();

    static {
        MAP.put("MongoRepository", "org.springframework.data.mongodb.repository.MongoRepository");
        MAP.put("Repository", "org.springframework.stereotype.Repository");
    }

    @Override
    public PsiJavaFile process(PsiClass aClass) {
        final PsiFileFactory fileFactory = PsiFileFactory.getInstance(aClass.getProject());
        final String repoName = String.format("%sRepository.java", aClass.getName());
        final PsiJavaFile javaFile = (PsiJavaFile) fileFactory
                .createFileFromText(repoName, JavaFileType.INSTANCE, this.buildMongoDBRepositoryContent(aClass));
        doImportClasses(aClass, javaFile);
        format(javaFile, javaFile.getProject());
        return javaFile;
    }

    private static List<Boolean> doImportClasses(PsiClass aClass, PsiJavaFile javaFile) {
        javaFile.importClass(aClass);
        return MAP.values().stream()
                .map(s -> ClassImportUtils.findClassGlobal(s, aClass.getProject()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(javaFile::importClass)
                .collect(Collectors.toList());


    }

    public void format(PsiElement psiElement, Project project) {
        CodeStyleManager.getInstance(project).reformat(psiElement);
    }


    private String buildMongoDBRepositoryContent(PsiClass aClass) {
        return "@Repository\n" +
                "public interface " + aClass.getName() + "Repository extends MongoRepository<" + aClass.getName() + ", String> {}";
    }

}
