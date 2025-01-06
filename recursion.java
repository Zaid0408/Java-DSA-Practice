public class recursion {

    public static int fibo(int n)
    {
        if(n==0 || n==1)
            return n;
        return fibo(n-1)+fibo(n+1);
    }
    public static Boolean SortedArray(int arr[],int i)
    {
        if(arr.length==1)
        {
            return true;
        }
        if(arr[i]>arr[i+1])
        {
            return false;
        }
        return SortedArray(arr, i+1);
    }
    public static int firstOccurence(int arr[],int key,int i){
        if(i==arr.length)
            return -1;
   
        if(arr[i]==key)
            return i;
        return firstOccurence(arr, key, i+1);
    }

    public static void reverse(String str)
    {
        if(str.length()==0)
            return ;
        String res= str.substring(1);
        reverse(res);
        System.out.println(str.charAt(0));// correct this
    }
    public static void replacePI(String s) //s="pippxxpipxpxpi"
    {
        if(s.length()==0)
            return;
        
        if(s.charAt(0)=='p' && s.charAt(1)=='i')
        {
            System.out.print("3.14");
            replacePI(s.substring(2));
        }
        else
        {
            System.out.print(s.charAt(0));
            replacePI(s.substring(1));
        }
    }
    public static void removeDup(String s,int i, StringBuilder newStr,Boolean map[]) // remove duplicate in string
    {
        if(s.length()==i){
            System.out.println(newStr);
            return ;
        }
        char ch= s.charAt(i);
        if(map[ch-'a']==true)
            removeDup(s, i+1, newStr, map);
        else{
            map[ch-'a']=true;
            removeDup(s, i, newStr.append(ch), map);
        }
        
       
    }
    public static void permutation(String s, String ans) //Permutations of a string
    {
        if(s.length()==0)
        {
            System.out.println(ans);
            return;
        }
        for (int i = 0; i < s.length(); i++)
        {
            char ch= s.charAt(i);
            String ros=s.substring(0,i)+ s.substring(i+1);
    
            permutation(ros,(ans+ch));
        }
        
    }
    public static void SubstringsOf(String s,String ans) // all possible substrings of a string
    {
        if(s.length()==0)
        {
            System.out.println(ans);
            return;
        }
        char ch=s.charAt(0);
        String ros=s.substring(1);
    
        SubstringsOf(ros,ans);
        SubstringsOf(ros,(ans+ch));
    }
    

    public static int PowerOfN(int x, int n)// x^n in log n time
    {
        if(n==0 )
            return 1;
        else if(n==1)
            return x;
        else{
            if(n%2==0)
                return PowerOfN(x, n/2)*PowerOfN(x, n/2);
            else
                return x* PowerOfN(x, n/2)*PowerOfN(x, n/2);
        }

    }
    public static int TillingProblem(int n) 
    {
        if(n==1 || n==0)
            return 1;// can be done by tabulation
        return TillingProblem(n-1)+TillingProblem(n-2);
    }
    public static int Pairingfriends(int n) // can be done by tabulation
    {
        if(n==1 || n==2) //explanation in book
            return n;
        return Pairingfriends(n-1)+ (n-1)*Pairingfriends(n-2);
    }
    public static void BinaryString(int n, int lastplace,String str){
        // if(lastplace==0){
        //     //str.append("0");
        //     BinaryString(n-1, 0, str.append("0"));
        //     BinaryString(n-1, 1, str.append("1"));

        // }
        // else {
        //     BinaryString(n-1, 0, str.append("0"));
        // }

        //Optimized:
        if(n==0){//base
            System.out.println(str);
            return;
        }
        BinaryString(n-1, 0, str+"0");//main work
        if(lastplace==0){
            BinaryString(n-1, 1, str+"1");
        }
    }
    public static void ToH(int n,char src,char dest,char temp)
    {
        if(n==1)
            System.out.println("Move disk "+n+" from "+src+" to "+dest);
        else{
            ToH(n-1,src,temp,dest);
            System.out.println("Move disk "+n+" from "+src+" to "+dest);
            ToH(n-1,temp,dest,src);
        }
    }
    public static int knapsack(int[] wt, int[] val, int cap, int n)
    { // 0-1 knapsack problem . 0-1 means either have to include or to exclude the weight cannot add 
        if(cap==0 || n==0)
            return 0;
        
        if(wt[n-1]<=cap)
        {
            // include the weight into knapsack
            int ans1= val[n-1]+knapsack(wt, val, cap-wt[n-1], n-1);
            // exclude the weight from knapsack
            int ans2= knapsack(wt, val, cap, n-1);
            return Math.max(ans1, ans2);
        }
        else{
            return knapsack(wt, val, cap, n-1);
        }
    }
    char ch1,ch2;
    public int longestCommonSubsequence(String text1, String text2) {
        
        if(text1.length()==0 || text2.length()==0)
            return 0;
        
        ch1=text1.charAt(text1.length()-1);
        ch2=text2.charAt(text2.length()-1);
        if(ch1==ch2)
            return  1 + longestCommonSubsequence(text1.substring(0,text1.length()-1), text2.substring(0,text2.length()-1));
        else{
            int ans1= longestCommonSubsequence(text1.substring(0,text1.length()-1), text2);
            int ans2= longestCommonSubsequence(text1,text2.substring(0,text2.length()-1));
            return Math.max(ans1,ans2);
        }

    }
    public static void main(String[] args) {
        // System.out.println(PowerOfN(5, 9));
        //permutation("ZAID", "");
        // SubstringsOf("ZAID", "");
        Boolean map[]=new Boolean[26];
        for(int i=0;i<map.length;i++)
        {
            map[i]=false;
        }
       //System.out.println(Pairingfriends(7));
        //removeDup("zzzaaiiiidddd",0,new StringBuilder(""),map);
        //replacePI("pippxxpipxpxpi");
        //BinaryString(4, 0, "");
        //ToH(3, 'A','B','C');
        int W=7;
        int val[]={15,14,10,45,30};
        int weight[]={2,5,1,3, 4};
        System.out.println(knapsack(weight, val, W, val.length));
    }
}
