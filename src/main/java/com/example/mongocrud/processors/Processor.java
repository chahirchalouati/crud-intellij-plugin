package com.example.mongocrud.processors;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiFile;

import java.util.HashMap;
import java.util.Map;

public abstract class Processor {
    protected final Map<String, PsiClass> processHistory = new HashMap<>();

    public abstract Processor process(PsiClass aClass, PsiFile psiFile);

    public abstract Processor next(Processor processor);

    public abstract String generateTemplate(PsiClass aClass);
}
