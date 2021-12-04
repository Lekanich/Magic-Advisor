package lekanich.messages;

import java.text.MessageFormat;
import java.util.EnumMap;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.IntStream;
import com.intellij.util.text.OrdinalFormat;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WisdomBundle {

	private static final Map<Author, ResourceBundle> BUNDLES = new EnumMap<>(Author.class);
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
		for (Author author : Author.values()) {
			final ResourceBundle bundle = ResourceBundle.getBundle(author.bundleName);
			BUNDLES.put(author, bundle);
			ADVICE_CAPACITY[author.ordinal()] = bundle.keySet().size();
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
				final ResourceBundle bundle = BUNDLES.get(author);
				return Optional.of(new Advice(
						message(bundle, String.valueOf(number)),
						CommonBundle.message(author.key)));
			}
		}

		return Optional.empty();
	}

	public static String message(@Nullable final ResourceBundle bundle,
								 @NotNull final String key,
								 Object @NotNull ... params) {
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
