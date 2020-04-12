package lekanich;

import com.intellij.AbstractBundle;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.PropertyKey;

import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@NoArgsConstructor
public class WisdomBundle {
	/**
	 * The {@link java.util.ResourceBundle} path.
	 */
	@NonNls
	private static final String BUNDLE_NAME = "messages.WisdomBundle";

	/**
	 * The {@link java.util.ResourceBundle} instance.
	 */
	private static final ResourceBundle BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

	private static final String WISDOM_PREFIX = "wisdom.advice.";
	private static final String MAGIC_BALL_PREFIX = "wisdom.magic.ball.";

	public static String message(@PropertyKey(resourceBundle = BUNDLE_NAME) String key, Object... params) {
		return AbstractBundle.message(BUNDLE, key, params);
	}

	public static List<String> getMagicBallAdvices() {
		return BUNDLE.keySet().stream()
				.filter(key -> key.startsWith(MAGIC_BALL_PREFIX))
				.map(WisdomBundle::message)
				.collect(Collectors.toList());
	}

	public static List<String> getAdvices() {
		return BUNDLE.keySet().stream()
				.filter(key -> key.startsWith(WISDOM_PREFIX))
				.map(WisdomBundle::message)
				.collect(Collectors.toList());
	}
}
