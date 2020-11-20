package lekanich.messages;

import com.intellij.AbstractBundle;
import org.jetbrains.annotations.NonNls;

/**
 * @author Lekanich
 */
public class MagicBallBundle extends AbstractBundle {
    @NonNls
    private static final String BUNDLE_NAME = "messages.MagicBallBundle";
    @NonNls
    public static final String[] KEYS = {
            "wisdom.magic.ball.positive.1",
            "wisdom.magic.ball.positive.2",
            "wisdom.magic.ball.positive.3",
            "wisdom.magic.ball.positive.4",
            "wisdom.magic.ball.positive.5",
            "wisdom.magic.ball.negative.1",
            "wisdom.magic.ball.negative.2",
            "wisdom.magic.ball.negative.3",
            "wisdom.magic.ball.negative.4",
            "wisdom.magic.ball.negative.5",
            "wisdom.magic.ball.non.commit.positive.1",
            "wisdom.magic.ball.non.commit.positive.2",
            "wisdom.magic.ball.non.commit.positive.3",
            "wisdom.magic.ball.non.commit.positive.4",
            "wisdom.magic.ball.non.commit.positive.5",
            "wisdom.magic.ball.neutral.1",
            "wisdom.magic.ball.neutral.2",
            "wisdom.magic.ball.neutral.3",
            "wisdom.magic.ball.neutral.4",
            "wisdom.magic.ball.neutral.5"
    };

    private static final MagicBallBundle BUNDLE = new MagicBallBundle();

    private MagicBallBundle() {
        super(BUNDLE_NAME);
    }

    public static int getMagicBallAdvicesCount() {
        return KEYS.length;
    }

    public static String getMagicBallAdviceByIndex(int index) {
        return BUNDLE.getMessage(KEYS[index]);
    }
}
