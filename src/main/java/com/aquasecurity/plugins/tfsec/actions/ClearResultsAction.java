package com.aquasecurity.plugins.tfsec.actions;

import com.aquasecurity.plugins.tfsec.ui.TfsecWindow;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.ui.content.Content;
import org.jetbrains.annotations.NotNull;

/**
 * ClearResultsAction removes the tree and findings
 */
public class ClearResultsAction extends AnAction {

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
        final ToolWindow toolWindow = ToolWindowManager.getInstance(project).getToolWindow("TFSec Findings");
        final Content content = toolWindow.getContentManager().getContent(0);
        final TfsecWindow tfsecWindow = (TfsecWindow) content.getComponent();
        tfsecWindow.updateFindings(null);
        tfsecWindow.redraw();
    }
}


