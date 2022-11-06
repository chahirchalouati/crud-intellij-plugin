package com.example.mongocrud.utils;

import com.intellij.lang.Language;
import com.intellij.psi.PsiFile;

import static java.util.Objects.nonNull;

public class PsiLanguageUtils {
    public static void validateFileLanguage(PsiFile psiFile, Language language) {
        if (nonNull(psiFile) && psiFile.getLanguage() != language) {
            DialogUtils.getInfoDialog(psiFile.getProject(), "the selected file is not a java file");
        }
    }

}
