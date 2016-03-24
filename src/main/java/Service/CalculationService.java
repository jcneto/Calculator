package Service;

import org.apache.log4j.Logger;

import Model.MathEntity;

public class CalculationService
{
	Logger LOGGER = Logger.getLogger(CalculationService.class);

	public int calculate(final MathEntity mathEntity)
	{
		try
		{
			return mathEntity.getValue();
		}
		catch (ArithmeticException e)
		{
			LOGGER.error("Calculation issue", e);
			throw e;
		}
	}
}
