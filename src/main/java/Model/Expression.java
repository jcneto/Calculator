package Model;

import org.apache.log4j.Logger;

import Service.CalculationService;


public class Expression implements MathEntity
{
	Logger LOGGER = Logger.getLogger(Expression.class);


	private Operation operation;

	private MathEntity entity1;

	private MathEntity entity2;

	private CalculationService calculationService = new CalculationService();


	public Expression(final Operation operation, final MathEntity entity1, final MathEntity entity2)
	{
		this.operation = operation;
		this.entity1 = entity1;
		this.entity2 = entity2;
	}

	public int getValue()
	{
		int result;
		int v1 = this.getEntity1().getValue();
		int v2 = this.getOperation() != Operation.LET ? this.getEntity2().getValue() : 0;

		switch (this.getOperation())
		{
			case ADD:
				if (Integer.MAX_VALUE - v2 < v1)
				{
					throw new ArithmeticException(String.format("%d + %d is bigger than Integer.MaxValue", v1, v2));
				}
				result = v1 + v2;
				LOGGER.debug(String.format("%d + %d = %d", v1, v2, result));
				break;
			case DIV:
				if (Integer.MIN_VALUE * v2 > v1)
				{
					throw new ArithmeticException(String.format("%d / %d is smaller than Integer.MinValue", v1, v2));
				}
				result = v1 / v2;
				LOGGER.debug(String.format("%d / %d = %d", v1, v2, result));
				break;
			case SUB:
				if (Integer.MIN_VALUE + v2 > v1)
				{
					throw new ArithmeticException(String.format("%d - %d is smaller than Integer.MinValue", v1, v2));
				}
				result = v1 - v2;
				LOGGER.debug(String.format("%d - %d = %d", v1, v2, result));
				break;
			case MULT:
				if (Integer.MAX_VALUE / v2 < v1)
				{
					throw new ArithmeticException(String.format("%d * %d is bigger than Integer.MaxValue", v1, v2));
				}
				result = v1 * v2;
				LOGGER.debug(String.format("%d * %d = %d", v1, v2, result));
				break;
			case LET:
				result = v1;
				LOGGER.debug(String.format("%s = %d", this.getOperation(), result));
				break;
			default:
				//Operation not found
				throw new UnsupportedOperationException();
		}

		return result;
	}

	public Operation getOperation()
	{
		return operation;
	}

	public MathEntity getEntity1()
	{
		return entity1;
	}

	public MathEntity getEntity2()
	{
		return entity2;
	}


	@Override
	public String toString()
	{
		if (this.getOperation() != Operation.LET)
		{
			return String.format("%s %s %s", entity1.toString(), this.getOperation(), entity2.toString());
		}
		else
		{
			return String.format("%s %s", this.getOperation(), entity1.toString());
		}
	}
}
