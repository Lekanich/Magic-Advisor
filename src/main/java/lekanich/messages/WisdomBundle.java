package lekanich.messages;

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

    public static String message(@PropertyKey(resourceBundle = BUNDLE_NAME) String key, Object... params) {
        return AbstractBundle.message(BUNDLE, key, params);
    }

    public static List<String> getMagicBallAdvices() {
        return BUNDLE.keySet().stream()
                .map(WisdomBundle::message)
                .collect(Collectors.toList());
    }
}
