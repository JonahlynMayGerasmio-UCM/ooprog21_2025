import java.util.Scanner;

public class CompareStrings 
{
    public static void main(String[] args) 
    {

        Scanner input = new Scanner(System.in);

        String predefined = "Carmen";

        System.out.print("Enter your name > ");
        String name = input.nextLine();

        if (name.equals(predefined)) 
        {
            System.out.println("Carmen equals " + name);
        } 
        else 
        {
            System.out.println("Carmen does not equal " + name);
        }

        input.close();
    }
}
