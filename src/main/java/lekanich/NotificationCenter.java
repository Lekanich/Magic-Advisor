package lekanich;

import java.util.Optional;
import com.intellij.notification.NotificationDisplayType;
import com.intellij.notification.NotificationGroup;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author Lekanich
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NotificationCenter {

    public static NotificationGroup getNotificationGroup() {
        String displayId = "Magic Advisor";
        return Optional.ofNullable(NotificationGroup.findRegisteredGroup(displayId))
                .orElseGet(() -> new NotificationGroup(displayId, NotificationDisplayType.BALLOON, true));
    }

}
