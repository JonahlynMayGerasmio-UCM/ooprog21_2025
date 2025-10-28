import java.util.Scanner;

public class BankBalanceByRateAndYear {
   public static void main(String[] args) {

      Scanner inp = new Scanner(System.in);

      System.out.print("Enter initial bank balance > ");
      double balance = inp.nextDouble();

      double[] rts = {0.02, 0.03, 0.04, 0.05};

      for(int i = 0; i < rts.length; i++){
      
         double CB = balance;
         System.out.println();
         System.out.println("With an initial balance of $" + CB + " at an interest rate of " + rts[i]);

         for(int year = 1; year <= 4; year++){
            CB = CB + (CB * rts[i]);
            System.out.println("After year " + year + " balance is " + CB);
         }
      }
   }
}
