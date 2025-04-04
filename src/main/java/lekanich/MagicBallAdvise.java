package lekanich;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.util.Pair;
import lekanich.messages.CommonBundle;
import lekanich.messages.MagicBallBundle;
import org.jetbrains.annotations.NotNull;

public final class MagicBallAdvise extends AnAction {
	/**
	 * in seconds
	 */
	private static final int TIMEOUT_FOR_REPEAT_ANSWER = 2;

	/**
	 * in seconds
	 */
	private static final int TIMEOUT_FOR_HALF_CHANCE_TO_REPEAT_ANSWER = 5;

	/**
	 * [when happens, what it was]
	 */
	private static final AtomicReference<Pair<Long, String>> LAST_VALUE = new AtomicReference<>();

	@Override
	public void actionPerformed(@NotNull final AnActionEvent e) {
		final String message = selectMessage();

		final Notification notification = NotificationCenter.getNotificationGroup()
				.createNotification(message, NotificationType.INFORMATION);
		notification.setTitle(CommonBundle.message("wisdom.main.magic.ball.title"));

		Notifications.Bus.notify(notification, e.getProject());
	}

	private String selectMessage() {
		final long now = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
		final Pair<Long, String> last = LAST_VALUE.get();

		if (last != null) {
			final boolean reuse = needToReuse(now - last.getFirst());
			if (reuse) {
				LAST_VALUE.set(Pair.create(now, last.getSecond()));
				return last.getSecond();
			}
		}

		final int size = MagicBallBundle.getMagicBallAdvicesCount();
		if (size == 0) {
			return CommonBundle.message("wisdom.dummy.answer");
		}

		final int randomIndex = Randomizer.nextInt(size);
		final String message = MagicBallBundle.getMagicBallAdviceByIndex(randomIndex);
		LAST_VALUE.set(Pair.create(now, message));
		return message;
	}

	private boolean needToReuse(final long timeDelta) {
		if (timeDelta <= TIMEOUT_FOR_REPEAT_ANSWER) {
			return true;
		} else if (timeDelta <= TIMEOUT_FOR_HALF_CHANCE_TO_REPEAT_ANSWER) {
			return Math.random() <= 0.5;
		}
		return false;
	}

	@Override
	public boolean isDumbAware() {
		return true;
	}

}
