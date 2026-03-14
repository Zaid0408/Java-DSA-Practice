import java.util.*;

import org.w3c.dom.Node;
public class ListNode2 {
    public class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }
    public ListNode partition(ListNode head, int x) { // leetcode 86
        // partion but preserve relative order.
        // All nodes less than x have to come before x in the list while maintaining their order in head list.
        // followed by all 
        ListNode before_head=new ListNode(-1);
        ListNode after_head=new ListNode(-1);
        ListNode before=before_head;
        ListNode after=after_head;
        
        while(head!=null)
        {
            if(head.val<x)
            {   
                before.next=head;
                before=before.next;
            }
            else 
            {
                after.next=head;
                after=after.next;
            }
            head=head.next;
        }
        //System.out.println(head.val);
        after.next=null; // this was the issue . if this was not made null the list is cyclic error was coming
        before.next=after_head.next;
        return before_head.next;
    }
    // leetcode 328 Odd Even Linked List Odd position comes first all of them then append remaining even position at the end
    // same pattern as above 
    public ListNode oddEvenList(ListNode head) {
        int position = 0;
        ListNode oddHead = new ListNode(0);
        ListNode evenHead = new ListNode(0);
        
        ListNode odd = oddHead; // to traverse head
        ListNode even = evenHead;
        
        while(head != null){
            position++;
            if(position % 2 == 0){
                even.next = head;
                even = even.next;
            } else{
                odd.next = head;
                odd = odd.next;
            }
            head = head.next;
        }
        odd.next = evenHead.next;
        even.next = null;
        return oddHead.next;
    }

    // leetcode 206 Reverse Linked List
    public ListNode reverseList(ListNode head) {
        if(head==null)
            return null;
        ListNode prev=null;
        ListNode cur=head;
        ListNode nex=head.next;
        while(cur!=null){
            cur.next=prev;
            prev=cur;
            cur=nex;
            if(nex==null)
                continue;
            else 
                nex=nex.next;
        }
        return prev;
    }
    // recursion
    public ListNode reverseListRecursion(ListNode head) {
        if(head==null || head.next==null )
            return head;

        ListNode temp= reverseListRecursion(head.next);
        head.next.next=head;
        head.next=null; // to prevent cyclic error

        return temp;
    }

    public ListNode reverseDLL(ListNode head) {
    ListNode current = head;
    ListNode temp = null;

    // Traverse through the list and swap next and prev pointers
    while (current != null) {
        // Swap the next and prev pointers of the current node
        // temp = current.prev;
        // current.prev = current.next;
        // current.next = temp;

        // Move to the next node (which is now stored in current.prev because of the swap)
        // current = current.prev;
    }

    // Before the while loop finishes, temp holds the reference to the last node
    // of the original list, which is the new head.
    // Need to handle the case where the list was empty or single node.
    if (temp != null) {
        // head = temp.prev;  The new head is the node that was originally the last node
    }
    
    return head;
}

    // leetcode 234 Palindrome Linked List
    public boolean isPalindrome(ListNode head) {
        ListNode sl=split(head);
        ListNode rev= reverseList(sl);

        return palindrome(head,rev); // impo to pass head and not 

    }
    public boolean palindrome(ListNode sl,ListNode rev)
    {
        while(rev!=null)
        {
            if(sl.val!= rev.val)
                return false;
            sl=sl.next;
            rev=rev.next;
        }

        return true;
    }
    public ListNode split(ListNode head) {  
        ListNode fast = head;
        ListNode slow = head;

        while(fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        return slow;
    }

    //leetcode 142  Linked List cycle 2 
    // Detect from where the cycle starts wherever fast and slow meet initially that means cycle is present 
    // post that set fast to head and increment each pointer once eventually they will reach the start of cycle
    // mathematical proof : https://leetcode.com/problems/linked-list-cycle-ii/description/comments/1567596/ 
    public ListNode detectCycle(ListNode head) {
        ListNode fast = head;
        ListNode slow = head;

        while(fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if(fast==slow){
                fast=head;
                while(fast != slow) {
                    slow = slow.next;
                    fast = fast.next;
                }

                return fast;
            }
        }
        return null;
    }

    //leetcode 2
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) { // leetcode 2
        int j=0,k=0,carry=0;
        ListNode ans=new ListNode();
        ListNode temp=ans;
        while(l1 != null || l2 !=null || carry!=0)
        {
            j= l1!=null?l1.val:0;
            k= l2!=null?l2.val:0;

            // new digit
            int val= (j+k+ carry);
            carry= val/10; // calc carry before val 
            val=val%10;
            temp.next=new ListNode(val);

            // update the pointers
            temp=temp.next;
            l1= l1!=null?l1.next:null;
            l2= l2!=null?l2.next:null;

        }
        return ans.next;
        
    }
    public ListNode removeNthFromEnd(ListNode head, int n) { // leetcode 19
        ListNode temp=head;
        int size=0;
        
        if(head.next==null ){
            return null;
        }
        if(n==1){
            temp.next=null;
            return head;
        }
        while(temp!=null){
            temp=temp.next;
            size++;
        }
        if(size==n)
        {
            head=head.next;
            return head;
        }
        ListNode t= head;
        int i=0;
        while(i<size-n-1)
        {
            t=t.next;
            i++;
        }
        t.next=t.next.next;
        return head;
    }
    public ListNode sortList(ListNode head) { // leetcode 148
        if(head==null || head.next==null)
            return head;
        ListNode temp=head;
        List<Integer> ans=new ArrayList<>();
        while(temp!=null)
        {
            ans.add(temp.val);
            temp=temp.next;
        }
        Collections.sort(ans);
        int i=0;
        temp=head;
        while(temp!=null)
        {
            temp.val=ans.get(i++);
            temp=temp.next;
        }
        return head;

    }
    // leetcode 160 Intersection of Two Linked Lists
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        ListNode temp=headA;
        ListNode tem=headB;

        while(temp!=tem)
        {
            temp = temp==null?headB:temp.next;
            tem = tem==null?headA:tem.next;
        }

        return temp;
    }
    class LRUCache { // leetcode 146
        LinkedHashMap<Integer,Integer> ans;
        public LRUCache(int capacity) {
            ans=new LinkedHashMap<Integer,Integer>(capacity,0.75f,true){
                @Override
                protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
                    return size() > capacity;
                }
            };
    
        }
        
        public int get(int key) {
            // if(ans.containsKey(key))
            //     return ans.get(key);
            // else
            //     return -1;
            return ans.getOrDefault(key,-1);
        }
        
        public void put(int key, int value) {
            ans.put(key,value);
            
        }
    }
    
}
