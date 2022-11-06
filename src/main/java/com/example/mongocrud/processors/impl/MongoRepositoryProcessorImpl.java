package com.example.mongocrud.processors.impl;

import com.example.mongocrud.processors.Processor;
import com.example.mongocrud.processors.RepositoryProcessor;
import com.example.mongocrud.utils.ClassImportUtils;
import com.example.mongocrud.utils.PsiCommonUtils;
import com.example.mongocrud.utils.PsiDirectoryUtils;
import com.intellij.ide.highlighter.JavaFileType;
import com.intellij.psi.*;

import java.util.HashMap;
import java.util.Map;

import static com.example.mongocrud.utils.CommandUtils.execute;
import static com.example.mongocrud.utils.OperationTypeUtils.MONGO_OPERATION_REPOSITORY;

public class MongoRepositoryProcessorImpl extends RepositoryProcessor {
    private static final Map<String, Object> MAP = new HashMap<>();

    static {
        MAP.put("MongoRepository", "org.springframework.data.mongodb.repository.MongoRepository");
        MAP.put("Repository", "org.springframework.stereotype.Repository");
    }

    @Override
    public Processor process(PsiClass aClass, PsiFile psiFile) {
        final PsiFileFactory fileFactory = PsiFileFactory.getInstance(aClass.getProject());
        final String repoName = String.format("%sRepository.java", aClass.getName());
        final PsiJavaFile javaFile = (PsiJavaFile) fileFactory.createFileFromText(repoName, JavaFileType.INSTANCE, this.generateTemplate(aClass));
        MAP.put(aClass.getName(), aClass);
        ClassImportUtils.importClasses(MAP, aClass.getProject(), javaFile);
        final PsiDirectory parentDirectory = PsiDirectoryUtils.getParentDirectory(psiFile);
        final PsiDirectory buildTargetDirectory = PsiDirectoryUtils.buildTargetDirectory(psiFile, parentDirectory, "repository");
        final String packageName = PsiCommonUtils.getPackageName((PsiJavaFile) psiFile, "repository");
        execute(psiFile.getProject(), v -> {
            javaFile.setPackageName(packageName);
            PsiCommonUtils.format(javaFile, psiFile.getProject());
            buildTargetDirectory.add(javaFile);
        });
        this.processHistory.put(MONGO_OPERATION_REPOSITORY, javaFile.getClasses()[0]);
        return this;
    }


    @Override
    public Processor next(Processor processor) {
        return processor;
    }

    @Override
    public String generateTemplate(PsiClass aClass) {
        return "@Repository\n" + "public interface " + aClass.getName() + "Repository extends MongoRepository<" + aClass.getName() + ", String> {}";
    }

}
