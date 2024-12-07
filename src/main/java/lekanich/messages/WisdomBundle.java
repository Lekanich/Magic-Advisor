package lekanich.messages;

import java.text.MessageFormat;
import java.util.*;
import java.util.stream.IntStream;
import com.intellij.util.text.OrdinalFormat;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WisdomBundle {

	private static final Map<Author, ResourceBundle> BUNDLES = new EnumMap<>(Author.class);
	private static final Map<Integer, String> NUMERIC_KEY_TO_REAL = new HashMap<>();
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

		@Override
		public String toString() {
			return "[" + title + " : " + message + "]";
		}
	}

	static {
		int index = 0;
		for (Author author : Author.values()) {
			final ResourceBundle bundle = ResourceBundle.getBundle(author.bundleName);
			BUNDLES.put(author, bundle);
			final Set<String> keys = bundle.keySet();
			ADVICE_CAPACITY[author.ordinal()] = keys.size();

			// calculate numeric keys for a real (this one is global, why not)
			for (String key : keys) {
				NUMERIC_KEY_TO_REAL.put(index++, key);
			}
		}
		ADVICE_AMOUNT = IntStream.of(ADVICE_CAPACITY).sum();
	}

	public static int getAmount() {
		return ADVICE_AMOUNT;
	}

	public static void printStatistic() {
		log.info("Advice amount: {}", ADVICE_AMOUNT);
		int prevEnd = 0;
		for (Author author : Author.values()) {
			log.info("{}: {}, {}-{}", author.bundleName, ADVICE_CAPACITY[author.ordinal()],
					prevEnd, prevEnd + ADVICE_CAPACITY[author.ordinal()]);
			prevEnd += ADVICE_CAPACITY[author.ordinal()];
		}
	}

	public static Optional<Advice> getAdvice(final int number) {
		final Author author = calculateAuthor(number);
		if (author == null) {
			return Optional.empty();
		}

		final ResourceBundle bundle = BUNDLES.get(author);
		return Optional.of(new Advice(
				message(bundle, NUMERIC_KEY_TO_REAL.get(number)),
				CommonBundle.message(author.key)));
	}

	private static Author calculateAuthor(final int number) {
		int sum = 0;
		for (int i = 0; i < ADVICE_CAPACITY.length; i++) {
			sum += ADVICE_CAPACITY[i];
			if (number < sum) {
				return Author.values()[i];
			}
		}
		return null;
	}

	public static String message(@NotNull final ResourceBundle bundle,
								 @NotNull final String key,
								 final Object @NotNull ... params) {
		String value;
		try {
			value = bundle.getString(key);
		} catch (MissingResourceException e) {
			value = null;
		}
		if (value == null) {
			return null;
		}

		final MessageFormat format = new MessageFormat(value);
		OrdinalFormat.apply(format);
		return format.format(params);
	}
}
