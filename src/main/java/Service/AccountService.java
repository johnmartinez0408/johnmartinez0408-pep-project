package Service;

import DAO.AccountDAO;
import Model.Account;

import javax.management.InvalidAttributeValueException;

public class AccountService {
    
    public AccountDAO accountDAO;

    //Constructor
    public AccountService(){
        accountDAO = new AccountDAO();
    }

    //Register Account
    public Account addAccount(Account account) throws InvalidAttributeValueException{
        
        Account existingAccountInDb = accountDAO.getAccountByUsername(account.username);
        
        //Validate parameters
        if(existingAccountInDb!=null || account.getUsername() == null || account.getPassword().length()<4 || account.getUsername().equals("")){
            throw new InvalidAttributeValueException("Invalid data provided for adding an account from MessageService");
        }

        return accountDAO.addAccount(account);
    }
    

    //Process login
    public Account loginToAccount(Account account){
        return accountDAO.loginToAccount(account);
    }

    //Checks if account with specified accountId is present in database
    public boolean accountExists(int accountId){
        return accountDAO.accountExists(accountId);
    }

}
