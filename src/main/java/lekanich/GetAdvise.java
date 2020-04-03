package lekanich;

import com.intellij.notification.*;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class GetAdvise extends AnAction {

	@Override
	public void actionPerformed(@NotNull AnActionEvent e) {
		String message = selectMessage();

		NotificationGroup group = new NotificationGroup(WisdomBundle.message("wisdom.main.name"), NotificationDisplayType.STICKY_BALLOON, true);
		Notification notification = group.createNotification(
				WisdomBundle.message("wisdom.main.advisor.title"),
				message,
				NotificationType.INFORMATION,
				new NotificationListener.UrlOpeningListener(false)
		);

		Notifications.Bus.notify(notification, e.getProject());
	}

	private String selectMessage() {
		List<String> wisdom = WisdomBundle.getAdvices();
		if (wisdom.isEmpty()) {
			wisdom = Collections.singletonList(WisdomBundle.message("wisdom.dummy.answer"));
		}
		return wisdom.get((int) (Math.random() * wisdom.size()));
	}

	@Override
	public boolean isDumbAware() {
		return true;
	}
}
