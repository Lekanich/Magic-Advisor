package lekanich.messages;

import java.util.ResourceBundle;
import com.intellij.AbstractBundle;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.PropertyKey;

/**
 * @author Lekanich
 */
public class CommonBundle {
    @NonNls
    private static final String BUNDLE_NAME = "messages.CommonBundle";

    private static final ResourceBundle BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

    public static String message(@PropertyKey(resourceBundle = BUNDLE_NAME) String key, Object... params) {
        return AbstractBundle.message(BUNDLE, key, params);
    }
}
