package com.crcl.mongocrud.actions;

import com.crcl.mongocrud.processors.ControllerProcessorImpl;
import com.crcl.mongocrud.processors.impl.DtoProcessorImpl;
import com.crcl.mongocrud.processors.impl.RepositoryProcessorFactory;
import com.crcl.mongocrud.processors.impl.ServiceProcessorFactory;
import com.crcl.mongocrud.utils.PsiCommonUtils;
import com.crcl.mongocrud.utils.PsiLanguageUtils;
import com.intellij.lang.java.JavaLanguage;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiJavaFile;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Optional;

import static com.crcl.mongocrud.utils.AnnotationUtils.hasRequiredAnnotations;
import static com.crcl.mongocrud.utils.DependenciesUtils.MONGO_DOCUMENT_MAPPING;
import static com.crcl.mongocrud.utils.OperationTypeUtils.MONGO_OPERATION_REPOSITORY;
import static com.crcl.mongocrud.utils.OperationTypeUtils.MONGO_OPERATION_SERVICE;

public class CrudAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        Optional.ofNullable(event.getProject()).ifPresent(project -> {
            final PsiFile psiFile = event.getData(CommonDataKeys.PSI_FILE);
            PsiLanguageUtils.validateFileLanguage(psiFile, JavaLanguage.INSTANCE);
            final PsiClass aClass = PsiCommonUtils.getPsiJavaTargetClass((PsiJavaFile) Objects.requireNonNull(psiFile));
            if (hasRequiredAnnotations(aClass, MONGO_DOCUMENT_MAPPING)) {
                RepositoryProcessorFactory.getRepository(MONGO_OPERATION_REPOSITORY)
                        .process(aClass, psiFile)
                        .next(new DtoProcessorImpl())
                        .process(aClass, psiFile)
                        .next(ServiceProcessorFactory.getServiceProcessor(MONGO_OPERATION_SERVICE))
                        .process(aClass, psiFile)
                        .next(new ControllerProcessorImpl())
                        .process(aClass, psiFile);
            }
        });
    }


}
