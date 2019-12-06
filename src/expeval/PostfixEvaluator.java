package expeval;
import stack.*;

import java.lang.reflect.Array;
import java.util.*;

/**
 * Evaluates postfix expression
 *
 * @author (T.M. Rao)
 *
 */
public class PostfixEvaluator
{
    //Input expression
    private Expression postfixExpression;
    private int valueOfExpression;
    List<String> varNames 	= Arrays.asList("A", "B", "C", "D", "E");
    int[] 	values 	= {10,   5,  8,  9,  3};


    /**
     * Constructor: assigns parameter to the instance variable
     */
    //------------------------------------------------------------
    public PostfixEvaluator(Expression postfix)
    {
        postfixExpression = postfix;
    }

    /**
     * Evaluates the postfixExpression
     */
    //-----------------------------------------------------------
    public int eval()
    {
        //Starts with an empty operand stack
        IStack<Token> operandStack = new ArrayListStack<Token>();

        //Temp variable
        Token nextToken;
        ArrayList<Token> postfix = postfixExpression.getExpression();
        //Main Loop: Parse the postfix expression
        for (int k = 0; k < postfix.size(); k++)
        {
            //Get the next token from postfix
            nextToken = postfix.get(k);

            //If it is an operand, push it into stack
            if (nextToken.isOperand())
            {
                operandStack.push(nextToken);
                //System.out.println(operandStack);
            }
            //If it is an operator,
            else if (nextToken.isOperator())
            {
                //Get two operands out of the stack
                if (operandStack.isEmpty())
                {
                    System.out.println
                            ("Error in PostfixEvaluator.eval() "+
                                    "-- Input expression was probably wrong");
                    return Integer.MIN_VALUE;
                }
                Token operand2 = operandStack.pop();

                if (operandStack.isEmpty())
                {
                    System.out.println
                            ("Error in PostfixEvaluator.eval() "+
                                    "-- Input expression was probably wrong");
                    return Integer.MIN_VALUE;
                }
                Token operand1 = operandStack.pop();

                //Perform the operation
                Token result =
                        calculate(nextToken, operand1, operand2);

                //Push the result back into the stack
                operandStack.push(result);
                //System.out.println(operandStack);
            }
        }

        //At the end, if only one element is left in the stack
        if (operandStack.isEmpty())
        {
            System.out.println("Error in PostfixEvaluator.eval() "+
                    "-- Input expression was probably wrong");
            return Integer.MIN_VALUE;
        }

        //Get the operand out of the stack, and convert it into
        //an integer
        Token topToken = operandStack.pop();
        valueOfExpression = Integer.parseInt(topToken.getBody());

        if (operandStack.isEmpty())
            return valueOfExpression;
        else
        {
            System.out.println("Error in PostfixEvaluator.eval() "+
                    "-- Input expression was probably wrong");
            return Integer.MIN_VALUE;
        }

    }

    /**
     * Performs an arithmetic operation
     */
    //---------------------------------------------------------------
    private Token calculate(Token opr, Token opd1, Token opd2)
    {
        // Get the first char from opr, it is the operator: +, -, ...

        String operator = opr.getBody();
        int op1, op2;
        //Get the two operands by converting from String to int

        if ((varNames.contains(opd1.getBody()))) op1 = values[varNames.indexOf(opd1.getBody())];
        else op1 = Integer.parseInt(opd1.getBody());
        if ((varNames.contains(opd2.getBody()))) op2 = values[varNames.indexOf(opd2.getBody())];
        else op2 = Integer.parseInt(opd2.getBody());

        //Default return value, in case an error occurs
        int res = Integer.MAX_VALUE;

        //Perform the operation, and set a value for res
        switch (operator)
        {
            case "+": res = op1+op2;break;
            case "-": res = op1-op2;break;
            case "*": res = op1*op2;break;
            case "/": if (op2 != 0)
                res = op1/op2;
            else
                System.out.println("Division by zero error in"+
                        " PostfixEvaluator.calculate().");
                break;
            case "%": if (op2 != 0)
                res = op1%op2;
            else
                System.out.println("Division by zero error in"+
                        " PostfixEvaluator.calculate().");
                break;
            case "<": res = (op1 < op2)? 1: 0; break;
            case ">": res = (op1 > op2)? 1: 0; break;
            case "<=": res = (op1 <= op2)? 1: 0; break;
            case ">=": res = (op1 >= op2)? 1: 0; break;
            case "==": res = (op1 == op2)? 1: 0; break;
            case "!=": res = (op1 != op2)? 1: 0; break;
            case "&&": res = ((op1 > 0) && (op2 > 0))? 1: ((op1 < 0) && (op2 < 0))? 1: 0;
            break;
            case "||": res = ((op1 != 0) || (op2 != 0))? 1: 0;
            break;
            default: System.out.println("Illegal Operator in "+
                    "PostfixEvaluator.calculate()");
        }
        //Convert res into a Token and return it.
        return new Token(""+res);
    }
}

