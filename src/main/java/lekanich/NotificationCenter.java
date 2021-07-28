package lekanich;

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
        return NotificationGroup.findRegisteredGroup(displayId);
    }

}
