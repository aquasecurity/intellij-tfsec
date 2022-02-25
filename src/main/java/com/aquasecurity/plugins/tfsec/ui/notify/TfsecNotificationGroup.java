package com.aquasecurity.plugins.tfsec.ui.notify;

import com.intellij.notification.NotificationGroupManager;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.Nullable;

public class TfsecNotificationGroup {
    public static void notifyError(@Nullable Project project, String content) {
        notify(project, content, NotificationType.ERROR);
    }

    public static void notifyWarning(@Nullable Project project, String content) {
        notify(project, content, NotificationType.WARNING);
    }

    public static void notifyInformation(@Nullable Project project, String content) {
        notify(project, content, NotificationType.INFORMATION);
    }

    private static void notify(@Nullable Project project, String content, NotificationType notificationType) {
        NotificationGroupManager.getInstance().getNotificationGroup("TFSec Notifications")
                .createNotification(content, notificationType)
                .notify(project);
    }
}
