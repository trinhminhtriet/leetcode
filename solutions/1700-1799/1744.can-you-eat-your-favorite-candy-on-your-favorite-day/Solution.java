class Solution {
    public boolean[] canEat(int[] candiesCount, int[][] queries) {
        int typesOfCandies = candiesCount.length;
        long[] cumulativeCandies = new long[typesOfCandies + 1];
      
        // Calculate the cumulative sum of candies to determine the total
        // number of candies up to a certain type
        for (int i = 0; i < typesOfCandies; ++i) {
            cumulativeCandies[i + 1] = cumulativeCandies[i] + candiesCount[i];
        }
      
        int numberOfQueries = queries.length;
        boolean[] canEatOnDay = new boolean[numberOfQueries];
      
        // Iterate over each query to determine if you can eat the candies
        for (int i = 0; i < numberOfQueries; ++i) {
            int type = queries[i][0]; // Type of candy
            int day = queries[i][1]; // Day number
            int maxCandies = queries[i][2]; // Maximum number of candies you can eat
          
            // The earliest amount of candies you could eat by that day
            // is equal to the day number if you eat 1 candy per day
          
            // Whereas the most amount of candies you could eat is if
            // you eat 'maxCandies' on each day including 'day'
          
            long leastCandiesYouCouldEat = day; 
            long mostCandiesYouCouldEat = (long) (day + 1) * maxCandies;
          
            // Check if there's any overlap between the range of candies you could eat
            // and the range of candies available for that type. You can eat the candy
            // on that day if at least one candy of that type is within your eating range.
            canEatOnDay[i] = leastCandiesYouCouldEat < cumulativeCandies[type + 1] && mostCandiesYouCouldEat > cumulativeCandies[type];
        }
      
        return canEatOnDay;
    }
}