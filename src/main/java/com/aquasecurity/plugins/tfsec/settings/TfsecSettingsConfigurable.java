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
        boolean modified = !tfsecSettingsComponent.getTfsecPath().equals(settings.tfsecPath);
        return modified;
    }

    @Override
    public void apply() {
        TfsecSettingState settings = TfsecSettingState.getInstance();
        settings.tfsecPath = tfsecSettingsComponent.getTfsecPath();
    }

    @Override
    public void reset() {
        TfsecSettingState settings = TfsecSettingState.getInstance();
        tfsecSettingsComponent.setTfsecPath(settings.tfsecPath);
    }

    @Override
    public void disposeUIResources() {
        tfsecSettingsComponent = null;
    }

}
