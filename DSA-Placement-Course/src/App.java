public class App {
    void isPrime(int n)
    {
        for(int i=2;i<n/2+1;i++)
        {
            if(n%i==0)
            {   System.out.println("Is  not  prime");
                return;
            }
        }
        System.out.println("Is prime");
    }
    int max(int a, int b, int c)
    {
        return (a>b)?(a>c)?a:c:(b>c)?b:c;
    }
    public static void main(String[] args) throws Exception {
        App a=new App();
        //a.isPrime(23);
        System.out.println(a.max(21,32,12));
    }
}
