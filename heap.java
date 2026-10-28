import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class heap {
    static class heap1 { // implementing heap using arraylist
        // if parent is at index i the left child will be at 2i+1 and right child at 2i+2
        // if child is at index x then parent will be at (x-1)/2
        ArrayList<Integer> arr = new ArrayList<>();
        void insert(int data) {
            arr.add(data); // data will be new child node added ti end of arraylist
            int child = arr.size() - 1; // getting index of new child or newly added data
            int parent = (child - 1) / 2; // getting index of parent of new child
            while (parent >= 0 && arr.get(parent) < arr.get(child)) { // loop to heapify or to convert arraylist to min heap
                int temp = arr.get(parent);
                arr.set(parent, arr.get(child));
                arr.set(child, temp);
            }
        }
        int peek() {
            return arr.get(0);
        }
        int remove() {
            // deleting in heap means removing minimum element 
            // step 1 move minimum element to end of arraylist
            int last = arr.size() - 1;
            int min = arr.get(0);
            arr.set(0, arr.get(last));
            arr.set(last, min);

            // step 2 remove last element
            int ans=arr.remove(arr.size() - 1);

            // step 3 heapify
            int i=0;
            while(2*i+2<arr.size()){
                int left = 2*i+1;
                int right = 2*i+2;
                int temp= Math.min(arr.get(i),Math.min(left, right));
                // if(temp==arr.get(i)){
                //     break;
                // }
                int swap = arr.get(i);
                arr.set(arr.indexOf(temp), swap);
                arr.set(i, temp);
                
                i++;
            }
            return ans;
        }
    
        
    }
    public int findKthLargest(int[] nums, int k) {
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        for(int num: nums)
            maxHeap.add(num);
        int ans=-1;
        for (int i = 0; i < k; i++) {
            ans = maxHeap.poll(); 
        }
        return ans;
    }
    // leetcode 703
    class KthLargest {
        public KthLargest(int k, int[] nums) {
            for(int num:nums)
                pq.add(num);
            p=k;
        }
        public int add(int val) {
            pq.add(val);
            while(pq.size()>p)
                pq.poll();
            return pq.peek();
        }
        PriorityQueue<Integer> pq=new PriorityQueue<>(); // this is minheap
        int p=0;
    }
    public int lastStoneWeight(int[] stones) { // Leetcode 1046
        PriorityQueue<Integer> maxBoi=new PriorityQueue<>(Collections.reverseOrder());
        maxBoi.add(0);
        for(int stone : stones)
            maxBoi.add(stone);
        while(maxBoi.size()>1)
        {
            int x=maxBoi.poll();
            int y=maxBoi.poll();
            if(x==y)
                continue;
            else
            {
                maxBoi.add(x-y);
            }
        }
        return maxBoi.peek();
    }

    /*
    Sort a K-Sorted Array
You are given an integer array arr and an integer k.

The array is k-sorted, meaning every element is at most k positions away from its correct position in the completely sorted array.

Sort the array in non-decreasing order and return the sorted array.

Your solution should take advantage of the k-sorted property rather than simply sorting the entire array.

Example 1:
Input: arr = [3, 1, 2, 5, 4, 7, 6], k = 2

Output: [1, 2, 3, 4, 5, 6, 7]

Explanation: Every element is at most 2 positions away from its position in the sorted array. After sorting, the array becomes [1, 2, 3, 4, 5, 6, 7].

Example 2:
Input: arr = [4, 2, 3, 1, 6, 5], k = 3

Output: [1, 2, 3, 4, 5, 6]

Explanation: The array satisfies the k = 3 condition. Sorting it gives [1, 2, 3, 4, 5, 6].
    */
     
    public int[] sortKSortedArray(int[] arr, int k) {
        if (arr == null || arr.length == 0) {
            return arr;
        }
        PriorityQueue<Integer> pq=new PriorityQueue<>();
        int i=0,j=0;
        int ans[]=new int[arr.length];
        while(i<=k && i<arr.length){
            pq.add(arr[i]);
            i++;
        }

        while(i<arr.length)
        {
            int cur=arr[i];
            ans[j++]=pq.poll();
            pq.add(cur);
            i++;
        }
        while(pq.size()!=0){
            ans[j++]=pq.poll();
        }

        return ans;
    }

    // 1331. Rank Transform of an Array

    public int[] arrayRankTransform(int[] arr) {
        // Min-heap to automatically sort elements in ascending order
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for (int num : arr) {
            pq.offer(num);
        }
        // Map to store each unique number and its corresponding rank
        Map<Integer, Integer> rankMap = new HashMap<>();
        int rank = 1;
        // Extract elements from the heap in increasing order and assign ranks
        while (!pq.isEmpty()) {
            int num = pq.poll();
            // If the number is not already mapped, assign the current rank
            if (!rankMap.containsKey(num)) {
                rankMap.put(num, rank++);
            }
        }
        // Replace each element in the original array with its rank from the map
        int[] ans = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            ans[i] = rankMap.get(arr[i]);
        }
        
        return ans;
    }
}
