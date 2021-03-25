/* 
 * Jameel. H. Khan 
 * Mini-Project 2 - Card Class
 * EN.605.201.83.SP21
 */

public class Card
{
	private int pointValue;
	private String suit;
	private String symbol;
	
	// default constructor
	public Card()
	{
		pointValue = 0;
		suit = "None";
	}
	
	// constructor method
	public Card( int newPointValue, String newSuit, String newSymbol )
	{
		pointValue = newPointValue;
		suit = newSuit;
		symbol = newSymbol;
	}
	
	// set method
	public void setCard( int newPointValue, String newSuit, String newSymbol )
	{
		this.pointValue = newPointValue;
		this.suit = newSuit;
		this.symbol = newSymbol;
	}
	
	// get method for card point value
	public int getPointValue()
	{
		return pointValue;	
	}
	
	// get method for card suit, although this may not be used
	public String getSuit()
	{
		return suit;
	}
	
	// get method for symbol
	public String getSymbol()
	{
		return symbol;
	}
} // end of Card class
