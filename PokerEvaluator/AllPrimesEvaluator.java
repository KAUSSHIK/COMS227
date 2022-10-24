package hw4;

import api.Card;

/**
 * Evaluator for a hand in which the rank of each card is a prime number.
 * The number of cards required is equal to the hand size. 
 * 
 * The name of this evaluator is "All Primes".
 */
//Note: You must edit this declaration to extend AbstractEvaluator
//or to extend some other class that extends AbstractEvaluator
public class AllPrimesEvaluator extends AbstractEvaluator
{
  /**
   * Constructs the evaluator.
   * @param ranking
   *   ranking of this hand
   * @param handSize
   *   number of cards in a hand
   */
  public AllPrimesEvaluator(int ranking, int handSize)
  {
	  super(ranking, handSize);
  }

public String getName() {
	return "All Primes";
}
/**
 * A Private helper method that checks if a given integer i is a prime number or not
 * @param i
 * @return true or false
 */
private boolean isPrime(int i) {
	if (i <= 1) {
		return false;
	}
    for (int j = 2; j < i; j++) {
    	if (i % j == 0) {
    		return false;
    	}
    }
    return true;
}

public boolean canSatisfy(Card[] mainCards) {
	boolean result = true;
	for(int i = 0; i < mainCards.length; i++) {
		if(isPrime(mainCards[i].getRank())) {
			result = true;
		}
		else {
			result = false;
			return result;
		}
	}
	return result;
}

  
}
