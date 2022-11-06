package com.example.mongocrud.processors.impl;

import com.example.mongocrud.processors.MapperProcessor;
import com.example.mongocrud.processors.Processor;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiFile;

public class MapperProcessorImpl extends MapperProcessor {

    @Override
    public Processor process(PsiClass aClass, PsiFile psiFile) {
        return this;
    }


    @Override
    public Processor next(Processor processor) {
        return this;
    }


    @Override
    public String generateTemplate(PsiClass aClass) {
        return "this";
    }
}
