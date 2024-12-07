package lekanich;

import java.util.Random;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author Lekanich
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Randomizer {
	private static final Random RANDOM = new Random();

	public static int nextInt(final int upperBound) {
		return RANDOM.nextInt(upperBound);
	}
}
