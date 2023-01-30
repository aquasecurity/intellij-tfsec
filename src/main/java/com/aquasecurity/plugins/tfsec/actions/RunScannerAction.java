package com.aquasecurity.plugins.tfsec.actions;


import com.aquasecurity.plugins.tfsec.ui.notify.TfsecNotificationGroup;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataConstants;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.fileTypes.FileTypes;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

/**
 * RunScannerAction executes tfsec then calls update results
 */
public class RunScannerAction extends AnAction {

    private Project project;
    private String directory;

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

        this.directory = this.project.getBasePath();
        VirtualFile virtualFile = CommonDataKeys.VIRTUAL_FILE.getData(e.getDataContext());

        if (virtualFile != null) {
            if (virtualFile.getFileType() == FileTypes.UNKNOWN) {
                this.directory = virtualFile.getPath();
            } else {
                this.directory = virtualFile.getParent().getPath();
            }
        }

        File resultFile = null;
        try {
            resultFile = File.createTempFile("tfsec", ".json");
        } catch (IOException ex) {
            TfsecNotificationGroup.notifyError(project, ex.getLocalizedMessage());
        }

        TfsecBackgroundRunTask runner = new TfsecBackgroundRunTask(project, directory, resultFile, ResultProcessor::updateResults);
        if (SwingUtilities.isEventDispatchThread()) {
            ProgressManager.getInstance().run(runner);
        } else {
            ApplicationManager.getApplication().invokeLater(runner);
        }
    }


}
