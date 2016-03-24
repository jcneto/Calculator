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
	public void successfulTest()
	{
		int result = calculator.calculate("add(1, 2)");
		Assert.assertEquals(3, result);
	}

	@Test
	public void successfulTest2()
	{
		int result = calculator.calculate("add(1, mult(2, 3))");
		Assert.assertEquals(7, result);
	}

	@Test
	public void successfulTest3()
	{
		int result = calculator.calculate("mult(add(2,2),div(9,3))");
		Assert.assertEquals(12, result);
	}

	@Test
	public void successfulTest4()
	{
		int result = calculator.calculate("add(1, add(1, add(1, add(1, add(1, add(1, add(1, add(1, add(1, add(1, 2))))))))))");
		Assert.assertEquals(12, result);
	}

	@Test
	public void successfulTest5()
	{
		int result = calculator.calculate("sub(add(2,2),div(9,3))");
		Assert.assertEquals(1, result);
	}

	@Test
	public void successfulTest6()
	{
		int result = calculator.calculate("let(a, 5, add(a, a))");
		Assert.assertEquals(10, result);
	}

	@Test
	public void successfulTest7()
	{
		int result = calculator.calculate("let(a, 5, let(b, mult(a, 10), add(b, a)))");
		Assert.assertEquals(55, result);
	}

	@Test(expected = ArithmeticException.class)
	public void errorTest1()
	{
		calculator.calculate("div(1,0)");
	}

	@Test(expected = IllegalArgumentException.class)
	public void errorTest2()
	{
		calculator.calculate("THIS_IS_WRONG(1,1)");
	}

	@Test(expected = ArithmeticException.class)
	public void errorTest3()
	{
		calculator.calculate("mult(2147483647,3)");
	}

	@Test(expected = ArithmeticException.class)
	public void errorTest4()
	{
		calculator.calculate("add(2147483647,3)");
	}
}
