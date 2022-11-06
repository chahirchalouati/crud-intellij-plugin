package com.example.mongocrud.processors;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiJavaFile;

public interface Processor {
    PsiJavaFile process(PsiClass aClass);
}
