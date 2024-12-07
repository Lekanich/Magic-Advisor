package lekanich.messages;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import com.intellij.openapi.util.Pair;
import lekanich.messages.WisdomBundle.Advice;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Lekanich
 */
@Slf4j
public class WisdomBundleTest {
	@Test
	public void checkAdviceKeys() {
		Assert.assertNotEquals(0, WisdomBundle.getAmount());

		final Map<String, List<Pair<String, Integer>>> messages = new HashMap<>();
		for (int i = 0; i < WisdomBundle.getAmount(); i++) {
			Optional<Advice> optional = WisdomBundle.getAdvice(i);
			Assert.assertTrue("Advice not found by index: " + i, optional.isPresent());
			Advice advice = optional.get();
			Assert.assertNotNull("Advice message not found by index: " + i, advice.getMessage());
			Assert.assertNotNull(advice.getTitle());

			messages.computeIfAbsent(advice.getMessage(), k -> new ArrayList<>()).add(new Pair<>(advice.getTitle(), i));
		}

		WisdomBundle.printStatistic();
		final List<Map.Entry<String, List<Pair<String, Integer>>>> duplicatedValues = messages.entrySet().stream()
				.filter(e -> e.getValue().size() > 1)
				.toList();
		duplicatedValues.forEach(e -> {
			log.error("Duplicated message: {}", e.getKey());
			e.getValue().forEach(p -> log.error("Title: {}, index: {}", p.getFirst(), p.getSecond()));
		});

		Assert.assertTrue(duplicatedValues.isEmpty());
		Assert.assertEquals(WisdomBundle.getAmount(), messages.size());
	}
}