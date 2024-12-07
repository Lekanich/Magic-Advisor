package lekanich.messages;

import com.intellij.AbstractBundle;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.PropertyKey;

/**
 * @author Lekanich
 */
public class CommonBundle extends AbstractBundle {
    @NonNls
    private static final String BUNDLE_NAME = "messages.CommonBundle";

    private static final CommonBundle BUNDLE = new CommonBundle();

    private CommonBundle() {
        super(BUNDLE_NAME);
    }

    public static String message(@PropertyKey(resourceBundle = BUNDLE_NAME) final String key, final Object... params) {
        return BUNDLE.getMessage(key, params);
    }
}
