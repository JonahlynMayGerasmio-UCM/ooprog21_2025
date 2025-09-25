import java.util.Scanner;

public class DebugThree2
{
    public static void main(String args[])
    {
        int a, b, c;
        Scanner inp= new Scanner(System.in);

        System.out.print("Enter an integer >> ");
        a = inp.nextInt();
        
        System.out.print("Enter a second integer >> ");
        b = inp.nextInt();  
        
        System.out.print("Enter a third integer >> ");
        c = inp.nextInt();  

        
        add(a, b);
        add(b, c);
        add(a, c);
        subtract(a, b);
        subtract(b, c);
        subtract(a, c);
    }

    public static void add(int a, int b)
    {
        
        System.out.println("The sum of " + a + " and " + b + " is " + (a + b));
    }

    public static void subtract(int a, int b)
    {
        
        System.out.println("The difference between " + a + " and " + b + " is " + (a - b));
    }
}
