import java.util.*;
class strings {
    Scanner sc = new Scanner(System.in);
    public static float ShortestPath(String s)
    {
        int x=0,y=0;
        for(int i=0;i<s.length();i++)
        {
            if(s.charAt(i)=='N'){y++;}
            else if(s.charAt(i)=='S'){y--;}
            else if(s.charAt(i)=='E'){x++;}
            else if(s.charAt(i)=='W'){x--;}
            else{
                System.out.println("Invalid String\n");
                return 0;
            }
        }
        int X2=x*x;
        int Y2=y*y;
        
        return (float)Math.sqrt(Y2+X2);
    }
    public static String compress1(String str)
    {
        StringBuilder sb= new StringBuilder("");
        int count=0;
        for(int i=0;i<str.length()-1;i++)
        {
            char ch=str.charAt(i);
            count=1;
            while(ch==str.charAt(i+1) && i<str.length()-1){count++;i++;}
            sb.append(ch);
            sb.append(count);
        }
        if(sb.toString().compareTo(str)>0)
        {
            return str;
        }
        return sb.toString();
        
    }
    public static String compress2(String str)
    {
        StringBuilder sb= new StringBuilder("");
        for(int i=0;i<str.length();i++)
        {
            //aaabbcccdd
            Integer count=1;
            if(i<str.length()-1)
            {
                while(str.charAt(i)==str.charAt(i+1) && i<str.length()-1){
                    count++;
                    i++;
                }
            }
            sb.append(str.charAt(i));
            if(count>1){sb.append(count);}
                
        }
        return sb.toString();
    }

    // letcode 1021 Remove Outermost Parentheses
    // whenever you encounter a first '(' remove it and when ct of '(' == ct of ')' then remove ')' again if you encounter '(' then you remove it. 
    // as it is given balanced parentheses already so after removing ')' 
    // you will always find next element is '(' and it will be again first for next consecutive sequence..
    public String removeOuterParentheses(String s) {
        String ans="";
        int c1=0;
        for(int i=0;i<s.length();i++)
        {
            char ch=s.charAt(i);
            if(ch=='(')
            {
                if(c1>0)
                {
                    ans=ans+ch;
                }
                c1++;
            }
            else if(ch==')')
            {
                c1--;
                if(c1>0)
                {
                    ans=ans+ch;
                }
            }
        }
        return ans;
    }
    public static void main(String[] args) {
        String path="WNEENESENNN";
       // System.out.println(ShortestPath(path));
        System.out.println(compress2("aaabbcccdd"));
    }
}