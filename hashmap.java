import java.util.*;
public class hashmap {
    public static void majorityElememt() {
        // Find all elements which occur more than n/3 times
        int arr[]={1,3,2,5,1,3,1,5,1};
        HashMap<Integer, Integer> hm = new HashMap<Integer, Integer>();
        for(int i=0;i<arr.length;i++){
            if(hm.containsKey(arr[i]))
            {
                hm.put(arr[i], hm.get(arr[i])+1);
            }
            else
            {
                hm.put(arr[i], 1);
                
            }
        }
        System.out.println("Majority elements are: ");
        Set<Integer> set = hm.keySet();
        for (Integer key : set) {
            if(hm.get(key)>=arr.length/3)
                System.out.print(key+", ");
        }
        System.out.println("Done ");
    }
    public static void DistinctElements() {
        int arr[]={4,3,2,5,6,7,3,4,2,1};
        HashSet<Integer> hs = new HashSet<Integer>();
        for(int i=0;i<arr.length;i++){
            hs.add(arr[i]);

        }
        System.out.println("Distinct elements are: "+hs);
        
    }
    public static void intersectionOfArrays() { //Intersection of two arrays
        int arr1[]={7,3,9};
        int arr2[]={6,3,9,2,9,4};
        
        HashSet<Integer> hs = new HashSet<Integer>();
        for(int i=0;i<arr1.length;i++){
            
            for(int j=0;j<arr2.length;j++){
                if(arr1[i]==arr2[j])
                    hs.add(arr1[i]);
            }
        }
        System.out.println("Intersection elements are: "+hs);
        // Union can be implemented by adding both arrays in hash set and 
        //it will only store the unique value

    }
    public static void findItenaryForTicket() {
        /*
         * Find itenary for ticket for given source and destination saved as key value pair 
         * in the hashmap hm. key will be source and value will be destination.
         * initial 2 loops used for finding starting point of the itenary. starting point will be the key which is not present in hm2
         * 
         */
        HashMap<String, String> hm = new HashMap<String, String>();
        hm.put("Chennai", "Bengaluru");
        hm.put("Mumbai", "Delhi");
        hm.put("Goa", "Chennai");
        hm.put("Delhi", "Goa");
        HashMap<String, String> hm2 = new HashMap<String, String>();
        for (String str : hm.keySet()) {
            hm2.put(hm.get(str), str); // reversing hm1 and storing in hm2
        }
        String startingPoint="";
        for (String sex : hm.keySet()) {
            if(!hm2.containsKey(sex)){
                startingPoint+=sex;// staring point should not be a key in hm2
                break;
            }
        }
        System.out.print(startingPoint);
        for (String sex : hm.keySet()) {
            System.out.print("-> "+hm.get(sex));// update key to value and use it as key for next iteration
            sex=hm.get(sex);
        }
    }
    /*
         * largest subarray with sum 0
         * find the length of the largest sub array whose sum is 0
         * Approach: use Hashmap ro store prefix sum and the index of that sum.
         * 
         */
        public static void SubArrZeroSum(){
            int arr[]={15,2,-2,-8,1,7,10,23};
            HashMap<Integer,Integer> map=new HashMap<>();
            //(sum,index)
            int sum=0,len=0;
            for(int i=0;i<arr.length;i++){
                sum+=arr[i];
                if(map.containsKey(sum))
                    len=Math.max(len,i-map.get(sum));
                else
                    map.put(sum,i);
                
            }
            System.out.println("Length of largest subarray with sum 0 is "+len);
        }
        // Note:
        // Sum(0,j)-Sum(0,i)=Sum(i+1,j) 
        // Sum(0,j)-Sum(0,i-1)=Sum(i,j) use this to find sub array sum =k(which is given)
        // Sum(j)+Sum(i)=k
        // Sum(j)-k=Sum(i) // search for this condition in ashmap
        public static int subarraySum(int[] nums, int k) {
            HashMap<Integer,Integer> map=new HashMap<>();
                //(sum,count or frequency)
            map.put(0,1);
            int sum=0,ans=0;
            for(int j=0;j<nums.length;j++)
            {
                sum+=nums[j];
                if(map.containsKey(sum-k))
                    ans+=map.get(sum-k);
                map.put(sum,map.getOrDefault(sum,0)+1);
            }
            return ans;
        }
        public static int[] twoSum(int[] nums, int target) {
            HashMap<Integer,Integer> ans= new HashMap<>();
            for(int i=0;i<nums.length;i++)
            {
                if(ans.containsKey(target-nums[i]))
                {
                    return new int[]{i,ans.get(target-nums[i])};
                }
                ans.put(nums[i],i);
            }
    
            return new int[]{0,0};
        }
    class LRUCache {
        LinkedHashMap<Integer,Integer> ans;
        public LRUCache(int capacity) {
            ans=new LinkedHashMap<Integer,Integer>(capacity,0.75f,true){
                @Override
                protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
                    return size() > capacity;
                }
            };
            /*
                * In the constructor, the third boolean parameter specifies the ordering mode. 
                * If we set it to true, it will be in access order.
                * By overriding removeEldestEntry in this way, we do not need to take care of it ourselves. 
                * It will automatically remove the least recent one when the size of map exceeds the specified capacity.
                */
        }
        public int get(int key) {
            return ans.getOrDefault(key,-1);
        }
        
        public void put(int key, int value) {
            ans.put(key,value);
            
        }
    }
    /**
     * Your LRUCache object will be instantiated and called as such:
     * LRUCache obj = new LRUCache(capacity);
     * int param_1 = obj.get(key);
     * obj.put(key,value);
     */

     public static String frquencySort(String s){
        HashMap<Character,Integer> map=new HashMap<>();
        for(int i=0;i<s.length();i++){
            map.put(s.charAt(i), map.getOrDefault(map.get(s.charAt(i)), 0)+1);
        }
        PriorityQueue<Map.Entry<Character,Integer>> pq=new PriorityQueue<>(
            (a,b)->a.getValue()==b.getValue()?a.getKey()-b.getKey():b.getValue()-a.getValue()
            );
        for (Map.Entry<Character, Integer> entry : map.entrySet()) {
            pq.add(entry);

        }
        StringBuilder sb=new StringBuilder();
        while (pq.size()!=0) {
            char ch=pq.remove().getKey();
            int val=map.get(ch);
            while(val!=0){
                sb.append(ch);
                val--;
            }
        }
        return sb.toString();
     }
     public static void main(String[] args) {

        HashMap<Integer, String> hm = new HashMap<Integer, String>();
        hm.put(5, "Five");
        hm.put(2, "Two");
        hm.put(3, "Three");
        hm.put(null, "Null");
        hm.put(1, null); 
        System.out.println(hm);

        System.out.println(hm.get(hm.size()));
        System.out.println(hm.get(5));
        System.out.println(hm.get(9));

        System.out.println(hm.hashCode());
        System.out.println(hm.containsKey(1));

        hm.remove(1);
        //System.out.println(hm+"\n");
        // Set to iterate through hashmap
        Set<Integer> set = hm.keySet();// using set to iterate over hashmap
        System.out.println(set);
        // for (Integer key : set) {
        //     System.out.println(key + " " + hm.get(key));
        // }
        /*
         * put(key,value) and get(key), remove(key) and containsKey(key),isEmpty(),keySet() are some methods of hashmap
         * hashmap implementation internally consists of two important data structures which are LinkedList and Array. 
         * There is a bucket of arrays with each element representing an individual LinkedList. 
         * The Inner Node class consists of a hash value, key, value, and the link to the next Node
         *  https://anmolsehgal.medium.com/java-hashmap-internal-implementation-21597e1efec3
         * reading on hashmap covers basics and collision as well
         */
        
        // hm.clear();// to clear the hashmap
        // System.out.println(hm);
        // LinkedHashMap <Integer,String> lhm = new LinkedHashMap<>();// Same order as insertion order
        // all operations are same as above 
        // doubly linked list inside


        TreeMap<Integer,String> tm = new TreeMap<Integer,String>();
        tm.put(5, "Five");
        tm.put(2, "Two");
        tm.put(3, "Three");
        //tm.put(null, "Null"); Cannot add null as key in TreeMap
        tm.put(1, null); 
        //System.out.println(tm);
        /*
         * Similar as hashmap functions put,get,remove and containsKey
         * STorage, retrieve and remove complexity is O(log(n))
         * keys are in a sorted manner
         * implemented using Red Black trees. also called self balancing trees.
         */

        //majorityElememt();
        


        HashSet<Integer> hs = new HashSet<Integer>();
        //implemented by using hashmap , no duplicates, unordered,null is allowed
        // to store only unique value
        hs.add(1);
        hs.add(2);
        hs.add(3);

        //DistinctElements();
        //intersectionOfArrays();
        //findItenaryForTicket();
        SubArrZeroSum();
    }
}
