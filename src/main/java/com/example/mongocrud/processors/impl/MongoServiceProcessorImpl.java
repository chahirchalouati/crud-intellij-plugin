package com.example.mongocrud.processors.impl;

import com.example.mongocrud.processors.Processor;
import com.example.mongocrud.processors.ServiceProcessor;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiFile;

public class MongoServiceProcessorImpl extends ServiceProcessor {
    @Override
    public Processor process(PsiClass aClass, PsiFile psiFile) {
        return this;
    }

    @Override
    public Processor next(Processor processor) {
        return processor;
    }

    @Override
    public String generateTemplate(PsiClass aClass) {
        return null;
    }


}
