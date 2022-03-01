package com.aquasecurity.plugins.tfsec.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class UpdateTfsecAction extends AnAction {
    private Project project;

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        this.project = e.getProject();

        if (project == null) {
            return;
        }

        TfsecBackgroundUpdateTask runner = new TfsecBackgroundUpdateTask(project);
        if (SwingUtilities.isEventDispatchThread()) {
            ProgressManager.getInstance().run(runner);
        } else {
            ApplicationManager.getApplication().invokeLater(runner);
        }
    }
}
