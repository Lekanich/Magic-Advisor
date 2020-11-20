package lekanich.messages;

import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import com.intellij.AbstractBundle;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.PropertyKey;

public class WisdomBundle extends AbstractBundle {
    @NonNls
    private static final String BUNDLE_NAME = "messages.WisdomBundle";

    /**
     * The {@link java.util.ResourceBundle} instance.
     */
    private static final ResourceBundle BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);
    private static final WisdomBundle INSTANCE = new WisdomBundle();

    private WisdomBundle() {
        super(BUNDLE_NAME);
    }

    public static String message(@PropertyKey(resourceBundle = BUNDLE_NAME) String key, Object... params) {
        return INSTANCE.getMessage(key, params);
    }

    public static List<String> getAdvices() {
        return BUNDLE.keySet().stream()
                .map(WisdomBundle::message)
                .collect(Collectors.toList());
    }
}
