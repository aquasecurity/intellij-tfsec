package com.aquasecurity.plugins.tfsec.actions;

import com.aquasecurity.plugins.tfsec.settings.TfsecSettingsConfigurable;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.options.ShowSettingsUtil;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

public class ShowTfsecSettingsAction extends AnAction {


    @Override
    public void update(@NotNull AnActionEvent e) {
        super.update(e);
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {

        Project project = e.getProject();
        if ( project == null) {
            return ;
        }

        TfsecSettingsConfigurable configurable = new TfsecSettingsConfigurable();
       ShowSettingsUtil.getInstance().editConfigurable(project, configurable);
    }
}
