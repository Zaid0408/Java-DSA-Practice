/* Theory 

1. Graph Definition

A graph is a collection of:

Vertices (Nodes) → Points
Edges → Connections between nodes
0 ----- 1
|      /
|     /
2 ----3
2. Types of Graphs

A. Undirected Graph

Edges work in both directions.
0 ----- 1
means
0 -> 1
1 -> 0

B. Directed Graph

Edges have a direction.

0 -----> 1
means

0 -> 1
but NOT
1 -> 0

C. Weighted Graph

Each edge has a cost.
0 ----5----> 1
Weight = 5

Most Used Representation in Leetcode Almost every graph problem uses one of these.

Representation	Usage
Adjacency List	⭐⭐⭐⭐⭐ (Most Common)
Adjacency Matrix	⭐
Edge List	⭐⭐

Focus almost entirely on Adjacency List.

3. Adjacency List (Undirected)

Example

0 --- 1
|     |
|     |
2 --- 3

Edges

0-1
0-2
1-3
2-3

Representation

0 -> 1,2
1 -> 0,3
2 -> 0,3
3 -> 1,2

Java

int V = 4;

ArrayList<ArrayList<Integer>> graph = new ArrayList<>();

for (int i = 0; i < V; i++)
    graph.add(new ArrayList<>());

// Undirected edge
graph.get(0).add(1);
graph.get(1).add(0);

graph.get(0).add(2);
graph.get(2).add(0);

graph.get(1).add(3);
graph.get(3).add(1);

graph.get(2).add(3);
graph.get(3).add(2);

Shortcut Function (Recommended) for undirected
static void addEdge(ArrayList<ArrayList<Integer>> graph, int u, int v) {
    graph.get(u).add(v);
    graph.get(v).add(u);
}

Usage

addEdge(graph, 0, 1);
addEdge(graph, 0, 2);
addEdge(graph, 1, 3);
addEdge(graph, 2, 3);
Traversing Neighbors
for (int neighbor : graph.get(node)) {
    System.out.println(neighbor);
}

Used in

DFS
BFS
Cycle Detection
Connected Components

Basically every graph problem.

4. Directed Graph

Example

0 → 1
↓
2 → 3

Representation

0 -> 1,2
1 ->
2 -> 3
3 ->

Java

int V = 4;

ArrayList<ArrayList<Integer>> graph = new ArrayList<>();

for (int i = 0; i < V; i++)
    graph.add(new ArrayList<>());

graph.get(0).add(1);
graph.get(0).add(2);
graph.get(2).add(3);

Notice

We only insert

u -> v

Not

v -> u
Helper Function
static void addEdge(ArrayList<ArrayList<Integer>> graph, int u, int v) {
    graph.get(u).add(v);
}
5. Weighted Graph

Most common representation:

Node -> (Neighbor, Weight)

Example

      5
0 -------- 1
 \         |
2 \        | 3
   \       |
    2 -----

Edges

0-1 weight 5
0-2 weight 2
1-2 weight 3
Pair Class (Most Common)
class Pair {
    int node;
    int weight;

    Pair(int node, int weight) {
        this.node = node;
        this.weight = weight;
    }
}
Graph Representation
ArrayList<ArrayList<Pair>> graph = new ArrayList<>();

for (int i = 0; i < V; i++)
    graph.add(new ArrayList<>());
Adding Undirected Weighted Edge
graph.get(0).add(new Pair(1, 5));
graph.get(1).add(new Pair(0, 5));

graph.get(0).add(new Pair(2, 2));
graph.get(2).add(new Pair(0, 2));

graph.get(1).add(new Pair(2, 3));
graph.get(2).add(new Pair(1, 3));
Traversing
for (Pair nbr : graph.get(node)) {

    int next = nbr.node;
    int wt = nbr.weight;

}
Helper Function
static void addEdge(ArrayList<ArrayList<Pair>> graph,int u, int v, int wt) { // for undirected weighted graph

    graph.get(u).add(new Pair(v, wt));
    graph.get(v).add(new Pair(u, wt));
}
Directed Weighted Graph

Only one insertion.

graph.get(u).add(new Pair(v, weight));


6. Edge List

Sometimes LeetCode directly gives

int[][] edges = {
    {0,1},
    {0,2},
    {1,3},
    {2,3}
};

or

int[][] edges = {
    {0,1,5},
    {0,2,2},
    {1,2,3}
};

This is called an Edge List.

Usually, your first step is to convert it into an adjacency list.

7. Adjacency Matrix (Rare)
0 1 1 0
1 0 0 1
1 0 0 1
0 1 1 0

Java

int[][] graph = new int[V][V];

graph[0][1] = 1;
graph[1][0] = 1;

Rarely used in interviews because it uses O(V²) space.

LeetCode Graph Template (99% of Problems)
Unweighted
ArrayList<ArrayList<Integer>> graph = new ArrayList<>();

for (int i = 0; i < n; i++)
    graph.add(new ArrayList<>());

for (int[] edge : edges) {
    int u = edge[0];
    int v = edge[1];

    graph.get(u).add(v);
    graph.get(v).add(u);   // Remove for directed graph
}
Weighted
class Pair {
    int node;
    int weight;

    Pair(int node, int weight) {
        this.node = node;
        this.weight = weight;
    }
}

ArrayList<ArrayList<Pair>> graph = new ArrayList<>();

for (int i = 0; i < n; i++)
    graph.add(new ArrayList<>());

for (int[] edge : edges) {
    int u = edge[0];
    int v = edge[1];
    int wt = edge[2];

    graph.get(u).add(new Pair(v, wt));
    graph.get(v).add(new Pair(u, wt)); // Remove for directed graph
}
Revision Notes
Undirected Graph: Store both u → v and v → u.
Directed Graph: Store only u → v.
Weighted Graph: Store (neighbor, weight) pairs instead of just the neighbor.
Adjacency List is the standard representation for almost every LeetCode graph problem.
Build the graph first, then solve it using DFS, BFS, Topological Sort, Union-Find, Dijkstra, or MST depending on the problem.

This foundation is enough for the vast majority of graph questions you'll encounter in interviews.

If input is:
int[][] edges

✅ Convert to an adjacency list.

Examples:

LC 1971
LC 785
LC 207
LC 210

If input is:
int[][] isConnected

where the matrix directly represents connections,

✅ Use the matrix directly.

Example:

LC 547
*/

import java.util.*;

public class Graph {

    /*  Count Components
        Problem Statement: Given an undirected Graph consisting of V vertices numbered from 0 to V-1 and E edges. The ith edge is represented by [ai,bi], denoting a edge between vertex ai and bi. We say two vertices u and v belong to a same component if there is a path from u to v or v to u. Find the number of connected components in the graph.
A connected component is a subgraph of a graph in which there exists a path between any two vertices, and no vertex of the subgraph shares an edge with a vertex outside of the subgraph.
Same to same as lc 547 number of connected components


Examples
Input: V=4, edges=[[0,1],[1,2]]
Output: 2

0--1--2 3 
Explanation: Vertices {0,1,2} forms the first component and vertex 3 forms the second component.
Input:V = 7, edges = [[0, 1], [1, 2], [2, 3], [4, 5]]
Output: 3

0--1--2--3 4--5 here 0--1 1--2 2--3 components are connected 
Explanation: The edges [0, 1], [1, 2], [2, 3] form a connected component with vertices {0, 1, 2, 3}
The edge [4, 5] forms another connected component with vertices {4, 5}.
Therefore, the graph has 3 connected components: {0, 1, 2, 3}, {4, 5}, and the isolated vertices {6}.
    */
    public int countComponents(int V, int[][] edges) {
        // edges here are zero based indexing
        // Create adjacency list from edge list
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < V; i++) {
            adj.add(new ArrayList<>());
        }
        for (int[] e : edges) {
            adj.get(e[0]).add(e[1]);
            adj.get(e[1]).add(e[0]);
        }

        // Array to keep track of visited nodes
        boolean[] visited = new boolean[V];

        // Variable to count the number of connected components
        int components = 0;

        // Traverse all nodes in the graph
        for (int i = 0; i < V; i++) {

            // If the node is not visited, it's a new component
            if (!visited[i]) {
                components++;

                // Start BFS from this node
                Queue<Integer> q = new LinkedList<>();
                q.offer(i);
                visited[i] = true;

                // Perform BFS traversal
                while (!q.isEmpty()) {
                    int node = q.poll();

                    // Visit all unvisited neighbors
                    for (int nbr : adj.get(node)) {
                        if (!visited[nbr]) {
                            visited[nbr] = true;
                            q.offer(nbr);
                        }
                    }
                }
            }
        }

        // Return the total number of connected components
        return components;
    }
    // BFS on Graph (Adjacency List), bfs means immidiate neighbours and not different levels 
    // visited array to keep track of the visited nodes, starting node always placed first then traversal starts and make visited[node]=true
    // use queue, same traversal as tree

    public List<Integer> bfs(int V, List<List<Integer>> adj, int node) {// adj is adjacency list>)
        List<Integer> BFS=new ArrayList<>();
        boolean visited[]=new boolean[V];
        Queue<Integer> queue=new LinkedList<>();
        queue.offer(node); // here since we are passing node we have to add it to the quque first
        // if no specific node we can add 0 or 1 depending which index it is based on.
        visited[node]=true;
        while(!queue.isEmpty())
        {
            int front=queue.poll();
            BFS.add(front);
            for(int nbr:adj.get(front)) // get all the neighbours of node 
            {
                if(!visited[nbr])
                {
                    queue.offer(nbr);
                    visited[nbr]=true;
                }
            }
        }
        return BFS;
    } 

    // DFS on graph , depth from the node
    // Use Recursion very important 
    // For every list of a particular node we visit that node and mark it visited 
    // ex : 1 -> {2,3}
    //      2-> {4,5}
    //      3-> {6,7}
    /*
        go to 1 check 1 has 2 and 3 . select 2, 2 has 4 and 5 select 4 ,no 4 so come back
        select 5 (from 2), add 5 , nothing from 5 comeback to 2 so come back to 1 and add 3 
        from 3 go to 6 and 7. 
    */

    private void dfsRecursionHelper(int node, List<List<Integer>> adj, List<Integer> res, boolean[] visited) {
        visited[node]=true;
        res.add(node);
        for(int nbr:adj.get(node))
        {
            if(!visited[nbr])
            {
                dfsRecursionHelper(nbr,adj,res,visited);
            }
        }
    }
    public List<Integer> dfsRecursion(int V, List<List<Integer>> adj, int node) {
        boolean visited[]=new boolean[V];
        List<Integer> res=new ArrayList<>();
        dfsRecursionHelper(node,adj,res,visited);
        return res;
    }

    public List<Integer> dfs(int V, List<List<Integer>> adj, int node) {
        List<Integer> DFS=new ArrayList<>();
        boolean visited[]=new boolean[V];
        Stack<Integer> stack=new Stack<>();
        stack.push(node); // here since we are passing node we have to add it to the quque first
        // if no specific node we can add 0 or 1 depending which index it is based on.
        visited[node]=true;
        while(!stack.isEmpty())
        {
            int top=stack.pop();
            DFS.add(top);
            for(int nbr:adj.get(top)) // get all the neighbours of node 
            { // the dfs order produceed here and in recursion will be different but unless the order is important we can use any
                if(!visited[nbr])
                {
                    stack.push(nbr);
                    visited[nbr]=true;
                }
            }
        }
        return DFS;
    }



    // for lc 547 number of provinces they give adjacency matrix instead of adjacency list
    public int findCircleNum(int[][] isConnected) {
        int n = isConnected.length;
        int[] visited = new int[n];
        int cnt = 0;
        for (int i = 0; i < n; i++) {
            if (visited[i] == 0) { // dfs on unvisited
                dfs(i, isConnected, visited);
                cnt++;
            }
        }
        return cnt;
    }
    private void dfs(int node, int[][] isConnected, int[] visited) {
        visited[node] = 1;
        for (int neighbor = 0; neighbor < isConnected.length; neighbor++) {
            if (isConnected[node][neighbor] == 1 && visited[neighbor] == 0) {
                dfs(neighbor, isConnected, visited);
            }
        }
    }
    // leetcode 998 Rotten Oranges
    // rotten problem, check commens for understanding
    class Pair{
        int a;int b;

        Pair(int a, int b)
        {
            this.a=a;
            this.b=b;
        }
    }
    public int orangesRotting(int[][] grid) {
        int minutes=0;int fresh=0;
        int n=grid.length;int m=grid[0].length;
        Queue<Pair> q=new LinkedList<Pair>();
        // Find all the rotten oranges and push in queue as bfs should start from there
        for(int i=0;i<n;i++)
        {
            for(int j=0;j<m;j++)
            {
                if(grid[i][j]==2)
                {
                    q.offer(new Pair(i,j));
                }
                else if(grid[i][j]==1)
                {
                    fresh++;
                }
            }
        }
        if(fresh==0) return 0; // no fresh oranges no answer
        // BFS
        int[] dRow = {-1, 1, 0, 0}; // helping in direction traversal 
        int[] dCol = {0, 0, -1, 1}; // use a pair of drow[i] or dcol[i] to go in a certain direction
        while (!q.isEmpty()) {
            int size = q.size(); // Number of oranges rotting at this current minute
            // Instead of maintaining a separate boolean[][] rotten array, you can modify the grid directly
            boolean rottedThisMinute = false; // instead of maintaining a list check if any rotting occurred at this minute in this loop
            for (int k = 0; k < size; k++) {
                Pair p=q.poll();
                int r=p.a;
                int c=p.b;

                // Check all 4 adjacent sides
                for (int d = 0; d < 4; d++) {
                    int nRow = r + dRow[d];
                    int nCol = c + dCol[d];

                    // Boundary check and verifying if it's a fresh orange
                    if (nRow >= 0 && nRow < n && nCol >= 0 && nCol < m && grid[nRow][nCol] == 1) {
                        grid[nRow][nCol] = 2; // Mark it as rotten directly in the grid
                        fresh--;       // One less fresh orange left
                        q.offer(new Pair(nRow, nCol));
                        rottedThisMinute = true;
                    }
                }
            }
            
            // Only increment time if this layer actually spread the rot further
            if (rottedThisMinute) {
                minutes++;
            }
        }
        return fresh==0?minutes:-1;
    }
    /*
    Why Rotten Oranges needs size but Flood Fill doesn't
Rotten Oranges
Minute 0
R F F

↓

Minute 1
R R F

↓

Minute 2
R R R
    */

    // leetcode 733 Flood fill: Similar to rotten oranges
    public int[][] floodFill(int[][] image, int sr, int sc, int color) {
        int[] dRow = {-1, 1, 0, 0}; // helping in direction traversal 
        int[] dCol = {0, 0, -1, 1};
        Queue<Pair> q=new LinkedList<>();
        int n=image.length;int m=image[0].length;
        int orignal=image[sr][sc];
        if(orignal==color)
            return image;
        image[sr][sc]=color;
        q.offer(new Pair(sr,sc));
        while(!q.isEmpty())
        {
            int size=q.size();
            Pair p=q.poll();
            int r=p.a;int c=p.b;
            for(int d=0;d<4;d++)
            {
                int row=r+dRow[d];
                int col=c+dCol[d];
                if(row<n && row>=0 && col<m && col>=0 && image[row][col]==orignal)
                {
                    image[row][col]=color;
                    q.offer(new Pair(row,col));
                }
            }
        }

        return image;

    }
    // Detect cycle in undirected graph using BFS
    // Store (node, parent) in the BFS queue.
// If a neighbor is unvisited, visit it and remember the current node as its parent.
// If a neighbor is already visited and is NOT the parent,
// then we have reached the same node through another path,
// which means the undirected graph contains a cycle.
// Run BFS/DFS from every unvisited node to handle disconnected graphs.
    public boolean isCycle(int V, List<Integer>[] adj) {
        boolean vis[]=new boolean[V];
        // impo to use this loop to check all nodes in the graph similar to countComponents implementation
        for(int i=0;i<V;i++) 
        { // this loop is impo as we have to traverse all egdes 
          // isCycleHelper will only traverse nodes starting from a particular src and that not necessary will have cyclic nature hence all nodes to be checked
            if(!vis[i])
            {
                if(isCycleHelper(i,adj,vis))
                    return true;
            }
        }
        return false;
    }
    // always remember :In an undirected graph, a cycle exists if while traversing the graph, we encounter a node that has already been visited and it is not the parent of the current node.
    private boolean isCycleHelper(int src, List<Integer>[] adj, boolean vis[]) {
        Queue<Pair> q=new LinkedList<>();
        vis[src]=true;
        q.offer(new Pair(src,-1));
        while(!q.isEmpty())
        {
            Pair p=q.poll();
            int i=p.a;int j=p.b;
            // here p.a means source/node currently and p.b means the parent of the current node. 
            // parent node is needed because if parent is already visited we can mark it as cyclic

            for(int nbr:adj[i])
            {
                if(vis[nbr]==false)
                {
                    vis[nbr]=true;
                    q.add(new Pair(nbr,i));
                }
                else if( j!=nbr)
                    return true;
            }
        }

        return false;
    }
    // dfs implementation for the same 
    private boolean isCycleHelper(int src,int parent, List<Integer>[] adj, boolean vis[]) {
        vis[src]=true;
        for(int nbr:adj[src])
        {
            if(!vis[nbr]){
                if (isCycleHelper(nbr,src,adj,vis))
                    return true;
            }
            else if(nbr!=parent)
                return true;
        }
        return false;
    }

    // lc 207

    /*
    Intuition : Kahns Algo BFS Topological sorting 

Imagine every course is locked.
Whenever all its prerequisites disappear,
it becomes unlocked.
If some courses remain locked forever,
they are part of a cycle.
indegree: simply the number of incoming edges
indegree here means the prerequisite count of coursese to be completed befroe taking a particular course
ex : to take course 3 you need to complete course 1 and 2 hence indegree is 2 
    */
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        List<List<Integer>> graph = new ArrayList<>();
        for(int i=0;i<numCourses;i++)
            graph.add(new ArrayList<>());

        int[] indegree = new int[numCourses];
        for(int[] edge : prerequisites)
        {
            int course = edge[0];
            int prereq = edge[1];

            graph.get(prereq).add(course);
            indegree[course]++;
        }
        Queue<Integer> q = new LinkedList<>();
        for(int i=0;i<numCourses;i++)
        {
            if(indegree[i]==0)
                q.offer(i);
        }
        int count=0;
        while(!q.isEmpty())
        {
            int node=q.poll();
            count++;
            for(int nbr:graph.get(node))
            {
                indegree[nbr]--;
                if(indegree[nbr]==0)
                    q.offer(nbr);
            }
        }
        return count==numCourses;
    }

    // leetcode 542 01 Matrix 
    // Similar to rotten oranges 
    public int[][] updateMatrix(int[][] mat) {
        int n=mat.length;int m=mat[0].length;
        int ans[][]=new int[n][m];
        boolean vis[][]=new boolean[n][m];
        Queue<int[]> q=new LinkedList<int[]>();
        for(int i=0;i<n;i++)
        {
            for(int j=0;j<m;j++)
            {
                if(mat[i][j]==0)
                {
                    q.offer(new int[]{i,j,0});
                    vis[i][j]=true;
                    ans[i][j]=0;
                }
            }
        }
        int[] dRow = {-1, 1, 0, 0}; // helping in direction traversal 
        int[] dCol = {0, 0, -1, 1};
        while(!q.isEmpty())
        {
            int sh[]=q.poll();
            int row=sh[0];int col=sh[1];int dist=sh[2];

            ans[row][col]=dist;
            for(int d=0;d<dRow.length;d++)
            {
                int r=row+dRow[d];
                int c=col+dCol[d];
                if(r>=0 &&r<n && c>=0 && c<m && !vis[r][c])
                {
                    if(mat[r][c]==1)
                    {
                        vis[r][c]=true;
                        q.offer(new int[]{r,c,dist+1});
                    }
                }
            }
        }

        return ans;
    }

    // Leetcode 200 Number of islands 
    /*
    Intuition : Dfs here because we need to go to every neighbour as all neighbours conected make one island
    For each row and col index do dfs from there
    if i and j are outside bounds and grid[i][j] is not water then return ie not '0'
    for every dfs call mark this as visited by making it '0' from '1' otherwise subsequent calls in the for loop will consider visisted values again
    post that do dfs on all 4 neigbours 

    for every succesful dfs completed one island has been discovered hence increment counter
    */
    public int numIslands(char[][] grid) {
        int island=0;
        int n=grid.length;int m=grid[0].length;
        for(int i=0;i<n;i++)
        {
            for(int j=0;j<m;j++)
            {
                if(grid[i][j]=='1')
                {
                    dfs(grid,i,j);
                    island++;
                }
            }
        }
        return island;

    }
    private void dfs(char[][] grid, int i, int j)
    {
        if(i>=grid.length || i<0 || j>=grid[0].length || j<0 || grid[i][j]=='0') // impo conditions to check otherwise infinite loop
            return;
        grid[i][j]='0';// mark this as visited

        dfs(grid,i+1,j);
        dfs(grid,i-1,j);
        dfs(grid,i,j+1);
        dfs(grid,i,j-1);
    }

    // lc 130 Surronded regions 
    /*
    The problem is about converting all 'O' regions that are completely surrounded by 'X' into 'X'. However, any 'O' that is connected to the boundary cannot be converted, since it's not fully surrounded.
So the key idea is:

Mark all 'O's that are connected to the boundary as safe.
At the end, flip all other 'O's (unvisited ones) into 'X'.
Approach
Use DFS (or BFS) starting from boundary cells. Whenever we see an 'O' on the boundary, perform DFS/BFS to mark all connected 'O's as visited (safe).
After this traversal, all boundary-connected 'O's remain as they are, because they cannot be surrounded.
Traverse the entire matrix:
If an 'O' is visited, leave it as 'O'.
If an 'O' is not visited, convert it into 'X'.
    */
    public void solve(char[][] board) {
        int m = board.length;
        int n = board[0].length;
        // visited matrix to mark all 'O's connected to the boundary
        boolean[][] vis = new boolean[m][n];

        // 1. DFS from first/last columns
        for(int i =0;i<m;i++){
            if(board[i][0] == 'O' && vis[i][0] == false){
                dfs(i,0,board,vis);
            }
            if(board[i][n-1] == 'O' && vis[i][n-1] == false){
                dfs(i,n-1,board,vis);
            }
        }
        // 2. DFS from first/last rows
        for(int j = 0;j<n;j++){
            if(board[0][j] == 'O' && vis[0][j] == false){
                dfs(0,j,board,vis);
            }
            if(board[m-1][j] == 'O' && vis[m-1][j] == false){
                dfs(m-1,j,board,vis);
            }
        }

        // 3. Flip surrounded 'O's
        for(int i =0;i<m;i++){
            for(int j =0;j<n;j++){
                // Flip 'O' if it's not marked as visited (i.e., not connected to border)
                if(board[i][j] == 'O' && vis[i][j] == false){
                    board[i][j] = 'X';
                }
            }
        }
    }

    public void dfs(int i, int j, char[][] board, boolean[][] vis){ // dfs here is similar to leetcode 200
        int m = board.length;
        int n = board[0].length;
        // Base case: out of bounds, already visited, or 'X'
        if(i<0 || j<0 || i>=m || j>=n || vis[i][j] == true || board[i][j] == 'X'){
            return;
        }
        vis[i][j] = true;
        // DFS in all 4 directions
        dfs(i+1,j,board,vis);
        dfs(i,j+1,board,vis);
        dfs(i-1,j,board,vis);
        dfs(i,j-1,board,vis);
    }

    // lc 785 check if graph is bipartite or not
    /*
    For DFS traversal, we need a start node and a visited array but in this case, instead of a visited array, we will take a colour array where all the nodes are initialised to -1 indicating they are not coloured yet.
In the DFS function call, make sure to pass the value of the assigned colour, and assign the same in the colour array. We will try to colour with 0 and 1, but you can choose other colours as well. We will start with the colour 0, you can start with 1 as well, just make sure for the adjacent node, it should be opposite of what the current node has. 
In DFS traversal, we travel in-depth to all its uncoloured neighbours using the adjacency list. For every uncoloured node, initialise it with the opposite colour to that of the current node.
If at any moment, we get an adjacent node from the adjacency list which is already coloured and has the same colour as the current node, we can say it is not possible to colour it, hence it cannot be bipartite. Thereby return a false indicating the given graph is not bipartite; otherwise, keep on returning true.
    */
    public boolean isBipartite(int[][] graph) {
        int V=graph.length;
        int color[]=new int[V];
        for(int i=0;i<V;i++) 
            color[i]=-1;

        for(int i=0;i<V;i++) 
        {
            if(color[i]==-1)
            {
                if(dfsBipartite(graph,color,i,0)==false)
                    return false; // returning false because code may early exit so no need of checking again and again
            }
        }
        return true;
        
    }
    private boolean dfsBipartite(int[][] graph,int color[],int i,int col)
    {
        color[i]=col;
        for(int nbr:graph[i])
        {
            if(color[nbr] == -1) {
                if(dfsBipartite(graph,color,nbr,1-col)==false) 
                    return false; 
            }
            else if(color[i]==color[nbr])
                return false;
        }
        return true;
    }

    // lc 1020
    /*
    Intuition
    Initialize a queue for BFS and a vis array to track visited cells.
Traverse the boundary of the grid. For every boundary land cell (grid[i][j] == 1), mark it visited and push it into the queue.
Run BFS from these boundary land cells:
At each step, pop a cell and explore its 4 neighbors (up, right, down, left).
If the neighbor is land and not yet visited, mark it visited and push it into the queue.
After BFS, iterate over the entire grid. Any land cell (grid[i][j] == 1) that remains unvisited is an enclave.
Count these enclaves and return the result.
    */
    public int numEnclaves(int[][] grid) {
        Queue<Pair> q=new LinkedList<>();
        int n=grid.length;int m=grid[0].length;
        boolean vis[][]=new boolean[n][m];

        for(int i=0;i<n;i++)
        {
            for(int j=0;j<m;j++)
            {
                if((i==0 || j==0 || i==n-1 || j==m-1))
                {
                    if(grid[i][j]==1)
                    {
                        q.offer(new Pair(i,j));
                        vis[i][j]=true;
                    }
                }
            }
        }
        int[] dRow = {-1, 1, 0, 0}; // helping in direction traversal 
        int[] dCol = {0, 0, -1, 1};
        while(!q.isEmpty())
        {
            Pair p=q.poll();
            int i=p.a;int j=p.b;
            for(int d=0;d<4;d++)
            {
                int r=i+dRow[d];
                int c=j+dCol[d];
                if(r<n && r>=0 && c<m && c>=0 && grid[r][c]==1)
                {
                    if(!vis[r][c]) {
                        vis[r][c]=true;
                        q.offer(new Pair(r,c));
                    }
                }
            }

        }

        int ans=0;
        for(int i=0;i<n;i++)
        {
            for(int j=0;j<m;j++)
            {
                if(grid[i][j]==1 && !vis[i][j])
                {
                   ans++;
                }
            }
        }
        return ans;

    }
    // lc 127 word ladder
    /*
        intuition
            Breadth-First Search (BFS) ensures the shortest path is found from startWord to targetWord.
    Initialize a queue with a pair {startWord, 1} representing the word and its current transformation steps.
    Insert all words from wordList into a unordered_set for O(1) lookups.
    While the queue is not empty:
    Pop a word and its step count.
    If this word is the targetWord, return the step count.
    For each character in the word, try replacing it with all letters 'a' to 'z':
        If the new word exists in the set, erase it from the set and push it into the queue with steps + 1.
    If no transformation sequence is found, return 0.

    */
    class Pair2<K, V> {
        private K key;
        private V value;
        public Pair2(K key, V value) {
            this.key = key;
            this.value = value;
        }
        public K getKey() { return key; }
        public V getValue() { return value; }
    }
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        Queue<Pair2<String, Integer>> q = new LinkedList<>();
        q.add(new Pair2<>(beginWord, 1));
        Set<String> st = new HashSet<>(wordList);
        st.remove(beginWord);
        while (!q.isEmpty()) {
            String word = q.peek().getKey();
            int steps = q.peek().getValue();
            q.poll();

            // If target word is found
            if (word.equals(endWord)) return steps;

            // Try changing every character
            for (int i = 0; i < word.length(); i++) {
                char[] arr = word.toCharArray();
                char original = arr[i];
                for (char ch = 'a'; ch <= 'z'; ch++) {
                    arr[i] = ch;
                    String newWord = new String(arr);
                    if (st.contains(newWord)) {
                        st.remove(newWord);
                        q.add(new Pair2<>(newWord, steps + 1));
                    }
                }
                arr[i] = original;
            }
        }
        return 0;
    }
}
