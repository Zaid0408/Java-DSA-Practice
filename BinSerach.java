import java.util.*;

public class BinSerach {
    
    // lowerbound problem to find the index of the smallest element greater than or equal to x. nums[i]>=x
    public int lowerBound(int[] nums, int x) {
        int ans=nums.length-1;
        int s=0,e=nums.length-1;
        while(s<=e)
        {
            int mid=(s+e)/2;
            if(nums[mid]>=x){
                ans=mid;
                e= mid-1;    
            }
            else if(nums[mid]<x)
            {
                s=mid+1;
            }
        }

        return ans;
    }

    // leetcode 35 Search Insert Position
    public int searchInsert(int[] nums, int target) { // same as lower bound finding 
        int ans=nums.length-1;
        if(target>nums[ans]) // one edge case where target is greater than the last element
            return ans+1;
        int s=0,e=nums.length-1;
        while(s<=e)
        {
            int mid=(s+e)/2;
            if(nums[mid]>=target){
                ans=mid;
                e= mid-1;    
            }
            else if(nums[mid]<target)
            {
                s=mid+1;
            }
        }

        return ans;
    }

    // upperbound problem The upper bound of x is defined as the smallest index i such that nums[i] > x.
    public int upperBound(int[] nums, int x) {
        int ans=nums.length-1;
        int s=0,e=nums.length-1;
        while(s<=e)
        {
            int mid=(s+e)/2;
            if(nums[mid]>x){
                ans=mid;
                e= mid-1;    
            }
            else if(nums[mid]<=x)
            {
                s=mid+1;
            }
        }

        return ans;
    }

    // floor and ceil : floor is the largest element less than or equal to x and ceil is the smallest element greater than or equal to x
    public int[] getFloorAndCeil(int[] nums, int x) {
        int ans=nums.length-1;
         int s=0,e=nums.length-1;
         boolean found= false;
         while(s<=e)
         {
             int mid=(s+e)/2;
             if(nums[mid]>=x){
                 ans=mid;
                 e= mid-1;    
                 if(nums[mid]==x)
                     found=true;
             }
             else if(nums[mid]<x)
             {
                 s=mid+1;
             }
         }
         if(found)
             return new int[]{nums[ans],nums[ans]};
         return new int[]{nums[ans-1],nums[ans]};
     }

    //leetcode 34. Find First and Last Position of Element in Sorted Array
    public int[] searchRange(int[] nums, int target) {
        int s=0,e=nums.length-1;
        int mid=0;
        int ans[]=new int[]{-1,-1};
        while(s<=e)
        {
            mid=(s+e)/2;
            if(nums[mid]>target)
                e=mid-1;
            else if(nums[mid]<target)
                s=mid+1;
            else if(nums[mid]==target)
            {
                int x=mid,y=mid;
                while(x< nums.length-1 && nums[x+1]==target)
                    x++;
                while(y>0 && nums[y-1]==target)
                    y--;
                ans[0]=y;
                ans[1]=x;
                return ans; // important otherwise time limit exceded
            }
        }

        return ans;
    }
    // count Occurences : Count occurences of given element in sorted array
    public int countOccurrences(int[] arr, int target) {
        // Your code goes here
        int s=0,e=arr.length-1;
        int mid=0;
        int ans=-1;
        while(s<=e)
        {
            mid=(s+e)/2;
            if(arr[mid]>target)
                e=mid-1;
            else if(arr[mid]<target)
                s=mid+1;
            else if(arr[mid]==target)
            {
                int x=mid,y=mid;
                while(x< arr.length-1 && arr[x+1]==target)
                    x++;
                while(y>0 && arr[y-1]==target)
                    y--;
                // ans[0]=y;
                // ans[1]=x;
                ans=x-y+1;
                return ans;
            }
        }

        return ans;
    }

    // leetcode 33 : Search in rotated sorted array
    public int search(int[] nums, int target) { 
        // Figure out the sorted part of the array left or right? then check if target comes in left part or right part based on relation with mid element
        // Atleast one half of the array is sorted compulsorily
        int ans=-1;
        int s=0,e=nums.length-1;
        int mid=0;
        while(s<=e)
        {
            mid=(s+e)/2;
            if(nums[mid]==target)
                ans=mid;
            if (nums[s] <= nums[mid]) { // assuming if left part of the array us sorted (from start to mid), nothing to do with the target
                if (target > nums[mid] || target < nums[s]) // target is on right side 
                    s = mid + 1;
                else
                    e = mid - 1;
            }
            else { // assuming if right part of the array us sorted (from mid to end), nothing to do with the target
                if (target < nums[mid] || target > nums[e]) // target is on left side
                    e = mid - 1;
                else
                    s = mid + 1;
            }
        }

        return ans;
    }

    // leetcode 81 Search in Rotated Sorted Array II , this time elements are repeated , same as above but extra condition added 
    public static boolean searchOnly(int[] nums, int target) {
        int s=0,e=nums.length-1;
        while(s<=e)
        {
            int mid=(s+e)/2;
            if(nums[mid]==target)
                return true;
            if(nums[s]== nums[mid] && nums[mid]==nums[e])// necessary because what if the array repeats and goes to the other half ex: [1,0,1,1,1] where target is 0 
            {
                s++;
                e--;
                continue;
            }
            if (nums[s] <= nums[mid]) { 
                if (target > nums[mid] || target < nums[s]) // target is on right side 
                    s = mid + 1;
                else
                    e = mid - 1;
            }
            else { 
                if (target < nums[mid] || target > nums[e]) // target is on left side
                    e = mid - 1;
                else
                    s = mid + 1;
            }
            
        }
        return false;
    }

    // leetcode 153 Find Minimum in Rotated Sorted Array
    public static int findMin(int[] nums) {
        // Find minimum element in a rotated sorted array
        // input: nums = [4,5,6,7,0,1,2] Output: 0 input: nums= [3,4,5,1,2] Output: 1
        int mini=Integer.MAX_VALUE;
        int start=0,end=nums.length-1;
        while(start<=end)
        {
            if(nums[start]<nums[end]) // when we reach a sorted subarray then the leftmost lement is the smallest element in the list
            {
                mini=Math.min(nums[start],mini);
                break;
            }
            int mid=(start+end)/2;
            mini=Math.min(nums[mid],mini);
            if(nums[start]<=nums[mid])
            {   // mid > start means mid is part of left sorted array and is larger than start hence search right sorted array part
                start=mid+1;
                
            }
            else
            {
                end=mid-1;
            }
        }
        return mini;
    }

    public static void main(String[] args)
    {
        //         m
        //         s e   
        int arr[]={1,0,1,1,1};
        System.out.println(searchOnly(arr, 0));
    }

}
