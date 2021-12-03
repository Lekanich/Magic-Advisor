package lekanich.messages;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;
import com.intellij.AbstractBundle;
import com.intellij.DynamicBundle;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WisdomBundle {

	private static final Map<Author, AbstractBundle> BUNDLES = new EnumMap<>(Author.class);
	private static final int[] ADVICE_CAPACITY = new int[Author.values().length];
	private static final int ADVICE_AMOUNT;

	@RequiredArgsConstructor
	public enum Author {
		PLATO("messages.PlatoBundle", "wisdom.author.plato"),
		SOCRATES("messages.SocratesBundle", "wisdom.author.socrates"),
		GANDHI("messages.GandhiBundle", "wisdom.author.gandhi"),
		COMMON("messages.WisdomBundle", "wisdom.author.common");

		private final String bundleName;
		private final String key;
	}

	@Getter
	@RequiredArgsConstructor
	public static final class Advice {
		private final String message;
		private final String title;
	}

	static {
		for (Author author : Author.values()) {
			final AbstractBundle bundle = new DynamicBundle(author.bundleName);
			BUNDLES.put(author, bundle);
			ADVICE_CAPACITY[author.ordinal()] = bundle.getResourceBundle().keySet().size();
		}
		ADVICE_AMOUNT = IntStream.of(ADVICE_CAPACITY).sum();
	}

	public static int getAmount() {
		return ADVICE_AMOUNT;
	}

	public static Optional<Advice> getAdvice(int number) {
		for (int i = 0; i < ADVICE_CAPACITY.length; i++) {
			if (ADVICE_CAPACITY[i] < number) {
				number -= ADVICE_CAPACITY[i];
			} else {
				final Author author = Author.values()[i];
				final AbstractBundle bundle = BUNDLES.get(author);
				return Optional.of(new Advice(
						bundle.getMessage(String.valueOf(number)),
						CommonBundle.message(author.key)));
			}
		}

		return Optional.empty();
	}
}
