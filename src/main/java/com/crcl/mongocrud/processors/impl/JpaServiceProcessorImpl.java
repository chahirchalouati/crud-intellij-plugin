package com.crcl.mongocrud.processors.impl;

import com.crcl.mongocrud.processors.Processor;
import com.crcl.mongocrud.processors.ServiceProcessor;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiFile;

public class JpaServiceProcessorImpl extends ServiceProcessor {

    @Override
    public Processor process(PsiClass aClass, PsiFile psiFile) {
        return null;
    }

    @Override
    public Processor next(Processor processor) {
        return null;
    }

    @Override
    public String generateTemplate(PsiClass aClass) {
        return null;
    }
}
