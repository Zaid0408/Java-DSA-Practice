import java.util.*;
public class QueueB {
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
        System.out.print(sq.peek()+" ");
        sq.remove();
        System.out.println();
        sq.add(4);
        // while(!sq.isEmpty()){
        //     System.out.println(sq.peek());
        //     sq.remove();
        // }
        nonRepeatingCharacter("aabccxb");
    }
}
