import java.util.*;
import java.io.*;

class Bank {
    private int accountNumber;
    private String name;
    private double balance;

    public Bank(int accountNumber, String name, double balance) {
        this.accountNumber = accountNumber;
        this.name = name;
        this.balance = balance;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public void withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
        } else {
            System.out.println("Insufficient balance.");
        }
    }

    public String toString() {
        return "Account Number: " + accountNumber + ", Name: " + name + ", Balance: $" + balance;
    }
}

 class BankManagementSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        ArrayList<Bank> banks = new ArrayList<>();
        File file = new File("bank.txt");
        if (file.exists()) {
            try {
                Scanner fileScanner = new Scanner(file);
                while (fileScanner.hasNextLine()) {
                    String line = fileScanner.nextLine();
                    String[] data = line.split(",");
                    Bank bank = new Bank(Integer.parseInt(data[0]), data[1], Double.parseDouble(data[2]));
                    banks.add(bank);
                }
                fileScanner.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        while (true) {
            System.out.println("1. Add account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Display all accounts");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter account number: ");
                    int accountNumber = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter balance: ");
                    double balance = scanner.nextDouble();
                    Bank bank = new Bank(accountNumber, name, balance);
                    banks.add(bank);
                    try {
                        BufferedWriter writer = new BufferedWriter(new FileWriter("bank.txt", true));
                        writer.write("ACC_NO."+"   "+"NAME"+"              BALANCE");
                        writer.write(accountNumber + "," + name + "," + balance + "\n");
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Account added successfully.");
                    break;
                case 2:
                    System.out.print("Enter account number: ");
                    accountNumber = scanner.nextInt();
                    System.out.print("Enter deposit amount: ");
                    double depositAmount = scanner.nextDouble();
                    for (Bank b : banks) {
                        if (b.getAccountNumber() == accountNumber) {
                            b.deposit(depositAmount);
                            try {
                                BufferedWriter writer = new BufferedWriter(new FileWriter("bank.txt"));
                                for (Bank bank1 : banks) {
                                    writer.write(bank1.getAccountNumber() + "," + bank1.getName() + "," + bank1.getBalance() + "\n");
                                }
                                writer.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            System.out.println("Deposit successful.");
                            break;
                        }
                    }
                    break;

                case 3:
                    System.out.print("Enter account number: ");
                    accountNumber = scanner.nextInt();
                    System.out.print("Enter withdraw amount: ");
                    double withdrawAmount = scanner.nextDouble();
                    for (Bank b : banks) {
                        if (b.getAccountNumber() == accountNumber) {
                            b.withdraw(withdrawAmount);
                            try {
                                BufferedWriter writer = new BufferedWriter(new FileWriter("bank.txt"));
                                for (Bank bank1 : banks) {
                                    writer.write(bank1.getAccountNumber() + "," + bank1.getName() + "," + bank1.getBalance() + "\n");
                                }
                                writer.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            System.out.println("Withdrawal successful.");
                            break;
                        }
                    }
                    break;
                case 4:
                    System.out.println("Accounts:");
                    for (Bank b : banks) {
                        System.out.println(b.toString());
                    }
                    break;
                case 5:
                    System.out.println("Exiting...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
                    break;
            }
        }
    }
}
