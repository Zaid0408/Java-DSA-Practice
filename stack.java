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
    static class ArrayStack { // implement stack using array
        int arr[];
        int top=0;
        public ArrayStack() {
            arr=new int[100];
        }
        public void push(int x) {
           arr[top++]=x;
        }
        public int pop() {
            if(isEmpty())
                return -1;
            int k=arr[top-1];
            arr[top-1]=0;
            top--;
            return k;
        }
        public int top() {
            if(isEmpty())
                return -1;
    
            return arr[top-1];
        }
        public boolean isEmpty() {
            return top==0;
        }
    }

    static class QueueStack { // implement stack using queue
        Queue<Integer> q;
        public QueueStack() {
            q = new LinkedList<>();
        }
    
        public void push(int x) {
            q.offer(x);
    
            for (int i = 0; i < q.size() - 1; i++) { // reversing the queue 
                q.offer(q.poll());
            }
        }
    
        public int pop() {
            return q.isEmpty() ? -1 : q.poll();
        }
    
        public int top() {
            return q.isEmpty() ? -1 : q.peek();
        }
    
        public boolean isEmpty() {
            return q.isEmpty();
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
        // Since head of LL is same as top of stack pop head and move head to next
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
        // similar pattern in one of the recursive problems
        if(s.isEmpty()){
            s.push(ch);
            return;
        }
        char c=s.pop();
        pushBottom(s,ch);
        s.push(c);
    }
    public static void ReverseStack(Stack<Character> s){
        // similar pattern in one of the recursive problems : order stack by descending order
        if(s.isEmpty()){
            return;
        }
        char c= s.pop();
        ReverseStack(s);
        pushBottom(s,c);

    }

    public static int priorityOfOperand(char ch){
        if(ch=='^')
            return 3;
        else if(ch=='/' || ch=='*')
            return 2;
        else if(ch=='+' || ch=='-')
            return 1;
        else
            return -1;
    }

    // Expression and their conversions

    /* Infix means basic expression where operators come in the middle fo operands
    ex : a+b*(c-d)

    Postfix means expression where operators come after operands 
    ex : abc*+- // note that there are no parenthesis in the output

    Prefix means expression where operators come before operands
    ex : +*abc-d
    */

    // Infix to Postfix

    /*
    1. Create an empty stack. and follow the below rules
    2. If the symbol is a '(', push it onto the stack.
    3. If the symbol is an operand, push it into the string.
    4. If the symbol is ')', pop the stack and add until you see a '('.
    5. if the symbol is an operator , pop the stack and add it to the expression until you see an operator with higher precedence.
    */

    public static String infixToPostFix(String exp){ // O(n)+ O(n) time 
        Stack<Character> s=new Stack<>();
        int n=exp.length();

        StringBuilder sb=new StringBuilder();

        for(int i=0;i<n;i++){
            char ch=exp.charAt(i);

            // Operand
            if (Character.isLetterOrDigit(ch)) {
                sb.append(ch);
            }
            if(ch=='('){
                s.push(ch);
            }
            else if(ch==')'){
                while( !s.isEmpty() && s.peek()!='('){
                    sb.append(s.pop());
                }
                if (!s.isEmpty()) {
                    s.pop(); // remove '('
                }
            }
            else
            {
                while(!s.isEmpty() && (priorityOfOperand(ch)<=priorityOfOperand(s.peek()) || (priorityOfOperand(ch) == priorityOfOperand(s.peek()) && ch != '^'))){
                    sb.append(s.pop());
                }
                s.push(ch);
            }
        }

        while(!s.isEmpty()){
            sb.append(s.pop());
        }
        System.out.println(sb);

        return sb.toString();

    }

    // Infix to Prefix 

    /*
    1. reverse the expression
        a. Swap the brackets as well if brack is ')' then make it '(' and vice versa 
    2. get the postfix expression
    3. reverse the postfix expression
    */

    public static void infixToPrefix(String exp){
        // Step 1: Reverse
        StringBuilder reversed = new StringBuilder(exp).reverse();

        // Step 2: Swap brackets
        for (int i = 0; i < reversed.length(); i++) {

            if (reversed.charAt(i) == '(')
                reversed.setCharAt(i, ')');

            else if (reversed.charAt(i) == ')')
                reversed.setCharAt(i, '(');
        }

        // Step 3: Get postfix
        String postfix = infixToPostFix(reversed.toString());

        // Step 4: Reverse postfix = prefix
        String prefix = new StringBuilder(postfix).reverse().toString();

        System.out.println(prefix);
    }

    // PostFix To Infix : Simple Logic

    /*
    Steps
    1. If operand push into the stack as is
    2. If operator pop twice from the stack  
        a.make an expression with the two string popped with the operator as the middle
            ex if in stack we have a,b then expression is (a+b) where b is top
        b.push the string expression into the stack 
        c. keep repeating step 2 until the expression is traversed
    3. return the top of the stack 
    */

    public String postfixToInfix(String exp){
        Stack<String> s=new Stack<>();

        for(int i=0;i<exp.length();i++){
            if(Character.isLetterOrDigit(exp.charAt(i))){
                s.push(exp.charAt(i)+""); // pushing char as string 
            }
            else{
                String op1=s.pop();
                String op2=s.pop();
                s.push("("+op2+exp.charAt(i)+op1+")");
            }
        }

        return s.peek();
    }

    // Prefix to infix

    /*
    Similar pattern as above
    1. Start from the end of the expression
    2. If operand push into the stack as is
    3. If operator pop twice from the stack  
        a.make an expression with the two string popped with the operator as the middle
            ex if in stack we have b,a then expression is (a+b) where a is top // THIS IS THE DIFFERENCE BETWEEN PREFIX AND POSTFIX
        b.push the string expression into the stack 
        c. keep repeating step 2 until the expression is traversed
    4. return the top of the stack 
    */

    public String prefixToInfix(String exp){
        Stack<String> s=new Stack<>();
        for(int i=exp.length()-1;i>=0;i--){
            if(Character.isLetterOrDigit(exp.charAt(i))){
                s.push(exp.charAt(i)+""); // pushing char as string
            }
            else{
                String op1=s.pop();
                String op2=s.pop();
                s.push("("+op1+exp.charAt(i)+op2+")"); // THIS IS THE DIFFERENCE BETWEEN PREFIX AND POSTFIX
            }
        }
        return s.peek();
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

    // leetcode 150 Evaluate Reverse Polish Notation

    public int evalRPN(String[] tokens) {
        int ans=0,p1,p2;
        Stack<Integer> s=new Stack<>();
        
        for(int i=0;i<tokens.length;i++)
        {
            if(Character.isDigit(tokens[i].charAt(tokens[i].length()-1)))
            {
                ans=Integer.parseInt(tokens[i]);
                s.push(ans);
            }
            else
            {
                p1=s.pop();
                p2=s.pop();
                if(tokens[i].equals("+"))
                    s.push(p1+p2);
                else if(tokens[i].equals("-"))
                    s.push(p2-p1);
                else if(tokens[i].equals("/"))
                    s.push(p2/p1);
                else if(tokens[i].equals("*"))
                    s.push(p2*p1);
            }
        }

        return s.peek();
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
    //leetcode 921 Minimum Add to Make Parentheses Valid
    // same problem solved with strings also
    public int minAddToMakeValid(String s) {
        Stack<Character> st= new Stack<>();
        for(int i=0;i<s.length();i++)
        {
            char ch=s.charAt(i);
            if(!st.isEmpty() && (ch==')' && st.peek()=='('))
            {
                st.pop();
                continue;
            }
            st.add(ch);
        }

        return st.size();
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

    // Trapping Rain Water using Stack , Similar to NGE problem
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
    

    // leetcode 1381
    // increment to the k bottom elements of the stack with val
    class CustomStack {
        int n,top=0;
        int stack[];
        public CustomStack(int maxSize) {
            stack=new int[maxSize];
            n=maxSize;
        }
        
        public void push(int x) {
            if(top==n)
                return;
    
            stack[top++]=x;
        }
        
        public int pop() {
            if(isEmpty())
                return -1;
    
            int val=stack[top-1];
            stack[top-1]=0;
            top--;
            return val;
        }
        
        public void increment(int k, int val) {
            int limit = Math.min(k, top);
            for (int i = 0; i < limit; i++) {
                stack[i] += val;
            }
        }
        private boolean isEmpty() {
            return top==0;
        }
    }

    // Min stack leetcode 155

    /*
    List to store the min as well 
    The way it works is create a node with min and val and next
    And store the numbers in such a way that the val gets added at head 
    Ex stack has top -> 1,2,3 and we have a list 2->3 so to store 1
    we create a node with 1 and min=1 and next=2->3 so it becomes 1->2->3

    */
    class MinStack {
        private class Node{
            Node next;
            int min,val;
    
            Node(int val,int min, Node nex)
            {
                this.min=min;
                this.val=val;
                this.next=nex;
            }
        }
    
        Node list;
        public MinStack() {
            
        }
        
        public void push(int val) {
            if(list==null)
            {
                list=new Node(val,val,null);
            }
            else{
                int mon = Math.min(val,list.min);
                list=new Node(val,mon,list); // create new node on top of the exiting list and store the stack there 
            }
        }
        
        public void pop() {
            list=list.next;
        }
        
        public int top() {
            return list.val;
        }
        
        public int getMin() {
            return list.min;
        }
    }

    // Monotonic stack problems

    // leetcode 496 next greater element

    /*
    Monotonic stack : A stack where the elements are in increasing or decreasing order 
    ans to store the next greater element of nums 1 by comparing nums 2
    nge to store next greater of all the elments in nums 2
    Stack has to be in decreasing order so that we can get the next greater element where top is the smallest element and the bottom is the greatest
    By ensuring that stack is in decreasing order of elemnts post index i , the next gretaer of nums2[i] will be the top of the stack
    */

    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        Stack s=new Stack<>();
        int ans[]=new int[nums1.length];
        int nge[]=new int[nums2.length];

        for(int i=nums2.length-1;i>=0;i--)
        {
            while(!s.isEmpty() && (int)s.peek()<=nums2[i])
                s.pop(); // done to preserve the decreasing order ex if stack has 1,2,4,6 and we get 5 then stack will be 5,6

            if(s.isEmpty())
                nge[i]=-1;
            else{
                nge[i]=(int)s.peek();
            }
            s.push(nums2[i]);
        }

        for(int i=0;i<nums1.length;i++){
            for(int j=0;j<nums2.length;j++){
                if(nums1[i]==nums2[j])
                    ans[i]=nge[j];
            }
        }

        return ans;
    }

    // leetcode 503 next greater element 2

    /*
    Same pattern as above , but since the array is circular instead of cheking 0...n we have to check i...i+n for each i 
    and nge[i] is the next greater elemnt while traversing among i...i+n
    REMEBER ITS JUST A MOD N CHANGE
    */

    public int[] nextGreaterElements(int nums[]){
        int n=nums.length;
        int nge[]=new int[2*n];
        Stack s=new Stack<>();
        for(int i=2*n-1;i>=0;i--)
        {
            while(!s.isEmpty() && (int)s.peek()<=nums[i%n])
                s.pop();

            if(s.isEmpty())
                nge[i]=-1;
            else{
                nge[i]=(int)s.peek();
            }
            s.push(nums[i%n]);
        }
        int ans[]=new int[n];
        for(int i=0;i<n;i++)
            ans[i]=nge[i];

        // Copy the nge array from 0 to n only to get the correct answer. first n elements in the nge array is the answer
        return ans;
    }
    // next smaller element problem
    // exactly same as next greater element pattern
    public int[] nextSmallerElements(int[] arr) { 
        int n=arr.length;
        int nse[]=new int[n];
        Stack<Integer> s=new Stack<>();
        for(int i=n-1;i>=0;i--)
        {
            while(!s.isEmpty() && (s.peek()>=arr[i]))
                s.pop();
            if(s.isEmpty())
                nse[i]=-1;
            else
                nse[i]=s.peek();
            s.push(arr[i]);
        }

        return nse;

    }

    // Previous smaller element  
    public int[] previousSmallerElements(int[] arr) { 
        int n=arr.length;
        int pse[]=new int[n];
        Stack<Integer> s=new Stack<>();
        for(int i=n-1;i>=0;i--)
        {
            while(!s.isEmpty() && (s.peek()>arr[i]))
                s.pop();
            if(s.isEmpty())
                pse[i]=-1;
            else
                pse[i]=s.peek();
            s.push(arr[i]);
        }

        return pse;

    }

    // previous greater element

    public int[] previousGreaterElements(int[] arr) {

        int n = arr.length;
        int[] pge = new int[n];
        Stack<Integer> s = new Stack<>();
    
        for (int i = 0; i < n; i++) {
    
            while (!s.isEmpty() && s.peek() < arr[i]) {
                s.pop();
            }
    
            if (s.isEmpty())
                pge[i] = -1;
            else
                pge[i] = s.peek();
    
            s.push(arr[i]);
        }
    
        return pge;
    }
    // Count the number of NGE in an array for a given indice

    /*
    Example 1

Input: arr = [3, 4, 2, 7, 5, 8, 10, 6], indices = [0, 5]
Output: [6, 1]
Explanation:

For index 0 → arr[0] = 3, elements greater than 3 to its right are [4, 7, 5, 8, 10, 6] → count = 6.
For index 5 → arr[5] = 8, greater elements to the right are [10] → count = 1.
    */
    public List<Integer> count_NGE(int[] arr, int[] indices) {
        int n=arr.length;
        int nse[]=new int[n];
        Stack<Integer> s=new Stack<>();
        for(int i=n-1;i>=0;i--)
        {
            while(!s.isEmpty() && (s.peek()<=arr[i]))
                s.pop();
            if(s.isEmpty())
                nse[i]=-1;
            else
                nse[i]=s.peek();
            s.push(arr[i]);
        }
        List<Integer> zeze= new ArrayList<>();
        for(int i:indices)
        {
            int count=0;
            for(int j=i;j<nse.length;j++)
            {
                if(nse[j]==-1)
                    break;
                count++;
            }
            zeze.add(count);
        }

        return zeze;
    }

    // leetcode 902 Sum of Subarray Minimums 

    // PLEASE GO THOUGH THE LOGIC AGIN
    /*
    LC 907 - Sum of Subarray Minimums

        PSE
        NSE
        One final traversal
        Time
        PSE : O(n)
        NSE : O(n)
        Final Contribution Loop : O(n)

        Total = O(n)

        Why not O(n²)?

        Because in monotonic stack:

        Each index is pushed once
        Each index is popped once

        So total stack operations are at most 2n.

        Space
        PSE array : O(n)
        NSE array : O(n)
        Stack     : O(n)

        Total = O(n)
    */
    public int sumSubarrayMins(int[] arr) { // almost the same as largest rectangle in histogram
        int mod = 1000000007;
        int[] nse = nextSmallerElements(arr); // HERE IN NSE AND PSE WE ARE STORING INDICES IN THE STACK AND NOT ELEMENTS 
        int[] pse = previousSmallerElements(arr); // HERE IN NSE AND PSE WE ARE STORING INDICES IN THE STACK AND NOT ELEMENTS 
        // CHECK IN CLASS class Solution 

        long ans = 0;
        for (int i = 0; i < arr.length; i++) {

            long left = i - pse[i];
            long right = nse[i] - i;
            ans = (ans + (left * right % mod) * arr[i] % mod) % mod;
        }
        return (int) ans;
        
    }

    // leetcode 735 Asteroid Collison

    public int[] asteroidCollision(int[] asteroids) {
        
        Stack<Integer> st=new Stack<>();
        int n=asteroids.length;
        for(int i=0;i<n;i++)
        {
            if(asteroids[i]>0)
            {
                st.push(asteroids[i]);
            }
            else
            {
                while(!st.isEmpty() && st.peek()>0 && st.peek()<(int)Math.abs(asteroids[i]))
                    st.pop(); // pse/nse/nge pattern 

                if(!st.isEmpty() && st.peek()==(int)Math.abs(asteroids[i]))
                    st.pop();
                else if(st.isEmpty() || st.peek()<0)
                    st.push(asteroids[i]);
            }
        }

        int[] arr = st.stream().mapToInt(Integer::intValue).toArray();
        return arr;
    }

    //Leetcode 402 Remove K Digits

    public String removeKdigits(String num, int k) {
        if(num.length()==k)
            return "0";
        
        Stack<Character> st=new Stack<>();
        for (int i = 0; i < num.length(); i++) {
            char ch = num.charAt(i);
            while (k > 0 && !st.isEmpty() && st.peek() > ch) {
                st.pop();
                k--;
            }
            st.push(ch);
        }

        // remove remaining digits from end
        while (k > 0 && !st.isEmpty()) {
            st.pop();
            k--;
        }
        StringBuilder ans = new StringBuilder();
        while (!st.isEmpty()) {
            ans.append(st.pop());
        }
        ans.reverse();
        int fir=0;
        while(fir<ans.length()-1 && ans.charAt(fir)=='0')
            fir++;
        
        if(fir>0)
            ans.delete(0,fir);
        return ans.isEmpty()?"0":ans.toString();
    }

    // Leetcode 2104 

    // Please go through the logic again

    /*
    The idea is:

Answer =
(sum of contribution as maximum)
-
(sum of contribution as minimum)

For every element:

Contribution as Maximum
=
arr[i] * (# subarrays where arr[i] is max)

Contribution as Minimum
=
arr[i] * (# subarrays where arr[i] is min)

So we need:

PSE + NSE  -> minimum contribution
PGE + NGE  -> maximum contribution
    */

    class Solution {

        /*
            LC 2104 - Sum of Subarray Ranges

            You compute:

            PSE
            NSE
            PGE
            NGE
            Contribution Loop
            Time
            PSE : O(n)
            NSE : O(n)
            PGE : O(n)
            NGE : O(n)
            Final Loop : O(n)

            Total:

            O(5n)
            =
            O(n)
            Space
            PSE : O(n)
            NSE : O(n)
            PGE : O(n)
            NGE : O(n)
            Stack : O(n)

            Total:

            O(5n)
            =
            O(n)
        */

        public long subArrayRanges(int[] nums) { // this method has similar pattern as leetcode 902 sum of subarray minimum written above 
    
            int[] nse = nextSmallerElements(nums); // STORING INDICES INSTEAD OF ELEMENTS
            int[] pse = previousSmallerElements(nums);// STORING INDICES INSTEAD OF ELEMENTS
    
            int[] nge = nextGreaterElements(nums);// STORING INDICES INSTEAD OF ELEMENTS
            int[] pge = previousGreaterElements(nums);// STORING INDICES INSTEAD OF ELEMENTS 
    
            long minSum = 0;
            long maxSum = 0;
    
            for (int i = 0; i < nums.length; i++) {
    
                long leftMin = i - pse[i];
                long rightMin = nse[i] - i;
    
                minSum += (long) nums[i] * leftMin * rightMin;
    
                long leftMax = i - pge[i];
                long rightMax = nge[i] - i;
    
                maxSum += (long) nums[i] * leftMax * rightMax;
            }
    
            return maxSum - minSum;
        }
    
        public int[] nextSmallerElements(int[] arr) {
            int n = arr.length;
            int[] nse = new int[n];
            Stack<Integer> s = new Stack<>();
    
            for (int i = n - 1; i >= 0; i--) {
    
                while (!s.isEmpty() && arr[s.peek()] >= arr[i])
                    s.pop();
    
                nse[i] = s.isEmpty() ? n : s.peek();
    
                s.push(i);
            }
    
            return nse;
        }
    
        public int[] previousSmallerElements(int[] arr) {
            int n = arr.length;
            int[] pse = new int[n];
            Stack<Integer> s = new Stack<>();
    
            for (int i = 0; i < n; i++) {
    
                while (!s.isEmpty() && arr[s.peek()] > arr[i])
                    s.pop();
    
                pse[i] = s.isEmpty() ? -1 : s.peek();
    
                s.push(i);
            }
    
            return pse;
        }
    
        public int[] nextGreaterElements(int[] arr) {
            int n = arr.length;
            int[] nge = new int[n];
            Stack<Integer> s = new Stack<>();
    
            for (int i = n - 1; i >= 0; i--) {
    
                while (!s.isEmpty() && arr[s.peek()] <= arr[i])
                    s.pop();
    
                nge[i] = s.isEmpty() ? n : s.peek();
    
                s.push(i);
            }
    
            return nge;
        }
    
        public int[] previousGreaterElements(int[] arr) {
            int n = arr.length;
            int[] pge = new int[n];
            Stack<Integer> s = new Stack<>();
    
            for (int i = 0; i < n; i++) {
    
                while (!s.isEmpty() && arr[s.peek()] < arr[i])
                    s.pop();
    
                pge[i] = s.isEmpty() ? -1 : s.peek();
    
                s.push(i);
            }
    
            return pge;
        }
    }

    // leetcode 901 online stock span

    class StockSpanner {
        List<Integer> prices;
        Stack<Integer> st;
        public StockSpanner() {
            prices = new ArrayList<>();
            st = new Stack<>();
        }
        public int next(int price) { // this is done because the code is running for one ele at one time no need for the outer for loop , PGE logic 
        // This method will add a new element everytime and use the existing stack and list to the PGE finding 
        // Span = index of price in the list - pge (top of the stack)
    
            prices.add(price);
            int idx = prices.size() - 1;
            while (!st.isEmpty() && prices.get(st.peek()) <= price) {
                st.pop();
            }
            int pge = st.isEmpty() ? -1 : st.peek();
            st.push(idx);
    
            return idx - pge;
        }
    }

    // largest area in histogram  leetcode 84

    public int largestRectangleArea(int[] heights) {
        int maxArea=0;
        // need nse and pse for width that can be accounted for a particular height
        Stack<Integer> s=new Stack<>();
        int nsr[]=nextSmallEle(s,heights);
        s=new Stack<>(); // empty current stack
        int nsl[]=previousSmallEle(s,heights);

        for(int i=0;i<heights.length;i++){
            int height=heights[i];
            int width= nsr[i]-nsl[i]-1; 
            // will be guranteed to make the calc max width including right and left side  for a particular height[i]
            int currArea= height* width;
            maxArea=Math.max(maxArea,currArea);
        }
        return maxArea;
    }
    public int[] previousSmallEle(Stack<Integer> s,int[] heights )
    {
        int pse[]=new int[heights.length];
        for(int i=0;i<heights.length;i++)
        {
            while(!s.isEmpty() && heights[s.peek()]>= heights[i])
                s.pop();

            if(s.isEmpty())
                pse[i]=-1;
            else
                pse[i]=s.peek();
            s.push(i);
            
        }

        return pse;
    }
    public int[] nextSmallEle(Stack<Integer> s,int[] heights )
    {
        int nse[]=new int[heights.length];
        for(int i=heights.length-1;i>=0;i--)
        {
            while(!s.isEmpty() && heights[s.peek()]>= heights[i])
                s.pop();

            if(s.isEmpty())
                nse[i]=heights.length;
            else
                nse[i]=s.peek();
            s.push(i);
            
        }

        return nse;
    }



    public int largestRectangleAreaOptimized(int[] heights) {
        Stack<Integer> st = new Stack<>();
        int maxArea = 0;
        int n = heights.length;
        // only pse logic is applied here
        // but if else under the loop 
        for (int i = 0; i <= n; i++) {
            int currHeight = (i == n) ? 0 : heights[i];
            while (!st.isEmpty() && currHeight < heights[st.peek()]) {
                int height = heights[st.pop()];
                int width;
                if (st.isEmpty()) {
                    width = i;
                } else {
                    width = i - st.peek() - 1;
                }
                maxArea = Math.max(maxArea, height * width);
            }
            st.push(i);
        }
        return maxArea;
    }

    // leetcode 85 
    public int maximalRectangle(char[][] matrix) {
        int rowArea=0; int ans=0;
        int rows=matrix.length;
        int cols=matrix[0].length;
        int[] prefixArr=new int[cols];
        
        for(int i=0;i<rows;i++)
        {
            for(int j=0;j<cols;j++)
            {
                if(matrix[i][j] == '1')
                    prefixArr[j]++;
                else
                    prefixArr[j] = 0;

                System.out.print(prefixArr[j]+" ");
            }
            rowArea=largestRectangleAreaOptimized(prefixArr);
            ans=Math.max(rowArea,ans);
        }

        return ans;
    }
    public int largestRectangleAreaOpt(int[] heights) {
        Stack<Integer> st = new Stack<>();
        int maxArea = 0;
        int n = heights.length;
        // only pse logic is applied here
        // but if else under the loop 
        for (int i = 0; i <= n; i++) {
            int currHeight = (i == n) ? 0 : heights[i]; // going till nand not n-1
            while (!st.isEmpty() && currHeight < heights[st.peek()]) {
                int height = heights[st.pop()];
                int width;
                if (st.isEmpty()) {
                    width = i;
                } else {
                    width = i - st.peek() - 1;
                }
                maxArea = Math.max(maxArea, height * width);
            }
            st.push(i);
        }
        return maxArea;
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
