package com.example.mongocrud.processors.impl;

import com.example.mongocrud.processors.DtoProcessor;
import com.example.mongocrud.processors.Processor;
import com.example.mongocrud.utils.ClassImportUtils;
import com.example.mongocrud.utils.FieldUtils;
import com.example.mongocrud.utils.PsiCommonUtils;
import com.example.mongocrud.utils.PsiDirectoryUtils;
import com.intellij.ide.highlighter.JavaFileType;
import com.intellij.psi.*;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static com.example.mongocrud.utils.CommandUtils.execute;
import static com.example.mongocrud.utils.OperationTypeUtils.DTO_OPERATION;
import static java.lang.String.format;

public class DtoProcessorImpl extends DtoProcessor {
    private final Map<String, Object> map;
    private final String setterTemplate = "public void set{0}({1} {2}) {\n" + "this.{2} = {2};\n" + "}";
    private final String getterTemplate = "public {1} get{0}() { return this.{2} ;}\n";

    private final Map<String, String> fields;
    private static final String DIR_NAME = "dtos";
    private static final String SUFFIX = "Dto";

    public DtoProcessorImpl() {
        map = new HashMap<>();
        fields = new HashMap<>();
    }

    @Override
    public Processor process(PsiClass aClass, PsiFile psiFile) {
        final PsiFileFactory fileFactory = PsiFileFactory.getInstance(aClass.getProject());
        final String fileName = format("%s%s.java", aClass.getName(), SUFFIX);
        final PsiJavaFile javaFile = (PsiJavaFile) fileFactory.createFileFromText(fileName, JavaFileType.INSTANCE, this.generateTemplate(aClass));
        ClassImportUtils.importClasses(map, aClass.getProject(), javaFile);
        final PsiDirectory parentDirectory = PsiDirectoryUtils.getParentDirectory(psiFile);
        final PsiDirectory buildTargetDirectory = PsiDirectoryUtils.buildTargetDirectory(psiFile, parentDirectory, DIR_NAME);
        final String packageName = PsiCommonUtils.getPackageName((PsiJavaFile) psiFile, DIR_NAME);
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
        return "public class " + aClass.getName()
                + "Dto {"
                + fieldBuilder(aClass.getAllFields())
                + getterBuilder()
                + setterBuilder()
                + "}";
    }

    private String setterBuilder() {
        return this.fields.entrySet()
                .stream()
                .map(entry -> MessageFormat.format(setterTemplate,
                        StringUtils.capitalize(entry.getValue()),
                        entry.getKey(),
                        entry.getValue()))
                .map(s -> s.concat("\n"))
                .reduce(String::concat)
                .orElse("");
    }

    private String getterBuilder() {
        return this.fields.entrySet()
                .stream()
                .map(entry -> MessageFormat.format(getterTemplate,
                        StringUtils.capitalize(entry.getValue()),
                        entry.getKey(),
                        entry.getValue()))
                .map(s -> s.concat("\n"))
                .reduce(String::concat)
                .orElse("");
    }

    private String fieldBuilder(PsiField[] allFields) {
        return Arrays.stream(allFields)
                .map(declareFields())
                .map(s -> s.concat("\n"))
                .reduce(String::concat)
                .orElse("");
    }

    @NotNull
    private Function<PsiField, String> declareFields() {
        return field -> format("private %s %s ;", resolveFieldType(field), field.getName());
    }

    private String resolveFieldType(PsiField field) {
        final String fieldType = field.getType().getCanonicalText();
        final boolean isClass = FieldUtils.isJavaName(fieldType) || FieldUtils.isJavaWord(fieldType);
        if (!isClass) {
            this.fields.put(field.getName(), fieldType);
            String[] parts = fieldType.split("\\.");
            return parts[parts.length - 1];
        }
        return fieldType;
    }
}
