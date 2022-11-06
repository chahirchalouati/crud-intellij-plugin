package com.example.mongocrud.processors.impl;

import com.example.mongocrud.processors.DtoProcessor;
import com.example.mongocrud.processors.Processor;
import com.example.mongocrud.utils.ClassImportUtils;
import com.example.mongocrud.utils.FieldUtils;
import com.example.mongocrud.utils.PsiCommonUtils;
import com.example.mongocrud.utils.PsiDirectoryUtils;
import com.intellij.ide.highlighter.JavaFileType;
import com.intellij.psi.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.example.mongocrud.utils.CommandUtils.execute;
import static com.example.mongocrud.utils.OperationTypeUtils.DTO_OPERATION;
import static java.lang.String.format;

public class DtoProcessorImpl extends DtoProcessor {
    private static final Map<String, String> MAP = new HashMap<>();

    static {
    }

    @Override
    public Processor process(PsiClass aClass, PsiFile psiFile) {
        final PsiFileFactory fileFactory = PsiFileFactory.getInstance(aClass.getProject());
        final String repoName = format("%sDto.java", aClass.getName());
        final PsiJavaFile javaFile = (PsiJavaFile) fileFactory.createFileFromText(repoName, JavaFileType.INSTANCE, this.generateTemplate(aClass));
        ClassImportUtils.importClasses(MAP, aClass, javaFile);
        final PsiDirectory parentDirectory = PsiDirectoryUtils.getParentDirectory(psiFile);
        final PsiDirectory buildTargetDirectory = PsiDirectoryUtils.buildTargetDirectory(psiFile, parentDirectory, "dtos");
        final String packageName = PsiCommonUtils.getPackageName((PsiJavaFile) psiFile, "dtos");
        execute(psiFile.getProject(), v -> {
            javaFile.setPackageName(packageName);
            PsiCommonUtils.format(javaFile, psiFile.getProject());
            buildTargetDirectory.add(javaFile);
        });

        this.processHistory.put(DTO_OPERATION, javaFile.getClasses()[0]);
        return this;
    }


    @Override
    public Processor next(Processor processor) {
        return processor;
    }


    @Override
    public String generateTemplate(PsiClass aClass) {
        return "public class " + aClass.getName() + "Dto {" + fieldBuilder(aClass.getAllFields()) + "}";
    }

    private String fieldBuilder(PsiField[] allFields) {
        return Arrays.stream(allFields)
                .map(field -> format("private %s %s ;", resolveFieldType(field), field.getName()))
                .map(s -> s.concat("\n"))
                .reduce(String::concat)
                .orElse("");
    }

    private static String resolveFieldType(PsiField field) {
        final String fieldType = field.getType().getCanonicalText();
        final boolean isClass = FieldUtils.isJavaName(fieldType) || FieldUtils.isJavaWord(fieldType);
        if (!isClass) {
            MAP.put(fieldType, fieldType);
            String[] parts = fieldType.split("\\.");
            return parts[parts.length - 1];
        }
        return fieldType;
    }
}
