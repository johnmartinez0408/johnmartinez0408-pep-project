package DAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;

import Model.Account;
import Util.ConnectionUtil;


public class AccountDAO {
    


    //Register Account
    public Account addAccount(Account account) {

        String sql = "INSERT INTO account (username, password) VALUES (?,?);";
        Connection conn = ConnectionUtil.getConnection();
        
        try{
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());
            ps.executeUpdate();
           
            ResultSet rs= ps.getGeneratedKeys();
            if(rs.next()){
                int generatedAccountId = (int) rs.getLong(1);
                account.setAccount_id(generatedAccountId);
            }
            conn.close();

        } catch(SQLException e){
            e.printStackTrace();
            return null;
        }
        return account;
    }

    //Process login
    public Account loginToAccount(Account account){
    
        String sql = "SELECT * FROM account WHERE username = ? AND password = ?;";
        Connection conn = ConnectionUtil.getConnection();
        try{
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                account.setAccount_id((int) rs.getLong(1));
                return account;
            }else{
                return null;
            }

        }catch(SQLException e){
            e.printStackTrace();
            return null;
        } 
    }

    //Get account by username -- used for duplicate check when creating an account
    public Account getAccountByUsername(String username){
        String sql = "SELECT * FROM account WHERE username =?;";
        Connection conn = ConnectionUtil.getConnection();

        try{
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                Account account = new Account();
                account.setAccount_id(rs.getInt("account_id"));
                account.setPassword(rs.getString("password"));
                account.setUsername(username);
                return account;
            }else{
                return null;
            }
        }catch(SQLException e){
            return null;
        }
    }

    //Returns a boolean value representing if an account exists in the db with the specified account id
    public boolean accountExists(int accountId){
        String sql = "SELECT * FROM account WHERE account_id = ?";
        Connection conn = ConnectionUtil.getConnection();

        try{
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, accountId);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return true;
            }else{
                return false;
            }
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }

}
