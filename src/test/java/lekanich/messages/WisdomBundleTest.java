package lekanich.messages;


import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import lekanich.messages.WisdomBundle.Advice;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Lekanich
 */
public class WisdomBundleTest {
	@Test
	public void checkAdviceKeys() {
		Assert.assertNotEquals(0, WisdomBundle.getAmount());

		Set<String> messages = new HashSet<>();
		for (int i = 0; i < WisdomBundle.getAmount(); i++) {
			Optional<Advice> optional = WisdomBundle.getAdvice(i);
			Assert.assertTrue(optional.isPresent());
			Advice advice = optional.get();
			Assert.assertNotNull(advice.getMessage());
			Assert.assertNotNull(advice.getTitle());
			messages.add(advice.getMessage());
		}

		Assert.assertEquals(WisdomBundle.getAmount(), messages.size());
	}
}