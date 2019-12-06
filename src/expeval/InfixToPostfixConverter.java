package expeval;
import stack.*;
import java.util.*;

/**
 * Converting an arithmetic expression from infix to postfix.
 * Assumptions:
 *   Arithmetic expression has only integer operands
 *   Only +, -, *, / and % operators are allowed.
 *   Usual rules of precedence are assumed.
 *   Parentheses are allowed.
 *
 * @author (T.M. Rao)
 *
 */
public class InfixToPostfixConverter
{
    // Expression to be  converted
    private Expression infixExpression;


    //Resulting postfix expression
    private Expression postfixExpression;

    /**
     * Constructor: Just assigns the parameter to instance variable
     */
    //---------------------------------------------------------------
    public InfixToPostfixConverter(Expression infix)
    {
        infixExpression = infix;
    }

    /**
     * Main conversion algorithm.
     */
    //--------------------------------------------------------------
    public void convertToPostfix()
    {
        //Create an empty postfix expression
        postfixExpression = new Expression();

        //create an empty operator stack
        IStack<Token> operatorStack = new ArrayListStack<Token>();

        //Temporary local variables
        Token nextToken = null;
        Token topOfStack = null;

        //Main loop
        ArrayList<Token> expr = infixExpression.getExpression();
        for (int k = 0; k < expr.size(); k++)
        {
            //Get the next token from infix expression
            nextToken = expr.get(k);

            //If it is an operand append it to postfix
            if (nextToken.isOperand())
                postfixExpression.add(nextToken);

                //If it is an open parenthesis push it into stack
            else if (nextToken.isOpenParen())
                operatorStack.push(nextToken);
                //If next token is a closed parenthesis
            else if (nextToken.isClosedParen())
            {
                //Keep pulling operators out of stack and appending
                //them to postfix, until top of stack is an open paren
                topOfStack = operatorStack.top();
                while (!topOfStack.isOpenParen())
                {
                    postfixExpression.add(topOfStack);
                    operatorStack.pop();
                    topOfStack = operatorStack.top();
                }
                //and then discard the open paren
                operatorStack.pop();
            }
            //If it is an operator ...
            else if (nextToken.isOperator())
            {
                // get the precedence of this token
                int tokenPrecedence = nextToken.getPrecedence();

                // If stack is empty, push nextToken into stack
                if (operatorStack.isEmpty())
                    operatorStack.push(nextToken);
                else
                {
                    //Get the precedence of the top of the stack
                    topOfStack = operatorStack.top();

                    //If the top of stack is an open parenthesis
                    //push nextToken
                    if (topOfStack.isOpenParen())
                        operatorStack.push(nextToken);
                    else
                    {
                        //Get the precedence of the top of stack
                        int stackPrecedence =
                                topOfStack.getPrecedence();

                        //if nextToken's precedence is > that of
//top of stack's, push next token into stack
                        if (tokenPrecedence > stackPrecedence)
                            operatorStack.push(nextToken);
                        else
                        {
                            //Keep pulling operators out of stack
                            //and appending
                            //them to postfix, as long all these
                            //conditions are true
                            while (
                                    (tokenPrecedence <= stackPrecedence) &&
                                            (!topOfStack.isOpenParen()) &&
                                            (!operatorStack.isEmpty()))
                            {
                                topOfStack = operatorStack.pop();
                                postfixExpression.add(topOfStack);
                                if (!operatorStack.isEmpty())
                                {
                                    topOfStack = operatorStack.top();
                                    stackPrecedence =
                                            topOfStack.getPrecedence();
                                }
                            }

                            //At the end push nextToken into Stack
                            operatorStack.push(nextToken);
                        }
                    }
                }
            }

            else
            {
                System.out.println
                        ("Illegal String in InfixToPostfixConverter");
                break;
            }
        }
        //At the end of the infix expression: pull all the operators
        //out of the stack and append them to postfix
        while (!operatorStack.isEmpty())
        {
            topOfStack = operatorStack.pop();
            postfixExpression.add(topOfStack);
        }
    }
    /**
     * Retriever method: returns the current postfix expression
     */
    public Expression getPostfix()
    {
        return postfixExpression;
    }

}
