import java.util.Queue;
import java.util.Stack;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class BinTree {
    static class node{
        int data;
        node left;
        node right;
        node(int data){
            this.data=data;
            this.left=null;
            this.right=null;
        }
    }
    static class BinaryTree{
        static int idx=-1;
        static node maketreePre(int nodes[]){// make tree from given preordered list
            idx++;
            if(nodes[idx]==-1 || idx>=nodes.length){
                return null;
            }
            node newNode=new node(nodes[idx]);
            newNode.left=maketreePre(nodes);
            newNode.right=maketreePre(nodes);
            return newNode;
        }
        
        
        void PreOrder(node root){ // PreOrder Traversal
            if(root==null){
                System.out.print("null ,");
                return;
            }
            System.out.print(root.data+" ,");
            PreOrder(root.left);
            PreOrder(root.right);
        }
        void PostOrder(node root){// PostOrder Traversal
            if(root==null){
                System.out.print("null ,");
                return;}
            PostOrder(root.left);
            PostOrder(root.right);
            System.out.print(root.data+" ,");
        }
        void InOrder(node root){//  InOrder Traversal
            if(root==null){
                System.out.print("null ,");
                return;}
            InOrder(root.left);
            System.out.print(root.data+" ,");
            InOrder(root.right);
        }
        void levelOrder(node root){ // Iterative approach
            /*
             * Remember that level order traversal is a breadth first search technique.
             * check leetcode question level order for better approach
             */
            if(root==null){
                return;
            }
            Queue<node> q=new LinkedList<>();
            q.add(root);
            q.add(null);
            while(!q.isEmpty()){
                node curr=q.remove();
                if(curr==null){
                    System.out.println();
                    if(q.isEmpty()){
                        break;
                    }
                    else{
                        q.add(null);
                        continue;
                    }
                }
                else{
                    System.out.print(curr.data+" ,");
                    if(curr.left!=null){
                        q.add(curr.left);
                    }
                    if(curr.right!=null){
                        q.add(curr.right);
                    }
                }
            }
            
        }
        public void traverse(node root,List<List<Integer>> ans, int level) // level=0 initially
        { // Level order traversal store result in ans arraylist.
            // bfs traversal recursion approach
            if(root==null)
                return;
            if(ans.size()==level){
                List<Integer> l=new ArrayList<>();
                l.add(root.data);
                
            }
            else{
                ans.get(level).add(root.data);
            }
            traverse(root.left,ans,level+1);
            traverse(root.right,ans,level+1);
        }
        public void dfs(node root) {
            // dfs traversal on tree iterative
            if (root == null) return;

            Stack<node> stack = new Stack<>();
            stack.push(root);

            while (!stack.isEmpty()) {
                node currentNode = stack.pop();
                System.out.print(currentNode.data + " ");  // Visit the node

                // Push right child first so that left child is processed first
                // when we pop the left child willget printed first
                if (currentNode.right != null) {
                    stack.push(currentNode.right);
                }
                if (currentNode.left != null) {
                    stack.push(currentNode.left);
                }
            }
        }
        public void dfs2(node root, int depth,List<List<Integer>> result) {
            if (root == null) return;
    
            // Ensure the ArrayList has space for the current depth level
            if (depth >= result.size()) {
                result.add(new ArrayList<>());  // Create a new list for the current depth
            }
    
            // Add the current node's value to the list corresponding to the current depth
            result.get(depth).add(root.data);
    
            // Recursively visit the left subtree
            dfs2(root.left, depth + 1,result);
    
            // Recursively visit the right subtree
            dfs2(root.right, depth + 1,result);
        }
        public static node lowestCommonAncestor(node root, node p, node q) {
            ArrayList<node> path1=new ArrayList<>();
            ArrayList<node> path2=new ArrayList<>();
            getPath(root, p, path1);
            getPath(root, q, path2);
            int i=0,j=0;
            
            while(i<path1.size() && j<path2.size()){
                if(path1.get(i)!=path2.get(j))
                {
                    break;
                    /*
                    * to check the last common node between both the list is where the lca is present
                    * if unequal means previous node is lca
                    */
                }
                i++;
                j++;
            }
            return path1.get(i-1);
            
        }
        public static boolean getPath(node root, node p, ArrayList<node> path){
            if(root==null){
                return false;
            }
            path.add(root);
            if(root==p){
                return true;
            }
            boolean a=getPath(root.left, p, path);
            boolean b=getPath(root.right, p, path);
            if(a || b){
                return true;
            }
            path.remove(path.size()-1);
            return false;
        }
        // Another approach
        public static node lowestCommonAncestor2(node root, node p, node q) {
            if( root==p || root==q|| root==null){
                return root;
            }
            node left=lowestCommonAncestor2(root.left, p, q);
            node right=lowestCommonAncestor2(root.right, p, q);
            if(left==null)
            {
                return right;
            }
            /*
            * if left is null then right tree has both p and q nodes and hence lca is in right tree 
            * else if right is null then left tree has both p and q nodes and hence lca is in left tree
            */
            if(right==null)
            {
                return left;
            }
            return root;
            // if both left and right are not null then root is the lca
        }
        public static int MinDistanceBetweenNodes(node root, node p, node q){
            /*
            * Minimum distance between two nodes in a tree
            * similar logic when compared to lowest common ancestor
            */
            if(root==null)
                return -1;
            if( root==p || root==q){
                return 0;
            }
            int left=MinDistanceBetweenNodes(root.left, p, q);
            int right=MinDistanceBetweenNodes(root.right, p, q);
            if(left==-1 && right==-1)
                return -1;
            else if(left==-1)
                return right+1;
            else if(right==-1)
                return left+1;
            return Math.min(left,right)+1;
        }
        public static void KLevel(node root, int k){
            if(root==null)
                return;
            if(k==3){ // k=3 means 3rd level
                System.out.print(root.data+" ,");
            }
            KLevel(root.left, k+1);
            KLevel(root.right, k+1);
        }
        // Height of tree is number of nodes from root to deepest node count one at a level
        // Recursion based: height at node is= 1+ max( height of left , height of right )
        
        public static int CountNodes(node root){
            if(root==null){
                return 0;
            }
            if(root.left==null && root.right==null){
                return 1;
            }
            int left=CountNodes(root.left);
            int right=CountNodes(root.right);
            return left+right+1;
        }
        public static int SumOfNodes(node root){
            if(root==null){
                return 0;
            }
            if(root.left==null && root.right==null){
                return root.data;
            }
            int left= SumOfNodes(root.left);
            int right=SumOfNodes(root.right);
            return left+right+root.data;
        }
        public static int heightOfTree(node root){
            if(root==null){
                return 0;
            }
            if(root.left==null && root.right==null){
                return 1;
            }
            int left=heightOfTree(root.left);
            int right=heightOfTree(root.right);
            return Math.max(left,right)+1;
        }
        // Diameter of root = max (height of left subtree + height of right subtree+1 , max(diameter of left subtree , diameter of right subtree))
        public static int diameterOfTree(node root){ // o(n^2) time complexity
            if(root==null)
                return 0;
            
            int leftDiameter=diameterOfTree(root.left);
            int leftHeight=heightOfTree(root.left);
            int rightDiameter=diameterOfTree(root.right);
            int rightHeight=heightOfTree(root.right);
            return Math.max(Math.max(leftDiameter,rightDiameter),leftHeight+rightHeight);
        }
        static class Info{
            int ht,diameter;
            Info(int ht,int diameter){
                this.ht=ht;
                this.diameter=diameter;
            }
        }
        public static Info diameterOfTree2(node root){ // o(n) time complexity
            if(root==null)
                return new Info(0,0);
            Info leftDiameter=diameterOfTree2(root.left);
            Info rightDiameter=diameterOfTree2(root.right);

            int diam=Math.max(Math.max(leftDiameter.diameter,rightDiameter.diameter),leftDiameter.ht+rightDiameter.ht+1);
            int ht=Math.max(leftDiameter.ht,rightDiameter.ht)+1;

            return new Info(ht,diam);
        }
        // Subtree is present in another tree or not
        /*
         * SubTree must be in the same fomat as present in the original tree and must be in the same order
         * ex           1
         *             / \
         *           2     3            2
         *          / \   / \          / \
         *         4   5 6   7        4   5
         *           main tree      Sub tree 
         * same format and value hence return true
         */

         // check if node and subnode are identical or not by comparing both roots and their left and right subtrees
        public static boolean isIdentical(node root, node subRoot){
            if(root==null && subRoot==null){// both are same at base case
                return true;
            }
            else if(root==null || subRoot==null || root.data!=subRoot.data){ 
                // If one of them is null or not same return false
                return false;
            }
            if(!isIdentical(root.left,subRoot.left) || !isIdentical(root.right,subRoot.right)){ 
                // check if left and right subtrees are identical or not. If not return false
                return false;
            }
            // above conditions not satisfied then is truly identical
            return true;
        }
        /*
         * Check if subroot is present in main tree
         * if root has same data as subroot check if they are identical by calling isIdentical
         * if both conditions not satisfied then check for left and right subtrees if they are equla to subroot
         * Or condition at the end cause either left or right subtree is same as subRoot
         */
        public static boolean isSubtree(node root, node subRoot){
            if(root==null)
                return false;
            if(root.data==subRoot.data){
                if(isIdentical(root, subRoot))
                    return true;
            }
            return isSubtree(root.left,subRoot) || isSubtree(root.right, subRoot);
        }
        
        
    }
    
    public static void main(String[] args) {
        int nodes[]= {1,2,4,-1,-1,5,-1,-1,3,-1,6,-1,-1};
        BinaryTree tree=new BinaryTree();
        //node root=tree.maketreePre(nodes);
        //tree.PreOrder(root);
        System.out.println();
        //tree.PostOrder(root);
        System.out.println();
        //tree.InOrder(root);
        //System.out.println();
        //tree.levelOrder(root);
        System.out.println();
        //System.out.println(tree.heightOfTree(root));
        System.out.println();
        //System.out.println(tree.CountNodes(root));
        //System.out.println(tree.SumOfNodes(root));
        node r=new node(1);
        r.left=new node(2);
        r.right=new node(3);
        r.left.left=new node(4);
        r.left.right=new node(5);
        r.right.left=new node(6);
        r.right.right=new node(7);
        System.out.println(tree.diameterOfTree2(r).diameter);

        node subRoot=new node(2);
        subRoot.left=new node(4);
        subRoot.right=new node(5);
        System.out.println(tree.diameterOfTree2(subRoot).diameter);

        System.out.println(tree.isSubtree(r,subRoot));
    }
}
