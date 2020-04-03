package lekanich;

import com.intellij.notification.*;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MagicBallAdvise extends AnAction {
	@Override
	public void actionPerformed(@NotNull AnActionEvent e) {
		List<String> wisdom = WisdomBundle.getMagicBallAdvices();
		if (wisdom.isEmpty()) {
			// or say some sad thing
			return;
		}
		String message = wisdom.get((int) (Math.random() * wisdom.size()));

		NotificationGroup group = new NotificationGroup(WisdomBundle.message("wisdom.main.name"), NotificationDisplayType.STICKY_BALLOON, true);
		Notification notification = group.createNotification(
				WisdomBundle.message("wisdom.main.magic.ball.title"),
				message,
				NotificationType.INFORMATION,
				new NotificationListener.UrlOpeningListener(false)
		);

		Notifications.Bus.notify(notification, e.getProject());
	}

	@Override
	public boolean isDumbAware() {
		return true;
	}

}
