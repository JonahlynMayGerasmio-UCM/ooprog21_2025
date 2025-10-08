import java.util.*;

public class Payroll
{
    public static void main(String[] args)
    {
        Scanner input = new Scanner(System.in);
        double hours, rate;

        System.out.print("How many hours did you work this week? ");
        hours = input.nextDouble();

        System.out.print("What is your regular pay rate? ");
        rate = input.nextDouble();

        Employee emp = new Employee(1, rate);

        double regularPay = emp.getRegularPay(hours);
        double overtimePay = emp.getOvertimePay(hours);

        System.out.println("Regular pay is " + regularPay);
        System.out.println("Overtime pay is " + overtimePay);

        input.close();
    }
}
