package com.aquasecurity.plugins.tfsec.settings;

import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.ui.components.fields.ExpandableTextField;
import com.intellij.ui.components.JBLabel;
import com.intellij.util.ui.FormBuilder;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * Supports creating and managing a {@link JPanel} for the Settings Dialog.
 */
public class TfsecSettingsComponent {

    private final JPanel settingsPanel;
    private final TextFieldWithBrowseButton tfsecPath = new TextFieldWithBrowseButton();

    private final ExpandableTextField tfsecOptions = new ExpandableTextField();

    public TfsecSettingsComponent() {

        tfsecPath.addBrowseFolderListener("tfsec binary path", "Set the explicit path to tfsec",
                ProjectManager.getInstance().getDefaultProject(), FileChooserDescriptorFactory.createSingleFileDescriptor()               );

        settingsPanel = FormBuilder.createFormBuilder()
                .addLabeledComponent(new JBLabel("Specific tfsec path: "), tfsecPath, 1, true)
                .addLabeledComponent(new JBLabel("tfsec options: "), tfsecOptions, 1, false)
                .addComponentFillVertically(new JPanel(), 0)
                .getPanel();
    }

    public JPanel getPanel() {
        return settingsPanel;
    }

    public JComponent getPreferredFocusedComponent() {
        return tfsecPath;
    }

    @NotNull
    public String getTfsecPath() {
        return tfsecPath.getText();
    }

    @NotNull
    public String getTfsecOptions() {
        return tfsecOptions.getText();
    }

    public void setTfsecPath(@NotNull String newText) {
        tfsecPath.setText(newText);
    }

    public void setTfsecOptions(@NotNull String newOptions) {
        tfsecOptions.setText(newOptions);
    }
}
