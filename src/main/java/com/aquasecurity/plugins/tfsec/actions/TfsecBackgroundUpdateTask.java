package com.aquasecurity.plugins.tfsec.actions;

import com.aquasecurity.plugins.tfsec.ui.notify.TfsecNotificationGroup;
import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.process.OSProcessHandler;
import com.intellij.execution.process.ScriptRunnerUtil;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

class TfsecBackgroundUpdateTask extends Task.Backgroundable implements Runnable {

    private final Project project;

    public TfsecBackgroundUpdateTask(Project project) {
        super(project, "Updating tfsec if required", false);
        this.project = project;
    }

    @Override
    public void run(@NotNull ProgressIndicator indicator) {
        this.run();
    }

    @Override
    public void run() {
        List<String> commandParts = new ArrayList<>();
        commandParts.add("tfsec");
        commandParts.add("--update");

        GeneralCommandLine commandLine = new GeneralCommandLine(commandParts);

        Process process;
        try {
            process = commandLine.createProcess();
        } catch (ExecutionException e) {
            TfsecNotificationGroup.notifyError(project, e.getLocalizedMessage());
            return;
        }

        TfsecNotificationGroup.notifyInformation(project, commandLine.getCommandLineString());
        OSProcessHandler handler = new OSProcessHandler(process, commandLine.getCommandLineString());

        try {
            String updateOutput = ScriptRunnerUtil.getProcessOutput(handler,
                    ScriptRunnerUtil.STDOUT_OR_STDERR_OUTPUT_KEY_FILTER,
                    100000000);
            if (updateOutput.startsWith("Error during update")) {
                TfsecNotificationGroup.notifyWarning(project, updateOutput);
            } else {
                TfsecNotificationGroup.notifyInformation(project, updateOutput);
            }
        } catch (ExecutionException e) {
            TfsecNotificationGroup.notifyError(project, e.getLocalizedMessage());
        }

    }
}
