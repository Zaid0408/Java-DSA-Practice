import java.util.*;
public class QueueB {

    static class ArrayQueue { // implement queue using array
        int arr[];
        int front, rear, size;
        int capacity = 100;
    
        public ArrayQueue() {
            arr = new int[capacity];
            front = 0;
            rear = -1;
            size = 0;
        }
    
        public void push(int x) {
            if (size == capacity)
                return; // Queue full
    
            rear++;
            arr[rear] = x;
            size++;
        }
    
        public int pop() {
            if (isEmpty())
                return -1;
    
            int k = arr[front];
            front++;
            size--;
    
            return k;
        }
    
        public int peek() {
            if (isEmpty())
                return -1;
    
            return arr[front];
        }
    
        public boolean isEmpty() {
            return size == 0;
        }
    }
    
    
    static class queue{ // Circular Queue using array
        static int size,front,rear;
        static int arr[];
        queue(int n){
            arr=new int[n];
            size=n;
            front=-1;
            rear=-1;
        }
        public static boolean IsEmpty(){
            return front==-1 && rear==-1;
        }
        public static boolean IsFull(){
            return (rear+1)%size==front;
        }
        public static void add(int data){
            if(IsFull()){
                System.out.println("Queue is full");
                return;
            }
            if(front ==-1){
                front=0;
            }
            rear=(rear+1)%size;
            arr[rear]=data;
        }
        public static int remove(){
            if(IsEmpty()){
                System.out.println("Queue is empty");
                return -1;
            }
            int val= arr[front];
            if(front==rear){
                rear=front=-1;
            }
            else{
                front=(front+1)%size;
            }
            
            return val;
        }
        public static int peek(){
            if(IsEmpty()){
                System.out.println("Queue is empty");
                return -1;
            }
            return arr[front];
        }
        public int Front() {
            if(front==-1)
                return -1;
            return arr[front];
        }
        
        public int Rear() {
            if(rear==-1)
                return -1;
            return arr[rear];
        }
    }
    static class StackQueue{ // using 2 stacks to implement queue
        static Stack<Integer> s1=new Stack<>();
        static Stack<Integer> s2=new Stack<>();
        public static boolean isEmpty(){
            return s1.isEmpty();
        }
        public static void add(int data){
            while(!s1.isEmpty()){
                s2.push(s1.pop());
            }
            s1.push(data);
            while(!s2.isEmpty()){
                s1.push(s2.pop());
            }
        }
        public static int remove(){
            if(s1.isEmpty()){
                System.out.println("Queue is empty");
                return -1;
            }
            return s1.pop();
        }
        public static int peek(){
            if(s1.isEmpty()){
                System.out.println("Queue is empty");
                return -1;
            }
            return s1.peek();
        }
    }

    public static void nonRepeatingCharacter(String s){
        Queue<Character> queue=new ArrayDeque<>();
        int count[] =new int[26];
        for(int i=0;i<s.length();i++)
        {
            char ch=s.charAt(i);
            queue.add(ch);
            count[ch-'a']+=1;
            while (!queue.isEmpty() && count[queue.peek()-'a']>1) {
                queue.remove();
            }
            if(queue.isEmpty())
                System.out.print("# ");
            else
                System.out.print(queue.peek()+" ");
        }  
    }
    public static void interLeave(int arr[]){
        Queue<Integer> a=new ArrayDeque<>();
        for(int i=0;i<arr.length;i++)
        {
            
        }
    }

    // leetcode 239 Sliding Window Maximum

    public int[] maxSlidingWindow(int[] nums, int k) {
        int n=nums.length;
        if (n == 0)
            return new int[0];
        int[] ans = new int[n - k + 1];
        Deque<Integer> dq = new LinkedList<>();
        int idx = 0;
        for (int i = 0; i < n; i++) { // Queue is in decreasing order always
        // fromt of the queue is alway tha maximum of the given window 
            // Remove elements outside current window
            while (!dq.isEmpty() && dq.peekFirst() <= i - k) {
                dq.pollFirst();
            }
            // Maintain decreasing order
            while (!dq.isEmpty() && nums[dq.peekLast()] <= nums[i]) {
                dq.pollLast();
            }
            dq.offerLast(i);
            // Window formed
            if (i >= k - 1) {
                ans[idx++] = nums[dq.peekFirst()];
            }
        }
        return ans;
    }

    class Node{
        int key, value;
        Node next,prev;
        Node(int key, int value)
        {
            this.key=key;
            this.value=value;
        }
    }
    /*
    * The Least Recently Used (LRU) Cache is a data structure that removes the "oldest" (least recently accessed) item when it reaches its capacity.
    
    To achieve O(1) time complexity for both get and put, we combine two data structures:
    
    HashMap: Provides O(1) lookups for any key.
    Doubly Linked List: Maintains the order of access. We move "fresh" items to the front and "stale" items stay at the back.
    By using Dummy Head and Dummy Tail nodes, we avoid null pointer checks during node removal or insertion, making the code much cleaner.
    

    Map contains key, Node(key,value); 

    Most optimal approach and best for interview 

    DLL is used to move the Recenetly used key value to the front so that the least recently used get shitft to the back

    head -> recently used .... least recently used -> tail
         <-                                        <-
    // this is why remove and insert front is done , remove will remove either least recently used or remove the key,value pair if provided , check get implementation
     */
    class LRUCache {
        Node head,tail;
        Map<Integer,Node> map; 
        final int capacity; // do no chnage capacity value while insertion/deletion
        public LRUCache(int capacity) {
            this.capacity=capacity;
            this.map=new HashMap<>();
            head=new Node(-1,-1);
            tail=new Node(-1,-1);
            head.next=tail;
            tail.prev=head;
        }
        
        public int get(int key) {
            if(!map.containsKey(key))
                return -1;
            // make the key value pair the recently used one 
            Node lru=map.get(key);
            remove(lru); // remove the key from current position 
            insertFront(lru);// and place at start
            return lru.value;
        }
        
        public void put(int key, int value) {
            if(map.containsKey(key)) // already contains the key but the value must be updated 
            {
                Node updateNode=map.get(key);
                updateNode.value=value;
                remove(updateNode);
                insertFront(updateNode);
            }
            else // adding new key
            {
                if(map.size()==capacity) // if capacity full delete least recently used key 
                {
                    Node toBeRemoved=tail.prev;
                    remove(toBeRemoved);
                    map.remove(toBeRemoved.key); // delete key also very important
    
                    Node toBeInserted=new Node(key,value);
                    insertFront(toBeInserted);
                    map.put(key,toBeInserted); // add new key 
                }
                else
                {
                    Node toBeInserted=new Node(key,value);
                    insertFront(toBeInserted);
                    map.put(key,toBeInserted); // add new key
                }
            }
        }
        public void remove(Node node)
        {
            // remove node fron the DLL 
            // needed for two things 1. Remove Least recently used while put operation when cappacity is full
            // 2. when get is performed put that into the front of the DLL as it was recently used 
            node.prev.next=node.next;
            node.next.prev=node.prev;
        }
        public void insertFront(Node node)
        {
            // insert into the front og the DLL
            // This is only done when a prticular key value pair is used recently either in get or put operation
            node.next=head.next;
            head.next.prev=node;
    
            head.next=node;
            node.prev=head;
        }
    }
    
    /**
     * Your LRUCache object will be instantiated and called as such:
     * LRUCache obj = new LRUCache(capacity);
     * int param_1 = obj.get(key);
     * obj.put(key,value);
     */
    
    
    public static void main(String[] args) {
        queue q=new queue(3);
        q.add(1);
        q.add(2);
        q.add(3);
        //System.out.print(q.peek()+" ");
        q.remove();
        //System.out.println();
        q.add(4);
        
        // while(!q.IsEmpty()){
        //     System.out.println(q.peek());
        //     q.remove();
        // }

        Queue<Integer> q2= new LinkedList<>(); // or ArrayDeque can be used
        // no Queue type as it is interface not class
        Queue<Integer> q3= new ArrayDeque<>(); // same methods in both ll and deque
        // has more methods suchas .add(), .peek(), .remove(), .isEmpty(), .size(), 
        StackQueue sq=new StackQueue();
        sq.add(1);
        sq.add(2);
        sq.add(3);
        System.out.println(sq.peek()+" ");
        sq.remove();
        System.out.println(sq.peek()   +" ");
        sq.add(4);
        // while(!sq.isEmpty()){
        //     System.out.println(sq.peek());
        //     sq.remove();
        // }
        nonRepeatingCharacter("aabccxb");
    }
}
