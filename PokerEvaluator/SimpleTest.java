package hw4;

import java.util.Arrays;

import api.Card;
import api.IEvaluator;

public class SimpleTest {
	public static void main(String[] args) {
		Card[] cards = Card.createArray("Ks, As, Js, Qs, 9s"); //Ac, Kc, Qc, Jc, 10c
		Arrays.sort(cards);
		System.out.println("OnePair Eval");
		IEvaluator one = new OnePairEvaluator(3, 6); 
		System.out.println(Arrays.toString(cards));
		boolean ans9 = one.canSubsetSatisfy(cards);
		System.out.println(ans9);
		System.out.println(one.getBestHand(cards)); 
		
		System.out.println("\nThreePair Eval");
		IEvaluator three = new ThreeOfAKindEvaluator(3, 6); 
		
		System.out.println(Arrays.toString(cards));
		boolean thr = three.canSubsetSatisfy(cards);
		System.out.println(thr);
		
		System.out.println(three.getBestHand(cards)); 
		
		System.out.println("\nFourPair Eval");
		IEvaluator four = new FourOfAKindEvaluator(3, 6); 
		
		System.out.println(Arrays.toString(cards));
		boolean an = four.canSubsetSatisfy(cards);
		System.out.println(an);
		
		System.out.println(four.getBestHand(cards)); 
		
		
		System.out.println("\nStraight Eval");
		
		StraightEvaluator straEval = new StraightEvaluator(1, 6, 1); 

		System.out.println(Arrays.toString(cards));
		boolean ans1 = straEval.canSubsetSatisfy(cards);
		System.out.println(ans1);
		
		System.out.println(straEval.getBestHand(cards)); 
		System.out.println("\nStright Flush");
		StraightFlushEvaluator straFlushEval = new StraightFlushEvaluator(2, 6, 1); 
		System.out.println(Arrays.toString(cards));
		boolean ans2 = straFlushEval.canSubsetSatisfy(cards);
		System.out.println(ans2);
		
		System.out.println(straFlushEval.getBestHand(cards)); 
		
		
		System.out.println("\nTesting AllPrimeEvaluator"); 
		AllPrimesEvaluator allPrime = new AllPrimesEvaluator(2, 6);
		System.out.println(Arrays.toString(cards));
		boolean ans3 = allPrime.canSubsetSatisfy(cards);
		System.out.println(ans3);
		
		
		System.out.println(allPrime.getBestHand(cards)); 
		
		System.out.println("\nTesting CatchAll"); 
		CatchAllEvaluator catchAl = new CatchAllEvaluator(1, 6); 
		System.out.println(Arrays.toString(cards));
		boolean ans4 = catchAl.canSubsetSatisfy(cards); 
		System.out.println(ans4);
		
		
		System.out.println(catchAl.getBestHand(cards)); 
	
		System.out.println("\nTesting FullHouse"); 
		FullHouseEvaluator fullhouse = new FullHouseEvaluator(1, 6); 
		//Hand ha = fullhouse.createHand(cards, null);
		//cards = ha.getMainCards();
		System.out.println(Arrays.toString(cards));
		boolean ans5 = fullhouse.canSubsetSatisfy(cards); 
		System.out.println(ans5);
		
		
		System.out.println(fullhouse.getBestHand(cards)); 
	}
}