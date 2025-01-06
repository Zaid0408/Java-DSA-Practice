import java.util.*;

public class stack {
    static class StackUsingArrayList{// Implement Stack using ArrayList
        static ArrayList<Integer> list=new ArrayList<>();
        public static boolean IsEmpty(){
            return list.size()==0;
        }
        public static void push(int data){
            list.add(data);
        }
        public static int pop(){
            if(IsEmpty()){
                return -1;
            }
            int val=list.get(list.size()-1);
            list.remove(list.size()-1);
            return val;
        }
        public static int peek(){
            if(IsEmpty()){
                return -1;
            }
            return list.get(list.size()-1);
        }
        public static void pushBottomOfStack(int data){
            if(IsEmpty()){
                push(data);
                return;
            }
            int val=pop();
            pushBottomOfStack(data);
            push(val);
        }
    }
    static class Node{
        int data;
        Node next;
        Node(int data){
            this.data=data;
            this.next=null;
        }
    }

    static class StackUsingLL{// Implement Stack using LinkedList
        static Node head=null;
        public static boolean IsEmpty(){
            return head==null;
        }
        // Remember head of LL is same as top of stack
        public static void push(int data){
            Node newNode=new Node(data);
            if(head==null){
                head=newNode;
                return;
            }
            newNode.next=head;
            head=newNode;
        }
        public static int pop(){
            if(IsEmpty()){
                return -1;
            }
            int val=head.data;
            head=head.next;
            return val;
        }
        public static int peek(){
            if(IsEmpty()){
                return -1;
            }
            return head.data;
        }
        // O(N) time complexity O(1) space
        public static void pushBottomOfStack(int data){
            if(IsEmpty()){
                push(data);
                return;
            }
            int val=pop();
            pushBottomOfStack(data);
            push(val);
        }
    }
    public static void ReverseString(String Str){
        Stack<Character> s=new Stack<>();
        for(int i=0;i<Str.length();i++){
            s.push(Str.charAt(i));
        }
        String ans="";
        while(!s.isEmpty()){
            ans+=s.pop();
        }
        System.out.println(ans);
    }
    public static void pushBottom(Stack<Character> s,char ch){
        if(s.isEmpty()){
            s.push(ch);
            return;
        }
        char c=s.pop();
        pushBottom(s,ch);
        s.push(c);
    }
    public static void ReverseStack(Stack<Character> s){
        if(s.isEmpty()){
            return;
        }
        char c= s.pop();
        ReverseStack(s);
        pushBottom(s,c);

    }

    //Stock Span Problem
    public static void stockSpan(int stocks[],int span[]){
        Stack<Integer> s= new Stack<>();
        span[0]=1;
        s.push(0);
        for(int i=0;i<stocks.length;i++){
            int currPrice= stocks[i];
            while(currPrice>stocks[s.peek()] && !s.isEmpty()){
                s.pop();
            }
            if(s.isEmpty()){
                span[i]=i+1;
            }
            else{
                int prevHigh=s.peek();
                span[i]=i-prevHigh;
            }
            s.push(i);

        }
    }

    //Valid parenthesis
    public static boolean isValidParenthesis(String s) {
        if(s.length()%2==1)
            return false;
        Stack<Character> c=new Stack<>();
        for(int i=0;i<s.length();i++)
        {
            if(!c.isEmpty()){
                if((s.charAt(i)==')' && c.peek()=='(')||(s.charAt(i)=='}' && c.peek()=='{')||(s.charAt(i)==']' && c.peek()=='[')){
                    c.pop();
                    continue;
                }
            }
            c.push(s.charAt(i));
        }
        return c.isEmpty();
    }

    //Duplicate Parenthesis present or not
    //If count<1 means closing parenthesis followed by an opening one immidiately hence duplicate
    public static boolean hasDuplicateParenthesis(String s) {
        Stack<Character> c=new Stack<>();
        for(int i=0;i<s.length();i++)
        {
            if(s.charAt(i)==')'||s.charAt(i)=='}'||s.charAt(i)==']'){
                int count=0;
                while (c.peek()!='('||c.peek()!='['||c.peek()!='{') {
                    c.pop();
                    count++;
                }
                if(count<1)
                    return true;
                else
                    c.pop();
            }
            else{
                c.push(s.charAt(i));
            }
        }
        return false;
    }
    // Generate Parenthesis
    public void backTrack(int n,int open,int close,StringBuilder s, List<String> ans)
    {
        /* 
        Three rules: 
        1. Only add open parenthesis when open < n
        2. Only add closed parenthesis when close<open
        3. return the string when open == close ==n
         */
        if(open == close && open == n){
            ans.add(s.toString());
            return;
        }
        
        if(open<n)
        {
            s.append("(");
            backTrack(n,open+1,close,s,ans);
            s.deleteCharAt(s.length() - 1);
        }

        if(close<open)
        {
            s.append(")");
            backTrack(n,open,close+1,s,ans);
            s.deleteCharAt(s.length() - 1);
        }

    }
    public List<String> generateParenthesis(int n) {
        List<String> ans= new ArrayList<>();
        StringBuilder s=new StringBuilder();
        backTrack(n,0,0, s,ans);
        return ans;
    }

    public static void maxAreaHistogram(int histogram[]){
        int maxArea=0;
        int nsr[]=new int[histogram.length];
        int nsl[]=new int[histogram.length];
        // O(n) time 
        // next smaller right
        Stack<Integer> s=new Stack<>();
        for(int i=histogram.length-1;i>=0;i--){
            while(!s.isEmpty() && histogram[s.peek()] >= histogram[i]){ // compare index and not value
                s.pop();
            }
            if(s.isEmpty())
                nsr[i]= histogram.length;
            else
                nsr[i]= s.peek();
            
            //push index
            s.push(i);
        }
        // next smaller left
        s=new Stack<>(); // empty current stack
        for(int i=0;i<histogram.length;i++){  
            while(!s.isEmpty() && histogram[s.peek()] >= histogram[i]){ // compare index and not value
                s.pop();
            }
            if(s.isEmpty())
                nsl[i]= -1;
            else
                nsl[i]= s.peek();
            
            s.push(i);
        }

        // Current Area: width =j-i-1 = nsr[i]-nsl[i]-1
        for(int i=0;i<histogram.length;i++){
            int height=histogram[i];
            int width= nsr[i]-nsl[i]-1;
            int currArea= height* width;
            maxArea=Math.max(maxArea,currArea);
        }
        System.out.println(maxArea);
    }

    // Trapping Rain Water using Stack
    public static int trap(int height[]){
        Stack<Integer> stack = new Stack<>();
        int n = height.length;
        int ans = 0;
        for (int i = 0; i < n; i++) {
            while ((!stack.isEmpty())&& (height[stack.peek()] < height[i])) {
                int pop_height = height[stack.peek()]; 
                stack.pop();
                if (stack.isEmpty())
                    break;
                int distance = i - stack.peek() - 1;
                int min_height= Math.min(height[stack.peek()],height[i])- pop_height;
                ans += distance * min_height;
            }
            stack.push(i);
        }
        return ans;
    }
    

    public static void main(String[] args) {
        //StackUsingArrayList s1=new StackUsingArrayList();// ArrayList implementation
        StackUsingLL s1=new StackUsingLL();// LinkedList implementation
        s1.push(1);
        s1.push(2);
        s1.push(3);
        s1.pushBottomOfStack(4);
        // while (!s1.IsEmpty()) {
        //     System.out.println(s1.peek());
        //     s1.pop();
            
        // }

        // Implementing Stack using Java Collections framework
        Stack<Integer> s2=new Stack<>();
        s2.push(1);
        s2.push(2);
        s2.push(3);
        s2.push(4);
        s2.push(5);
        s2.push(6);
        //System.out.println(s2.peek());
        s2.pop();
        //System.out.println(s2.peek());
        Stack<Character> s3=new Stack<>();
        s3.push('A');
        s3.push('B');
        s3.push('C');
        s3.push('D');
        //ReverseStack(s3);
        // while (!s3.isEmpty()) {
        //     System.out.println(s3.peek());
        //     s3.pop();
            
        // }
        //ReverseString("hello");

        int stocks[]={100,80,60, 70,60,85,100};
        int span[]=new int[stocks.length];
        // stockSpan(stocks,span);
        // for(int i=0;i<span.length;i++){
        //     System.out.println(span[i]+" ");
        // }


        //Next Greater element
        Stack<Integer> s4= new Stack<>();
        int nxt[]= new int[stocks.length];
        for(int i=stocks.length-1;i>=0;i--){
            //while loop part
            while(!s4.isEmpty() && stocks[s4.peek()]<=stocks[i]){ // compare index and not value
                s4.pop();
            }
            // if else
            if(s4.isEmpty())
                nxt[i]=-1;
            else
                nxt[i]= stocks[s4.peek()];
            
            //push index
            s4.push(i);
        }
        // for(int i=0;i<nxt.length;i++){
        //     System.out.print(nxt[i]+" ");
        // }
        System.out.println();
        //System.out.println(isValidParenthesis("({})[{}]"));
        //System.out.println(hasDuplicateParenthesis("((a+b))"));

        //int histogram[]={2,1,5,6,2,3};
        //maxAreaHistogram(histogram);

        int waterHeight[]={0,1,0,2,1,0,1,3,2,1,2,1};
        System.out.println(trap(waterHeight));
    }
}
