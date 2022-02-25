package com.aquasecurity.plugins.tfsec.actions;


import com.aquasecurity.plugins.tfsec.ui.notify.TfsecNotificationGroup;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

/**
 * RunScannerAction executes tfsec then calls update results
 */
public class RunScannerAction extends AnAction {

    private Project project;

    @Override
    public void update(@NotNull AnActionEvent e) {
        super.update(e);
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        this.project = e.getProject();

        if (project == null) {
            return;
        }

        File resultFile = null;
        try {
            resultFile = File.createTempFile("tfsec", ".json");
        } catch (IOException ex) {
            TfsecNotificationGroup.notifyError(project, ex.getLocalizedMessage());
        }

        TfsecBackgroundRunTask runner = new TfsecBackgroundRunTask(project, resultFile, ResultProcessor::updateResults);
        if (SwingUtilities.isEventDispatchThread()) {
            ProgressManager.getInstance().run(runner);
        } else {
            ApplicationManager.getApplication().invokeLater(runner);
        }
    }


}


