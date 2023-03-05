package com.crcl.mongocrud.processors;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiFile;

public class ControllerProcessorImpl extends ControllerProcessor {

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
