package hw4;


import api.Card;
import api.Hand;

/**
 * Evaluator for a generalized full house.  The number of required
 * cards is equal to the hand size.  If the hand size is an odd number
 * n, then there must be (n / 2) + 1 cards of the matching rank and the
 * remaining (n / 2) cards must be of matching rank. In this case, when constructing
 * a hand, the larger group must be listed first even if of lower rank
 * than the smaller group</strong> (e.g. as [3 3 3 5 5] rather than [5 5 3 3 3]).
 * If the hand size is even, then half the cards must be of matching 
 * rank and the remaining half of matching rank.  Any group of cards,
 * all of which are the same rank, always satisfies this
 * evaluator.
 * 
 * The name of this evaluator is "Full House".
 */
public class FullHouseEvaluator extends AbstractEvaluator
{
  /**
   * Constructs the evaluator.
   * @param ranking
   *   ranking of this hand
   * @param handSize
   *   number of cards in a hand
   */
  public FullHouseEvaluator(int ranking, int handSize)
  {
	  super(ranking, handSize);
  }

public String getName() {
	return "Full House";
}


/**
 * Private helper method that slices a Card array given the start and send index
 * @param arr - required Card array
 * @param start - start index
 * @param end -  end index
 * @return slice of the array
 */
private static Card[] Slice(Card[] arr, int start, int end)
{
	Card[] slice = new Card[end - start];

	for (int i = 0; i < slice.length; i++) {
		slice[i] = arr[start + i];
	}
return slice;
}

/**
 * Checks if all the cards in the array have the same 
 * @param cards
 * @return true or false
 */
private boolean nOfSameRank(Card[] cards) {
	boolean result = true;
	int rank = cards[0].getRank();
	for(int i = 0; i < cards.length; i++) {
		if(cards[i].getRank() == rank) {
			result = true;
		}
		else {
			result = false;
			return result;
		}
	}
	return result;
}

/**
 * Checks to see if the given array mainCards staisfies the condition of FullHouseEvaluator
 * we are basing this off by checking [(n/2) and (n/2)+1] or [(n/2)+1 and (n/2)] indexes
 */
public boolean canSatisfy(Card[] mainCards) {
	int len = mainCards.length;
	if(len == cardsRequired()) {
		if(len % 2 == 0) {
			Card[] tempCards1 = Slice(mainCards, 0, (len/2));
			Card[] tempCards2 = Slice(mainCards, len/2, len);
			if((nOfSameRank(tempCards1)) && (nOfSameRank(tempCards2))) {
				return true;
			}
		}
		else if(len % 2 == 1) {
			Card[] tempCards1 = Slice(mainCards, 0, (len/2));
			Card[] tempCards2 = Slice(mainCards, (len/2), len);			
			Card[] tempCards1x = Slice(mainCards, 0, (len/2) + 1);
			Card[] tempCards2x = Slice(mainCards, len/2 + 1, len);
			if((nOfSameRank(tempCards1)) && (nOfSameRank(tempCards2)) || (nOfSameRank(tempCards1x)) && (nOfSameRank(tempCards2x))) {
				return true;
			}
		}
	}
	return false;
}
@Override
/**
 * In this createHand we alter one condition when we prioritise the subset that has the more number of cards of the same suit even if the rank is lower
 */
public Hand createHand(Card[] allCards, int[] subset) {
	if(!canSatisfy(allCards)) {
    	return null;
    }
	if(allCards.length % 2 == 1) {
		int len = allCards.length;
		Card[] tempCards1 = Slice(allCards, 0, (len/2)+1);
		Card[] tempCards2 = Slice(allCards, (len/2)+1, len);			
		Card[] tempCards1x = Slice(allCards, 0, (len/2));
		Card[] tempCards2x = Slice(allCards, len/2, len);
		if((nOfSameRank(tempCards1x)) && (nOfSameRank(tempCards2x))) {
			Card[] tempAll = new Card[handSize()];
			for(int i = 0; i < tempCards2x.length; i++) {
				tempAll[i] = tempCards2x[i];
			}
			for(int i = 0; i < tempCards1x.length; i++) {
				tempAll[tempCards2x.length + i] = tempCards1x[i];
			}
			Hand hand = new Hand(tempAll, null, this);
			return hand;
		}
		else if((nOfSameRank(tempCards1)) && (nOfSameRank(tempCards2))) {
			Hand hand = new Hand(allCards, null, this);
			return hand;
		}
	}
	Hand hand = new Hand(allCards, null, this);
	return hand;
}

}
