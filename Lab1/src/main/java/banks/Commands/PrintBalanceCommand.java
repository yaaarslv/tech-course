package banks.Commands;

import banks.Entities.*;

/**
 * A command that prints the balance of a bank account to the console
 */
public class PrintBalanceCommand implements Command {
    private final BankAccount bankAccount;

    /**
     * Creates a new PrintBalanceCommand for the specified bank account
     * @param bankAccount the bank account to print the balance of
     */
    public PrintBalanceCommand(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    /**
     * Prints the balance of the bank account to the console
     */
    public void execute() {
        System.out.println(bankAccount.printAccountData());
    }
}
