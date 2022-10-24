package hw4;


import api.Card;
/**
 * Evaluator for a hand consisting of a "straight" in which the
 * card ranks are consecutive numbers.  The number of required 
 * cards is equal to the hand size.  An ace (card of rank 1) 
 * may be treated as the highest possible card or as the lowest
 * (not both).  To evaluate a straight containing an ace it is
 * necessary to know what the highest card rank will be in a
 * given game; therefore, this value must be specified when the
 * evaluator is constructed.  In a hand created by this
 * evaluator the cards are listed in descending order with high 
 * card first, e.g. [10 9 8 7 6] or [A K Q J 10], with
 * one exception: In case of an ace-low straight, the ace
 * must appear last, as in [5 4 3 2 A]
 * 
 * The name of this evaluator is "Straight".
 */
public class StraightEvaluator extends AbstractEvaluator
{  /**
	 * Tells us the maximum rank of the card that appears in the hand, deterimes if ACE is used to complete a high or a low straight
	 */
	private int maxCardRank;
  /**
   * Constructs the evaluator. Note that the maximum rank of
   * the cards to be used must be specified in order to 
   * correctly evaluate a straight with ace high.
   * @param ranking
   *   ranking of this hand
   * @param handSize
   *   number of cards in a hand
   * @param maxCardRank
   *   largest rank of any card to be used
   */
  public StraightEvaluator(int ranking, int handSize, int maxCardRank)
  {
	  super(ranking, handSize);
	  this.maxCardRank = maxCardRank;
  }
public String getName() {
	return "Straight";
}


//This method was made taking into account all the possible abnormalities, that occur when aces are present
public boolean canSatisfy(Card[] mainCards) {
	boolean result = true;
	if(maxCardRank != 1) {
		for(int i = 1; i < mainCards.length - 1; i++) {
			if(mainCards[i].getRank() - mainCards[i+1].getRank() == 1) {
				result = true;
			}
			else {
				result = false;
				return result;
			}
		}
		if(mainCards[0].getRank() == 1) {
			if(mainCards[mainCards.length - 1].getRank() - mainCards[0].getRank() == 1) {
				result = true;
			}
			else {
				result = false;
				return result;
			}
		}
		else {
			if(mainCards[0].getRank() - mainCards[1].getRank() == 1) {
				result = true;
			}
			else {
				result = false;
				return result;
			}
		}
	}
	else {
		for(int i = 1; i < mainCards.length - 1; i++) {
			if((mainCards[i].getRank() - mainCards[i+1].getRank() == 1) && (mainCards[1].getRank() == 13)) {
				result = true;
			}
			else {
				result = false;
				break;
			}
		}
	}
	return result;
}  
}
