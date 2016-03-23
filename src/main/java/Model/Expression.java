package Model;

import Service.CalculationService;


public class Expression implements MathEntity
{
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
		switch (this.getOperation())
		{
			case ADD:
				return this.getEntity1().getValue() + this.getEntity2().getValue();
			case DIV:
				return this.getEntity1().getValue() / this.getEntity2().getValue();
			case SUB:
				return this.getEntity1().getValue() - this.getEntity2().getValue();
			case MULT:
				return this.getEntity1().getValue() * this.getEntity2().getValue();
			case LET:
				return this.getEntity1().getValue();
			default:
				//Operation not found
				throw new UnsupportedOperationException();
		}
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
}
