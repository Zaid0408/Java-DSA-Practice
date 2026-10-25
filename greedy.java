import java.util.*;;

public class Greedy{
    // leetcode 455 Assign Cookies
    public int findContentChildren(int[] g, int[] s) {
        Arrays.sort(g);
        Arrays.sort(s);

        int n=g.length;int m=s.length;
        int l=0,r=0;
        while(l<m && r<n)
        {
            if(g[r]<=s[l])
            {
                r=r+1;
            }
            l=l+1;
        }
        return r;
    }
    // leetcode 860 Lemonade Change
    // maintain variables for eaach note ex : 5,10,20
    // if 5 is not present in the first element as we habe no change initially return false
    // if bill is 5 add it to 5 variable, if bill is 10 add to 10 variable and subtract 1 from 5 variable
    // if bill is 20 add to 20 variable and subtract 1 from 5 and 10 variable or subtract 3 times from 5 variable
    public boolean lemonadeChange(int[] bills) {
        if(bills[0]!=5)
            return false;
        int five=0,ten=0,twenty=0;

        for(int bill:bills)
        {
            if(bill==5)
                five++;
            else if(bill==10)
            {
                if(five<=0) return false;
                ten++;
                five--;
            }
            else{
                if((five>0 && ten>0))
                {
                    twenty++;
                    five--;ten--;
                }
                else if(five>=3)
                {
                    five=five-3;
                }
                else 
                    return false;
            }
        }

        return true;
    }

    // leetcode 55 Jump Game, this approach is DP also

    // 1. Base case: If the array has only one element, we are already at the destination.
// 2. Initialize a boolean DP array where jump[i] represents if index i is reachable.
// 3. Set the starting point jump[0] to true since we always begin at index 0.
// 4. Loop through each index and check if it is reachable before proceeding further.
// 5. From the current reachable index, iterate through all positions we can jump to.
// 6. Mark each of those newly reachable positions as true in our DP tracking array.
// 7. Early exit: If the final index has been marked reachable, return true immediately.
// 8. Return the final boolean status of whether the last index was successfully reached.
    public boolean canJump(int[] nums) {
        if(nums.length==1)
            return true;
        boolean jump[]=new boolean[nums.length];
        jump[0]=true;
        for (int i = 0; i < nums.length; i++) {
            if (!jump[i]) {
                return false;
            }
            for (int j = i + 1; j <= i + nums[i] && j < nums.length; j++) {
                jump[j] = true;
            }
            if (jump[nums.length - 1]) {
                return true;
            }
        }
        return jump[nums.length-1];
    }
    // Greedy SOln :
    // 1. Maintain a variable 'maxReach' to track the furthest index we can travel to.
// 2. Iterate through each index of the array from left to right sequentially.
// 3. If our current index surpasses 'maxReach', we are stuck and cannot proceed.
// 4. Update 'maxReach' by taking the maximum of its current value and i + nums[i].
// 5. If 'maxReach' covers or exceeds the last index, return true early.
// 6. Continue this greedy expansion step-by-step through the entire array loop.
// 7. If the loop completes successfully without getting stuck, return true overall.

    public boolean canJumpGreedy(int[] nums) {
        int maxReach=0;
        for(int i=0;i<nums.length;i++)
        {
            if(i>maxReach) return false;
            maxReach=Math.max(i+nums[i],maxReach);
            if (maxReach>=nums.length - 1) return true;
        }
        return true;
    }

    /*
    Shortest Job first algorithm
    Algo : scheduling policy that chooses the job with the shortest remaining processing time to be executed next. selects the waiting process with the samllest execution time possible 
    */

    public int findAvgWaitTime(int[] process)
    {   // TC : O(nlogn) + O(n)
        int waitTime=0;
        Arrays.sort(process);
        int sum=0;
        for(int i=0;i<process.length;i++)
        {
            waitTime+=sum;
            sum+=process[i];
        }
        return waitTime/process.length; // avg waiting time  
    }
    /*
    All jobs are available at the same time, so a min-heap can repeatedly choose the smallest burst time. 
    After removing one job, the current elapsed time is its waiting time for the next job.
    */
    public long solve(int[] bt) {
        // TC : O(nlogn) + O(n) 
        PriorityQueue<Integer> jobs = new PriorityQueue<>();
        for (int burst : bt) {
            jobs.offer(burst);
        }
 
        long elapsed = 0;
        long waitingSum = 0;
 
        while (!jobs.isEmpty()) {
            waitingSum += elapsed;
            elapsed += jobs.poll();
        }
 
        if (bt.length == 0) {
            return 0;
        }
        return waitingSum / bt.length;
    }


    // leetcode 56 Merge Intervals
    /**
     * Merges overlapping intervals.
     * 
     * Sorts the intervals by start time, then iterates through the sorted array.
     * If the current interval starts before the previous one ends, it merges the two.
     * The result is a new array with all overlapping intervals merged.
     */
    // [[1,1],[2,2],[0,0],[2,3],[1,3],[3,5],[2,3],[3,5]]
    // [[0,0],[1,1],[1,3],[2,2],[2,3],[2,3],[3,5],[3,5]]
    // [[0,0],[1,1],[1,3],[3,5],[3,5]] 
    public int[][] merge(int[][] intervals) {
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));
        List<int[]> ans=new ArrayList<>();
        int p[]=intervals[0];
        for(int i=1;i<intervals.length;i++)
        {
            if(intervals[i][0]<=p[1]) // merge logic 
            {
                p[1]=Math.max(p[1],intervals[i][1]);
            }
            else
            {
                ans.add(p);
                p=intervals[i];
            }
        }
        ans.add(p);
        return ans.toArray(new int[ans.size()][]);
    }

    // leetcode 435. Non-overlapping Intervals
        /**
     * Erases overlapping intervals.
     * 
     * Sorts the intervals by end time, then iterates through the sorted array.
     * If the current interval starts before the previous one ends, it is considered overlapping and is not included in the result.
     * The result is the number of intervals that were erased.
     */
    public int eraseOverlapIntervals(int[][] intervals) {
        Arrays.sort(intervals, (a,b)-> a[1]-b[1]);
        int p=intervals[0][1];
        int merged=0;
        for(int i=1;i<intervals.length;i++)
        {
            if(p>intervals[i][0])
            {
                merged++;
            }
            else
                p= intervals[i][1];
        }
        return merged;
    }
    public static void main(String[] args) {
        
    }
}