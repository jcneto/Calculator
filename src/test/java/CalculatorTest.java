import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CalculatorTest
{

	private Calculator calculator;

	@Before
	public void setup()
	{
		calculator = new Calculator();
	}

	@Test
	public void mainTest()
	{
		int result = calculator.calculate("add(1, 2)");
		Assert.assertEquals(3, result);
	}

	@Test
	public void mainTest2()
	{
		int result = calculator.calculate("add(1, mult(2, 3))");
		Assert.assertEquals(7, result);
	}

	@Test
	public void mainTest3()
	{
		int result = calculator.calculate("mult(add(2,2),div(9,3))");
		Assert.assertEquals(12, result);
	}

	@Test
	public void mainTest4()
	{
		int result = calculator.calculate("add(1, add(1, add(1, add(1, add(1, add(1, add(1, add(1, add(1, add(1, 2))))))))))");
		Assert.assertEquals(12, result);
	}

	@Test
	public void mainTest5()
	{
		int result = calculator.calculate("sub(add(2,2),div(9,3))");
		Assert.assertEquals(1, result);
	}

	@Test
	public void mainTest6()
	{
		int result = calculator.calculate("let(a, 5, add(a, a))");
		Assert.assertEquals(10, result);
	}

	@Test
	public void mainTest7()
	{
		int result = calculator.calculate("let(a, 5, let(b, mult(a, 10), add(b, a)))");
		Assert.assertEquals(55, result);
	}
}
