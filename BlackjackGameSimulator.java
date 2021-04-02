/* 
 * Jameel. H. Khan 
 * Mini-Project 2 - Blackjack Game
 * EN.605.201.83.SP21
 */

// import required package, will use ArrayList and Collections
import java.util.*;

public class BlackjackGameSimulator
{
	public static void main( String [] args )
	{
		// initialize variables
		ArrayList<Card> gameDeck = new ArrayList<Card>();
		ArrayList<Card> playerHand = new ArrayList<Card>();
		ArrayList<Card> dealerHand = new ArrayList<Card>();
		int playOrExit = 0;
		float moneyPot = 0;
		float bet;
		int playerPointTotal = 0;
		int dealerPointTotal = 0;
		int round = 1;		// keeps track of number of games played
		int hitOrStay = 0;
		
		// initialize Scanners to obtain user input
		Scanner inputChoice = new Scanner( System.in );
		Scanner inputPot = new Scanner( System.in );
		Scanner inputBet = new Scanner( System.in );
		Scanner playerAction = new Scanner( System.in );
		
		System.out.println( "Welcome to Blackjack!" );
		
		// Request player to add money to their pot
		System.out.println();
		System.out.print( "Enter the amount of money you'd like to add"
				+ " to your pot: $" );
		moneyPot = inputPot.nextFloat();
		
		// validity test for moneyPot input
		do
		{
			if( moneyPot <= 0 )
			{
				System.out.print( "Invalid entry! Please enter the "
						+ "amount of money you'd like to add to your pot: $" );
				moneyPot = inputPot.nextFloat();
			}
		}
		while( moneyPot <= 0 );
		
		// begin game play
		while( moneyPot > 0 )
		{
			System.out.println();
			System.out.println( "Round " + round + "!" );
			System.out.print( "Enter your bet: $" );
			bet = inputBet.nextFloat();
			
			// validity test for bet input to ensure a dollar 
			// amount > $0.00 and < moneyPot
			do
			{
				if( bet > moneyPot )
				{
					System.out.println( "Invalid entry! You can't bet more"
							+ " money than what you actually have!" );
					System.out.println( "You currently have $" + moneyPot 
							+ " in your pot." );
					System.out.println();
					System.out.print( "Enter your bet: $" );
					bet = inputBet.nextFloat();
				}
				else if( bet <= 0 )
				{
					System.out.print( "Invalid entry! In order to play, "
							+ "you must bet an amount of money, between "
							+ "$0.01 and $" + moneyPot + ". Enter your bet: $");
					bet = inputBet.nextFloat();
				}
			}
			while ( bet > moneyPot || bet <= 0 );
					
			// build a deck of 52 cards and their respected suits
			gameDeck = stackDeck();
			
			Collections.shuffle(gameDeck);
			System.out.println();
			System.out.println( "**Shuffling the deck...**" );
			System.out.println();
					
			// deal initial two cards to dealer and player
			dealDealerCard( gameDeck, dealerHand );
			dealDealerCard( gameDeck, dealerHand );
			showHiddenDealerHand( dealerHand );
			dealPlayerCard( gameDeck, playerHand );
			dealPlayerCard( gameDeck, playerHand );
			showPlayerHand( playerHand );					
			playerPointTotal += calculatePlayerPoints( playerHand );
					
			// the player can win if they achieve 21 points on the first deal in round 1
			if( round == 1 & playerPointTotal == 21 )
			{
				System.out.println( "You hit Blackjack with 21 points! You win!!" );
				// if I have time, the next 8 lines can be turned into a method
				moneyPot += bet;
				System.out.println( "You won $" + bet + "! Your pot now has $" + moneyPot );
				playerHand.clear();
				dealerHand.clear();
				gameDeck.clear();
				playerPointTotal = 0;
				dealerPointTotal = 0;
				round += 1;
				
				// ask if player wants to play again or exit game
				System.out.println();
				System.out.print( "Would you like to continue playing or exit the game?"
						+ " Enter '1' to play again, or '2' to exit the game: ");
				playOrExit = inputChoice.nextInt();
				
				// input validation
				if( playOrExit != 1 || playOrExit != 2 )
				{
					do
					{
						System.out.println();
						System.out.print( "Invalid entry. Enter '1' to play again, or '2' to exit the game: " );
						playOrExit = inputChoice.nextInt();
					}
					while( playOrExit != 1 || playOrExit != 2 );
				}
				
				// If statement will move while( moneyPot > 0 ) loop to next iteration 
				// without running any code that comes after it
				if( playOrExit == 1 )
				{ // play game again
					continue; 
				}
				else
				{ // exit game
					System.out.println();
					System.out.println( "Thank you for playing! Goodbye!!" );
					break;
				}
			}
			else
			{
				System.out.println();
				System.out.print( "Do you want to hit or stay? (Enter '1' to hit or '2' to stay): ");
				hitOrStay = playerAction.nextInt();
				
				// player chooses to hit
				if( hitOrStay == 1 )
				{
					do
					{
						System.out.println();
						dealPlayerCard( gameDeck, playerHand );
						showHiddenDealerHand( dealerHand );
						showPlayerHand( playerHand );					
						playerPointTotal = calculatePlayerPoints( playerHand );
						
						if( playerPointTotal > 21)
						{
							break;
						}
						else
						{
							System.out.println();
							System.out.print( "Do you want to hit or stay? (Enter '1' to hit or '2' to stay): ");
							hitOrStay = playerAction.nextInt();
						}
					}
					while( hitOrStay == 1);
					
					if( playerPointTotal > 21 )
					{
						System.out.println();
						System.out.println( "You bust with " + playerPointTotal + " points." );
						moneyPot -= bet;
						System.out.println();
						System.out.println( "You lost your $" + bet + "! Your pot now has $" + moneyPot );
						playerHand.clear();
						dealerHand.clear();
						gameDeck.clear();
						playerPointTotal = 0;
						dealerPointTotal = 0;
						round += 1;
						if( moneyPot <= 0 )
						{
							break;
						}
						
						// ask if player wants to play again or exit game
						System.out.println();
						System.out.print( "Would you like to continue playing or exit the game?"
								+ " Enter '1' to play again, or '2' to exit the game: ");
						playOrExit = inputChoice.nextInt();
						
						// If statement will move while( moneyPot > 0 ) loop to next iteration 
						// without running any code that comes after it
						if( playOrExit == 1 )
						{ // play game again
							continue; 
						}
						else
						{ // exit game
							System.out.println();
							System.out.println( "Thank you for playing! Goodbye!!" );
							break;
						}
					}
				}
				
				// player stays, it is now the dealer's turn
				dealerPointTotal = calculateDealerPoints( dealerHand );
				
				if( dealerPointTotal == 21 )
				{ // dealer reaches blackjack (21 points) on first round
					System.out.println( "The dealer hit Blackjack with 21 points! Dealer wins!" );
					// turn the next four lines into a method playerLoses()
					moneyPot -= bet;
					System.out.println();
					System.out.println( "You lost your $" + bet + "! Your pot now has $" + moneyPot );
					playerHand.clear();
					dealerHand.clear();
					gameDeck.clear();
					playerPointTotal = 0;
					dealerPointTotal = 0;
					round += 1;
					if( moneyPot <= 0 )
					{
						break;
					}
					
					// ask if player wants to play again or exit game
					System.out.println();
					System.out.print( "Would you like to continue playing or exit the game?"
							+ " Enter '1' to play again, or '2' to exit the game: ");
					playOrExit = inputChoice.nextInt();
					
					// If statement will move while( moneyPot > 0 ) loop to next iteration 
					// without running any code that comes after it
					if( playOrExit == 1 )
					{ // play game again
						continue; 
					}
					else
					{ // exit game
						System.out.println();
						System.out.println( "Thank you for playing! Goodbye!!" );
						break;
					}
				}
				else if( dealerPointTotal < 17 )
				{ // dealer has less than 17 points so must hit
					do
					{
						dealDealerCard( gameDeck, dealerHand );
						dealerPointTotal = calculateDealerPoints( dealerHand );
					}
					while( dealerPointTotal < 17 );
					
					if( dealerPointTotal > 21 )
					{
						System.out.println();
						System.out.println( "The dealer busts with " + dealerPointTotal + " points. You win!!" );
						System.out.println();
						// turn the next four lines into a method playerWins() ?
						moneyPot += bet;
						System.out.println( "You won $" + bet + "! Your pot now has $" + moneyPot );
						playerHand.clear();
						dealerHand.clear();
						gameDeck.clear();
						playerPointTotal = 0;
						dealerPointTotal = 0;
						round += 1;
						
						// ask if player wants to play again or exit game
						System.out.println();
						System.out.print( "Would you like to continue playing or exit the game?"
								+ " Enter '1' to play again, or '2' to exit the game: ");
						playOrExit = inputChoice.nextInt();
						
						// If statement will move while( moneyPot > 0 ) loop to next iteration 
						// without running any code that comes after it
						if( playOrExit == 1 )
						{ // play game again
							continue; 
						}
						else
						{ // exit game
							System.out.println();
							System.out.println( "Thank you for playing! Goodbye!!" );
							break;
						}
					}
				} // end of if( dealerPointTotal == 21 ) statement
						
				// determine winner after both player and dealer stay and have not crossed 21 points
						
				if( playerPointTotal > dealerPointTotal )
				{
					System.out.println();
					showFullDealerHand( dealerHand );
					System.out.print( "The dealer has " + dealerPointTotal + " points." );
					System.out.println();
					showPlayerHand( playerHand );					
					System.out.println();
					System.out.println( "You have " + playerPointTotal + " points" );
					System.out.println();
					System.out.println( "You win!!" );
					System.out.println();
					// turn the next four lines into a method playerWins() ?
					moneyPot += bet;
					System.out.println( "You won $" + bet + "! Your pot now has $" + moneyPot );
					playerHand.clear();
					dealerHand.clear();
					gameDeck.clear();
					playerPointTotal = 0;
					dealerPointTotal = 0;
					round += 1;
					
					// ask if player wants to play again or exit game
					System.out.println();
					System.out.print( "Would you like to continue playing or exit the game?"
							+ "Enter '1' to play again, or '2' to exit the game: ");
					playOrExit = inputChoice.nextInt();
					
					// If statement will move while( moneyPot > 0 ) loop to next iteration 
					// without running any code that comes after it
					if( playOrExit == 1 )
					{ // play game again
						continue; 
					}
					else
					{ // exit game
						System.out.println();
						System.out.println( "Thank you for playing! Goodbye!!" );
						break;
					}
				}
				else if( playerPointTotal < dealerPointTotal )
				{
					System.out.println();
					showFullDealerHand( dealerHand );
					System.out.print( "The dealer has " + dealerPointTotal + " points." );
					System.out.println();
					showPlayerHand( playerHand );					
					System.out.println();
					System.out.println( "You have " + playerPointTotal + " points" );
					System.out.println();
					System.out.println( "The dealer wins!" );
					System.out.println();
					moneyPot -= bet;
					System.out.println( "You lost your bet of $" + bet + "! Your pot now has $" + moneyPot );
					playerHand.clear();
					dealerHand.clear();
					gameDeck.clear();
					playerPointTotal = 0;
					dealerPointTotal = 0;
					round += 1;
					if( moneyPot <= 0 )
					{
						break;
					}
					
					// ask if player wants to play again or exit game
					System.out.println();
					System.out.print( "Would you like to continue playing or exit the game?"
							+ " Enter '1' to play again, or '2' to exit the game: ");
					playOrExit = inputChoice.nextInt();
					
					// If statement will move while( moneyPot > 0 ) loop to next iteration 
					// without running any code that comes after it
					if( playOrExit == 1 )
					{ // play game again
						continue; 
					}
					else
					{ // exit game
						System.out.println();
						System.out.println( "Thank you for playing! Goodbye!!" );
						break;
					}
				}
				else
				{
					System.out.println();
					showFullDealerHand( dealerHand );
					System.out.print( "The dealer has " + dealerPointTotal + " points." );
					System.out.println();
					showPlayerHand( playerHand );					
					System.out.println();
					System.out.println( "You have " + playerPointTotal + " points" );
					System.out.println();
					System.out.println( "It's a tie game!" );
					System.out.println();
					System.out.println( "You do not win or lose any money. Your pot still has $" + moneyPot );
					playerHand.clear();
					dealerHand.clear();
					gameDeck.clear();
					playerPointTotal = 0;
					dealerPointTotal = 0;
					round += 1;
					
					// ask if player wants to play again or exit game
					System.out.println();
					System.out.print( "Would you like to continue playing or exit the game?"
							+ " Enter '1' to play again, or '2' to exit the game: ");
					playOrExit = inputChoice.nextInt();
					
					// input validation CODE HAS ERROR DOESN'T WORK!!!!
					/*if( playOrExit != 1 || playOrExit != 2 )
					{
						do
						{
							System.out.println();
							System.out.print( "Invalid entry. Enter '1' to play again, or '2' to exit the game: " );
							playOrExit = inputChoice.nextInt();
						}
						while( playOrExit != 1 || playOrExit != 2 );
					}*/
					
					// If statement will move while( moneyPot > 0 ) loop to next iteration 
					// without running any code that comes after it
					if( playOrExit == 1 )
					{ // play game again
						continue; 
					}
					else
					{ // exit game
						System.out.println();
						System.out.println( "Thank you for playing! Goodbye!!" );
						break;
					}
				}
			} //  end of else statement for "if( round == 1 & playerPointTotal == 21 )" statement
		} // end of "while moneyPot > 0" loop
		
		if( moneyPot <=0 )
		{
			System.out.println();
			System.out.println( "You lost all of your money. Thank you for playing. Better luck next time!" );
		}
	} // end of main method
	
/**
 * The stackDeck() method creates an ArrayList of 52 Card objects representing 
 * a full deck of 52 playing cards. Each Card object is initialized and assigned 
 * to a local ArrayList call deck. Once the deck list is complete, it is 
 * returned by the method. All Ace cards are assigned an initial point value of 
 * 0 as they can take on a point value of either 1 or 11 depending on what 
 * other cards are held by the player/dealer. The actual point value of an Ace 
 * card is determined and assigned by two other methods, calculatePlayerPoints() 
 * and calculateDealerPoints(), at the appropriate time.
 * @return an ArrayList of 52 Card objects is returned
 */
	public static ArrayList<Card> stackDeck()
	{
		// initialize local ArrayList of Card Object
		ArrayList<Card> deck = new ArrayList<Card>();
		
		// create Card objects for each of the 52 cards in a deck
		// 1st set: diamond suits
		Card twoDiamond = new Card( 2, "Di", "2" );
		Card threeDiamond = new Card( 3, "Di", "3" );
		Card fourDiamond = new Card( 4, "Di", "4" );
		Card fiveDiamond = new Card( 5, "Di", "5" );
		Card sixDiamond = new Card( 6, "Di", "6" );
		Card sevenDiamond = new Card( 7, "Di", "7" );
		Card eightDiamond = new Card( 8, "Di", "8" );
		Card nineDiamond = new Card( 9, "Di", "9" );
		Card tenDiamond = new Card( 10, "Di", "10" );
		Card jackDiamond = new Card( 10, "Di", "J" );
		Card queenDiamond = new Card( 10, "Di", "Q" );
		Card kingDiamond = new Card( 10, "Di", "K" );
		Card aceDiamond = new Card( 0, "Di", "A" );
		
		// second set: club suits
		Card twoClub = new Card( 2, "Cl", "2" );
		Card threeClub = new Card( 3, "Cl", "3" );
		Card fourClub = new Card( 4, "Cl", "4" );
		Card fiveClub = new Card( 5, "Cl", "5" );
		Card sixClub = new Card( 6, "Cl", "6" );
		Card sevenClub = new Card( 7, "Cl", "7" );
		Card eightClub = new Card( 8, "Cl", "8" );
		Card nineClub = new Card( 9, "Cl", "9" );
		Card tenClub = new Card( 10, "Cl", "10" );
		Card jackClub = new Card( 10, "Cl", "J" );
		Card queenClub = new Card( 10, "Cl", "Q" );
		Card kingClub = new Card( 10, "Cl", "K" );
		Card aceClub = new Card( 0, "Cl", "A" );
		
		// third set: heart suits
		Card twoHeart = new Card( 2, "He", "2" );
		Card threeHeart = new Card( 3, "He", "3" );
		Card fourHeart = new Card( 4, "He", "4" );
		Card fiveHeart = new Card( 5, "He", "5" );
		Card sixHeart = new Card( 6, "He", "6" );
		Card sevenHeart = new Card( 7, "He", "7" );
		Card eightHeart = new Card( 8, "He", "8" );
		Card nineHeart = new Card( 9, "He", "9" );
		Card tenHeart = new Card( 10, "He", "10" );
		Card jackHeart = new Card( 10, "He", "J" );
		Card queenHeart = new Card( 10, "He", "Q" );
		Card kingHeart = new Card( 10, "He", "K" );
		Card aceHeart = new Card( 0, "He", "A" );
		
		// fourth set: spade suits
		Card twoSpade = new Card( 2, "Sp", "2" );
		Card threeSpade = new Card( 3, "Sp", "3" );
		Card fourSpade = new Card( 4, "Sp", "4" );
		Card fiveSpade = new Card( 5, "Sp", "5" );
		Card sixSpade = new Card( 6, "Sp", "6" );
		Card sevenSpade = new Card( 7, "Sp", "7" );
		Card eightSpade = new Card( 8, "Sp", "8" );
		Card nineSpade = new Card( 9, "Sp", "9" );
		Card tenSpade = new Card( 10, "Sp", "10" );
		Card jackSpade = new Card( 10, "Sp", "J" );
		Card queenSpade = new Card( 10, "Sp", "Q" );
		Card kingSpade = new Card( 10, "Sp", "K" );
		Card aceSpade = new Card( 0, "Sp", "A" );
		
		// add diamond suit
		deck.add(twoDiamond);
		deck.add(threeDiamond);
		deck.add(fourDiamond);
		deck.add(fiveDiamond);
		deck.add(sixDiamond);
		deck.add(sevenDiamond);
		deck.add(eightDiamond);
		deck.add(nineDiamond);
		deck.add(tenDiamond);
		deck.add(jackDiamond);
		deck.add(queenDiamond);
		deck.add(kingDiamond);
		deck.add(aceDiamond);
		
		// add club suit
		deck.add(twoClub);
		deck.add(threeClub);
		deck.add(fourClub);
		deck.add(fiveClub);
		deck.add(sixClub);
		deck.add(sevenClub);
		deck.add(eightClub);
		deck.add(nineClub);
		deck.add(tenClub);
		deck.add(jackClub);
		deck.add(queenClub);
		deck.add(kingClub);
		deck.add(aceClub);
		
		// add heart suit
		deck.add(twoHeart);
		deck.add(threeHeart);
		deck.add(fourHeart);
		deck.add(fiveHeart);
		deck.add(sixHeart);
		deck.add(sevenHeart);
		deck.add(eightHeart);
		deck.add(nineHeart);
		deck.add(tenHeart);
		deck.add(jackHeart);
		deck.add(queenHeart);
		deck.add(kingHeart);
		deck.add(aceHeart);
		
		// add spade suit
		deck.add(twoSpade);
		deck.add(threeSpade);
		deck.add(fourSpade);
		deck.add(fiveSpade);
		deck.add(sixSpade);
		deck.add(sevenSpade);
		deck.add(eightSpade);
		deck.add(nineSpade);
		deck.add(tenSpade);
		deck.add(jackSpade);
		deck.add(queenSpade);
		deck.add(kingSpade);
		deck.add(aceSpade);
		
		return deck;
	} // end of method stackDeck()
	
/**
 * The dealPlayerCard() method randomly selects a Card object from the deck of 
 * cards and assigns it to the player's current hand of cards, which is 
 * represented by the ArrayList playerHand.
 * @param gameDeck		The deck of cards created by the stackDeck() method
 * @param playerHand	An ArrayList of Card objects that represent the current
 * cards belonging to the player
 */
	public static void dealPlayerCard( ArrayList<Card> gameDeck, ArrayList<Card> playerHand )
	{
		Random rand = new Random();
		int randomNumber = rand.nextInt(gameDeck.size());
		playerHand.add(gameDeck.get(randomNumber));
		gameDeck.remove(randomNumber);
	} // end of method dealPlayerCard()

/**
 * The calculatePlayerPoints() method determines the current point value of the 
 * cards held by the player. It also checks to see if there are one or more 
 * Aces held by the player and if so, determines and assigns the appropriate 
 * point value for each Ace.
 * @param playerHand	An ArrayList of Card objects that represent the current
 * cards belonging to the player
 * @return the sum of all point values for each card in the player hand is 
 * returned
 */
	public static int calculatePlayerPoints( ArrayList<Card> playerHand )
	{
		int playerPoints = 0;
		
		// Calculate point value of hand by adding all card point values together
		// The default point value of an Ace is set to 0, so this will only total
		// the points for every other card except any aces.
		for( int i = 0; i < playerHand.size(); i++ )
		{
			playerPoints += playerHand.get(i).getPointValue();
		} // end of for-i loop
		
		// This portion checks for any Ace cards in the player hand and reassigns
		// the point value
		
		for( int i = 0; i < playerHand.size(); i++ )
		{
			if( playerHand.get(i).getSymbol() == "A" )
			{
				if( playerPoints <= 10 )
				{
					playerHand.get(i).setPoints( 11 );
				}
				else
				{
					playerHand.get(i).setPoints( 1 );
				}
				
				// after one Ace cards' point value has been updated, the total player points
				// for the hand needs to be calculated and updated again
				playerPoints = 0;
				for( int j = 0; j < playerHand.size(); j++ )
				{
					playerPoints += playerHand.get(j).getPointValue();
				} // end of for-j loop
			} // end of if statement
		} // end of for-i loop
		
		System.out.println();
		System.out.println( "You have " + playerPoints + " points" );
		
		return playerPoints;
	}
	
/**
 * The calculateDealerPoints() method determines the current point value of the 
 * cards held by the dealer. It also checks to see if there are one or more 
 * Aces held by the dealer and if so, determines and assigns the appropriate 
 * point value for each Ace.
 * @param dealerHand	An ArrayList of Card objects that represent the current
 * cards belonging to the dealer
 * @return the sum of all point values for each card in the dealer hand is 
 * returned
 */
	public static int calculateDealerPoints( ArrayList<Card> dealerHand )
	{
		int dealerPoints = 0;
		
		// Calculate point value of hand by adding all card point values together
		// The default point value of an Ace is set to 0, so this will only total
		// the points for every other card except any aces.
		for( int i = 0; i < dealerHand.size(); i++ )
		{
			dealerPoints += dealerHand.get(i).getPointValue();
		} // end of for-i loop
		
		// This portion checks for any Ace cards in the player hand and reassigns
		// the point value
				
		for( int i = 0; i < dealerHand.size(); i++ )
		{
			if( dealerHand.get(i).getSymbol() == "A" )
			{
				if( dealerPoints <= 10 )
				{
					dealerHand.get(i).setPoints( 11 );
				}
				else
				{
					dealerHand.get(i).setPoints( 1 );
				}
						
				// after one Ace cards' point value has been updated, the total player points
				// for the hand needs to be calculated and updated again
				dealerPoints = 0;
				for( int j = 0; j < dealerHand.size(); j++ )
				{
					dealerPoints += dealerHand.get(j).getPointValue();
				} // end of for-j loop
			} // end of if statement
		} // end of for-i loop
		
		return dealerPoints;
	}
	
/**
 * The dealDealerCard() method randomly selects a Card object from the deck of 
 * cards and assigns it to the dealer's current hand of cards, which is 
 * represented by the ArrayList dealerHand.
 * @param gameDeck		The deck of cards created by the stackDeck() method
 * @param dealerHand	An ArrayList of Card objects that represent the current
 * cards belonging to the dealer
 */
	public static void dealDealerCard( ArrayList<Card> gameDeck, ArrayList<Card> dealerHand )
	{
		Random rand = new Random();
		int randomNumber = rand.nextInt(gameDeck.size());
		dealerHand.add(gameDeck.get(randomNumber));
		gameDeck.remove(randomNumber);
	} // end of method dealPlayerCard()
	
/**
 * The showPlayerHand() method creates a basic graphical representation of a 
 * Card object and displays the cards held by the player in rows of 3.
 * @param playerHand	An ArrayList of Card objects that represent the current
 * cards belonging to the player
 */
	public static void showPlayerHand( ArrayList<Card> playerHand )
	{		
		// determine the number of cards currently held by the player. This is 
		// needed to properly format the graphical representation of cards for output
		int numOfPlayerCards = playerHand.size();
		
		System.out.println();
		System.out.println( "Your cards:");
		switch( numOfPlayerCards )
		{
		// the player will never have less than 2 cards, which is why we start with 2
		case 2:
			if( playerHand.get(0).getSymbol() == "10" )
			{
				// first row of cards: 2 cards with a 10 on the left
				System.out.println("-----------  " + "-----------  ");
				System.out.println("| " + playerHand.get(0).getSymbol() + "      |  " 
						+ "| " + playerHand.get(1).getSymbol() + "       |  " );
				System.out.println("| " + playerHand.get(0).getSuit() + "      |  " 
						+ "| " + playerHand.get(1).getSuit() + "      |  " );
				System.out.println("|         |  " + "|         |  " );
				System.out.println("|      " + playerHand.get(0).getSymbol() + " |  " 
						+ "|       " + playerHand.get(1).getSymbol() + " |  " );
				System.out.println("|      " + playerHand.get(0).getSuit() + " |  " 
						+ "|      " + playerHand.get(1).getSuit() + " |  " );
				System.out.println("-----------  " + "-----------  " );
			}
			else if( playerHand.get(1).getSymbol() == "10" )
			{
				// first row of cards: 2 cards with a 10 on the right
				System.out.println("-----------  " + "-----------  ");
				System.out.println("| " + playerHand.get(0).getSymbol() + "       |  " 
						+ "| " + playerHand.get(1).getSymbol() + "      |  " );
				System.out.println("| " + playerHand.get(0).getSuit() + "      |  " 
						+ "| " + playerHand.get(1).getSuit() + "     |  " );
				System.out.println("|         |  " + "|         |  " );
				System.out.println("|       " + playerHand.get(0).getSymbol() + " |  " 
						+ "|      " + playerHand.get(1).getSymbol() + " |  " );
				System.out.println("|      " + playerHand.get(0).getSuit() + " |  " 
						+ "|     " + playerHand.get(1).getSuit() + "  |  " );
				System.out.println("-----------  " + "-----------  " );
			}
			else
			{
				// first row of cards: 2 cards with no 10s
				System.out.println("-----------  " + "-----------  ");
				System.out.println("| " + playerHand.get(0).getSymbol() + "       |  " 
						+ "| " + playerHand.get(1).getSymbol() + "       |  " );
				System.out.println("| " + playerHand.get(0).getSuit() + "      |  " 
						+ "| " + playerHand.get(1).getSuit() + "      |  " );
				System.out.println("|         |  " + "|         |  " );
				System.out.println("|       " + playerHand.get(0).getSymbol() + " |  " 
						+ "|       " + playerHand.get(1).getSymbol() + " |  " );
				System.out.println("|      " + playerHand.get(0).getSuit() + " |  " 
						+ "|      " + playerHand.get(1).getSuit() + " |  " );
				System.out.println("-----------  " + "-----------  " );
			}
			break;
		case 3:
			if( playerHand.get(0).getSymbol() == "10" )
			{
				// first row of cards: 3 cards with a 10 on the left
				System.out.println("-----------  " + "-----------  " + "-----------  " );
				System.out.println("| " + playerHand.get(0).getSymbol() + "      |  " 
						+ "| " + playerHand.get(1).getSymbol() + "       |  " 
						+ "| " + playerHand.get(2).getSymbol() + "       |  " );
				System.out.println("| " + playerHand.get(0).getSuit() + "      |  " 
						+ "| " + playerHand.get(1).getSuit() + "      |  " 
						+ "| " + playerHand.get(2).getSuit() + "      |  " );
				System.out.println("|         |  " + "|         |  " + "|         |  " );
				System.out.println("|      " + playerHand.get(0).getSymbol() + " |  " 
						+ "|       " + playerHand.get(1).getSymbol() + " |  " 
						+ "|       " + playerHand.get(2).getSymbol() + " |  " );
				System.out.println("|      " + playerHand.get(0).getSuit() + " |  " 
						+ "|      " + playerHand.get(1).getSuit() + " |  " 
						+ "|      " + playerHand.get(2).getSuit() + " |  " );
				System.out.println("-----------  " + "-----------  " + "-----------  " );
			}
			else if( playerHand.get(1).getSymbol() == "10" )
			{
				// first row of cards: 3 cards with a 10 in the middle
				System.out.println("-----------  " + "-----------  " + "-----------  " );
				System.out.println("| " + playerHand.get(0).getSymbol() + "       |  " 
						+ "| " + playerHand.get(1).getSymbol() + "      |  " 
						+ "| " + playerHand.get(2).getSymbol() + "       |  " );
				System.out.println("| " + playerHand.get(0).getSuit() + "      |  " 
						+ "| " + playerHand.get(1).getSuit() + "     |  " 
						+ "| " + playerHand.get(2).getSuit() + "      |  " );
				System.out.println("|         |  " + "|         |  " + "|         |  " );
				System.out.println("|       " + playerHand.get(0).getSymbol() + " |  " 
						+ "|      " + playerHand.get(1).getSymbol() + " |  " 
						+ "|       " + playerHand.get(2).getSymbol() + " |  " );
				System.out.println("|      " + playerHand.get(0).getSuit() + " |  " 
						+ "|     " + playerHand.get(1).getSuit() + "  |  " 
						+ "|      " + playerHand.get(2).getSuit() + " |  " );
				System.out.println("-----------  " + "-----------  " + "-----------  " );
			}
			else if( playerHand.get(2).getSymbol() == "10" )
			{
				// first row of cards: 3 cards with a 10 on the right
				System.out.println("-----------  " + "-----------  " + "-----------  " );
				System.out.println("| " + playerHand.get(0).getSymbol() + "       |  " 
						+ "| " + playerHand.get(1).getSymbol() + "       |  " 
						+ "| " + playerHand.get(2).getSymbol() + "      |  " );
				System.out.println("| " + playerHand.get(0).getSuit() + "      |  " 
						+ "| " + playerHand.get(1).getSuit() + "      |  " 
						+ "| " + playerHand.get(2).getSuit() + "      |  " );
				System.out.println("|         |  " + "|         |  " + "|         |  " );
				System.out.println("|       " + playerHand.get(0).getSymbol() + " |  " 
						+ "|       " + playerHand.get(1).getSymbol() + " |  " 
						+ "|      " + playerHand.get(2).getSymbol() );
				System.out.println("|      " + playerHand.get(0).getSuit() + " |  " 
						+ "|      " + playerHand.get(1).getSuit() + " |  " 
						+ "|      " + playerHand.get(2).getSuit() + " |  " );
				System.out.println("-----------  " + "-----------  " + "-----------  " );
			}
			else
			{
				// first row of cards: 3 cards with no 10s
				System.out.println("-----------  " + "-----------  " + "-----------  ");
				System.out.println("| " + playerHand.get(0).getSymbol() + "       |  " 
						+ "| " + playerHand.get(1).getSymbol() + "       |  " 
						+ "| " + playerHand.get(2).getSymbol() + "       |  " );
				System.out.println("| " + playerHand.get(0).getSuit() + "      |  " 
						+ "| " + playerHand.get(1).getSuit() + "      |  " + "| " 
						+ playerHand.get(2).getSuit() + "      |  " );
				System.out.println("|         |  " + "|         |  " + "|         |  " );
				System.out.println("|       " + playerHand.get(0).getSymbol() + " |  " 
						+ "|       " + playerHand.get(1).getSymbol() + " |  " 
						+ "|       " + playerHand.get(2).getSymbol() + " |  " );
				System.out.println("|      " + playerHand.get(0).getSuit() + " |  " 
						+ "|      " + playerHand.get(1).getSuit() + " |  " 
						+ "|      " + playerHand.get(2).getSuit() + " |  " );
				System.out.println("-----------  " + "-----------  " + "-----------  " );
			}
			break;
		case 4:
			if( playerHand.get(0).getSymbol() == "10" )
			{
				// first row of cards: 3 cards with 10 on the left
				System.out.println("-----------  " + "-----------  " + "-----------  " );
				System.out.println("| " + playerHand.get(0).getSymbol() + "      |  " 
						+ "| " + playerHand.get(1).getSymbol() + "       |  " 
						+ "| " + playerHand.get(2).getSymbol() + "       |  " );
				System.out.println("| " + playerHand.get(0).getSuit() + "      |  " 
						+ "| " + playerHand.get(1).getSuit() + "      |  " 
						+ "| " + playerHand.get(2).getSuit() + "      |  " );
				System.out.println("|         |  " + "|         |  " + "|         |  " );
				System.out.println("|      " + playerHand.get(0).getSymbol() + " |  " 
						+ "|       " + playerHand.get(1).getSymbol() + " |  " 
						+ "|       " + playerHand.get(2).getSymbol() + " |  " );
				System.out.println("|      " + playerHand.get(0).getSuit() + " |  " 
						+ "|      " + playerHand.get(1).getSuit() + " |  " 
						+ "|      " + playerHand.get(2).getSuit() + " |  " );
				System.out.println("-----------  " + "-----------  " + "-----------  " );
				
				// second row of cards: 1 card with no 10s
				System.out.println("-----------");
				System.out.println("| " + playerHand.get(3).getSymbol() + "       |" );
				System.out.println("| " + playerHand.get(3).getSuit() + "      |" );
				System.out.println("|         |");
				System.out.println("|       " + playerHand.get(3).getSymbol() + " |" );
				System.out.println("|      " + playerHand.get(3).getSuit() + " |" );
				System.out.println("-----------");
			}
			else if( playerHand.get(1).getSymbol() == "10" )
			{
				// first row of cards: 3 cards with 10 in the middle
				System.out.println("-----------  " + "-----------  " + "-----------  " );
				System.out.println("| " + playerHand.get(0).getSymbol() + "       |  " 
						+ "| " + playerHand.get(1).getSymbol() + "      |  " 
						+ "| " + playerHand.get(2).getSymbol() + "       |  " );
				System.out.println("| " + playerHand.get(0).getSuit() + "      |  " 
						+ "| " + playerHand.get(1).getSuit() + "     |  " 
						+ "| " + playerHand.get(2).getSuit() + "      |  " );
				System.out.println("|         |  " + "|         |  " + "|         |  " );
				System.out.println("|       " + playerHand.get(0).getSymbol() + " |  " 
						+ "|      " + playerHand.get(1).getSymbol() + " |  " 
						+ "|       " + playerHand.get(2).getSymbol() + " |  " );
				System.out.println("|      " + playerHand.get(0).getSuit() + " |  " 
						+ "|     " + playerHand.get(1).getSuit() + "  |  " 
						+ "|      " + playerHand.get(2).getSuit() + " |  " );
				System.out.println("-----------  " + "-----------  " + "-----------  " );
				
				// second row of cards: 1 card with no 10s
				System.out.println("-----------");
				System.out.println("| " + playerHand.get(3).getSymbol() + "       |" );
				System.out.println("| " + playerHand.get(3).getSuit() + "      |" );
				System.out.println("|         |");
				System.out.println("|       " + playerHand.get(3).getSymbol() + " |" );
				System.out.println("|      " + playerHand.get(3).getSuit() + " |" );
				System.out.println("-----------");
			}
			else if( playerHand.get(2).getSymbol() == "10" )
			{
				// first row of cards: 3 cards with a 10 on the right
				System.out.println("-----------  " + "-----------  " + "-----------  " );
				System.out.println("| " + playerHand.get(0).getSymbol() + "       |  " 
						+ "| " + playerHand.get(1).getSymbol() + "       |  " 
						+ "| " + playerHand.get(2).getSymbol() + "      |  " );
				System.out.println("| " + playerHand.get(0).getSuit() + "      |  " 
						+ "| " + playerHand.get(1).getSuit() + "      |  " 
						+ "| " + playerHand.get(2).getSuit() + "      |  " );
				System.out.println("|         |  " + "|         |  " + "|         |  " );
				System.out.println("|       " + playerHand.get(0).getSymbol() + " |  " 
						+ "|       " + playerHand.get(1).getSymbol() + " |  " 
						+ "|      " + playerHand.get(2).getSymbol() );
				System.out.println("|      " + playerHand.get(0).getSuit() + " |  " 
						+ "|      " + playerHand.get(1).getSuit() + " |  " 
						+ "|      " + playerHand.get(2).getSuit() + " |  " );
				System.out.println("-----------  " + "-----------  " + "-----------  " );
				
				// second row of cards: 1 card with no 10s
				System.out.println("-----------");
				System.out.println("| " + playerHand.get(3).getSymbol() + "       |" );
				System.out.println("| " + playerHand.get(3).getSuit() + "      |" );
				System.out.println("|         |");
				System.out.println("|       " + playerHand.get(3).getSymbol() + " |" );
				System.out.println("|      " + playerHand.get(3).getSuit() + " |" );
				System.out.println("-----------");
			}
			else if( playerHand.get(3).getSymbol() == "10" )
			{
				// first row of cards, no 10s
				System.out.println("-----------  " + "-----------  " + "-----------  ");
				System.out.println("| " + playerHand.get(0).getSymbol() + "       |  " 
						+ "| " + playerHand.get(1).getSymbol() + "       |  " 
						+ "| " + playerHand.get(2).getSymbol() + "       |  " );
				System.out.println("| " + playerHand.get(0).getSuit() + "      |  " 
						+ "| " + playerHand.get(1).getSuit() + "      |  " + "| " 
						+ playerHand.get(2).getSuit() + "      |  " );
				System.out.println("|         |  " + "|         |  " + "|         |  " );
				System.out.println("|       " + playerHand.get(0).getSymbol() + " |  " 
						+ "|       " + playerHand.get(1).getSymbol() + " |  " 
						+ "|       " + playerHand.get(2).getSymbol() + " |  " );
				System.out.println("|      " + playerHand.get(0).getSuit() + " |  " 
						+ "|      " + playerHand.get(1).getSuit() + " |  " 
						+ "|      " + playerHand.get(2).getSuit() + " |  " );
				System.out.println("-----------  " + "-----------  " + "-----------  " );
				
				// second row of cards, with 10s
				System.out.println("-----------");
				System.out.println("| " + playerHand.get(3).getSymbol() + "      |" );
				System.out.println("| " + playerHand.get(3).getSuit() + "      |" );
				System.out.println("|         |");
				System.out.println("|      " + playerHand.get(3).getSymbol() + " |" );
				System.out.println("|      " + playerHand.get(3).getSuit() + " |" );
				System.out.println("-----------");
			}
			else
			{
				// first row of cards, no 10s
				System.out.println("-----------  " + "-----------  " + "-----------  ");
				System.out.println("| " + playerHand.get(0).getSymbol() + "       |  " 
						+ "| " + playerHand.get(1).getSymbol() + "       |  " 
						+ "| " + playerHand.get(2).getSymbol() + "       |  " );
				System.out.println("| " + playerHand.get(0).getSuit() + "      |  " 
						+ "| " + playerHand.get(1).getSuit() + "      |  " + "| " 
						+ playerHand.get(2).getSuit() + "      |  " );
				System.out.println("|         |  " + "|         |  " + "|         |  " );
				System.out.println("|       " + playerHand.get(0).getSymbol() + " |  " 
						+ "|       " + playerHand.get(1).getSymbol() + " |  " 
						+ "|       " + playerHand.get(2).getSymbol() + " |  " );
				System.out.println("|      " + playerHand.get(0).getSuit() + " |  " 
						+ "|      " + playerHand.get(1).getSuit() + " |  " 
						+ "|      " + playerHand.get(2).getSuit() + " |  " );
				System.out.println("-----------  " + "-----------  " + "-----------  " );
				
				// second row of cards, no 10s
				System.out.println("-----------");
				System.out.println("| " + playerHand.get(3).getSymbol() + "       |" );
				System.out.println("| " + playerHand.get(3).getSuit() + "      |" );
				System.out.println("|         |");
				System.out.println("|       " + playerHand.get(3).getSymbol() + " |" );
				System.out.println("|      " + playerHand.get(3).getSuit() + " |" );
				System.out.println("-----------");
			}
			break;
		case 5:
			if( playerHand.get(0).getSymbol() == "10" )
			{
				// first row of cards: 3 cards with 10 on the left
				System.out.println("-----------  " + "-----------  " + "-----------  " );
				System.out.println("| " + playerHand.get(0).getSymbol() + "      |  " 
						+ "| " + playerHand.get(1).getSymbol() + "       |  " 
						+ "| " + playerHand.get(2).getSymbol() + "       |  " );
				System.out.println("| " + playerHand.get(0).getSuit() + "      |  " 
						+ "| " + playerHand.get(1).getSuit() + "      |  " 
						+ "| " + playerHand.get(2).getSuit() + "      |  " );
				System.out.println("|         |  " + "|         |  " + "|         |  " );
				System.out.println("|      " + playerHand.get(0).getSymbol() + " |  " 
						+ "|       " + playerHand.get(1).getSymbol() + " |  " 
						+ "|       " + playerHand.get(2).getSymbol() + " |  " );
				System.out.println("|      " + playerHand.get(0).getSuit() + " |  " 
						+ "|      " + playerHand.get(1).getSuit() + " |  " 
						+ "|      " + playerHand.get(2).getSuit() + " |  " );
				System.out.println("-----------  " + "-----------  " + "-----------  " );
				
				// second row of cards: 2 cards with no 10s
				System.out.println("-----------  " + "-----------  ");
				System.out.println("| " + playerHand.get(3).getSymbol() + "       |  " 
						+ "| " + playerHand.get(4).getSymbol() + "       |  " );
				System.out.println("| " + playerHand.get(3).getSuit() + "      |  " 
						+ "| " + playerHand.get(4).getSuit() + "      |  " );
				System.out.println("|         |  " + "|         |  " );
				System.out.println("|       " + playerHand.get(3).getSymbol() + " |  " 
						+ "|       " + playerHand.get(4).getSymbol() + " |  " );
				System.out.println("|      " + playerHand.get(3).getSuit() + " |  " 
						+ "|      " + playerHand.get(4).getSuit() + " |  " );
				System.out.println("-----------  " + "-----------  " );
			}
			else if( playerHand.get(1).getSymbol() == "10" )
			{
				// first row of cards: 3 cards with 10 in the middle
				System.out.println("-----------  " + "-----------  " + "-----------  " );
				System.out.println("| " + playerHand.get(0).getSymbol() + "       |  " 
						+ "| " + playerHand.get(1).getSymbol() + "      |  " 
						+ "| " + playerHand.get(2).getSymbol() + "       |  " );
				System.out.println("| " + playerHand.get(0).getSuit() + "      |  " 
						+ "| " + playerHand.get(1).getSuit() + "     |  " 
						+ "| " + playerHand.get(2).getSuit() + "      |  " );
				System.out.println("|         |  " + "|         |  " + "|         |  " );
				System.out.println("|       " + playerHand.get(0).getSymbol() + " |  " 
						+ "|      " + playerHand.get(1).getSymbol() + " |  " 
						+ "|       " + playerHand.get(2).getSymbol() + " |  " );
				System.out.println("|      " + playerHand.get(0).getSuit() + " |  " 
						+ "|     " + playerHand.get(1).getSuit() + "  |  " 
						+ "|      " + playerHand.get(2).getSuit() + " |  " );
				System.out.println("-----------  " + "-----------  " + "-----------  " );
				
				// second row of cards: 2 cards with no 10s
				System.out.println("-----------  " + "-----------  ");
				System.out.println("| " + playerHand.get(3).getSymbol() + "       |  " 
						+ "| " + playerHand.get(4).getSymbol() + "       |  " );
				System.out.println("| " + playerHand.get(3).getSuit() + "      |  " 
						+ "| " + playerHand.get(4).getSuit() + "      |  " );
				System.out.println("|         |  " + "|         |  " );
				System.out.println("|       " + playerHand.get(3).getSymbol() + " |  " 
						+ "|       " + playerHand.get(4).getSymbol() + " |  " );
				System.out.println("|      " + playerHand.get(3).getSuit() + " |  " 
						+ "|      " + playerHand.get(4).getSuit() + " |  " );
				System.out.println("-----------  " + "-----------  " );
			}
			else if( playerHand.get(2).getSymbol() == "10" )
			{
				// first row of cards: 3 cards with a 10 on the right
				System.out.println("-----------  " + "-----------  " + "-----------  " );
				System.out.println("| " + playerHand.get(0).getSymbol() + "       |  " 
						+ "| " + playerHand.get(1).getSymbol() + "       |  " 
						+ "| " + playerHand.get(2).getSymbol() + "      |  " );
				System.out.println("| " + playerHand.get(0).getSuit() + "      |  " 
						+ "| " + playerHand.get(1).getSuit() + "      |  " 
						+ "| " + playerHand.get(2).getSuit() + "      |  " );
				System.out.println("|         |  " + "|         |  " + "|         |  " );
				System.out.println("|       " + playerHand.get(0).getSymbol() + " |  " 
						+ "|       " + playerHand.get(1).getSymbol() + " |  " 
						+ "|      " + playerHand.get(2).getSymbol() );
				System.out.println("|      " + playerHand.get(0).getSuit() + " |  " 
						+ "|      " + playerHand.get(1).getSuit() + " |  " 
						+ "|      " + playerHand.get(2).getSuit() + " |  " );
				System.out.println("-----------  " + "-----------  " + "-----------  " );
				
				// second row of cards: 2 cards with no 10s
				System.out.println("-----------  " + "-----------  ");
				System.out.println("| " + playerHand.get(3).getSymbol() + "       |  " 
						+ "| " + playerHand.get(4).getSymbol() + "       |  " );
				System.out.println("| " + playerHand.get(3).getSuit() + "      |  " 
						+ "| " + playerHand.get(4).getSuit() + "      |  " );
				System.out.println("|         |  " + "|         |  " );
				System.out.println("|       " + playerHand.get(3).getSymbol() + " |  " 
						+ "|       " + playerHand.get(4).getSymbol() + " |  " );
				System.out.println("|      " + playerHand.get(3).getSuit() + " |  " 
						+ "|      " + playerHand.get(4).getSuit() + " |  " );
				System.out.println("-----------  " + "-----------  " );
			}
			else if( playerHand.get(3).getSymbol() == "10" )
			{
				// first row of cards, no 10s
				System.out.println("-----------  " + "-----------  " + "-----------  ");
				System.out.println("| " + playerHand.get(0).getSymbol() + "       |  " 
						+ "| " + playerHand.get(1).getSymbol() + "       |  " 
						+ "| " + playerHand.get(2).getSymbol() + "       |  " );
				System.out.println("| " + playerHand.get(0).getSuit() + "      |  " 
						+ "| " + playerHand.get(1).getSuit() + "      |  " + "| " 
						+ playerHand.get(2).getSuit() + "      |  " );
				System.out.println("|         |  " + "|         |  " + "|         |  " );
				System.out.println("|       " + playerHand.get(0).getSymbol() + " |  " 
						+ "|       " + playerHand.get(1).getSymbol() + " |  " 
						+ "|       " + playerHand.get(2).getSymbol() + " |  " );
				System.out.println("|      " + playerHand.get(0).getSuit() + " |  " 
						+ "|      " + playerHand.get(1).getSuit() + " |  " 
						+ "|      " + playerHand.get(2).getSuit() + " |  " );
				System.out.println("-----------  " + "-----------  " + "-----------  " );
				
				// second row of cards: 2 cards with a 10 on the left
				System.out.println("-----------  " + "-----------  ");
				System.out.println("| " + playerHand.get(3).getSymbol() + "      |  " 
						+ "| " + playerHand.get(4).getSymbol() + "       |  " );
				System.out.println("| " + playerHand.get(3).getSuit() + "      |  " 
						+ "| " + playerHand.get(4).getSuit() + "      |  " );
				System.out.println("|         |  " + "|         |  " );
				System.out.println("|      " + playerHand.get(3).getSymbol() + " |  " 
						+ "|       " + playerHand.get(4).getSymbol() + " |  " );
				System.out.println("|      " + playerHand.get(3).getSuit() + " |  " 
						+ "|      " + playerHand.get(4).getSuit() + " |  " );
				System.out.println("-----------  " + "-----------  " );
			}
			else if( playerHand.get(4).getSymbol() == "10" )
			{
				// first row of cards, no 10s
				System.out.println("-----------  " + "-----------  " + "-----------  ");
				System.out.println("| " + playerHand.get(0).getSymbol() + "       |  " 
						+ "| " + playerHand.get(1).getSymbol() + "       |  " 
						+ "| " + playerHand.get(2).getSymbol() + "       |  " );
				System.out.println("| " + playerHand.get(0).getSuit() + "      |  " 
						+ "| " + playerHand.get(1).getSuit() + "      |  " + "| " 
						+ playerHand.get(2).getSuit() + "      |  " );
				System.out.println("|         |  " + "|         |  " + "|         |  " );
				System.out.println("|       " + playerHand.get(0).getSymbol() + " |  " 
						+ "|       " + playerHand.get(1).getSymbol() + " |  " 
						+ "|       " + playerHand.get(2).getSymbol() + " |  " );
				System.out.println("|      " + playerHand.get(0).getSuit() + " |  " 
						+ "|      " + playerHand.get(1).getSuit() + " |  " 
						+ "|      " + playerHand.get(2).getSuit() + " |  " );
				System.out.println("-----------  " + "-----------  " + "-----------  " );
				
				// second row of cards: 2 cards with a 10 on the right
				System.out.println("-----------  " + "-----------  ");
				System.out.println("| " + playerHand.get(3).getSymbol() + "       |  " 
						+ "| " + playerHand.get(4).getSymbol() + "      |  " );
				System.out.println("| " + playerHand.get(3).getSuit() + "      |  " 
						+ "| " + playerHand.get(4).getSuit() + "     |  " );
				System.out.println("|         |  " + "|         |  " );
				System.out.println("|       " + playerHand.get(3).getSymbol() + " |  " 
						+ "|      " + playerHand.get(4).getSymbol() + " |  " );
				System.out.println("|      " + playerHand.get(3).getSuit() + " |  " 
						+ "|     " + playerHand.get(4).getSuit() + "  |  " );
				System.out.println("-----------  " + "-----------  " );
			}
			else
			{
				// first row of cards, no 10s
				System.out.println("-----------  " + "-----------  " + "-----------  ");
				System.out.println("| " + playerHand.get(0).getSymbol() + "       |  " 
						+ "| " + playerHand.get(1).getSymbol() + "       |  " 
						+ "| " + playerHand.get(2).getSymbol() + "       |  " );
				System.out.println("| " + playerHand.get(0).getSuit() + "      |  " 
						+ "| " + playerHand.get(1).getSuit() + "      |  " + "| " 
						+ playerHand.get(2).getSuit() + "      |  " );
				System.out.println("|         |  " + "|         |  " + "|         |  " );
				System.out.println("|       " + playerHand.get(0).getSymbol() + " |  " 
						+ "|       " + playerHand.get(1).getSymbol() + " |  " 
						+ "|       " + playerHand.get(2).getSymbol() + " |  " );
				System.out.println("|      " + playerHand.get(0).getSuit() + " |  " 
						+ "|      " + playerHand.get(1).getSuit() + " |  " 
						+ "|      " + playerHand.get(2).getSuit() + " |  " );
				System.out.println("-----------  " + "-----------  " + "-----------  " );
				
				// second row of cards, no 10s
				System.out.println("-----------  " + "-----------  ");
				System.out.println("| " + playerHand.get(3).getSymbol() + "       |  " 
						+ "| " + playerHand.get(4).getSymbol() + "       |  " );
				System.out.println("| " + playerHand.get(3).getSuit() + "      |  " 
						+ "| " + playerHand.get(4).getSuit() + "      |  " );
				System.out.println("|         |  " + "|         |  " );
				System.out.println("|       " + playerHand.get(3).getSymbol() + " |  " 
						+ "|       " + playerHand.get(4).getSymbol() + " |  " );
				System.out.println("|      " + playerHand.get(3).getSuit() + " |  " 
						+ "|      " + playerHand.get(4).getSuit() + " |  " );
				System.out.println("-----------  " + "-----------  " );
			}
			break;
		case 6:
			if( playerHand.get(0).getSymbol() == "10" )
			{
				// first row of cards: 3 cards with 10 on the left
				System.out.println("-----------  " + "-----------  " + "-----------  " );
				System.out.println("| " + playerHand.get(0).getSymbol() + "      |  " 
						+ "| " + playerHand.get(1).getSymbol() + "       |  " 
						+ "| " + playerHand.get(2).getSymbol() + "       |  " );
				System.out.println("| " + playerHand.get(0).getSuit() + "      |  " 
						+ "| " + playerHand.get(1).getSuit() + "      |  " 
						+ "| " + playerHand.get(2).getSuit() + "      |  " );
				System.out.println("|         |  " + "|         |  " + "|         |  " );
				System.out.println("|      " + playerHand.get(0).getSymbol() + " |  " 
						+ "|       " + playerHand.get(1).getSymbol() + " |  " 
						+ "|       " + playerHand.get(2).getSymbol() + " |  " );
				System.out.println("|      " + playerHand.get(0).getSuit() + " |  " 
						+ "|      " + playerHand.get(1).getSuit() + " |  " 
						+ "|      " + playerHand.get(2).getSuit() + " |  " );
				System.out.println("-----------  " + "-----------  " + "-----------  " );
				
				// second row of cards: 3 cards with no 10s
				System.out.println("-----------  " + "-----------  " + "-----------  ");
				System.out.println("| " + playerHand.get(3).getSymbol() + "       |  " 
						+ "| " + playerHand.get(4).getSymbol() + "       |  " 
						+ "| " + playerHand.get(5).getSymbol() + "       |  " );
				System.out.println("| " + playerHand.get(3).getSuit() + "      |  " 
						+ "| " + playerHand.get(4).getSuit() + "      |  " + "| " 
						+ playerHand.get(5).getSuit() + "      |  " );
				System.out.println("|         |  " + "|         |  " + "|         |  " );
				System.out.println("|       " + playerHand.get(3).getSymbol() + " |  " 
						+ "|       " + playerHand.get(4).getSymbol() + " |  " 
						+ "|       " + playerHand.get(5).getSymbol() + " |  " );
				System.out.println("|      " + playerHand.get(3).getSuit() + " |  " 
						+ "|      " + playerHand.get(4).getSuit() + " |  " 
						+ "|      " + playerHand.get(5).getSuit() + " |  " );
				System.out.println("-----------  " + "-----------  " + "-----------  " );
			}
			else if( playerHand.get(1).getSymbol() == "10" )
			{
				// first row of cards: 3 cards with 10 in the middle
				System.out.println("-----------  " + "-----------  " + "-----------  " );
				System.out.println("| " + playerHand.get(0).getSymbol() + "       |  " 
						+ "| " + playerHand.get(1).getSymbol() + "      |  " 
						+ "| " + playerHand.get(2).getSymbol() + "       |  " );
				System.out.println("| " + playerHand.get(0).getSuit() + "      |  " 
						+ "| " + playerHand.get(1).getSuit() + "     |  " 
						+ "| " + playerHand.get(2).getSuit() + "      |  " );
				System.out.println("|         |  " + "|         |  " + "|         |  " );
				System.out.println("|       " + playerHand.get(0).getSymbol() + " |  " 
						+ "|      " + playerHand.get(1).getSymbol() + " |  " 
						+ "|       " + playerHand.get(2).getSymbol() + " |  " );
				System.out.println("|      " + playerHand.get(0).getSuit() + " |  " 
						+ "|     " + playerHand.get(1).getSuit() + "  |  " 
						+ "|      " + playerHand.get(2).getSuit() + " |  " );
				System.out.println("-----------  " + "-----------  " + "-----------  " );
				
				// second row of cards: 3 cards with no 10s
				System.out.println("-----------  " + "-----------  " + "-----------  ");
				System.out.println("| " + playerHand.get(3).getSymbol() + "       |  " 
						+ "| " + playerHand.get(4).getSymbol() + "       |  " 
						+ "| " + playerHand.get(5).getSymbol() + "       |  " );
				System.out.println("| " + playerHand.get(3).getSuit() + "      |  " 
						+ "| " + playerHand.get(4).getSuit() + "      |  " + "| " 
						+ playerHand.get(5).getSuit() + "      |  " );
				System.out.println("|         |  " + "|         |  " + "|         |  " );
				System.out.println("|       " + playerHand.get(3).getSymbol() + " |  " 
						+ "|       " + playerHand.get(4).getSymbol() + " |  " 
						+ "|       " + playerHand.get(5).getSymbol() + " |  " );
				System.out.println("|      " + playerHand.get(3).getSuit() + " |  " 
						+ "|      " + playerHand.get(4).getSuit() + " |  " 
						+ "|      " + playerHand.get(5).getSuit() + " |  " );
				System.out.println("-----------  " + "-----------  " + "-----------  " );
			}
			else if( playerHand.get(2).getSymbol() == "10" )
			{
				// first row of cards: 3 cards with a 10 on the right
				System.out.println("-----------  " + "-----------  " + "-----------  " );
				System.out.println("| " + playerHand.get(0).getSymbol() + "       |  " 
						+ "| " + playerHand.get(1).getSymbol() + "       |  " 
						+ "| " + playerHand.get(2).getSymbol() + "      |  " );
				System.out.println("| " + playerHand.get(0).getSuit() + "      |  " 
						+ "| " + playerHand.get(1).getSuit() + "      |  " 
						+ "| " + playerHand.get(2).getSuit() + "      |  " );
				System.out.println("|         |  " + "|         |  " + "|         |  " );
				System.out.println("|       " + playerHand.get(0).getSymbol() + " |  " 
						+ "|       " + playerHand.get(1).getSymbol() + " |  " 
						+ "|      " + playerHand.get(2).getSymbol() );
				System.out.println("|      " + playerHand.get(0).getSuit() + " |  " 
						+ "|      " + playerHand.get(1).getSuit() + " |  " 
						+ "|      " + playerHand.get(2).getSuit() + " |  " );
				System.out.println("-----------  " + "-----------  " + "-----------  " );
				
				// second row of cards: 3 cards with no 10s
				System.out.println("-----------  " + "-----------  " + "-----------  ");
				System.out.println("| " + playerHand.get(3).getSymbol() + "       |  " 
						+ "| " + playerHand.get(4).getSymbol() + "       |  " 
						+ "| " + playerHand.get(5).getSymbol() + "       |  " );
				System.out.println("| " + playerHand.get(3).getSuit() + "      |  " 
						+ "| " + playerHand.get(4).getSuit() + "      |  " + "| " 
						+ playerHand.get(5).getSuit() + "      |  " );
				System.out.println("|         |  " + "|         |  " + "|         |  " );
				System.out.println("|       " + playerHand.get(3).getSymbol() + " |  " 
						+ "|       " + playerHand.get(4).getSymbol() + " |  " 
						+ "|       " + playerHand.get(5).getSymbol() + " |  " );
				System.out.println("|      " + playerHand.get(3).getSuit() + " |  " 
						+ "|      " + playerHand.get(4).getSuit() + " |  " 
						+ "|      " + playerHand.get(5).getSuit() + " |  " );
				System.out.println("-----------  " + "-----------  " + "-----------  " );
			}
			else if( playerHand.get(3).getSymbol() == "10" )
			{
				// first row of cards, no 10s
				System.out.println("-----------  " + "-----------  " + "-----------  ");
				System.out.println("| " + playerHand.get(0).getSymbol() + "       |  " 
						+ "| " + playerHand.get(1).getSymbol() + "       |  " 
						+ "| " + playerHand.get(2).getSymbol() + "       |  " );
				System.out.println("| " + playerHand.get(0).getSuit() + "      |  " 
						+ "| " + playerHand.get(1).getSuit() + "      |  " + "| " 
						+ playerHand.get(2).getSuit() + "      |  " );
				System.out.println("|         |  " + "|         |  " + "|         |  " );
				System.out.println("|       " + playerHand.get(0).getSymbol() + " |  " 
						+ "|       " + playerHand.get(1).getSymbol() + " |  " 
						+ "|       " + playerHand.get(2).getSymbol() + " |  " );
				System.out.println("|      " + playerHand.get(0).getSuit() + " |  " 
						+ "|      " + playerHand.get(1).getSuit() + " |  " 
						+ "|      " + playerHand.get(2).getSuit() + " |  " );
				System.out.println("-----------  " + "-----------  " + "-----------  " );
				
				// second row of cards: 3 cards with a 10 on the left
				System.out.println("-----------  " + "-----------  " + "-----------  " );
				System.out.println("| " + playerHand.get(3).getSymbol() + "      |  " 
						+ "| " + playerHand.get(4).getSymbol() + "       |  " 
						+ "| " + playerHand.get(5).getSymbol() + "       |  " );
				System.out.println("| " + playerHand.get(3).getSuit() + "      |  " 
						+ "| " + playerHand.get(4).getSuit() + "      |  " 
						+ "| " + playerHand.get(5).getSuit() + "      |  " );
				System.out.println("|         |  " + "|         |  " + "|         |  " );
				System.out.println("|      " + playerHand.get(3).getSymbol() + " |  " 
						+ "|       " + playerHand.get(4).getSymbol() + " |  " 
						+ "|       " + playerHand.get(5).getSymbol() + " |  " );
				System.out.println("|      " + playerHand.get(3).getSuit() + " |  " 
						+ "|      " + playerHand.get(4).getSuit() + " |  " 
						+ "|      " + playerHand.get(5).getSuit() + " |  " );
				System.out.println("-----------  " + "-----------  " + "-----------  " );
			}
			else if( playerHand.get(4).getSymbol() == "10" )
			{
				// first row of cards, no 10s
				System.out.println("-----------  " + "-----------  " + "-----------  ");
				System.out.println("| " + playerHand.get(0).getSymbol() + "       |  " 
						+ "| " + playerHand.get(1).getSymbol() + "       |  " 
						+ "| " + playerHand.get(2).getSymbol() + "       |  " );
				System.out.println("| " + playerHand.get(0).getSuit() + "      |  " 
						+ "| " + playerHand.get(1).getSuit() + "      |  " + "| " 
						+ playerHand.get(2).getSuit() + "      |  " );
				System.out.println("|         |  " + "|         |  " + "|         |  " );
				System.out.println("|       " + playerHand.get(0).getSymbol() + " |  " 
						+ "|       " + playerHand.get(1).getSymbol() + " |  " 
						+ "|       " + playerHand.get(2).getSymbol() + " |  " );
				System.out.println("|      " + playerHand.get(0).getSuit() + " |  " 
						+ "|      " + playerHand.get(1).getSuit() + " |  " 
						+ "|      " + playerHand.get(2).getSuit() + " |  " );
				System.out.println("-----------  " + "-----------  " + "-----------  " );
				
				// second row of cards: 3 cards with 10 in the middle
				System.out.println("-----------  " + "-----------  " + "-----------  " );
				System.out.println("| " + playerHand.get(3).getSymbol() + "       |  " 
						+ "| " + playerHand.get(4).getSymbol() + "      |  " 
						+ "| " + playerHand.get(5).getSymbol() + "       |  " );
				System.out.println("| " + playerHand.get(3).getSuit() + "      |  " 
						+ "| " + playerHand.get(4).getSuit() + "     |  " 
						+ "| " + playerHand.get(5).getSuit() + "      |  " );
				System.out.println("|         |  " + "|         |  " + "|         |  " );
				System.out.println("|       " + playerHand.get(3).getSymbol() + " |  " 
						+ "|      " + playerHand.get(4).getSymbol() + " |  " 
						+ "|       " + playerHand.get(5).getSymbol() + " |  " );
				System.out.println("|      " + playerHand.get(3).getSuit() + " |  " 
						+ "|     " + playerHand.get(4).getSuit() + "  |  " 
						+ "|      " + playerHand.get(5).getSuit() + " |  " );
				System.out.println("-----------  " + "-----------  " + "-----------  " );
			}
			else if( playerHand.get(5).getSymbol() == "10" )
			{
				// first row of cards, no 10s
				System.out.println("-----------  " + "-----------  " + "-----------  ");
				System.out.println("| " + playerHand.get(0).getSymbol() + "       |  " 
						+ "| " + playerHand.get(1).getSymbol() + "       |  " 
						+ "| " + playerHand.get(2).getSymbol() + "       |  " );
				System.out.println("| " + playerHand.get(0).getSuit() + "      |  " 
						+ "| " + playerHand.get(1).getSuit() + "      |  " + "| " 
						+ playerHand.get(2).getSuit() + "      |  " );
				System.out.println("|         |  " + "|         |  " + "|         |  " );
				System.out.println("|       " + playerHand.get(0).getSymbol() + " |  " 
						+ "|       " + playerHand.get(1).getSymbol() + " |  " 
						+ "|       " + playerHand.get(2).getSymbol() + " |  " );
				System.out.println("|      " + playerHand.get(0).getSuit() + " |  " 
						+ "|      " + playerHand.get(1).getSuit() + " |  " 
						+ "|      " + playerHand.get(2).getSuit() + " |  " );
				System.out.println("-----------  " + "-----------  " + "-----------  " );
				
				// second row of cards: 3 cards with a 10 on the right
				System.out.println("-----------  " + "-----------  " + "-----------  " );
				System.out.println("| " + playerHand.get(3).getSymbol() + "       |  " 
						+ "| " + playerHand.get(4).getSymbol() + "       |  " 
						+ "| " + playerHand.get(5).getSymbol() + "      |  " );
				System.out.println("| " + playerHand.get(3).getSuit() + "      |  " 
						+ "| " + playerHand.get(4).getSuit() + "      |  " 
						+ "| " + playerHand.get(5).getSuit() + "      |  " );
				System.out.println("|         |  " + "|         |  " + "|         |  " );
				System.out.println("|       " + playerHand.get(3).getSymbol() + " |  " 
						+ "|       " + playerHand.get(4).getSymbol() + " |  " 
						+ "|      " + playerHand.get(5).getSymbol() );
				System.out.println("|      " + playerHand.get(3).getSuit() + " |  " 
						+ "|      " + playerHand.get(4).getSuit() + " |  " 
						+ "|      " + playerHand.get(5).getSuit() + " |  " );
				System.out.println("-----------  " + "-----------  " + "-----------  " );
			}
			else
			{
				// first row of cards, 3 cards no 10s
				System.out.println("-----------  " + "-----------  " + "-----------  ");
				System.out.println("| " + playerHand.get(0).getSymbol() + "       |  " 
						+ "| " + playerHand.get(1).getSymbol() + "       |  " 
						+ "| " + playerHand.get(2).getSymbol() + "       |  " );
				System.out.println("| " + playerHand.get(0).getSuit() + "      |  " 
						+ "| " + playerHand.get(1).getSuit() + "      |  " + "| " 
						+ playerHand.get(2).getSuit() + "      |  " );
				System.out.println("|         |  " + "|         |  " + "|         |  " );
				System.out.println("|       " + playerHand.get(0).getSymbol() + " |  " 
						+ "|       " + playerHand.get(1).getSymbol() + " |  " 
						+ "|       " + playerHand.get(2).getSymbol() + " |  " );
				System.out.println("|      " + playerHand.get(0).getSuit() + " |  " 
						+ "|      " + playerHand.get(1).getSuit() + " |  " 
						+ "|      " + playerHand.get(2).getSuit() + " |  " );
				System.out.println("-----------  " + "-----------  " + "-----------  " );
				
				// second row of cards, 3 cards no 10s
				System.out.println("-----------  " + "-----------  " + "-----------  ");
				System.out.println("| " + playerHand.get(3).getSymbol() + "       |  " 
						+ "| " + playerHand.get(4).getSymbol() + "       |  " 
						+ "| " + playerHand.get(5).getSymbol() + "       |  " );
				System.out.println("| " + playerHand.get(3).getSuit() + "      |  " 
						+ "| " + playerHand.get(4).getSuit() + "      |  " + "| " 
						+ playerHand.get(5).getSuit() + "      |  " );
				System.out.println("|         |  " + "|         |  " + "|         |  " );
				System.out.println("|       " + playerHand.get(3).getSymbol() + " |  " 
						+ "|       " + playerHand.get(4).getSymbol() + " |  " 
						+ "|       " + playerHand.get(5).getSymbol() + " |  " );
				System.out.println("|      " + playerHand.get(3).getSuit() + " |  " 
						+ "|      " + playerHand.get(4).getSuit() + " |  " 
						+ "|      " + playerHand.get(5).getSuit() + " |  " );
				System.out.println("-----------  " + "-----------  " + "-----------  " );
			}
			break;
		default:
			break;
		} // end of switch statement
	} // end of method showPlayerHand()

/**
 * The showHiddenDealerHand() method creates a basic graphical representation of a 
	 * Card object and displays the first two cards held by the dealer. The 
	 * identity of the first card is revealed to the player however the identity 
	 * and value of the second card is hidden from the player.
	 * @param dealerHand	An ArrayList of Card objects that represent the current
	 * cards belonging to the dealer
 */
	public static void showHiddenDealerHand( ArrayList<Card> dealerHand )
	{
		System.out.println( "Dealer cards:");
		if( dealerHand.get(0).getSymbol() == "10" )
		{
			// first row of cards: 2 cards with a 10 on the left
			System.out.println("-----------  " + "-----------  ");
			System.out.println("| " + dealerHand.get(0).getSymbol() + "      |  " 
					+ "|  +   +  |  " );
			System.out.println("| " + dealerHand.get(0).getSuit() + "      |  " 
					+ "|         |  " );
			System.out.println("|         |  " + "|  +   +  |  " );
			System.out.println("|      " + dealerHand.get(0).getSymbol() + " |  " 
					+ "|         |  " );
			System.out.println("|      " + dealerHand.get(0).getSuit() + " |  " 
					+ "|  +   +  |  " );
			System.out.println("-----------  " + "-----------  " );
		}
		else
		{
			// first row of cards: 2 cards no 10s
			System.out.println("-----------  " + "-----------  ");
			System.out.println("| " + dealerHand.get(0).getSymbol() + "       |  " 
					+ "|  +   +  |  " );
			System.out.println("| " + dealerHand.get(0).getSuit() + "      |  " 
					+ "|         |  " );
			System.out.println("|         |  " + "|  +   +  |  " );
			System.out.println("|       " + dealerHand.get(0).getSymbol() + " |  " 
					+ "|         |  " );
			System.out.println("|      " + dealerHand.get(0).getSuit() + " |  " 
					+ "|  +   +  |  " );
			System.out.println("-----------  " + "-----------  " );
		}
		
	}
	
/**
 * The showFullDealerHand() method creates a basic graphical representation of a 
 * Card object and displays the cards held by the dealer in rows of 3.
 * @param dealerHand	An ArrayList of Card objects that represent the current
 * cards belonging to the dealer
 */
	public static void showFullDealerHand( ArrayList<Card> dealerHand )
	{
		int numOfDealerCards = dealerHand.size();
		
		System.out.println();
		System.out.println( "Dealer cards:");
		switch( numOfDealerCards )
		{
		case 2:
			if( dealerHand.get(0).getSymbol() == "10" )
			{
				// first row of cards: 2 cards with a 10 on the left
				System.out.println("-----------  " + "-----------  ");
				System.out.println("| " + dealerHand.get(0).getSymbol() + "      |  " 
						+ "| " + dealerHand.get(1).getSymbol() + "       |  " );
				System.out.println("| " + dealerHand.get(0).getSuit() + "      |  " 
						+ "| " + dealerHand.get(1).getSuit() + "      |  " );
				System.out.println("|         |  " + "|         |  " );
				System.out.println("|      " + dealerHand.get(0).getSymbol() + " |  " 
						+ "|       " + dealerHand.get(1).getSymbol() + " |  " );
				System.out.println("|      " + dealerHand.get(0).getSuit() + " |  " 
						+ "|      " + dealerHand.get(1).getSuit() + " |  " );
				System.out.println("-----------  " + "-----------  " );
			}
			else if( dealerHand.get(1).getSymbol() == "10" )
			{
				// first row of cards: 2 cards with a 10 on the right
				System.out.println("-----------  " + "-----------  ");
				System.out.println("| " + dealerHand.get(0).getSymbol() + "       |  " 
						+ "| " + dealerHand.get(1).getSymbol() + "      |  " );
				System.out.println("| " + dealerHand.get(0).getSuit() + "      |  " 
						+ "| " + dealerHand.get(1).getSuit() + "     |  " );
				System.out.println("|         |  " + "|         |  " );
				System.out.println("|       " + dealerHand.get(0).getSymbol() + " |  " 
						+ "|      " + dealerHand.get(1).getSymbol() + " |  " );
				System.out.println("|      " + dealerHand.get(0).getSuit() + " |  " 
						+ "|     " + dealerHand.get(1).getSuit() + "  |  " );
				System.out.println("-----------  " + "-----------  " );
			}
			else
			{
				// first row of cards: 2 cards with no 10s
				System.out.println("-----------  " + "-----------  ");
				System.out.println("| " + dealerHand.get(0).getSymbol() + "       |  " 
						+ "| " + dealerHand.get(1).getSymbol() + "       |  " );
				System.out.println("| " + dealerHand.get(0).getSuit() + "      |  " 
						+ "| " + dealerHand.get(1).getSuit() + "      |  " );
				System.out.println("|         |  " + "|         |  " );
				System.out.println("|       " + dealerHand.get(0).getSymbol() + " |  " 
						+ "|       " + dealerHand.get(1).getSymbol() + " |  " );
				System.out.println("|      " + dealerHand.get(0).getSuit() + " |  " 
						+ "|      " + dealerHand.get(1).getSuit() + " |  " );
				System.out.println("-----------  " + "-----------  " );
			}
			break;
		case 3:
			if( dealerHand.get(0).getSymbol() == "10" )
			{
				// first row of cards: 3 cards with a 10 on the left
				System.out.println("-----------  " + "-----------  " + "-----------  " );
				System.out.println("| " + dealerHand.get(0).getSymbol() + "      |  " 
						+ "| " + dealerHand.get(1).getSymbol() + "       |  " 
						+ "| " + dealerHand.get(2).getSymbol() + "       |  " );
				System.out.println("| " + dealerHand.get(0).getSuit() + "      |  " 
						+ "| " + dealerHand.get(1).getSuit() + "      |  " 
						+ "| " + dealerHand.get(2).getSuit() + "      |  " );
				System.out.println("|         |  " + "|         |  " + "|         |  " );
				System.out.println("|      " + dealerHand.get(0).getSymbol() + " |  " 
						+ "|       " + dealerHand.get(1).getSymbol() + " |  " 
						+ "|       " + dealerHand.get(2).getSymbol() + " |  " );
				System.out.println("|      " + dealerHand.get(0).getSuit() + " |  " 
						+ "|      " + dealerHand.get(1).getSuit() + " |  " 
						+ "|      " + dealerHand.get(2).getSuit() + " |  " );
				System.out.println("-----------  " + "-----------  " + "-----------  " );
			}
			else if( dealerHand.get(1).getSymbol() == "10" )
			{
				// first row of cards: 3 cards with a 10 in the middle
				System.out.println("-----------  " + "-----------  " + "-----------  " );
				System.out.println("| " + dealerHand.get(0).getSymbol() + "       |  " 
						+ "| " + dealerHand.get(1).getSymbol() + "      |  " 
						+ "| " + dealerHand.get(2).getSymbol() + "       |  " );
				System.out.println("| " + dealerHand.get(0).getSuit() + "      |  " 
						+ "| " + dealerHand.get(1).getSuit() + "     |  " 
						+ "| " + dealerHand.get(2).getSuit() + "      |  " );
				System.out.println("|         |  " + "|         |  " + "|         |  " );
				System.out.println("|       " + dealerHand.get(0).getSymbol() + " |  " 
						+ "|      " + dealerHand.get(1).getSymbol() + " |  " 
						+ "|       " + dealerHand.get(2).getSymbol() + " |  " );
				System.out.println("|      " + dealerHand.get(0).getSuit() + " |  " 
						+ "|     " + dealerHand.get(1).getSuit() + "  |  " 
						+ "|      " + dealerHand.get(2).getSuit() + " |  " );
				System.out.println("-----------  " + "-----------  " + "-----------  " );
			}
			else if( dealerHand.get(2).getSymbol() == "10" )
			{
				// first row of cards: 3 cards with a 10 on the right
				System.out.println("-----------  " + "-----------  " + "-----------  " );
				System.out.println("| " + dealerHand.get(0).getSymbol() + "       |  " 
						+ "| " + dealerHand.get(1).getSymbol() + "       |  " 
						+ "| " + dealerHand.get(2).getSymbol() + "      |  " );
				System.out.println("| " + dealerHand.get(0).getSuit() + "      |  " 
						+ "| " + dealerHand.get(1).getSuit() + "      |  " 
						+ "| " + dealerHand.get(2).getSuit() + "      |  " );
				System.out.println("|         |  " + "|         |  " + "|         |  " );
				System.out.println("|       " + dealerHand.get(0).getSymbol() + " |  " 
						+ "|       " + dealerHand.get(1).getSymbol() + " |  " 
						+ "|      " + dealerHand.get(2).getSymbol() );
				System.out.println("|      " + dealerHand.get(0).getSuit() + " |  " 
						+ "|      " + dealerHand.get(1).getSuit() + " |  " 
						+ "|      " + dealerHand.get(2).getSuit() + " |  " );
				System.out.println("-----------  " + "-----------  " + "-----------  " );
			}
			else
			{
				// first row of cards: 3 cards with no 10s
				System.out.println("-----------  " + "-----------  " + "-----------  ");
				System.out.println("| " + dealerHand.get(0).getSymbol() + "       |  " 
						+ "| " + dealerHand.get(1).getSymbol() + "       |  " 
						+ "| " + dealerHand.get(2).getSymbol() + "       |  " );
				System.out.println("| " + dealerHand.get(0).getSuit() + "      |  " 
						+ "| " + dealerHand.get(1).getSuit() + "      |  " + "| " 
						+ dealerHand.get(2).getSuit() + "      |  " );
				System.out.println("|         |  " + "|         |  " + "|         |  " );
				System.out.println("|       " + dealerHand.get(0).getSymbol() + " |  " 
						+ "|       " + dealerHand.get(1).getSymbol() + " |  " 
						+ "|       " + dealerHand.get(2).getSymbol() + " |  " );
				System.out.println("|      " + dealerHand.get(0).getSuit() + " |  " 
						+ "|      " + dealerHand.get(1).getSuit() + " |  " 
						+ "|      " + dealerHand.get(2).getSuit() + " |  " );
				System.out.println("-----------  " + "-----------  " + "-----------  " );
			}
			break;
		case 4:
			if( dealerHand.get(0).getSymbol() == "10" )
			{
				// first row of cards: 3 cards with 10 on the left
				System.out.println("-----------  " + "-----------  " + "-----------  " );
				System.out.println("| " + dealerHand.get(0).getSymbol() + "      |  " 
						+ "| " + dealerHand.get(1).getSymbol() + "       |  " 
						+ "| " + dealerHand.get(2).getSymbol() + "       |  " );
				System.out.println("| " + dealerHand.get(0).getSuit() + "      |  " 
						+ "| " + dealerHand.get(1).getSuit() + "      |  " 
						+ "| " + dealerHand.get(2).getSuit() + "      |  " );
				System.out.println("|         |  " + "|         |  " + "|         |  " );
				System.out.println("|      " + dealerHand.get(0).getSymbol() + " |  " 
						+ "|       " + dealerHand.get(1).getSymbol() + " |  " 
						+ "|       " + dealerHand.get(2).getSymbol() + " |  " );
				System.out.println("|      " + dealerHand.get(0).getSuit() + " |  " 
						+ "|      " + dealerHand.get(1).getSuit() + " |  " 
						+ "|      " + dealerHand.get(2).getSuit() + " |  " );
				System.out.println("-----------  " + "-----------  " + "-----------  " );
				
				// second row of cards: 1 card with no 10s
				System.out.println("-----------");
				System.out.println("| " + dealerHand.get(3).getSymbol() + "       |" );
				System.out.println("| " + dealerHand.get(3).getSuit() + "      |" );
				System.out.println("|         |");
				System.out.println("|       " + dealerHand.get(3).getSymbol() + " |" );
				System.out.println("|      " + dealerHand.get(3).getSuit() + " |" );
				System.out.println("-----------");
			}
			else if( dealerHand.get(1).getSymbol() == "10" )
			{
				// first row of cards: 3 cards with 10 in the middle
				System.out.println("-----------  " + "-----------  " + "-----------  " );
				System.out.println("| " + dealerHand.get(0).getSymbol() + "       |  " 
						+ "| " + dealerHand.get(1).getSymbol() + "      |  " 
						+ "| " + dealerHand.get(2).getSymbol() + "       |  " );
				System.out.println("| " + dealerHand.get(0).getSuit() + "      |  " 
						+ "| " + dealerHand.get(1).getSuit() + "     |  " 
						+ "| " + dealerHand.get(2).getSuit() + "      |  " );
				System.out.println("|         |  " + "|         |  " + "|         |  " );
				System.out.println("|       " + dealerHand.get(0).getSymbol() + " |  " 
						+ "|      " + dealerHand.get(1).getSymbol() + " |  " 
						+ "|       " + dealerHand.get(2).getSymbol() + " |  " );
				System.out.println("|      " + dealerHand.get(0).getSuit() + " |  " 
						+ "|     " + dealerHand.get(1).getSuit() + "  |  " 
						+ "|      " + dealerHand.get(2).getSuit() + " |  " );
				System.out.println("-----------  " + "-----------  " + "-----------  " );
				
				// second row of cards: 1 card with no 10s
				System.out.println("-----------");
				System.out.println("| " + dealerHand.get(3).getSymbol() + "       |" );
				System.out.println("| " + dealerHand.get(3).getSuit() + "      |" );
				System.out.println("|         |");
				System.out.println("|       " + dealerHand.get(3).getSymbol() + " |" );
				System.out.println("|      " + dealerHand.get(3).getSuit() + " |" );
				System.out.println("-----------");
			}
			else if( dealerHand.get(2).getSymbol() == "10" )
			{
				// first row of cards: 3 cards with a 10 on the right
				System.out.println("-----------  " + "-----------  " + "-----------  " );
				System.out.println("| " + dealerHand.get(0).getSymbol() + "       |  " 
						+ "| " + dealerHand.get(1).getSymbol() + "       |  " 
						+ "| " + dealerHand.get(2).getSymbol() + "      |  " );
				System.out.println("| " + dealerHand.get(0).getSuit() + "      |  " 
						+ "| " + dealerHand.get(1).getSuit() + "      |  " 
						+ "| " + dealerHand.get(2).getSuit() + "      |  " );
				System.out.println("|         |  " + "|         |  " + "|         |  " );
				System.out.println("|       " + dealerHand.get(0).getSymbol() + " |  " 
						+ "|       " + dealerHand.get(1).getSymbol() + " |  " 
						+ "|      " + dealerHand.get(2).getSymbol() );
				System.out.println("|      " + dealerHand.get(0).getSuit() + " |  " 
						+ "|      " + dealerHand.get(1).getSuit() + " |  " 
						+ "|      " + dealerHand.get(2).getSuit() + " |  " );
				System.out.println("-----------  " + "-----------  " + "-----------  " );
				
				// second row of cards: 1 card with no 10s
				System.out.println("-----------");
				System.out.println("| " + dealerHand.get(3).getSymbol() + "       |" );
				System.out.println("| " + dealerHand.get(3).getSuit() + "      |" );
				System.out.println("|         |");
				System.out.println("|       " + dealerHand.get(3).getSymbol() + " |" );
				System.out.println("|      " + dealerHand.get(3).getSuit() + " |" );
				System.out.println("-----------");
			}
			else if( dealerHand.get(3).getSymbol() == "10" )
			{
				// first row of cards, no 10s
				System.out.println("-----------  " + "-----------  " + "-----------  ");
				System.out.println("| " + dealerHand.get(0).getSymbol() + "       |  " 
						+ "| " + dealerHand.get(1).getSymbol() + "       |  " 
						+ "| " + dealerHand.get(2).getSymbol() + "       |  " );
				System.out.println("| " + dealerHand.get(0).getSuit() + "      |  " 
						+ "| " + dealerHand.get(1).getSuit() + "      |  " + "| " 
						+ dealerHand.get(2).getSuit() + "      |  " );
				System.out.println("|         |  " + "|         |  " + "|         |  " );
				System.out.println("|       " + dealerHand.get(0).getSymbol() + " |  " 
						+ "|       " + dealerHand.get(1).getSymbol() + " |  " 
						+ "|       " + dealerHand.get(2).getSymbol() + " |  " );
				System.out.println("|      " + dealerHand.get(0).getSuit() + " |  " 
						+ "|      " + dealerHand.get(1).getSuit() + " |  " 
						+ "|      " + dealerHand.get(2).getSuit() + " |  " );
				System.out.println("-----------  " + "-----------  " + "-----------  " );
				
				// second row of cards, with 10s
				System.out.println("-----------");
				System.out.println("| " + dealerHand.get(3).getSymbol() + "      |" );
				System.out.println("| " + dealerHand.get(3).getSuit() + "      |" );
				System.out.println("|         |");
				System.out.println("|      " + dealerHand.get(3).getSymbol() + " |" );
				System.out.println("|      " + dealerHand.get(3).getSuit() + " |" );
				System.out.println("-----------");
			}
			else
			{
				// first row of cards, no 10s
				System.out.println("-----------  " + "-----------  " + "-----------  ");
				System.out.println("| " + dealerHand.get(0).getSymbol() + "       |  " 
						+ "| " + dealerHand.get(1).getSymbol() + "       |  " 
						+ "| " + dealerHand.get(2).getSymbol() + "       |  " );
				System.out.println("| " + dealerHand.get(0).getSuit() + "      |  " 
						+ "| " + dealerHand.get(1).getSuit() + "      |  " + "| " 
						+ dealerHand.get(2).getSuit() + "      |  " );
				System.out.println("|         |  " + "|         |  " + "|         |  " );
				System.out.println("|       " + dealerHand.get(0).getSymbol() + " |  " 
						+ "|       " + dealerHand.get(1).getSymbol() + " |  " 
						+ "|       " + dealerHand.get(2).getSymbol() + " |  " );
				System.out.println("|      " + dealerHand.get(0).getSuit() + " |  " 
						+ "|      " + dealerHand.get(1).getSuit() + " |  " 
						+ "|      " + dealerHand.get(2).getSuit() + " |  " );
				System.out.println("-----------  " + "-----------  " + "-----------  " );
				
				// second row of cards, no 10s
				System.out.println("-----------");
				System.out.println("| " + dealerHand.get(3).getSymbol() + "       |" );
				System.out.println("| " + dealerHand.get(3).getSuit() + "      |" );
				System.out.println("|         |");
				System.out.println("|       " + dealerHand.get(3).getSymbol() + " |" );
				System.out.println("|      " + dealerHand.get(3).getSuit() + " |" );
				System.out.println("-----------");
			}
			break;
		case 5:
			if( dealerHand.get(0).getSymbol() == "10" )
			{
				// first row of cards: 3 cards with 10 on the left
				System.out.println("-----------  " + "-----------  " + "-----------  " );
				System.out.println("| " + dealerHand.get(0).getSymbol() + "      |  " 
						+ "| " + dealerHand.get(1).getSymbol() + "       |  " 
						+ "| " + dealerHand.get(2).getSymbol() + "       |  " );
				System.out.println("| " + dealerHand.get(0).getSuit() + "      |  " 
						+ "| " + dealerHand.get(1).getSuit() + "      |  " 
						+ "| " + dealerHand.get(2).getSuit() + "      |  " );
				System.out.println("|         |  " + "|         |  " + "|         |  " );
				System.out.println("|      " + dealerHand.get(0).getSymbol() + " |  " 
						+ "|       " + dealerHand.get(1).getSymbol() + " |  " 
						+ "|       " + dealerHand.get(2).getSymbol() + " |  " );
				System.out.println("|      " + dealerHand.get(0).getSuit() + " |  " 
						+ "|      " + dealerHand.get(1).getSuit() + " |  " 
						+ "|      " + dealerHand.get(2).getSuit() + " |  " );
				System.out.println("-----------  " + "-----------  " + "-----------  " );
				
				// second row of cards: 2 cards with no 10s
				System.out.println("-----------  " + "-----------  ");
				System.out.println("| " + dealerHand.get(3).getSymbol() + "       |  " 
						+ "| " + dealerHand.get(4).getSymbol() + "       |  " );
				System.out.println("| " + dealerHand.get(3).getSuit() + "      |  " 
						+ "| " + dealerHand.get(4).getSuit() + "      |  " );
				System.out.println("|         |  " + "|         |  " );
				System.out.println("|       " + dealerHand.get(3).getSymbol() + " |  " 
						+ "|       " + dealerHand.get(4).getSymbol() + " |  " );
				System.out.println("|      " + dealerHand.get(3).getSuit() + " |  " 
						+ "|      " + dealerHand.get(4).getSuit() + " |  " );
				System.out.println("-----------  " + "-----------  " );
			}
			else if( dealerHand.get(1).getSymbol() == "10" )
			{
				// first row of cards: 3 cards with 10 in the middle
				System.out.println("-----------  " + "-----------  " + "-----------  " );
				System.out.println("| " + dealerHand.get(0).getSymbol() + "       |  " 
						+ "| " + dealerHand.get(1).getSymbol() + "      |  " 
						+ "| " + dealerHand.get(2).getSymbol() + "       |  " );
				System.out.println("| " + dealerHand.get(0).getSuit() + "      |  " 
						+ "| " + dealerHand.get(1).getSuit() + "     |  " 
						+ "| " + dealerHand.get(2).getSuit() + "      |  " );
				System.out.println("|         |  " + "|         |  " + "|         |  " );
				System.out.println("|       " + dealerHand.get(0).getSymbol() + " |  " 
						+ "|      " + dealerHand.get(1).getSymbol() + " |  " 
						+ "|       " + dealerHand.get(2).getSymbol() + " |  " );
				System.out.println("|      " + dealerHand.get(0).getSuit() + " |  " 
						+ "|     " + dealerHand.get(1).getSuit() + "  |  " 
						+ "|      " + dealerHand.get(2).getSuit() + " |  " );
				System.out.println("-----------  " + "-----------  " + "-----------  " );
				
				// second row of cards: 2 cards with no 10s
				System.out.println("-----------  " + "-----------  ");
				System.out.println("| " + dealerHand.get(3).getSymbol() + "       |  " 
						+ "| " + dealerHand.get(4).getSymbol() + "       |  " );
				System.out.println("| " + dealerHand.get(3).getSuit() + "      |  " 
						+ "| " + dealerHand.get(4).getSuit() + "      |  " );
				System.out.println("|         |  " + "|         |  " );
				System.out.println("|       " + dealerHand.get(3).getSymbol() + " |  " 
						+ "|       " + dealerHand.get(4).getSymbol() + " |  " );
				System.out.println("|      " + dealerHand.get(3).getSuit() + " |  " 
						+ "|      " + dealerHand.get(4).getSuit() + " |  " );
				System.out.println("-----------  " + "-----------  " );
			}
			else if( dealerHand.get(2).getSymbol() == "10" )
			{
				// first row of cards: 3 cards with a 10 on the right
				System.out.println("-----------  " + "-----------  " + "-----------  " );
				System.out.println("| " + dealerHand.get(0).getSymbol() + "       |  " 
						+ "| " + dealerHand.get(1).getSymbol() + "       |  " 
						+ "| " + dealerHand.get(2).getSymbol() + "      |  " );
				System.out.println("| " + dealerHand.get(0).getSuit() + "      |  " 
						+ "| " + dealerHand.get(1).getSuit() + "      |  " 
						+ "| " + dealerHand.get(2).getSuit() + "      |  " );
				System.out.println("|         |  " + "|         |  " + "|         |  " );
				System.out.println("|       " + dealerHand.get(0).getSymbol() + " |  " 
						+ "|       " + dealerHand.get(1).getSymbol() + " |  " 
						+ "|      " + dealerHand.get(2).getSymbol() );
				System.out.println("|      " + dealerHand.get(0).getSuit() + " |  " 
						+ "|      " + dealerHand.get(1).getSuit() + " |  " 
						+ "|      " + dealerHand.get(2).getSuit() + " |  " );
				System.out.println("-----------  " + "-----------  " + "-----------  " );
				
				// second row of cards: 2 cards with no 10s
				System.out.println("-----------  " + "-----------  ");
				System.out.println("| " + dealerHand.get(3).getSymbol() + "       |  " 
						+ "| " + dealerHand.get(4).getSymbol() + "       |  " );
				System.out.println("| " + dealerHand.get(3).getSuit() + "      |  " 
						+ "| " + dealerHand.get(4).getSuit() + "      |  " );
				System.out.println("|         |  " + "|         |  " );
				System.out.println("|       " + dealerHand.get(3).getSymbol() + " |  " 
						+ "|       " + dealerHand.get(4).getSymbol() + " |  " );
				System.out.println("|      " + dealerHand.get(3).getSuit() + " |  " 
						+ "|      " + dealerHand.get(4).getSuit() + " |  " );
				System.out.println("-----------  " + "-----------  " );
			}
			else if( dealerHand.get(3).getSymbol() == "10" )
			{
				// first row of cards, no 10s
				System.out.println("-----------  " + "-----------  " + "-----------  ");
				System.out.println("| " + dealerHand.get(0).getSymbol() + "       |  " 
						+ "| " + dealerHand.get(1).getSymbol() + "       |  " 
						+ "| " + dealerHand.get(2).getSymbol() + "       |  " );
				System.out.println("| " + dealerHand.get(0).getSuit() + "      |  " 
						+ "| " + dealerHand.get(1).getSuit() + "      |  " + "| " 
						+ dealerHand.get(2).getSuit() + "      |  " );
				System.out.println("|         |  " + "|         |  " + "|         |  " );
				System.out.println("|       " + dealerHand.get(0).getSymbol() + " |  " 
						+ "|       " + dealerHand.get(1).getSymbol() + " |  " 
						+ "|       " + dealerHand.get(2).getSymbol() + " |  " );
				System.out.println("|      " + dealerHand.get(0).getSuit() + " |  " 
						+ "|      " + dealerHand.get(1).getSuit() + " |  " 
						+ "|      " + dealerHand.get(2).getSuit() + " |  " );
				System.out.println("-----------  " + "-----------  " + "-----------  " );
				
				// second row of cards: 2 cards with a 10 on the left
				System.out.println("-----------  " + "-----------  ");
				System.out.println("| " + dealerHand.get(3).getSymbol() + "      |  " 
						+ "| " + dealerHand.get(4).getSymbol() + "       |  " );
				System.out.println("| " + dealerHand.get(3).getSuit() + "      |  " 
						+ "| " + dealerHand.get(4).getSuit() + "      |  " );
				System.out.println("|         |  " + "|         |  " );
				System.out.println("|      " + dealerHand.get(3).getSymbol() + " |  " 
						+ "|       " + dealerHand.get(4).getSymbol() + " |  " );
				System.out.println("|      " + dealerHand.get(3).getSuit() + " |  " 
						+ "|      " + dealerHand.get(4).getSuit() + " |  " );
				System.out.println("-----------  " + "-----------  " );
			}
			else if( dealerHand.get(4).getSymbol() == "10" )
			{
				// first row of cards, no 10s
				System.out.println("-----------  " + "-----------  " + "-----------  ");
				System.out.println("| " + dealerHand.get(0).getSymbol() + "       |  " 
						+ "| " + dealerHand.get(1).getSymbol() + "       |  " 
						+ "| " + dealerHand.get(2).getSymbol() + "       |  " );
				System.out.println("| " + dealerHand.get(0).getSuit() + "      |  " 
						+ "| " + dealerHand.get(1).getSuit() + "      |  " + "| " 
						+ dealerHand.get(2).getSuit() + "      |  " );
				System.out.println("|         |  " + "|         |  " + "|         |  " );
				System.out.println("|       " + dealerHand.get(0).getSymbol() + " |  " 
						+ "|       " + dealerHand.get(1).getSymbol() + " |  " 
						+ "|       " + dealerHand.get(2).getSymbol() + " |  " );
				System.out.println("|      " + dealerHand.get(0).getSuit() + " |  " 
						+ "|      " + dealerHand.get(1).getSuit() + " |  " 
						+ "|      " + dealerHand.get(2).getSuit() + " |  " );
				System.out.println("-----------  " + "-----------  " + "-----------  " );
				
				// second row of cards: 2 cards with a 10 on the right
				System.out.println("-----------  " + "-----------  ");
				System.out.println("| " + dealerHand.get(3).getSymbol() + "       |  " 
						+ "| " + dealerHand.get(4).getSymbol() + "      |  " );
				System.out.println("| " + dealerHand.get(3).getSuit() + "      |  " 
						+ "| " + dealerHand.get(4).getSuit() + "     |  " );
				System.out.println("|         |  " + "|         |  " );
				System.out.println("|       " + dealerHand.get(3).getSymbol() + " |  " 
						+ "|      " + dealerHand.get(4).getSymbol() + " |  " );
				System.out.println("|      " + dealerHand.get(3).getSuit() + " |  " 
						+ "|     " + dealerHand.get(4).getSuit() + "  |  " );
				System.out.println("-----------  " + "-----------  " );
			}
			else
			{
				// first row of cards, no 10s
				System.out.println("-----------  " + "-----------  " + "-----------  ");
				System.out.println("| " + dealerHand.get(0).getSymbol() + "       |  " 
						+ "| " + dealerHand.get(1).getSymbol() + "       |  " 
						+ "| " + dealerHand.get(2).getSymbol() + "       |  " );
				System.out.println("| " + dealerHand.get(0).getSuit() + "      |  " 
						+ "| " + dealerHand.get(1).getSuit() + "      |  " + "| " 
						+ dealerHand.get(2).getSuit() + "      |  " );
				System.out.println("|         |  " + "|         |  " + "|         |  " );
				System.out.println("|       " + dealerHand.get(0).getSymbol() + " |  " 
						+ "|       " + dealerHand.get(1).getSymbol() + " |  " 
						+ "|       " + dealerHand.get(2).getSymbol() + " |  " );
				System.out.println("|      " + dealerHand.get(0).getSuit() + " |  " 
						+ "|      " + dealerHand.get(1).getSuit() + " |  " 
						+ "|      " + dealerHand.get(2).getSuit() + " |  " );
				System.out.println("-----------  " + "-----------  " + "-----------  " );
				
				// second row of cards, no 10s
				System.out.println("-----------  " + "-----------  ");
				System.out.println("| " + dealerHand.get(3).getSymbol() + "       |  " 
						+ "| " + dealerHand.get(4).getSymbol() + "       |  " );
				System.out.println("| " + dealerHand.get(3).getSuit() + "      |  " 
						+ "| " + dealerHand.get(4).getSuit() + "      |  " );
				System.out.println("|         |  " + "|         |  " );
				System.out.println("|       " + dealerHand.get(3).getSymbol() + " |  " 
						+ "|       " + dealerHand.get(4).getSymbol() + " |  " );
				System.out.println("|      " + dealerHand.get(3).getSuit() + " |  " 
						+ "|      " + dealerHand.get(4).getSuit() + " |  " );
				System.out.println("-----------  " + "-----------  " );
			}
			break;
		case 6:
			if( dealerHand.get(0).getSymbol() == "10" )
			{
				// first row of cards: 3 cards with 10 on the left
				System.out.println("-----------  " + "-----------  " + "-----------  " );
				System.out.println("| " + dealerHand.get(0).getSymbol() + "      |  " 
						+ "| " + dealerHand.get(1).getSymbol() + "       |  " 
						+ "| " + dealerHand.get(2).getSymbol() + "       |  " );
				System.out.println("| " + dealerHand.get(0).getSuit() + "      |  " 
						+ "| " + dealerHand.get(1).getSuit() + "      |  " 
						+ "| " + dealerHand.get(2).getSuit() + "      |  " );
				System.out.println("|         |  " + "|         |  " + "|         |  " );
				System.out.println("|      " + dealerHand.get(0).getSymbol() + " |  " 
						+ "|       " + dealerHand.get(1).getSymbol() + " |  " 
						+ "|       " + dealerHand.get(2).getSymbol() + " |  " );
				System.out.println("|      " + dealerHand.get(0).getSuit() + " |  " 
						+ "|      " + dealerHand.get(1).getSuit() + " |  " 
						+ "|      " + dealerHand.get(2).getSuit() + " |  " );
				System.out.println("-----------  " + "-----------  " + "-----------  " );
				
				// second row of cards: 3 cards with no 10s
				System.out.println("-----------  " + "-----------  " + "-----------  ");
				System.out.println("| " + dealerHand.get(3).getSymbol() + "       |  " 
						+ "| " + dealerHand.get(4).getSymbol() + "       |  " 
						+ "| " + dealerHand.get(5).getSymbol() + "       |  " );
				System.out.println("| " + dealerHand.get(3).getSuit() + "      |  " 
						+ "| " + dealerHand.get(4).getSuit() + "      |  " + "| " 
						+ dealerHand.get(5).getSuit() + "      |  " );
				System.out.println("|         |  " + "|         |  " + "|         |  " );
				System.out.println("|       " + dealerHand.get(3).getSymbol() + " |  " 
						+ "|       " + dealerHand.get(4).getSymbol() + " |  " 
						+ "|       " + dealerHand.get(5).getSymbol() + " |  " );
				System.out.println("|      " + dealerHand.get(3).getSuit() + " |  " 
						+ "|      " + dealerHand.get(4).getSuit() + " |  " 
						+ "|      " + dealerHand.get(5).getSuit() + " |  " );
				System.out.println("-----------  " + "-----------  " + "-----------  " );
			}
			else if( dealerHand.get(1).getSymbol() == "10" )
			{
				// first row of cards: 3 cards with 10 in the middle
				System.out.println("-----------  " + "-----------  " + "-----------  " );
				System.out.println("| " + dealerHand.get(0).getSymbol() + "       |  " 
						+ "| " + dealerHand.get(1).getSymbol() + "      |  " 
						+ "| " + dealerHand.get(2).getSymbol() + "       |  " );
				System.out.println("| " + dealerHand.get(0).getSuit() + "      |  " 
						+ "| " + dealerHand.get(1).getSuit() + "     |  " 
						+ "| " + dealerHand.get(2).getSuit() + "      |  " );
				System.out.println("|         |  " + "|         |  " + "|         |  " );
				System.out.println("|       " + dealerHand.get(0).getSymbol() + " |  " 
						+ "|      " + dealerHand.get(1).getSymbol() + " |  " 
						+ "|       " + dealerHand.get(2).getSymbol() + " |  " );
				System.out.println("|      " + dealerHand.get(0).getSuit() + " |  " 
						+ "|     " + dealerHand.get(1).getSuit() + "  |  " 
						+ "|      " + dealerHand.get(2).getSuit() + " |  " );
				System.out.println("-----------  " + "-----------  " + "-----------  " );
				
				// second row of cards: 3 cards with no 10s
				System.out.println("-----------  " + "-----------  " + "-----------  ");
				System.out.println("| " + dealerHand.get(3).getSymbol() + "       |  " 
						+ "| " + dealerHand.get(4).getSymbol() + "       |  " 
						+ "| " + dealerHand.get(5).getSymbol() + "       |  " );
				System.out.println("| " + dealerHand.get(3).getSuit() + "      |  " 
						+ "| " + dealerHand.get(4).getSuit() + "      |  " + "| " 
						+ dealerHand.get(5).getSuit() + "      |  " );
				System.out.println("|         |  " + "|         |  " + "|         |  " );
				System.out.println("|       " + dealerHand.get(3).getSymbol() + " |  " 
						+ "|       " + dealerHand.get(4).getSymbol() + " |  " 
						+ "|       " + dealerHand.get(5).getSymbol() + " |  " );
				System.out.println("|      " + dealerHand.get(3).getSuit() + " |  " 
						+ "|      " + dealerHand.get(4).getSuit() + " |  " 
						+ "|      " + dealerHand.get(5).getSuit() + " |  " );
				System.out.println("-----------  " + "-----------  " + "-----------  " );
			}
			else if( dealerHand.get(2).getSymbol() == "10" )
			{
				// first row of cards: 3 cards with a 10 on the right
				System.out.println("-----------  " + "-----------  " + "-----------  " );
				System.out.println("| " + dealerHand.get(0).getSymbol() + "       |  " 
						+ "| " + dealerHand.get(1).getSymbol() + "       |  " 
						+ "| " + dealerHand.get(2).getSymbol() + "      |  " );
				System.out.println("| " + dealerHand.get(0).getSuit() + "      |  " 
						+ "| " + dealerHand.get(1).getSuit() + "      |  " 
						+ "| " + dealerHand.get(2).getSuit() + "      |  " );
				System.out.println("|         |  " + "|         |  " + "|         |  " );
				System.out.println("|       " + dealerHand.get(0).getSymbol() + " |  " 
						+ "|       " + dealerHand.get(1).getSymbol() + " |  " 
						+ "|      " + dealerHand.get(2).getSymbol() );
				System.out.println("|      " + dealerHand.get(0).getSuit() + " |  " 
						+ "|      " + dealerHand.get(1).getSuit() + " |  " 
						+ "|      " + dealerHand.get(2).getSuit() + " |  " );
				System.out.println("-----------  " + "-----------  " + "-----------  " );
				
				// second row of cards: 3 cards with no 10s
				System.out.println("-----------  " + "-----------  " + "-----------  ");
				System.out.println("| " + dealerHand.get(3).getSymbol() + "       |  " 
						+ "| " + dealerHand.get(4).getSymbol() + "       |  " 
						+ "| " + dealerHand.get(5).getSymbol() + "       |  " );
				System.out.println("| " + dealerHand.get(3).getSuit() + "      |  " 
						+ "| " + dealerHand.get(4).getSuit() + "      |  " + "| " 
						+ dealerHand.get(5).getSuit() + "      |  " );
				System.out.println("|         |  " + "|         |  " + "|         |  " );
				System.out.println("|       " + dealerHand.get(3).getSymbol() + " |  " 
						+ "|       " + dealerHand.get(4).getSymbol() + " |  " 
						+ "|       " + dealerHand.get(5).getSymbol() + " |  " );
				System.out.println("|      " + dealerHand.get(3).getSuit() + " |  " 
						+ "|      " + dealerHand.get(4).getSuit() + " |  " 
						+ "|      " + dealerHand.get(5).getSuit() + " |  " );
				System.out.println("-----------  " + "-----------  " + "-----------  " );
			}
			else if( dealerHand.get(3).getSymbol() == "10" )
			{
				// first row of cards, no 10s
				System.out.println("-----------  " + "-----------  " + "-----------  ");
				System.out.println("| " + dealerHand.get(0).getSymbol() + "       |  " 
						+ "| " + dealerHand.get(1).getSymbol() + "       |  " 
						+ "| " + dealerHand.get(2).getSymbol() + "       |  " );
				System.out.println("| " + dealerHand.get(0).getSuit() + "      |  " 
						+ "| " + dealerHand.get(1).getSuit() + "      |  " + "| " 
						+ dealerHand.get(2).getSuit() + "      |  " );
				System.out.println("|         |  " + "|         |  " + "|         |  " );
				System.out.println("|       " + dealerHand.get(0).getSymbol() + " |  " 
						+ "|       " + dealerHand.get(1).getSymbol() + " |  " 
						+ "|       " + dealerHand.get(2).getSymbol() + " |  " );
				System.out.println("|      " + dealerHand.get(0).getSuit() + " |  " 
						+ "|      " + dealerHand.get(1).getSuit() + " |  " 
						+ "|      " + dealerHand.get(2).getSuit() + " |  " );
				System.out.println("-----------  " + "-----------  " + "-----------  " );
				
				// second row of cards: 3 cards with a 10 on the left
				System.out.println("-----------  " + "-----------  " + "-----------  " );
				System.out.println("| " + dealerHand.get(3).getSymbol() + "      |  " 
						+ "| " + dealerHand.get(4).getSymbol() + "       |  " 
						+ "| " + dealerHand.get(5).getSymbol() + "       |  " );
				System.out.println("| " + dealerHand.get(3).getSuit() + "      |  " 
						+ "| " + dealerHand.get(4).getSuit() + "      |  " 
						+ "| " + dealerHand.get(5).getSuit() + "      |  " );
				System.out.println("|         |  " + "|         |  " + "|         |  " );
				System.out.println("|      " + dealerHand.get(3).getSymbol() + " |  " 
						+ "|       " + dealerHand.get(4).getSymbol() + " |  " 
						+ "|       " + dealerHand.get(5).getSymbol() + " |  " );
				System.out.println("|      " + dealerHand.get(3).getSuit() + " |  " 
						+ "|      " + dealerHand.get(4).getSuit() + " |  " 
						+ "|      " + dealerHand.get(5).getSuit() + " |  " );
				System.out.println("-----------  " + "-----------  " + "-----------  " );
			}
			else if( dealerHand.get(4).getSymbol() == "10" )
			{
				// first row of cards, no 10s
				System.out.println("-----------  " + "-----------  " + "-----------  ");
				System.out.println("| " + dealerHand.get(0).getSymbol() + "       |  " 
						+ "| " + dealerHand.get(1).getSymbol() + "       |  " 
						+ "| " + dealerHand.get(2).getSymbol() + "       |  " );
				System.out.println("| " + dealerHand.get(0).getSuit() + "      |  " 
						+ "| " + dealerHand.get(1).getSuit() + "      |  " + "| " 
						+ dealerHand.get(2).getSuit() + "      |  " );
				System.out.println("|         |  " + "|         |  " + "|         |  " );
				System.out.println("|       " + dealerHand.get(0).getSymbol() + " |  " 
						+ "|       " + dealerHand.get(1).getSymbol() + " |  " 
						+ "|       " + dealerHand.get(2).getSymbol() + " |  " );
				System.out.println("|      " + dealerHand.get(0).getSuit() + " |  " 
						+ "|      " + dealerHand.get(1).getSuit() + " |  " 
						+ "|      " + dealerHand.get(2).getSuit() + " |  " );
				System.out.println("-----------  " + "-----------  " + "-----------  " );
				
				// second row of cards: 3 cards with 10 in the middle
				System.out.println("-----------  " + "-----------  " + "-----------  " );
				System.out.println("| " + dealerHand.get(3).getSymbol() + "       |  " 
						+ "| " + dealerHand.get(4).getSymbol() + "      |  " 
						+ "| " + dealerHand.get(5).getSymbol() + "       |  " );
				System.out.println("| " + dealerHand.get(3).getSuit() + "      |  " 
						+ "| " + dealerHand.get(4).getSuit() + "     |  " 
						+ "| " + dealerHand.get(5).getSuit() + "      |  " );
				System.out.println("|         |  " + "|         |  " + "|         |  " );
				System.out.println("|       " + dealerHand.get(3).getSymbol() + " |  " 
						+ "|      " + dealerHand.get(4).getSymbol() + " |  " 
						+ "|       " + dealerHand.get(5).getSymbol() + " |  " );
				System.out.println("|      " + dealerHand.get(3).getSuit() + " |  " 
						+ "|     " + dealerHand.get(4).getSuit() + "  |  " 
						+ "|      " + dealerHand.get(5).getSuit() + " |  " );
				System.out.println("-----------  " + "-----------  " + "-----------  " );
			}
			else if( dealerHand.get(5).getSymbol() == "10" )
			{
				// first row of cards, no 10s
				System.out.println("-----------  " + "-----------  " + "-----------  ");
				System.out.println("| " + dealerHand.get(0).getSymbol() + "       |  " 
						+ "| " + dealerHand.get(1).getSymbol() + "       |  " 
						+ "| " + dealerHand.get(2).getSymbol() + "       |  " );
				System.out.println("| " + dealerHand.get(0).getSuit() + "      |  " 
						+ "| " + dealerHand.get(1).getSuit() + "      |  " + "| " 
						+ dealerHand.get(2).getSuit() + "      |  " );
				System.out.println("|         |  " + "|         |  " + "|         |  " );
				System.out.println("|       " + dealerHand.get(0).getSymbol() + " |  " 
						+ "|       " + dealerHand.get(1).getSymbol() + " |  " 
						+ "|       " + dealerHand.get(2).getSymbol() + " |  " );
				System.out.println("|      " + dealerHand.get(0).getSuit() + " |  " 
						+ "|      " + dealerHand.get(1).getSuit() + " |  " 
						+ "|      " + dealerHand.get(2).getSuit() + " |  " );
				System.out.println("-----------  " + "-----------  " + "-----------  " );
				
				// second row of cards: 3 cards with a 10 on the right
				System.out.println("-----------  " + "-----------  " + "-----------  " );
				System.out.println("| " + dealerHand.get(3).getSymbol() + "       |  " 
						+ "| " + dealerHand.get(4).getSymbol() + "       |  " 
						+ "| " + dealerHand.get(5).getSymbol() + "      |  " );
				System.out.println("| " + dealerHand.get(3).getSuit() + "      |  " 
						+ "| " + dealerHand.get(4).getSuit() + "      |  " 
						+ "| " + dealerHand.get(5).getSuit() + "      |  " );
				System.out.println("|         |  " + "|         |  " + "|         |  " );
				System.out.println("|       " + dealerHand.get(3).getSymbol() + " |  " 
						+ "|       " + dealerHand.get(4).getSymbol() + " |  " 
						+ "|      " + dealerHand.get(5).getSymbol() );
				System.out.println("|      " + dealerHand.get(3).getSuit() + " |  " 
						+ "|      " + dealerHand.get(4).getSuit() + " |  " 
						+ "|      " + dealerHand.get(5).getSuit() + " |  " );
				System.out.println("-----------  " + "-----------  " + "-----------  " );
			}
			else
			{
				// first row of cards, 3 cards no 10s
				System.out.println("-----------  " + "-----------  " + "-----------  ");
				System.out.println("| " + dealerHand.get(0).getSymbol() + "       |  " 
						+ "| " + dealerHand.get(1).getSymbol() + "       |  " 
						+ "| " + dealerHand.get(2).getSymbol() + "       |  " );
				System.out.println("| " + dealerHand.get(0).getSuit() + "      |  " 
						+ "| " + dealerHand.get(1).getSuit() + "      |  " + "| " 
						+ dealerHand.get(2).getSuit() + "      |  " );
				System.out.println("|         |  " + "|         |  " + "|         |  " );
				System.out.println("|       " + dealerHand.get(0).getSymbol() + " |  " 
						+ "|       " + dealerHand.get(1).getSymbol() + " |  " 
						+ "|       " + dealerHand.get(2).getSymbol() + " |  " );
				System.out.println("|      " + dealerHand.get(0).getSuit() + " |  " 
						+ "|      " + dealerHand.get(1).getSuit() + " |  " 
						+ "|      " + dealerHand.get(2).getSuit() + " |  " );
				System.out.println("-----------  " + "-----------  " + "-----------  " );
				
				// second row of cards, 3 cards no 10s
				System.out.println("-----------  " + "-----------  " + "-----------  ");
				System.out.println("| " + dealerHand.get(3).getSymbol() + "       |  " 
						+ "| " + dealerHand.get(4).getSymbol() + "       |  " 
						+ "| " + dealerHand.get(5).getSymbol() + "       |  " );
				System.out.println("| " + dealerHand.get(3).getSuit() + "      |  " 
						+ "| " + dealerHand.get(4).getSuit() + "      |  " + "| " 
						+ dealerHand.get(5).getSuit() + "      |  " );
				System.out.println("|         |  " + "|         |  " + "|         |  " );
				System.out.println("|       " + dealerHand.get(3).getSymbol() + " |  " 
						+ "|       " + dealerHand.get(4).getSymbol() + " |  " 
						+ "|       " + dealerHand.get(5).getSymbol() + " |  " );
				System.out.println("|      " + dealerHand.get(3).getSuit() + " |  " 
						+ "|      " + dealerHand.get(4).getSuit() + " |  " 
						+ "|      " + dealerHand.get(5).getSuit() + " |  " );
				System.out.println("-----------  " + "-----------  " + "-----------  " );
			}
			break;
		default:
			break;
		} // end of switch statement
	} // end of method showFullDealerHand()
} // end of class BlackjackGameSimulator