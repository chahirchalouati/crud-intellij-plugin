package com.example.mongocrud.actions;

import com.example.mongocrud.utils.PsiUtils;
import com.example.mongocrud.processors.Processor;
import com.example.mongocrud.service.RepositoryProcessorFactory;
import com.example.mongocrud.utils.RespositoryTypeUtils;
import com.intellij.lang.java.JavaLanguage;
import com.intellij.openapi.actionSystem.*;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiJavaFile;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Optional;

import static com.example.mongocrud.utils.AnnotationUtils.hasRequiredAnnotations;
import static com.example.mongocrud.utils.DependenciesUtils.MONGO_DOCUMENT_MAPPING;

public class CrudAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        Optional.ofNullable(event.getProject()).ifPresent(project -> {
            final PsiFile psiFile = event.getData(CommonDataKeys.PSI_FILE);
            PsiUtils.validateFileLanguage(psiFile, JavaLanguage.INSTANCE);
            final PsiClass aClass = PsiUtils.getPsiJavaTargetClass((PsiJavaFile) Objects.requireNonNull(psiFile));
            if (hasRequiredAnnotations(aClass, MONGO_DOCUMENT_MAPPING)) {
                final Processor processor = RepositoryProcessorFactory.getRepository(RespositoryTypeUtils.MONGO_REPOSITORY);
                final PsiJavaFile psiJavaFile = processor.process(aClass);
                final PsiDirectory parentDirectory = Objects.requireNonNull(psiFile).getContainingDirectory().getParentDirectory();
                final PsiDirectory repository = PsiUtils.getOrCreateSubDirectory(Objects.requireNonNull(parentDirectory), "repository", project);
                final String packageName = PsiUtils.getPackageName((PsiJavaFile) psiFile, "repository");
                psiJavaFile.setPackageName(packageName);
                PsiUtils.format(psiJavaFile, project);
                repository.add(psiJavaFile);
            }
        });
    }


}
