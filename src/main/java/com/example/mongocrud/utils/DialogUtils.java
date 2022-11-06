package com.example.mongocrud.utils;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;

import static com.intellij.debugger.ui.impl.watch.MessageDescriptor.INFORMATION;

public class DialogUtils {
    public static void getInfoDialog(Project project, String message) {
        Messages.showMessageDialog(project, message, String.valueOf(INFORMATION), Messages.getInformationIcon());
    }


}
