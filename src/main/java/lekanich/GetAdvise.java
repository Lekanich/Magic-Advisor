package lekanich;

import com.intellij.notification.*;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;

public class GetAdvise extends AnAction {

	@Override
	public void actionPerformed(@NotNull AnActionEvent e) {
		NotificationGroup group = new NotificationGroup(WisdomBundle.message("wisdom.main.name"), NotificationDisplayType.STICKY_BALLOON, true);
		Notification notification = group.createNotification(
				WisdomBundle.message("wisdom.main.title"),
				WisdomBundle.message("wisdom.dummy.cheer.up"),
				NotificationType.INFORMATION,
				new NotificationListener.UrlOpeningListener(false)
		);

		Notifications.Bus.notify(notification, e.getProject());
	}
}
