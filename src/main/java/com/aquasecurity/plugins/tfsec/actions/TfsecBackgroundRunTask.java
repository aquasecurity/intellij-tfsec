package com.aquasecurity.plugins.tfsec.actions;

import com.aquasecurity.plugins.tfsec.settings.TfsecSettingState;
import com.aquasecurity.plugins.tfsec.ui.notify.TfsecNotificationGroup;
import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.process.OSProcessHandler;
import com.intellij.execution.process.ScriptRunnerUtil;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;

class TfsecBackgroundRunTask extends Task.Backgroundable implements Runnable {

    private final Project project;
    private final String directory;
    private final File resultFile;
    private final BiConsumer<Project, File> callback;

    public TfsecBackgroundRunTask(Project project, String directory, File resultFile, BiConsumer<Project, File> updateResults) {
        super(project, "Running tfsec", false);
        this.project = project;
        this.directory = directory;
        this.resultFile = resultFile;
        this.callback = updateResults;
    }

    @Override
    public void run(@NotNull ProgressIndicator indicator) {
        this.run();
    }

    @Override
    public void run() {
        List<String> commandParts = new ArrayList<>();
        TfsecSettingState settingState = TfsecSettingState.getInstance();

        commandParts.add(settingState.tfsecPath);
        commandParts.add("-f=json");
        commandParts.add("--soft-fail");
        commandParts.add(String.format("--out=%s", resultFile.getAbsolutePath()));

        if (!Objects.equals(settingState.tfsecOptions, "")) {
            commandParts.add(settingState.tfsecOptions);
        }

        commandParts.add(this.directory);

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
            ScriptRunnerUtil.getProcessOutput(handler,
                    ScriptRunnerUtil.STDOUT_OR_STDERR_OUTPUT_KEY_FILTER,
                    100000000);
            TfsecNotificationGroup.notifyInformation(project, "tfsec run completed, updating results");
            SwingUtilities.invokeLater(() -> {
                callback.accept(this.project, this.resultFile);
            });
        } catch (ExecutionException e) {
            TfsecNotificationGroup.notifyError(project, e.getLocalizedMessage());
        }

    }
}
