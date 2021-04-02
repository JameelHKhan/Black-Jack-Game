/**
 * This class creates an object called Card that represents an individual
 * playing card in a standard 52-card deck. The Card object has three
 * attributes: pointValue, suit, and symbol. The pointValue attribute 
 * represents the number of points a Card object holds in a game of Blackjack. 
 * The suit attribute represents one of the four suits that the Card object 
 * holds within a deck. The symbol attribute is a one or two character String 
 * that serves as second identifier for the Card object. This is necessary as 
 * Jack, Queen, and King cards all of the same point values but each represent
 * a unique Card object.
 * @author Jameel H. Khan
 * @version 1.0
 *
 */

public class Card
{
	private int pointValue;
	private String suit;
	private String symbol;
	
	/**
	 * default constructor
	 */
	public Card()
	{
		pointValue = 0;
		suit = "None";
	}
	
	/**
	 * Constructor method
	 * @param newPointValue	represents the number of points a Card object holds 
	 * in a game of Blackjack
	 * @param newSuit		represents one of the four suits that the Card object 
	 * holds within a deck
	 * @param newSymbol		a one or two character String that serves as second 
	 * identifier for the Card object.
	 */
	public Card( int newPointValue, String newSuit, String newSymbol )
	{
		pointValue = newPointValue;
		suit = newSuit;
		symbol = newSymbol;
	}
	
	/**
	 * 
	 * @param newPointValue	represents the number of points a Card object holds 
	 * in a game of Blackjack
	 * @param newSuit		represents one of the four suits that the Card object 
	 * holds within a deck
	 * @param newSymbol		a one or two character String that serves as second 
	 * identifier for the Card object.
	 */
	public void setCard( int newPointValue, String newSuit, String newSymbol )
	{
		this.pointValue = newPointValue;
		this.suit = newSuit;
		this.symbol = newSymbol;
	}
	
	/**
	 * Set method that only updates the point value of Card objects. This will 
	 * only be used when the program needs to update the point value of an Ace.
	 * @param newPointValue	represents the number of points a Card object holds 
	 * in a game of Blackjack
	 */
	public void setPoints( int newPointValue )
	{
		this.pointValue = newPointValue;
	}
	
	/**
	 * Get method for card point value
	 * @return	the point value of the specified Card object is returned
	 */
	public int getPointValue()
	{
		return pointValue;	
	}
	
	/**
	 * Get method for card suit, although this may not be used
	 * @return	the suit of the specified Card object is returned
	 */
	public String getSuit()
	{
		return suit;
	}
	
	/**
	 * Get method for card symbol
	 * @return	the symbol of the specified Card object is returned
	 */
	public String getSymbol()
	{
		return symbol;
	}
} // end of Card class
