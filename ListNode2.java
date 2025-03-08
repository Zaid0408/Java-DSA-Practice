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
}
