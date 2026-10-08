import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class DPAdvanced{

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode() {}
        TreeNode(int val) { this.val = val; }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
           this.left = left;
            this.right = right;
        }
    }


    // DP on trees

    // leetcode 96 Unique Binary Search Trees
    public static int CountingTrees(int n){
        // given a number find the total number of bst's possible
        // exactly same to same logic as catalan number
        int dp[]=new int[n+1];
        dp[0]=1;
        dp[1]=1;
        for(int i=2;i<=n;i++){
            for(int j=0;j<i;j++){
                dp[i]+=dp[j]*dp[i-j-1];
            }
        }
        return dp[n];
    }
    // lc 95 Unique Binary Search Trees II
    // LC 95 Unique Binary Search Trees II

    public List<TreeNode> generateTrees(int n) {
        if (n == 0) {
            return new ArrayList<>();
        }
        Map<String, List<TreeNode>> memo = new HashMap<>();
        return generateTreesHelper(1, n, memo);        
    }

    private List<TreeNode> generateTreesHelper(int start, int end, Map<String, List<TreeNode>> memo) {
        String key = start + "-" + end;
        if (memo.containsKey(key)) {
            return memo.get(key);
        }
        List<TreeNode> trees = new ArrayList<>();
        if (start > end) {
            trees.add(null);
            return trees;
        }
        for (int rootVal = start; rootVal <= end; rootVal++) {
            List<TreeNode> leftTrees = generateTreesHelper(start, rootVal - 1, memo);
            List<TreeNode> rightTrees = generateTreesHelper(rootVal + 1, end, memo);
            for (TreeNode leftTree : leftTrees) {
                for (TreeNode rightTree : rightTrees) {
                    TreeNode root = new TreeNode(rootVal);
                    root.left = leftTree;
                    root.right = rightTree;
                    trees.add(root);
                }
            }
        }
        memo.put(key, trees);
        return trees;
    }

    // lc 337 House robber 3 : Do house robber 1 for revision
    // basic intuition : Tree is binary tree , so we cannot rob the children of root node and its children node simultaneously
    // so we have 2 ways : rob the root.left and root.right and add the value
    // or rob the root.left.left and root.left.right and root.right.left and root.right.right along with the root itslef
    // return the max out of these 2 ways 
    // recusrion : similar to house robber 1

    /*
    House Robber III

Question:

Rob houses in a tree, but you cannot rob directly connected houses.

Imagine:

          3
        /   \
       2     3
        \     \
         3     1

At node 3, you have two choices:
Don't rob current node

Then you are free to rob either child.
don't rob node
      ↓
best(left) + best(right)
Rob current node
Then you cannot rob its children.
rob node
   ↓
node value
+
best grandchildren

So the tree-DP intuition becomes:
At every node, make a TAKE/SKIP decision, but the decision changes what you're allowed to do with the children.
This is very similar to the TAKE/SKIP intuition you've already learned in subsequence DP.
    */
   /*
 * House Robber III - Tree DP Intuition:
 * At every node, decide whether to ROB or NOT ROB the current node.
 * If we ROB the node, we cannot rob its immediate children.
 * If we DON'T rob it, each child can independently choose ROB or NOT ROB.
 * Therefore, each subtree should provide both possibilities to its parent.
 * Think of the state as: [maximum if ROBBED, maximum if NOT ROBBED].
 * This is the tree version of TAKE/SKIP DP, where the current choice affects children.
 */
    public int rob(TreeNode root) {
        if(root==null)
            return 0;
        int sum=0;
        // sum will store rot.val + root.left.left + root.left.right + root.right.left + root.right.right
        // which is root + grand children values
        if(root.left!=null)
        {
            sum+= rob(root.left.left) + rob(root.left.right);
        }
        if(root.right!=null)
        {
            sum+= rob(root.right.left) + rob(root.right.right);
        }
        // rob(root.left) + rob(root.right) will store root.left + root.right which are children
        return Math.max(sum+root.val, rob(root.left) + rob(root.right));
    }
    // recursion has overlapping subproblems and exponenetial time complexity
    // one overlapping sub problem is that for a given root we calc root.left and root.left.left 
    // but when the recursive call runs for root.left we again calculate root.left.left and root.left.right which is overlapping and time consuming
    // Something similar to a memoization 
    public int robHelper(TreeNode root, Map<TreeNode,Integer> map) {
        if(root==null)
            return 0;
        if(map.containsKey(root))
            return map.get(root);
        int sum=0;

        if(root.left!=null)
        {
            sum+= rob(root.left.left) + rob(root.left.right);
        }
        if(root.right!=null)
        {
            sum+= rob(root.right.left) + rob(root.right.right);
        }
        sum=Math.max(sum+root.val, rob(root.left) + rob(root.right));
        map.put(root,sum);
        return sum;
    }

    public int[] robHelper(TreeNode root) 
    {
        if (root == null)
            return new int[]{0, 0};
        int[] left = robHelper(root.left);
        int[] right = robHelper(root.right);
        // TAKE current node
        int robCurrent = root.val + left[1] + right[1];
        // SKIP current node
        int skipCurrent =Math.max(left[0], left[1])+ Math.max(right[0], right[1]);

        return new int[]{robCurrent, skipCurrent};

        // int[] ans = robHelper(root);
        // return Math.max(ans[0], ans[1]);
    }

}