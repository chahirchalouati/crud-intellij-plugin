package com.example.mongocrud.utils;

import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;

import java.util.function.Consumer;

public class CommandUtils {
    public static void execute(Project project, Consumer consumer) {
        WriteCommandAction.runWriteCommandAction(project, () -> consumer.accept(null));

    }

}
