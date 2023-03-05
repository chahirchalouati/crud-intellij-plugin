package com.crcl.mongocrud.utils;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;

public class DialogUtils {
    private DialogUtils() {
    }

    public static void getInfoDialog(Project project, String message) {
        Messages.showMessageDialog(project, message, "INFO", Messages.getInformationIcon());
    }


}
