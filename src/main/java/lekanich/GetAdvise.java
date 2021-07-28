package lekanich;

import java.util.Collections;
import java.util.List;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationListener;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import lekanich.messages.CommonBundle;
import lekanich.messages.WisdomBundle;
import org.jetbrains.annotations.NotNull;

public final class GetAdvise extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        String message = selectMessage();

        Notification notification = NotificationCenter.getNotificationGroup().createNotification(
                CommonBundle.message("wisdom.main.advisor.title"),
                message,
                NotificationType.INFORMATION
        );

        Notifications.Bus.notify(notification, e.getProject());
    }

    private String selectMessage() {
        List<String> wisdom = WisdomBundle.getAdvices();
        if (wisdom.isEmpty()) {
            wisdom = Collections.singletonList(CommonBundle.message("wisdom.dummy.answer"));
        }
        return wisdom.get((int) (Math.random() * wisdom.size()));
    }

    @Override
    public boolean isDumbAware() {
        return true;
    }
}
