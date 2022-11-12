package com.example.mongocrud.processors.impl;

import com.example.mongocrud.processors.DtoProcessor;
import com.example.mongocrud.processors.Processor;
import com.example.mongocrud.utils.*;
import com.intellij.ide.highlighter.JavaFileType;
import com.intellij.psi.*;
import org.apache.commons.lang.text.StrSubstitutor;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static com.example.mongocrud.utils.CommandUtils.execute;
import static com.example.mongocrud.utils.OperationTypeUtils.DTO_OPERATION;
import static java.lang.String.format;

public class DtoProcessorImpl extends DtoProcessor {
    private static final String DIR_NAME = "dtos";
    private static final String SUFFIX = "Dto";
    private final Map<String, Object> importsMap;
    private final Map<String, String> fields;

    public DtoProcessorImpl() {
        importsMap = new HashMap<>();
        fields = new HashMap<>();
    }
    /**
     * @deprecated
     */
    @Deprecated(forRemoval = true, since = "")
    private static Function<Map.Entry<String, String>, String> getTemplate(String tpl) {
        return entry -> {
            final Map<String, String> valuesMap = new HashMap<>();
            valuesMap.put("Cname", StringUtils.capitalize(entry.getKey()));
            valuesMap.put("name", entry.getKey());
            valuesMap.put("type", entry.getValue());
            StrSubstitutor sub = new StrSubstitutor(valuesMap);
            return sub.replace(tpl);
        };
    }

    private static Function<Map.Entry<String, String>, String> getPropsTemplate() {
        return entry -> "\"" + entry.getKey() + "='\" + " + entry.getKey() + " + '\\'' +";
    }

    @Override
    public Processor process(PsiClass aClass, PsiFile psiFile) {
        final PsiFileFactory fileFactory = PsiFileFactory.getInstance(aClass.getProject());
        final String fileName = format("%s%s.java", aClass.getName(), SUFFIX);
        final PsiJavaFile javaFile = (PsiJavaFile) fileFactory.createFileFromText(fileName, JavaFileType.INSTANCE, this.generateTemplate(aClass));
        ClassImportUtils.importClasses(importsMap, aClass.getProject(), javaFile);
        DtoUtils.copyFields(aClass, javaFile);
        final PsiDirectory parentDirectory = PsiDirectoryUtils.getParentDirectory(psiFile);
        final PsiDirectory buildTargetDirectory = PsiDirectoryUtils.buildTargetDirectory(psiFile, parentDirectory, DIR_NAME);
        final String packageName = PsiCommonUtils.getPackageName((PsiJavaFile) psiFile, DIR_NAME);
        execute(psiFile.getProject(), () -> {
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
        return "public class "
                + aClass.getName()
                + "Dto {}";
    }
    /**
     * @deprecated
     */
    @Deprecated(forRemoval = true, since = "")
    private String printToStringTemplate(String className) {
        return "@Override\n" +
                "public String toString() {\n" +
                "return \"" + className + "{\" +\n" +
                printTemplate(getPropsTemplate()) +
                "'}';\n" +
                "}";
    }

    private String printTemplate(Function<Map.Entry<String, String>, String> function) {
        return this.fields.entrySet()
                .stream()
                .map(function)
                .map(s -> s.concat("\n"))
                .reduce(String::concat)
                .orElse("");
    }

    /**
     * @deprecated
     */
    @Deprecated(forRemoval = true, since = "")
    private String printFields(PsiField[] allFields) { // Noncompliant
        return Arrays.stream(allFields)
                .map(declareFields())
                .map(s -> s.concat("\n"))
                .reduce(String::concat)
                .orElse("");
    }

    private Function<PsiField, String> declareFields() {
        return field -> format("private %s %s ;", resolveFieldType(field), field.getName());
    }

    private String resolveFieldType(PsiField field) {
        final String fieldType = field.getType().getCanonicalText();
        final boolean isPrimitiveDataType = FieldUtils.isInJavaNames(fieldType) || FieldUtils.isInJavaWords(fieldType);
        if (isPrimitiveDataType) {
            this.fields.put(field.getName(), fieldType);
            return fieldType;
        }
        String[] parts = fieldType.split("\\.");
        this.importsMap.put(fieldType, fieldType);
        this.fields.put(field.getName(), fieldType);
        return parts[parts.length - 1];
    }
}
