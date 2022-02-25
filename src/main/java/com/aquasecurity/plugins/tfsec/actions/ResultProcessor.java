package com.aquasecurity.plugins.tfsec.actions;

import com.aquasecurity.plugins.tfsec.model.Findings;
import com.aquasecurity.plugins.tfsec.ui.TfsecWindow;
import com.aquasecurity.plugins.tfsec.ui.notify.TfsecNotificationGroup;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.ui.content.Content;

import java.io.File;
import java.io.IOException;

/**
 * ResultProcessor takes the results finding and unmarshalls to object
 * Then updates the findings window
 */
public class ResultProcessor {

    public static void updateResults(Project project, File resultFile) {

        Findings findings;
        try {
            findings = new ObjectMapper().readValue(resultFile, Findings.class);
        } catch (IOException e) {
            TfsecNotificationGroup.notifyError(project, String.format("Failed to deserialize the results file. %s", e.getLocalizedMessage()));
            return;
        }

        // redraw the explorer with the updated content
        final ToolWindow toolWindow = ToolWindowManager.getInstance(project).getToolWindow("Tfsec Findings");
        final Content content = toolWindow.getContentManager().getContent(0);
        final TfsecWindow tfsecWindow = (TfsecWindow) content.getComponent();
        tfsecWindow.updateFindings(findings);
        tfsecWindow.redraw();
    }

}
