import java.util.ArrayList;
import java.util.List;

public class backtracking {
    public static void changeArr(int arr[],int i,int val){
        if(i== arr.length){
            printArr(arr);
            return;
        }
        // to check for backtracking 
        arr[i]=val;
        changeArr(arr, i+1, val+1); //fnx call step
        arr[i]=arr[i]-2;// backtracking step
    }
    public static void printArr(int arr[]){
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]+" ");
        }
        System.out.println();
    }

    public static void Subsets(String str, String ans, int i){
        //base
        if(i==str.length())
        {
            if(ans.length()==0)
            {
                System.out.println("null");
                return;
            }
            else{
                System.out.println(ans);
                return;
            }
            
        }
        //Yes Choice
        Subsets(str, ans+str.charAt(i), i+1);
        //No choice
        Subsets(str, ans, i+1);
    }
    public static void Permutations(String s, String ans){
        if(s.length()==0)
        {
            System.out.println(ans);
            return;
        }
        for (int i = 0; i < s.length(); i++)
        {
            char ch= s.charAt(i);
            String ros=s.substring(0,i)+ s.substring(i+1);
    
            Permutations(ros,(ans+ch));
        }
    }
    public static List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> ans=new ArrayList<>(); 
        List<Integer> temp=new ArrayList<>();
        permutations(nums,temp,ans);
        return ans;

    }// This and below function for permutations on list given that all elements are unique
    public static void permutations(int[] nums,List<Integer> temp,List<List<Integer>> ans){
        
        if(temp.size()==nums.length){
            ans.add(new ArrayList<>(temp));
        }
        else{
            for(int i=0;i<nums.length;i++)
            {
                if(temp.contains(nums[i]))
                    continue;
                temp.add(nums[i]);
                permutations(nums,temp,ans);
                temp.remove(temp.size()-1);
            }
        }
    }
    // Subsets of numbers in an array. Returning a list. unique elements
    public List<List<Integer>> subsets(int[] nums) {
        List<Integer> subset=new ArrayList<>();
        List<List<Integer>> temp=new ArrayList<>();
        Subsets(nums,0, subset,temp);
        return temp;
    }
    public void Subsets(int nums[],int i,List<Integer> subset,List<List<Integer>> temp)
    {
        if(i==nums.length)
        {
            temp.add(new ArrayList<>(subset));
            return;
        }
        // Two choices
        // First include current element while making subset
        subset.add(nums[i]);
        Subsets(nums,i+1, subset,temp); 
        // Second not to include the current element while making subset
        subset.remove(subset.size()-1);
        Subsets(nums,i+1, subset,temp);
    }
    public static boolean isSafe(int[][] arr, int row, int col, int n) {
        // Check if there is a queen in the same column
        for (int i = 0; i < row; i++) {
            if (arr[i][col] == 1)
                return false;
        }

        // Check if there is a queen in the upper-left diagonal
        for (int i = row, j = col; i >= 0 && j >= 0; i--, j--) {
            if (arr[i][j] == 1)
                return false;
        }

        // Check if there is a queen in the upper-right diagonal
        for (int i = row, j = col; i >= 0 && j < n; i--, j++) {
            if (arr[i][j] == 1)
                return false;
        }

        return true;
    }

    public static boolean nQueen(int[][] arr, int x, int n) {
        if (x >= n) {
            return true;
        }
        for (int i = 0; i < n; i++) {
            if (isSafe(arr, x, i, n)) {
                arr[x][i] = 1;
                if (nQueen(arr, x + 1, n))
                    return true;
                arr[x][i] = 0; // Backtracking
            }
        }
        return false;
    }
    public static int GridWays(int i, int j, int n, int m)
    {
        if(i==n-1 && j==m-1)
            return 1;
        else if(i==n || j==m)
            return 0;
        return GridWays(i+1, j, n, m)+GridWays(i, j+1, n, m);
    }
    public static boolean sudokuSolver(int arr[][],int row, int col){
        //base case
        if(row==9 && col==9)
            return true; //in this case soln recieved
        else if(row==9)
            return false;
        //recursion :-
        int nextRow=row,nextCol=col+1;
        if(col+1==9)
        {
            nextRow=row+1;
            nextCol=0; // to go to next row
        }

        if(arr[row][col]!=0){
            return sudokuSolver(arr, nextRow, nextCol); // for already existing digit in the sudoku no need to change anything
        }
        for(int digit=1;digit<=9;digit++)
        {
            if(isSudokuSafe(arr, row, col, digit)){
                arr[row][col]=digit;
                if(sudokuSolver(arr, nextRow, nextCol)){
                    return true;
                }
                arr[row][col]=0;//backtracking step to make number as zero if cannot place digit
            }
        }
        return false;
    }
    public static boolean isSudokuSafe(int[][] arr, int row, int col, int digit) {
        // Check if there is a queen in the same column
        for (int i = 0; i < 8; i++) {
            if (arr[i][col] == digit)
                return false;
        }
        for (int i = 0; i < 8; i++) {
            if (arr[row][i] == digit)
                return false;
        }
        int sr=(row/3)*3,sc=(col/3)*3;
        for(int i=sr;i<=sr+2;i++)
        {
            for(int j=sc;j<=sc+2;j++)
            {
                if (arr[i][j] == digit)
                    return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        /*int arr[]=new int[9];
        changeArr(arr, 0, 1); basic backtracking checking
        printArr(arr);*/
        //Subsets("ABCD", "", 0);
        //Permutations("ABC", "");


        // N- Queens problem
        /*int n = 5; // Change the value of n as needed
        int[][] arr1 = new int[n][n];

        if (nQueen(arr1, 0, n)) { // will return true if answer is obtained
            // Print the solution
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    System.out.print(arr1[i][j] + " ");
                }
                System.out.println();
            }
        } else {
            System.out.println("No solution exists.");// only for n= 2,3
        }*/



        //System.out.println(GridWays(0, 0, 5, 5));

        int[][] sudokuMatrix = {
            {5, 3, 0, 0, 7, 0, 0, 0, 0},
            {6, 0, 0, 1, 9, 5, 0, 0, 0},
            {0, 9, 8, 0, 0, 0, 0, 6, 0},
            {8, 0, 0, 0, 6, 0, 0, 0, 3},
            {4, 0, 0, 8, 0, 3, 0, 0, 1},
            {7, 0, 0, 0, 2, 0, 0, 0, 6},
            {0, 6, 0, 0, 0, 0, 2, 8, 0},
            {0, 0, 0, 4, 1, 9, 0, 0, 5},
            {0, 0, 0, 0, 8, 0, 0, 7, 9}
        };

        System.out.println(sudokuSolver(sudokuMatrix, 0, 0));

        // Print the Sudoku matrix
        // for (int i = 0; i < sudokuMatrix.length; i++) {
        //     for (int j = 0; j < sudokuMatrix[i].length; j++) {
        //         System.out.print(sudokuMatrix[i][j] + " ");
        //     }
        //     System.out.println();
        // }
    }
    
}
