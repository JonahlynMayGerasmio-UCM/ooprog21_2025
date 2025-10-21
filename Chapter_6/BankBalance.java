import java.util.Scanner;

public class BankBalance {
    public static void main(String[] args) 
    {
    
        Scanner inp  = new Scanner(System.in);
        
        double balance;
        double interestRate = 0.03;
        int year = 0;
        int option;

        System.out.print("Enter initial bank balance > ");
        balance = inp.nextDouble();

        do {
            year++;
            balance += balance * interestRate;
            System.out.printf("After year %d at %.2f interest rate, balance is $%.1f%n",
                               year, interestRate, balance);

            System.out.print("\nDo you want to see the balance at the end of another year?\n");
            System.out.print("Enter 1 for yes or any other number for no >> ");
            option = inp.nextInt();

        } while (option == 1);

        inp.close();
    }
}
