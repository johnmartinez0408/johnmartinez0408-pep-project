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
        //Validate parameters
        if(account.getUsername() == null || account.getPassword().length()<4){
            throw new InvalidAttributeValueException("Invalid data provided for adding an account from MessageService");
        }

        //TODO verify that user isn't already in db
        // if(false){
        //     return null;
        // }

        return accountDAO.addAccount(account);
    }
    

    //Process login
    public Account loginToAccount(Account account){
        return accountDAO.loginToAccount(account);
    }

}
