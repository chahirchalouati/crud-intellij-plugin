package com.example.mongocrud.processors.impl;

import com.example.mongocrud.processors.Processor;
import com.example.mongocrud.processors.RepositoryProcessor;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiFile;

public class JpaRepositoryProcessorImpl extends RepositoryProcessor {
    /**
     * @param aClass
     * @param psiFile
     * @return
     */
    @Override
    public Processor process(PsiClass aClass, PsiFile psiFile) {
        return null;
    }

    /**
     * @param processor
     * @return
     */
    @Override
    public Processor next(Processor processor) {
        return null;
    }

    /**
     * @param aClass
     * @return
     */
    @Override
    public String generateTemplate(PsiClass aClass) {
        return null;
    }
}
