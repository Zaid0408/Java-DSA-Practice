import java.util.*;

public class bitMani {
    //to check if number is odd or even do %10 or do :n & 1 if ans=0 then even else odd


    public int getIthBit(int num,int i) 
    {
        return (num & (1<<i)); // num & (1<<i) will make 1 bit shift to i places on the left then ith bit in num & 1 will givethe ith bit
    }
    public int setIthbit(int num,int i){// convert ith bit to 1
        return (num | (1<<i));
    }
    public int clearIthbit(int num,int i){// convert ith bit to 0
        return (num & ~(1<<i));
    }
    public int updateIthbit(int num,int i,int bit){// convert ith bit to 0
        if(bit==0)
            return clearIthbit(num, i);
        else
            return setIthbit(num, i);
    }
    public int clearRangeofBit(int num,int i,int j){// convert ith bit to 0
        int a=((~0)<<j+1);
        int b=(1<<i)-1;
        return (num &(a|b));
     
    }
    public int clearLastIBits(int num, int i){
        return num & ((~0)<<i);
    }
    public static void FindNo_OfSetBits(int num)
    {
        int count=0;
        while(num>0){
            
            if((num & 1)!=0)
                count++;
            num=num>>1;
        }
        System.out.println(count);
    }
    public static void checkPowerOfTwo(int num){
        int k=num & (num-1);
        if(k==0)
        {
            System.out.println("Power of two");
        }
        else
        System.out.println("Not a Power of two");

    }
    public static int fastExpo(int num,int p)
    {
        int ans=1,a=1;
        while(p>0)
        {
            if((p & 1)==1)
            {
                ans=ans*num;
            }
            else
            {
                ans =ans*1;
            }
            num=num*num;
            p=(p>>1);
        }
        return ans;
    }
    public static int getSum(int a, int b) {
        // a & b will give 
        while (b != 0) {
            int carry = a & b;     // carry= 010 & 011=010    001 & 100=000
            a = a ^ b;       // Xor a and b      // a =  010 ^ 011=001       001 ^ 100= 101       
            b = carry << 1;        // b= 010 << 1=100
        }
        return a;
        // return a+b;
    }
    // Reverse 32 bit integer. given int is guranteed 32 bit integer
    // you need treat n as an unsigned value
    public int reverseBits(int n) {
        int ans=0;
        for(int i=0;i<32;i++)
        {
            int bit= (n >> i) & 1;
            ans= ans | (bit << (31-i));
        }
        return ans;
    }

    public static void main(String[] args) {
       // FindNo_OfSetBits(15);
       //checkPowerOfTwo(17);
       //System.out.println(fastExpo(3, 5));
       // x^x=0 always using this to solve below: Swap two numbers suing bit manipulation
      
      
       /*int x=3,y=4;
       System.out.println("Before swapping x and y is : "+x+" and "+y);
       x=x^y;
       y=x^y;
       x=x^y;
       System.out.println("After swapping x and y is : "+x+" and "+y);*/

       // Add 1 to an integer using bit mani ie -x=~x+1 so x+1=-~x
       int p=4;
       //System.out.println("P is "+p+" and p+1 is "+ -~p);
       System.out.println(getSum(2, 3));


    }
}
