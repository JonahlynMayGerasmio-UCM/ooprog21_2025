import javax.swing.JOptionPane;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.*;
import java.util.stream.Collectors;
import java.io.*;

class Expense implements Serializable {
    private LocalDate date;
    private String category;
    private double amount;
    
    public Expense(LocalDate date, String category, double amount) {
        this.date = date;
        this.category = category;
        this.amount = amount;
    }
    
    public LocalDate getDate() { return date; }
    public String getCategory() { return category; }
    public double getAmount() { return amount; }
    
    @Override
    public String toString() {
        return String.format("%s | %s | $%.2f", date, category, amount);
    }
}

class BudgetData implements Serializable {
    private double weeklyBudget = 0;
    private double monthlyBudget = 0;
    private double yearlyBudget = 0;
    
    public void setWeeklyBudget(double amount) { this.weeklyBudget = amount; }
    public void setMonthlyBudget(double amount) { this.monthlyBudget = amount; }
    public void setYearlyBudget(double amount) { this.yearlyBudget = amount; }
    
    public double getWeeklyBudget() { return weeklyBudget; }
    public double getMonthlyBudget() { return monthlyBudget; }
    public double getYearlyBudget() { return yearlyBudget; }
}

class ExpenseTracker implements Serializable {
    private static final String[] CATEGORIES = {
        "Bills", "Food", "Transportation", "Education", 
        "Entertainment", "Clothes", "Grocery"
    };
    
    private List<Expense> expenses;
    private BudgetData budgetData;
    
    public ExpenseTracker() {
        this.expenses = new ArrayList<>();
        this.budgetData = new BudgetData();
    }
    
    public String[] getCategories() { return CATEGORIES; }
    
    public void setBudget(String period, double amount) {
        switch(period.toLowerCase()) {
            case "weekly":
                budgetData.setWeeklyBudget(amount);
                break;
            case "monthly":
                budgetData.setMonthlyBudget(amount);
                break;
            case "yearly":
                budgetData.setYearlyBudget(amount);
                break;
        }
    }
    
    public void addExpense(String category, double amount) {
        Expense expense = new Expense(LocalDate.now(), category, amount);
        expenses.add(expense);
    }
    
    public String getRemainingBudget() {
        LocalDate now = LocalDate.now();
        
        double weeklySpent = getWeeklyExpenses(now);
        double monthlySpent = getMonthlyExpenses(now);
        double yearlySpent = getYearlyExpenses(now);
        
        StringBuilder sb = new StringBuilder("=== REMAINING BUDGET ===\n\n");
        
        if (budgetData.getWeeklyBudget() > 0) {
            double remaining = budgetData.getWeeklyBudget() - weeklySpent;
            sb.append(String.format("Weekly Budget: $%.2f\n", budgetData.getWeeklyBudget()));
            sb.append(String.format("Weekly Spent: $%.2f\n", weeklySpent));
            sb.append(String.format("Weekly Remaining: $%.2f\n\n", remaining));
        }
        
        if (budgetData.getMonthlyBudget() > 0) {
            double remaining = budgetData.getMonthlyBudget() - monthlySpent;
            sb.append(String.format("Monthly Budget: $%.2f\n", budgetData.getMonthlyBudget()));
            sb.append(String.format("Monthly Spent: $%.2f\n", monthlySpent));
            sb.append(String.format("Monthly Remaining: $%.2f\n\n", remaining));
        }
        
        if (budgetData.getYearlyBudget() > 0) {
            double remaining = budgetData.getYearlyBudget() - yearlySpent;
            sb.append(String.format("Yearly Budget: $%.2f\n", budgetData.getYearlyBudget()));
            sb.append(String.format("Yearly Spent: $%.2f\n", yearlySpent));
            sb.append(String.format("Yearly Remaining: $%.2f\n", remaining));
        }
        
        return sb.toString();
    }
    
    private double getWeeklyExpenses(LocalDate date) {
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        int currentWeek = date.get(weekFields.weekOfWeekBasedYear());
        int currentYear = date.getYear();
        
        return expenses.stream()
            .filter(e -> e.getDate().getYear() == currentYear && 
                        e.getDate().get(weekFields.weekOfWeekBasedYear()) == currentWeek)
            .mapToDouble(Expense::getAmount)
            .sum();
    }
    
    private double getMonthlyExpenses(LocalDate date) {
        return expenses.stream()
            .filter(e -> e.getDate().getMonth() == date.getMonth() && 
                        e.getDate().getYear() == date.getYear())
            .mapToDouble(Expense::getAmount)
            .sum();
    }
    
    private double getYearlyExpenses(LocalDate date) {
        return expenses.stream()
            .filter(e -> e.getDate().getYear() == date.getYear())
            .mapToDouble(Expense::getAmount)
            .sum();
    }
    
    public String getRecentTransactions() {
        StringBuilder sb = new StringBuilder("=== RECENT TRANSACTIONS ===\n\n");
        
        if (expenses.isEmpty()) {
            sb.append("No transactions yet.");
        } else {
            List<Expense> sorted = new ArrayList<>(expenses);
            sorted.sort((a, b) -> b.getDate().compareTo(a.getDate()));
            
            for (int i = 0; i < Math.min(20, sorted.size()); i++) {
                sb.append(String.format("%d. %s\n", i + 1, sorted.get(i)));
            }
        }
        
        return sb.toString();
    }
    
    public String getSpendingChart(String period) {
        LocalDate now = LocalDate.now();
        List<Expense> filtered;
        
        switch(period.toLowerCase()) {
            case "weekly":
                WeekFields weekFields = WeekFields.of(Locale.getDefault());
                int currentWeek = now.get(weekFields.weekOfWeekBasedYear());
                filtered = expenses.stream()
                    .filter(e -> e.getDate().getYear() == now.getYear() && 
                                e.getDate().get(weekFields.weekOfWeekBasedYear()) == currentWeek)
                    .collect(Collectors.toList());
                break;
            case "monthly":
                filtered = expenses.stream()
                    .filter(e -> e.getDate().getMonth() == now.getMonth() && 
                                e.getDate().getYear() == now.getYear())
                    .collect(Collectors.toList());
                break;
            case "yearly":
                filtered = expenses.stream()
                    .filter(e -> e.getDate().getYear() == now.getYear())
                    .collect(Collectors.toList());
                break;
            default:
                filtered = expenses;
        }
        
        Map<String, Double> categoryTotals = new LinkedHashMap<>();
        Map<String, Integer> categoryCount = new LinkedHashMap<>();
        
        for (String cat : CATEGORIES) {
            categoryTotals.put(cat, 0.0);
            categoryCount.put(cat, 0);
        }
        
        for (Expense e : filtered) {
            categoryTotals.merge(e.getCategory(), e.getAmount(), Double::sum);
            categoryCount.merge(e.getCategory(), 1, Integer::sum);
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("=== %s SPENDING CHART ===\n\n", period.toUpperCase()));
        
        double total = categoryTotals.values().stream().mapToDouble(Double::doubleValue).sum();
        
        if (total == 0) {
            sb.append("No expenses for this period.");
        } else {
            for (String category : CATEGORIES) {
                double amount = categoryTotals.get(category);
                int count = categoryCount.get(category);
                double percentage = (amount / total) * 100;
                
                sb.append(String.format("%-15s: $%8.2f (%5.1f%%) - %d transactions\n", 
                    category, amount, percentage, count));
            }
            sb.append(String.format("\nTotal Spent: $%.2f\n", total));
        }
        
        return sb.toString();
    }
    
    public void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream("expense_data.dat"))) {
            oos.writeObject(this);
        } catch (IOException e) {
            System.err.println("Error saving data: " + e.getMessage());
        }
    }
    
    public static ExpenseTracker loadData() {
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream("expense_data.dat"))) {
            return (ExpenseTracker) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ExpenseTracker();
        }
    }
}

public class ExpenseTrackerApp {
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "123";
    
    public static void main(String[] args) {
        if (!login()) {
            JOptionPane.showMessageDialog(null, "Login failed. Exiting...");
            return;
        }
        
        ExpenseTracker tracker = ExpenseTracker.loadData();
        
        while (true) {
            String menu = "=== EXPENSE TRACKER MENU ===\n\n" +
                         "1. Add Budget\n" +
                         "2. View Recent Transactions\n" +
                         "3. Spending Chart\n" +
                         "4. Add Expense\n" +
                         "5. Log out";
            
            String choice = JOptionPane.showInputDialog(null, menu, 
                "Main Menu", JOptionPane.PLAIN_MESSAGE);
            
            if (choice == null || choice.equals("5")) {
                tracker.saveData();
                JOptionPane.showMessageDialog(null, "Logged out successfully!");
                break;
            }
            
            switch (choice) {
                case "1":
                    addBudget(tracker);
                    break;
                case "2":
                    viewTransactions(tracker);
                    break;
                case "3":
                    viewSpendingChart(tracker);
                    break;
                case "4":
                    addExpense(tracker);
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Invalid choice!");
            }
            
            tracker.saveData();
        }
    }
    
    private static boolean login() {
        String username = JOptionPane.showInputDialog(null, 
            "Enter username:", "Login", JOptionPane.PLAIN_MESSAGE);
        
        if (username == null) return false;
        
        String password = JOptionPane.showInputDialog(null, 
            "Enter password:", "Login", JOptionPane.PLAIN_MESSAGE);
        
        if (password == null) return false;
        
        return username.equals(USERNAME) && password.equals(PASSWORD);
    }
    
    private static void addBudget(ExpenseTracker tracker) {
        String[] periods = {"Weekly", "Monthly", "Yearly"};
        String period = (String) JOptionPane.showInputDialog(null,
            "Select budget period:", "Add Budget",
            JOptionPane.QUESTION_MESSAGE, null, periods, periods[0]);
        
        if (period != null) {
            String amountStr = JOptionPane.showInputDialog(null,
                "Enter " + period.toLowerCase() + " budget amount:");
            
            if (amountStr != null) {
                try {
                    double amount = Double.parseDouble(amountStr);
                    tracker.setBudget(period, amount);
                    JOptionPane.showMessageDialog(null, 
                        period + " budget set to $" + amount + "\n\n" + 
                        tracker.getRemainingBudget());
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Invalid amount!");
                }
            }
        }
    }
    
    private static void viewTransactions(ExpenseTracker tracker) {
        JOptionPane.showMessageDialog(null, tracker.getRecentTransactions(),
            "Recent Transactions", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private static void viewSpendingChart(ExpenseTracker tracker) {
        String[] periods = {"Weekly", "Monthly", "Yearly"};
        String period = (String) JOptionPane.showInputDialog(null,
            "Select period:", "Spending Chart",
            JOptionPane.QUESTION_MESSAGE, null, periods, periods[0]);
        
        if (period != null) {
            JOptionPane.showMessageDialog(null, 
                tracker.getSpendingChart(period),
                "Spending Chart", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private static void addExpense(ExpenseTracker tracker) {
        String[] categories = tracker.getCategories();
        String category = (String) JOptionPane.showInputDialog(null,
            "Select category:", "Add Expense",
            JOptionPane.QUESTION_MESSAGE, null, categories, categories[0]);
        
        if (category != null) {
            String amountStr = JOptionPane.showInputDialog(null,
                "Enter amount for " + category + ":");
            
            if (amountStr != null) {
                try {
                    double amount = Double.parseDouble(amountStr);
                    tracker.addExpense(category, amount);
                    JOptionPane.showMessageDialog(null, 
                        "Expense added successfully!\n\n" + 
                        tracker.getRemainingBudget());
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Invalid amount!");
                }
            }
        }
    }
}