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
            }else{
                return null;
            }

        }catch(SQLException e){
            e.printStackTrace();
        }
        return account;
    }

}
