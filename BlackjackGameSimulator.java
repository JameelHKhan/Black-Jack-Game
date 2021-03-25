/* 
 * Jameel. H. Khan 
 * Mini-Project 2 - Blackjack Game
 * EN.605.201.83.SP21
 */

// import required package, will use ArrayList and Collections
import java.util.*;
import java.util.Scanner;

public class BlackjackGameSimulator
{
	public static void main( String [] args )
	{
		// initialize variables
		ArrayList<Card> deck = new ArrayList<Card>();
		int userChoice;
		float moneyPot = 0;
		float bet;
		ArrayList<Card> playerHand = new ArrayList<Card>();
		ArrayList<Card> dealerHand = new ArrayList<Card>();
		int playerPointTotal = 0;
		int dealerPointTotal = 0;
		int numOfPlayerCards = 0;
		int numOfDealerCards = 0;
		int round = 0;		// keeps track of number of games played
		String hitOrStay;
		
		// Scanner to obtain user input
		Scanner inputChoice = new Scanner( System.in );
		
		System.out.println( "Welcome to Blackjack!" );
		
		do
		{
			// read input
			System.out.println( "Please choose from the following menu" );
			System.out.println( "1 - Play Blackjack" );
			System.out.println( "2 - Exit game" );
			System.out.print( "Enter your selection: ");
			userChoice = inputChoice.nextInt();
			
			if( userChoice == 1 )
			{
				Scanner inputPot = new Scanner( System.in );
				Scanner inputBet = new Scanner( System.in );
				Scanner playerAction = new Scanner( System.in );
				System.out.print( "Enter the amount of money you'd like to add to your pot: $" );
				moneyPot = inputPot.nextFloat();
				
				// validity test for moneyPot input
				do
				{
					if( moneyPot <= 0 )
					{
						System.out.println( "Invalid entry! Please enter the amount of money you'd like to add to your pot: $" );
						moneyPot = inputPot.nextFloat();
					}
				}
				while( moneyPot <= 0 );
				
				// the following do-while loop contains the code for actual game play, iterated per round
				do
				{
					System.out.println();
					System.out.println( "Round " + round + "!" );
					System.out.print( "Enter your bet: $" );
					bet = inputBet.nextFloat();
					
					// validity test for bet input to ensure a dollar amount greater than $0.00 and no more than the value of the player's moneyPot is entered
					do
					{
						if( bet > moneyPot )
						{
							System.out.println( "Invalid entry! You can't bet more money that what you actually have! ");
							System.out.println( "You current have $" + moneyPot + " in your pot." );
							System.out.println();
							System.out.println( "Enter your bet: $" );
							bet = inputBet.nextFloat();
						}
						else if( bet <= 0 )
						{
							System.out.println( "Invalid entry! In order to play, you must bet an amount of money, between $0.01 and $" + moneyPot + ". Enter your bet: $");
							bet = inputBet.nextFloat();
						}
					}
					while ( bet > moneyPot | bet <= 0 );
					
					stackDeck(deck);
					
					// create a shuffleDeck method for the next four lines of code
					Collections.shuffle(deck);
					System.out.println();
					System.out.println( "Shuffling the deck..." );
					System.out.println();
					
					dealPlayerCard( deck, playerHand );
					dealPlayerCard( deck, playerHand );
					showPlayerHand( playerPointTotal, playerHand, numOfPlayerCards );
					
					if( round == 1 & playerPointTotal == 21 )
					{
						System.out.println( "You hit Blackjack with 21 points! You win!!" );
						// turn the next four lines into a method playerWins() ?
						moneyPot += bet;
						System.out.println( "You won $" + bet + "! Your pot now has $" + moneyPot );
						playerHand.clear();
						playerPointTotal = 0;
						round += 1;
					}
					else
					{
						dealDealerCard( dealerPointTotal, deck, dealerHand );
						dealDealerCard( dealerPointTotal, deck, dealerHand );
						showDealerHand( dealerHand, numOfDealerCards );
						System.out.println();
						System.out.print( "Do you hit or stay? (hit/stay):" );
						hitOrStay = playerAction.nextLine().toLowerCase();
						
						// input validation
						while( hitOrStay != "hit" | hitOrStay != "stay" )
						{
							System.out.println();
							System.out.print( "Invalid entry! Please type 'hit' for another card or 'stay' to continue: " );
							hitOrStay = playerAction.nextLine().toLowerCase();
						}
						
						// player hits
						do
						{
							dealPlayerCard( deck, playerHand );
							showDealerHand( dealerHand, numOfDealerCards );
							showPlayerHand( playerPointTotal, playerHand, numOfPlayerCards );
							if( playerPointTotal > 21)
							{
								System.out.println();
								System.out.print( "You bust with " + playerPointTotal + " points. You lose!" );
								// turn the next four lines into a method playerLoses()
								moneyPot -= bet;
								System.out.println( "You lost $" + bet + "! Your pot now has $" + moneyPot );
								playerHand.clear();
								playerPointTotal = 0;
								round += 1;
							}
							else
							{
								System.out.println();
								System.out.print( "Do you hit or stay? (hit/stay):" );
								hitOrStay = playerAction.nextLine().toLowerCase();
							}
						}
						while( hitOrStay == "hit" );
						
						
						// player stays, it is now the dealer's turn
						do
						{
							dealDealerCard( dealerPointTotal, deck, dealerHand );
							
							// if the dealer gets over 21 points while taking hits, the player wins
							if( dealerPointTotal > 21 )
							{
								System.out.println();
							}
						}
						while( dealerPointTotal < 17 );
						
						
					}
				}
				while( moneyPot > 0 );
			}
			else if( userChoice == 2 )
			{
				System.out.println( "Thank you for playing, goodbye!" );
			}
			else
			{
				System.out.println( "Invalid selection! Please try again." );
			} // end of if statement
		}
		while( userChoice != 2 );
		

		// System.out.println(twoDiamond.getPointValue());
		// System.out.println(twoDiamond.getSuit());
		// System.out.println(twoDiamond.getSymbol());
		
	} // end of main method
	
	/*
	 * 
	 */
	public static void stackDeck( ArrayList<Card> deck )
	{
		deck.clear();
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
	} // end of method stackDeck()
	
	/*
	 * // deal player card
				// generate random number to select first card from deck (based on ArrayList elements)
				// Assign selected card to player hand array then remove selected card from deck
	 */
	public static void dealPlayerCard( ArrayList<Card> deck, ArrayList<Card> playerHand )
	{
		Random rand = new Random();
		int randomNumber = rand.nextInt(deck.size());
		playerHand.add(deck.get(randomNumber));
		deck.remove(randomNumber);
	}
	
	/*
	 * 
	 */
	public static void dealDealerCard( int dealerPointTotal, ArrayList<Card> deck, ArrayList<Card> dealerHand )
	{
		// calculate point total
		for( int i = 0; i < dealerHand.size(); i++ )
		{
			dealerPointTotal += dealerHand.get(i).getPointValue();
		}
		
		// add one card to dealer hand
		Random rand = new Random();
		int randomNumber = rand.nextInt(deck.size());
		dealerHand.add(deck.get(randomNumber));
		deck.remove(randomNumber);
	}
	
	/*
	 * 
	 */
	public static void showPlayerHand( int playerPointTotal, ArrayList<Card> playerHand, int numOfPlayerCards )
	{
		// calculate point total
		for( int i = 0; i < playerHand.size(); i++ )
		{
			playerPointTotal += playerHand.get(i).getPointValue();
		}
		
		// display output
		numOfPlayerCards = playerHand.size();
		System.out.println();
		System.out.println( "You have " + playerPointTotal + " points." );
		switch( numOfPlayerCards )
		{
		case 1:
			if( playerHand.get(0).getSymbol() == "10" )
			{
				// first row of cards: 1 card of 10
				System.out.println("-----------");
				System.out.println("| " + playerHand.get(0).getSymbol() + "      |" );
				System.out.println("| " + playerHand.get(0).getSuit() + "      |" );
				System.out.println("|         |");
				System.out.println("|      " + playerHand.get(0).getSymbol() + " |" );
				System.out.println("|      " + playerHand.get(0).getSuit() + " |" );
				System.out.println("-----------");
			}
			else
			{
				// first row of cards: no 10 card
				System.out.println("-----------");
				System.out.println("| " + playerHand.get(0).getSymbol() + "       |" );
				System.out.println("| " + playerHand.get(0).getSuit() + "      |" );
				System.out.println("|         |");
				System.out.println("|       " + playerHand.get(0).getSymbol() + " |" );
				System.out.println("|      " + playerHand.get(0).getSuit() + " |" );
				System.out.println("-----------");
			}
			break;
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
				
				// next row of cards
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
				
				// next row of cards
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
		}
		/*for( int i = 0; i < playerHand.size(); i++ )
		{
			System.out.println("----------");
			System.out.println("| " + playerHand.get(i).getSymbol() + "      |" );
			System.out.println("| " + playerHand.get(i).getSuit() + "     |" );
			System.out.println("|        |");
			System.out.println("|      " + playerHand.get(i).getSymbol() + " |" );
			System.out.println("|     " + playerHand.get(i).getSuit() + " |" );
			System.out.println("----------");
		}*/
	}
	
	/*
	 * 
	 */
	public static void showDealerHand( ArrayList<Card> dealerHand, int numOfDealerCards )
	{
		
	}
} // end of BlackjackGameSimulator class
