import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;

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

		if (ArrayUtils.isEmpty(args))
		{
			System.err.println("Please provide and input such as \"add(2,2)\"");
			System.exit(-1);
		}

		Level level = Level.ALL;
		if (args.length > 1)
		{
			String logLevel = StringUtils.stripToEmpty(args[1]).toLowerCase();

			if (logLevel.equals("info"))
			{
				level = Level.INFO;
			}
			else if (logLevel.equals("error"))
			{
				level = Level.ERROR;
			}
			else if (!logLevel.equals("debug"))
			{
				System.err.println("Unrecognized log level, setting to ALL.");
			}
		}
		LogManager.getRootLogger().setLevel(level);

		Calculator calc = new Calculator();

		System.out.println(args[0] + " = " + calc.calculate(args[0]));
		System.exit(0);
	}
}
