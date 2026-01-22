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

}
