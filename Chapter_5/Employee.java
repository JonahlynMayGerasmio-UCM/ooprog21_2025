public class Employee
{
    int empNum;
    double payRate;
    final int MAX_EMP_NUM = 9999;
    final double MAX_RATE = 60.00;
    final double OVERTIME_RATE = 1.5;

    public Employee(int e, double r)
    {
        empNum = e;
        payRate = r;
    }

    public double getRegularPay(double hours)
    {
        if(hours > 40)
            hours = 40;
        return hours * payRate;
    }

    public double getOvertimePay(double hours)
    {
        double overtimeHours = 0;
        if(hours > 40)
            overtimeHours = hours - 40;
        return overtimeHours * payRate * OVERTIME_RATE;
    }
}
