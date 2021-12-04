package lekanich;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import lekanich.messages.CommonBundle;
import lekanich.messages.WisdomBundle;
import lekanich.messages.WisdomBundle.Advice;
import org.jetbrains.annotations.NotNull;

public final class GetAdvise extends AnAction {

	@Override
	public void actionPerformed(@NotNull final AnActionEvent e) {
		final Advice advice = selectMessage();
		final Notification notification = NotificationCenter.getNotificationGroup()
				.createNotification(advice.getMessage(), NotificationType.INFORMATION);
		notification.setTitle(advice.getTitle());

		Notifications.Bus.notify(notification, e.getProject());
	}

	private Advice selectMessage() {
		return WisdomBundle.getAdvice(Randomizer.nextInt(WisdomBundle.getAmount()))
				.orElseGet(() -> new Advice(
						CommonBundle.message("wisdom.dummy.answer"),
						CommonBundle.message("wisdom.main.advisor.title")
				));
	}

	@Override
	public boolean isDumbAware() {
		return true;
	}
}
