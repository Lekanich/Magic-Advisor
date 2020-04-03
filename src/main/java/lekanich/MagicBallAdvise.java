package lekanich;

import com.intellij.notification.*;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.util.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class MagicBallAdvise extends AnAction {
	/**
	 * in seconds
	 */
	private static final int TIMEOUT_FOR_REPEAT_ANSWER = 3;

	/**
	 * in seconds
	 */
	private static final int TIMEOUT_FOR_HALF_CHANCE_TO_REPEAT_ANSWER = 10;

	/**
	 * [when happens, what it was]
	 */
	private static AtomicReference<Pair<Long, String>> lastValue = new AtomicReference<>();

	@Override
	public void actionPerformed(@NotNull AnActionEvent e) {
		String message = selectMessage();

		NotificationGroup group = new NotificationGroup(WisdomBundle.message("wisdom.main.name"), NotificationDisplayType.STICKY_BALLOON, true);
		Notification notification = group.createNotification(
				WisdomBundle.message("wisdom.main.magic.ball.title"),
				message,
				NotificationType.INFORMATION,
				new NotificationListener.UrlOpeningListener(false)
		);

		Notifications.Bus.notify(notification, e.getProject());
	}

	private String selectMessage() {
		long now = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
		Pair<Long, String> last = lastValue.get();

		if (last != null) {
			boolean reuse = needToReuse(now - last.getFirst());
			if (reuse) {
				lastValue.set(Pair.create(now, last.getSecond()));
				return last.getSecond();
			}
		}

		List<String> wisdom = WisdomBundle.getMagicBallAdvices();
		if (wisdom.isEmpty()) {
			wisdom = Collections.singletonList(WisdomBundle.message("wisdom.dummy.answer"));
		}

		String message = wisdom.get(randomIndex(wisdom));
		lastValue.set(Pair.create(now, message));
		return message;
	}

	private boolean needToReuse(long timeDelta) {
		if (timeDelta <= TIMEOUT_FOR_REPEAT_ANSWER) {
			return true;
		} else if (timeDelta <= TIMEOUT_FOR_HALF_CHANCE_TO_REPEAT_ANSWER) {
			return Math.random() <= 0.5;
		}
		return false;
	}

	private int randomIndex(List<String> wisdom) {
		return (int) (Math.random() * wisdom.size());
	}

	@Override
	public boolean isDumbAware() {
		return true;
	}

}
