import java.util.ArrayList;
import java.util.Collections;
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
}
