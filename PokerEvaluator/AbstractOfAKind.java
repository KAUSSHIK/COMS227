package hw4;

import java.util.ArrayList;
import api.Card;
import api.Hand;
import util.SubsetFinder;

public abstract class AbstractOfAKind extends AbstractEvaluator {

	protected AbstractOfAKind(int ranking, int handSize) {
		super(ranking, handSize);
	}

	/**
	 * This method check if all cards in a particular cards array are of the same
	 * rank
	 * 
	 * @param mainCards
	 * @return true or false
	 */
	public boolean canSatisfy(Card[] mainCards) {
		boolean result = false;
		if (mainCards.length == cardsRequired()) {
			for (int i = 0; i < cardsRequired() - 1; i++) {
				if (mainCards[i].getRank() == (mainCards[i + 1]).getRank()) {
					result = true;
				} else {
					result = false;
					break;
				}
			}
		}
		return result;
	}

	/**
	 * Method gets us the required subset at which the group of 2 or 3 or 4 of the
	 * same kind occur
	 * 
	 * @param allCards
	 * @return subset of indexes
	 */
	private int[] requiredSubset(Card[] allCards) {
		ArrayList<int[]> subsets = SubsetFinder.findSubsets(allCards.length, cardsRequired());
		for (int i = 0; i < subsets.size(); ++i) {
			int[] subset = subsets.get(i);
			Card[] tempMain = new Card[subset.length];
			for (int j = 0; j < subset.length; j++) {
				tempMain[j] = allCards[subset[j]];
			}
			if (canSatisfy(tempMain)) {
				return subset;
			}
		}
		return null;
	}

	/**
	 * Method to check if a given card is present in a given array mainCards
	 * 
	 * @param mainCards
	 * @param card
	 * @return true or false
	 */
	private boolean doesNotExistInMainCards(Card[] mainCards, Card card) {
		boolean result = false;
		for (int i = 0; i < mainCards.length; i++) {
			if (!card.equals(mainCards[i])) {
				result = true;
			} else {
				result = false;
				break;
			}
		}
		return result;
	}

	/**
	 * Creates hand using the allCards provided and the indexes of suitable cards
	 * from the subset array
	 */
	@Override
	public Hand createHand(Card[] allCards, int[] subset) {
		if (!canSatisfy(allCards)) {
			return null;
		}
		Card[] mainCards = new Card[cardsRequired()];
		for (int j = 0; j < mainCards.length; j++) {
			mainCards[j] = allCards[subset[j]];
		}
		if (!canSatisfy(mainCards)) {
			return null;
		}
		Card[] sideCards = new Card[handSize() - cardsRequired()];
		int count = 0;
		for (int i = 0; i < allCards.length; i++) {
			if ((doesNotExistInMainCards(mainCards, allCards[i]))) {
				sideCards[count] = allCards[i];
				count++;
			}
			if ((count >= sideCards.length)) {
				break;
			}
		}
		Hand hand = new Hand(mainCards, sideCards, this);
		return hand;
	}

	/**
	 * Returns the best hand possible given the allCards array, we use our private
	 * helper method requiredSubset ot find the indexes where the best cards that
	 * satisfy the evaluator are located
	 */
	@Override
	public Hand getBestHand(Card[] allCards) {
		return this.createHand(allCards, requiredSubset(allCards));
	}
}
