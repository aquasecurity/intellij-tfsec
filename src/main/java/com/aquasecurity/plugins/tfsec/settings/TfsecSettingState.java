package com.aquasecurity.plugins.tfsec.settings;


import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


@State(
        name = "com.aquasecurity.plugins.tfsec.settings.AppSettingsState",
        storages = @Storage("tfsec.xml")
)
public class TfsecSettingState implements PersistentStateComponent<TfsecSettingState> {

    public String tfsecPath = "tfsec";

    public static TfsecSettingState getInstance() {
        return ApplicationManager.getApplication().getService(TfsecSettingState.class);
    }

    @Nullable
    @Override
    public TfsecSettingState getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull TfsecSettingState state) {
        XmlSerializerUtil.copyBean(state, this);
    }

}