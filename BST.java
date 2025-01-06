import java.util.*;
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
    public static Node delete(Node root, int data){
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
