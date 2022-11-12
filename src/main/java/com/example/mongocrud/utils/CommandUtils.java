package com.example.mongocrud.utils;

import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;

public class CommandUtils {
    private CommandUtils() {
    }

    public static void execute(Project project, Command command) {
        WriteCommandAction.runWriteCommandAction(project, () -> {
            try {
                command.execute();
            } catch (Exception e) {
                DialogUtils.getInfoDialog(project, e.getMessage());
            }
        });

    }

}
