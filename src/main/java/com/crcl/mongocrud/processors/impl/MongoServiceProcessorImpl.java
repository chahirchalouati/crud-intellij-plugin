package com.crcl.mongocrud.processors.impl;

import com.crcl.mongocrud.processors.Processor;
import com.crcl.mongocrud.processors.ServiceProcessor;
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
