import org.apache.commons.lang3.StringUtils;

import Model.Expression;
import Service.CalculationService;
import Service.ExtractionService;

public class Calculator
{

	public int calculate(String input)
	{
		input = sanitizeInput(input);

		CalculationService calculationService = new CalculationService();
		ExtractionService extractionService = new ExtractionService();

		Expression entity = extractionService.extract(input);

		return calculationService.calculate(entity);
	}

	private String sanitizeInput(final String input)
	{
		return StringUtils.deleteWhitespace(input);
	}


	public static void main(String[] args)
	{

		Calculator calc = new Calculator();

		System.out.println(calc.calculate(args[0]));
	}
}
