package com.aquasecurity.plugins.tfsec.settings;


import com.intellij.openapi.options.Configurable;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * Provides controller functionality for application settings.
 */
public class TfsecSettingsConfigurable implements Configurable {

    private TfsecSettingsComponent tfsecSettingsComponent;

    // A default constructor with no arguments is required because this implementation
    // is registered as an applicationConfigurable EP

    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return "Tfsec: Settings";
    }

    @Override
    public JComponent getPreferredFocusedComponent() {
        return tfsecSettingsComponent.getPreferredFocusedComponent();
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        tfsecSettingsComponent = new TfsecSettingsComponent();
        return tfsecSettingsComponent.getPanel();
    }

    @Override
    public boolean isModified() {
        TfsecSettingState settings = TfsecSettingState.getInstance();
        return !tfsecSettingsComponent.getTfsecPath().equals(settings.tfsecPath) ||
                !tfsecSettingsComponent.getTfsecOptions().equals(settings.tfsecOptions);
    }

    @Override
    public void apply() {
        TfsecSettingState settings = TfsecSettingState.getInstance();
        settings.tfsecPath = tfsecSettingsComponent.getTfsecPath();
        settings.tfsecOptions = tfsecSettingsComponent.getTfsecOptions();
    }

    @Override
    public void reset() {
        TfsecSettingState settings = TfsecSettingState.getInstance();
        tfsecSettingsComponent.setTfsecPath(settings.tfsecPath);
        tfsecSettingsComponent.setTfsecOptions(settings.tfsecOptions);
    }

    @Override
    public void disposeUIResources() {
        tfsecSettingsComponent = null;
    }

}
