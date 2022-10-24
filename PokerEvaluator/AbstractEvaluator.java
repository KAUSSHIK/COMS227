package hw4;
import java.util.ArrayList;

import api.Card;
import api.Hand;
import api.IEvaluator;
import util.SubsetFinder;

/**
 * The class AbstractEvaluator includes common code for all evaluator types.
 * @author kausshik
 * My base class is : AbstractEvaluator ---> implements IEvaluator
 * There is another abstarct class called AbstractOfAKind ---> extends AbstractEvaluator
 * Classes that extend ABSTRACT OF A KIND : OnePairEvaluator, ThreeOfAKind, and FourOfAKind
 *   Reason to exetnd From AbstractOfAKind and not directly AbstractEvaluator is becuase the canSatisfy(), createHand(), and getBestHand() methods
 *   of these classes are very similar to each other than to the same methods of the other classes
 */

public abstract class AbstractEvaluator implements IEvaluator {
	/**
	 * Tracks the handSize of the current Evaluator
	 */
	private int handSize;
	/**
	 * Tracks the card ranking (priority) of the current Evaluator
	 */
	private int ranking;
	/**
	 * Tells us the minimum number of cards required to form a hand in the porker game
	 */
	private int cardsRequired;
	
	protected AbstractEvaluator(int ranking, int handSize) {
		this.handSize = handSize;
		this.ranking = ranking;
		this.cardsRequired = handSize;
	}
	
	public int getRanking() {
		return ranking;
	}
	
	public int handSize() {
		return handSize;
	}
	
	public int cardsRequired() {
		return cardsRequired;
	}
	
	public boolean canSubsetSatisfy(Card[] allCards) {
		ArrayList<int[]> subsets = SubsetFinder.findSubsets(allCards.length, cardsRequired());
		for (int i = 0; i < subsets.size(); ++i)
	    {
	      int[] subset = subsets.get(i);
	      Card[] tempMain = new Card[cardsRequired()];
	      for(int j = 0; j < cardsRequired(); j++) {
	    	  tempMain[j] = allCards[subset[j]];
	      }
	      if(canSatisfy(tempMain)) {
	    	  return true;
	      }
	    }
		return false;
	}
	
	public Hand createHand(Card[] allCards, int[] subset) {
		if(!canSatisfy(allCards)) {
	    	return null;
	    }
		Hand hand = new Hand(allCards, null, this);
		return hand;
	}
	
	public Hand getBestHand(Card[] allCards) {
		return this.createHand(allCards, null);
	}
  
}
