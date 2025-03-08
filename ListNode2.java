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
        after.next=null; // this waqs the issue . if this was not made null the list is cyclic error was coming
        before.next=after_head.next;
        return before_head.next;
    }
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
            carry= val/10;
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
}
