package expeval;

/**
 * This defines the concept of an Expression. The expression is
 * implemented as an array of tokens: where each token contains
 * a string.
 *
 * @author (T.M. Rao)
 *
 */
import java.util.*;
public class Expression
{
    //----------------------------------------------------------------
    private ArrayList<Token> expression;
//Expression is input as a String



    //----------------------------------------------------------------
    /**
     * Constructor: The String exp contains the infix expression.
     * This breaks it into tokens and
     * stores them in an ArrayList.
     */
    public Expression(String exp)
    {
        //Create the array that represents the body of the Expression
        expression = new ArrayList<Token>();


        //Tokenize the expression using the String Tokenizer class.
        //Assumed that the tokens are separated by spaces
        StringTokenizer strTok = new StringTokenizer(exp," ");

        //Scan the input expression from left to right for tokens
        while (strTok.hasMoreElements())
        {
            Token tok = new Token((String) strTok.nextElement());
            expression.add(tok);
        }
    }

    //-----------------------------------------------------------------
    /**
     * Alternative constructor. This creates an array with no tokens
     * in it.
     */
    public Expression()
    {
        //Create the array that represents the body of the Expression
        expression = new ArrayList<Token>();
    }

    //----------------------------------------------------------------
    public int size()
    {
        return expression.size();
    }


    //-----------------------------------------------------------------
    /**
     * Appending a token at the end of the expression
     */
    public void add(Token newElement)
    {
        expression.add(newElement);
    }

    //-----------------------------------------------------------------
    /**
     * Creates a printable string from the expression
     */
    public String toString()
    {
        String ret = "";
        for (int i = 0; i < expression.size(); i++)
            ret = ret + expression.get(i).getBody()+" ";
        return ret;
    }

    //----------------------------------------------------------------
    public ArrayList<Token> getExpression()
    {
        return expression;
    }
}

