import java.util.*;

import javax.swing.tree.TreeNode;
public class BST {
    static class Node{
        int data;
        Node left;
        Node right;
        Node(int data){
            this.data=data;
            this.left=null;
            this.right=null;
        }
    }
    // leetcode 701 Insert into a Binary Search Tree
    public static Node insert(Node root, int data){
        if(root==null){
            return new Node(data);
        }
        if(data<root.data){
            root.left=insert(root.left,data);
        }
        else{
            root.right=insert(root.right,data);
        }
        return root;
    }
    // Difference between BST and BT is that 
    /*
        In a BST, the left subtree is always smaller than the right subtree.
        Left Subtree and right subtree are also BST in case of a BST 
        L<N<R should be the format of the BST. 
        in case of duplicates L<=N<R something similar but very rare

        height of BST O(log n)
    */

    public List<Integer> floorCeilOfBST(Node root, int key) {
        List<Integer> ans=new ArrayList<>();
        int floor=floorOfBST(root,key);
        int ceil = ceilOfBST(root,key);

        ans.add(floor);
        ans.add(ceil);

        return ans;
    }
    public int floorOfBST(Node root, int key)
    {
        int floor=-1;
        while(root!=null)
        {
            if(root.data==key)
            {
                floor=root.data;
                return floor;
            }
            if(root.data < key)
            {
                floor=root.data;
                root=root.right;
            }
            else{
                root=root.left;
            }
        }
        return floor<key?floor:-1;
    }
    public int ceilOfBST(Node root, int key)
    {
        int ceil=-1;
        while(root!=null)
        {
            if(root.data==key)
            {
                ceil=root.data;
                return ceil;
            }
            if(root.data < key)
            {
                ceil=root.data;
                root=root.right;
            }
            else{
                return root.data;
            }
        }
        return ceil>key?ceil:-1;
    }
    public static void InOrder(Node root)// print inorder of bst . Should always be in ascending order.
    {
        if(root==null){
            return;
        }
        InOrder(root.left);
        System.out.print(root.data+" ,");
        InOrder(root.right);
    }
    public static boolean SearchBST(Node root, int data){
        if(root==null){
            return false;
        }
        if(root.data==data){
            return true;
        }
        if(data<root.data){
            return SearchBST(root.left,data);
        }
        else{
            return SearchBST(root.right,data);
        }
    }
    public static Node GetInorderSuccessor(Node root){
        while (root.left!=null) {
            root=root.left;
            
        }
        return root;
    }
    // leetcode 450 Delete Node in a BST
    public static Node delete(Node root, int data){
        if(root == null){
            return null;
        }
        if(root.data>data){
            root.left=delete(root.left,data);
        }
        else if(root.data<data){
            root.right=delete(root.right,data);
        }   
        else if(root.data==data){
            if(root.left==null && root.right==null){
                return null;
            }
            // single child case
            else if(root.left==null)
            {
                return root.right;
            }
            else if(root.right==null){
                return root.left;
            }
            else{
                // replace with the minimum value or left most node in the right subtree
                Node minRight=GetInorderSuccessor(root.right);
                root.data=minRight.data;
                root.right=delete(root.right,minRight.data);
            }
        }
        return root;
    }
    public static void path2Leaf(Node root, List<Integer> path,List<List<Integer>> ans){
        if(root==null){
            ans.add(path);
            return;
        }
        path.add(root.data);
        if(root.left==null && root.right==null){
            ans.add(path);
        }
        // all possible paths from root to leaf nodes stored in ans
        path2Leaf(root.left, path, ans);
        path2Leaf(root.right, path, ans);
        path.remove(path.size()-1);
    }
    public static boolean IsValidTree(Node root,Node min,Node max){
        // check if bst is valid and satifies all constraints
        // for valid condition is maximum value of left subtree must be < than node 
        // and minimum value of right subtree must be > than node
        if(root==null){
            return true;
        }
        if(min!=null && root.data<=min.data){
            return false;
        }
        if(max!=null && root.data>=max.data){
            return false;
        }
        return IsValidTree(root.left,min,root) && IsValidTree(root.right,root,max);
    }
    public static Node InvertTree(Node root){
        // inverting or creating a mirror image of the tree
        if(root==null){
            return null;
        }
        Node left=InvertTree(root.left);
        Node right=InvertTree(root.right);
        root.left=right;
        root.right=left;
        return root;
    }
    // sorted array to a bst 
    public static Node ArrToBST(int[] arr, int start, int end){
        if(start<=end){
            int mid=(start+end)/2;
            Node root=new Node(arr[mid]);
            root.left=ArrToBST(arr, start, mid-1);
            root.right=ArrToBST(arr, mid+1, end);
            return root;
        }
        return null;
        
    }

    //leetcode 230 Kth Smallest Element in a BST
    public int kthSmallest(Node root, int k) {
        List<Integer> oa=new ArrayList<>();
        kthSmall(root, oa);
        return oa.get(k-1);
    }
    private void kthSmall(Node root, List<Integer> oa)
    {
        // inorder traversal of a BST
        // this traversal will ensure that all numbers in the list are always in ascending/nondecreaseing order hence kth smallest is at k-1th posistion
        if(root==null)
            return;
        
        kthSmall(root.left,oa);
        oa.add(root.data);
        kthSmall(root.right,oa);
        
    }

    // leetcode 98 Validate Binary Search Tree
    // intuition:
    /*
        Every node has an allowed range. Initially: (-∞, +∞)
Suppose the root is 5. Then:
          5
      (-∞,+∞)

      /       \
 (-∞,5)[3]    (5,+∞)[7]
      \
 (3,5)[4]

Now suppose we go left to 3.
Its range becomes
(-∞,5)

Go right to 4. Its range becomes
(3,5)

Every recursive call narrows the valid range. If any node falls outside its range, it's not a BST.
    *
     */
    public boolean isValidBST(Node root) {
        return dfs(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }
    private boolean dfs(Node node, long low, long high) {
        if(node == null)
            return true;
        if(node.data <= low || node.data >= high)
            return false;
        return dfs(node.left, low, node.data) && dfs(node.right, node.data, high);
    }

    // leetcode 235  Lowest Common Ancestor of a Binary Search Tree
    /* here three things which make the time smaller that normal bst
    1. If p.data and q.data are both less than root.data, then the lowest common ancestor must be in the left subtree. so check left only
    2. If p.data and q.data are both greater than root.data, then the lowest common ancestor must be in the right subtree. so check right only
    3. Otherwise due to BST property of left being the smallest and right being largest, either p or q is smallest hence root is the LCA
     */
    public Node lowestCommonAncestor(Node root, Node p, Node q) {
        if( root==p || root==q|| root==null){
            return root;
        }
        Node node=null;
        if(root.data>p.data && root.data>q.data)
            return lowestCommonAncestor(root.left, p, q);
        else if(root.data<p.data && root.data<q.data)
            return lowestCommonAncestor(root.right, p, q);
        return root;
    }

    // leetcode 1008 Construct Binary Search Tree from Preorder Traversal 
    // check strivers video

    public Node bstFromPreorder(int[] preorder) {
        return  bstFromPreorder(preorder,Integer.MAX_VALUE,new int[]{0});
    }
    public Node bstFromPreorder(int[] preorder, int upperBound, int z[])
    {
        if(z[0]==preorder.length || preorder[z[0]]>upperBound)
            return null;
        
        Node node=new Node(preorder[z[0]++]);
        node.left=bstFromPreorder(preorder,node.data,z);
        node.right=bstFromPreorder(preorder,upperBound,z);
        return node;
    }
    // leetcode 173 Binary Search Tree Iterator
    // tc O(N) space complexity O(H)
    class BSTIterator {
        Stack<Node> st=new Stack<>();
        public BSTIterator(Node root) {
            Node node=root;
            while(node!=null)
            {
                st.push(node);
                node=node.left;
            }
        }
        
        public int next() {
            Node cur = st.peek();
            st.pop();
            if(cur.right!=null)
            {
                Node x=cur.right;
                while(x!=null)
                {
                    st.push(x);
                    x=x.left;
                }
            }
            return cur.data;
        }
        
        public boolean hasNext() {
            if(st.isEmpty()==true)
                return false;
            return true;
        }
    }

    // leetcode 653 Two SUm in binary search tree

    // please check video soln 

    class BSTIterator1 {
        Stack<Node> st=new Stack<>();
        boolean reverse=true;
        public BSTIterator1(Node root, boolean rev) {
            reverse=rev;
            pushAll(root);
        }
        public void pushAll(Node node)
        {
            while(node!=null)
            {
                st.push(node);
                if(reverse==true)
                    node=node.right;
                else
                    node=node.left;
            }
        }
        
        public int next() {
            Node cur = st.pop();
            if(reverse==false)
                pushAll(cur.right);
            else
                pushAll(cur.left);
            return cur.data;
        }
        
        public boolean hasNext() {
            return !st.isEmpty();
        }
    }
    
    class Solution {
        public boolean findTarget(Node root, int k) {
            if(root==null) return false;
    
            BSTIterator1 l=new BSTIterator1(root,false);
            BSTIterator1 r=new BSTIterator1(root,true);
    
            int i=l.next();int j=r.next();
    
            while(i<j)
            {
                if(i+j ==k) return true;
                else if(i+j <k)
                    i=l.next();
                else
                    j=r.next();
            }
    
            return false;
        }
    }


    /*
     * Note: Convert BST to Balanced BST
     *  To convert unbalanced bst to a balanced bst , take inorder of that tree store it in arraylist
     * use the arraylist to create a bst with the ArrToBST(int[] arr, int start, int end) function 
     * 
     */
    public static List<Integer> getInorderFromTree(Node Root)
    {
        List<Integer> inorder = new ArrayList<>();
        if(Root == null)
            return inorder;
        inorder.addAll(getInorderFromTree(Root.left));
        inorder.add(Root.data);
        inorder.addAll(getInorderFromTree(Root.right));
        return inorder;
    }
    /*
     * Function above and below will combine 2 bst into 1
     * get the inorder of both the trees and combine them
     * sort them in ascending order and return the inorder of merged tree
     */
    public static List<Integer> combineBST(Node root1, Node root2){
        List<Integer> inorder1 = getInorderFromTree(root1);
        List<Integer> inorder2 = getInorderFromTree(root2);
        inorder1.addAll(inorder2);// add all values of inorder2 to inorder1
        inorder1.sort(Comparator.naturalOrder());// sort the arraylist inorder1
        return inorder1;// return inorder1 which is inorder of new bst
    }
    public static void main(String[] args) {
        int arr[]={1,2,3,4,5,6,7};
        Node root=ArrToBST(arr, 0, arr.length-1);
        InOrder(root);
    }
}
