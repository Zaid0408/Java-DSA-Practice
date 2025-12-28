import java.util.*;
class twoDarr {
    Scanner sc = new Scanner(System.in);
    public static void SpiralMat(int matrix[][])
    {
       int row_start=0,col_start=0,row_end=matrix.length-1,col_end=matrix[0].length-1;
       while(row_start<=row_end || col_start<=col_end)
       {
            for(int i=col_start;i<=col_end;i++)//Go right
            {
                System.out.print(matrix[row_start][i]+", ");
            }
            row_start+=1;
            for(int i=row_start;i<=row_end;i++)//go down
            {
                System.out.print(matrix[i][col_end]+", ");
            }
            col_end-=1;
            if(row_start<= row_end){
                for(int i=col_end;i>=col_start;i--)//go left
                {
                    System.out.print(matrix[row_end][i]+", ");
                }
                row_end-=1;
            }
            if(col_start<= col_end){
                for(int i=row_end;i>=row_start;i--)//go up
                {
                    System.out.print(matrix[i][col_start]+", ");
                }
                col_start+=1;
            }
       }
    }
    public static int DiagnolSum(int matrix[][])
    {
        int Diagnolsum=0;
        for(int i=0,j=0;i<matrix.length && j<matrix[0].length;i++,j++)
        {
            Diagnolsum+=matrix[i][j];
        }
        for(int i=0,j=matrix[0].length-1;i<matrix.length && j>=0;i++,j--)
        {
            Diagnolsum+=matrix[i][j];
        }
        return Diagnolsum;

    }
    // Leetcode 79 Word Search
    /*
     * Input: board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], 
     * word = "ABCCED" Output: true
     */
    public boolean exist(char[][] board, String word) {
        boolean visited[][]= new boolean[board.length][board[0].length];
        for(int i=0;i<board.length;i++)
        {
            for(int j=0;j<board[0].length;j++)
            {
                if(dfs(i,j,board,word,0,visited))
                    return true;
            }
        }
        return false;
    }
    // ABOVE AND BELOW ARE DFS TRAVERSAL ON MATRIX REMEMBER THIS ALGO
    public boolean dfs(int r,int c,char[][] board, String word,int i,boolean visited[][])
    {
        if(i==word.length())
            return true;
        if(r>=board.length || r<0 || c>=board[0].length || c<0 )
            return false;
        if(word.charAt(i) != board[r][c] || visited[r][c])
            return false;
        
        visited[r][c]=true;
        boolean ans= dfs(r+1,c,board,word,i+1,visited) ||
                     dfs(r-1,c,board,word,i+1,visited) ||
                     dfs(r,c+1,board,word,i+1,visited) ||
                     dfs(r,c-1,board,word,i+1,visited) ;
                    
        visited[r][c]=false;
        return ans;
    }
    public boolean searchMatrix(int[][] matrix, int target) {
        /* 
        *. Start from top right corner. if the element is greater than target, move left else move down. 
        */
        int i = 0, j = matrix[0].length - 1; // start from top right corner

        while (i < matrix.length && j >= 0) {
            if (matrix[i][j] == target) {
                return true;
            } else if (matrix[i][j] > target) {
                j--; 
            } else {
                i++; 
            }
        }
        return false;
    }

    /*
    * For rotation of matrix : 90 = transpose + reverse row
    180 = reverse row + reverse column
    270 = transpose + reverse col
     */
    public void rotate(int[][] matrix) {
        // For rotation of matrix : 90 = transpose + reverse row
        int m=matrix.length;
        for(int i=0;i<m;i++)
        {
            for(int j=i;j<m;j++)
            {
                int s=matrix[i][j];
                matrix[i][j]=matrix[j][i];
                matrix[j][i]=s;
            }
        }

        for(int i=0;i<m;i++)
        {
            for(int j=0;j<m/2;j++)
            {
                int s=matrix[i][j];
                matrix[i][j]=matrix[i][m-1-j];
                matrix[i][m-1-j]=s;
            }
        }
    }
    public static void main(String[] args) {
        int matrix[][]={{1,2,3,4},
                        {5,6,7,8},
                        {9,10,11,12},
                        {13,14,15,16}
                        };
        //System.out.println(matrix.length);
        //System.out.println(matrix[0].length);
        SpiralMat(matrix);
        //System.out.println(DiagnolSum(matrix));
        
    }
}