/*
 * [y] hybris Platform
 *
 * Copyright (c) 2000-2016 hybris AG
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of hybris
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with hybris.
 */
package Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import Model.Expression;
import Model.MathEntity;
import Model.Number;
import Model.Operation;

public class ExtractionService
{

	public static final String OPERATION_BASE_PATTERN = "(add|sub|mult|div|let)(\\(.*\\))";
	private Pattern OPERATION_EXTRACTION_PATTERN = Pattern.compile(OPERATION_BASE_PATTERN + "", Pattern.CASE_INSENSITIVE);

	// Matches a number or an operation
	private Pattern SIMPLE_ARGUMENTS_EXTRACTION_PATTERN = Pattern.compile("\\((\\d+|" + OPERATION_BASE_PATTERN + "),(\\d+|" + OPERATION_BASE_PATTERN + ")\\)",
																		  Pattern.CASE_INSENSITIVE);

	private Pattern LET_ARGUMENTS_EXTRACTION_PATTERN = Pattern.compile(
			"\\(([a-zA-Z]),(\\d+|" + OPERATION_BASE_PATTERN + "),(\\d+|" + OPERATION_BASE_PATTERN + ")\\)", Pattern.CASE_INSENSITIVE);

	public Expression extract(final String input)
	{

		Matcher operationMatcher = OPERATION_EXTRACTION_PATTERN.matcher(input);

		if (operationMatcher.matches())
		{
			Operation operation = Operation.valueOf(operationMatcher.group(1).toUpperCase());

			final String attributesPortion = operationMatcher;

			final Matcher simpleArgumentsMatcher = SIMPLE_ARGUMENTS_EXTRACTION_PATTERN.matcher(attributesPortion);
			final Matcher letArgumentsMatcher = LET_ARGUMENTS_EXTRACTION_PATTERN.matcher(attributesPortion);

			MathEntity v1 = null;
			MathEntity v2 = null;
			if (simpleArgumentsMatcher.matches())
			{
				v1 = createMathEntity(simpleArgumentsMatcher.group(1));
				v2 = createMathEntity(simpleArgumentsMatcher.group(4));
			}
			else if (letArgumentsMatcher.matches())
			{
				String varName = letArgumentsMatcher.group(1);
				String varValue = letArgumentsMatcher.group(2);
				String varExpression = letArgumentsMatcher.group(5);

				operationMatcher = OPERATION_EXTRACTION_PATTERN.matcher(varExpression);

				if (operationMatcher.matches())
				{
					String replacedString = varExpression.substring(varExpression.indexOf("("),varExpression.lastIndexOf(")")+1);

					String finalString = replacedString.replaceAll("\\b" + varName + "\\b", varValue);

					varExpression = varExpression.replace(replacedString, finalString);
				}
				v1 = createMathEntity(varExpression);
			}
			return new Expression(operation, v1, v2);
		}

		return null;
	}

	private MathEntity createMathEntity(final String stringValue)
	{
		if (StringUtils.isNumeric(stringValue))
		{
			return new Number(Integer.parseInt(stringValue));
		}
		else
		{
			return extract(stringValue);
		}
	}
}
