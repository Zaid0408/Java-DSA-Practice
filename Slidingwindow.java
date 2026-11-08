public class Slidingwindow {
    // Constant Window:

    // leetcode 1423

    /*
    * Problem: Given an array of card points, we need to pick exactly k cards 
    * from either the absolute beginning or the absolute end of the row in 
    * multiple steps to maximize the total sum of the cards chosen.
    */

    /*
    * Approach: Instead of choosing k cards from the edges, we invert the logic 
    * by realizing that picking k cards from the ends leaves behind a single 
    * contiguous subarray of size (n - k) in the center. To maximize our score, 
    * we find the minimum possible sum of this middle subarray using a sliding 
    * window of size (n - k) and subtract it from the total sum of all cards.
    */

    public int maxScore(int[] cardPoints, int k) {
        int sum=0,n=cardPoints.length;
        for(int card:cardPoints)
            sum += card;
        if(n==k)
            return sum;
        
        int window=n-k;
        int currentWindowSum = 0;
        
        // 1. Find the sum of the first window of size (n - k)
        for (int i = 0; i < window; i++) {
            currentWindowSum += cardPoints[i];
        }
        int minSum = currentWindowSum;
        // 2. Slide the window across the rest of the array
        for (int i = window; i < n; i++) {
            currentWindowSum += cardPoints[i]-cardPoints[i-window];
            minSum = Math.min(minSum, currentWindowSum);
        }
        return sum-minSum;
    }

    // Longest and Smallest Window Problems:

    
}
