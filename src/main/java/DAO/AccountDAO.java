package DAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;


import Model.Account;
import Util.ConnectionUtil;


public class AccountDAO {
    


    //Register Account
    public Account addAccount(Account account) {

        String sql = "INSERT INTO account (id, username, password) VALUES (?,?,?);";
        Connection conn = ConnectionUtil.getConnection();
        
        try{

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, account.getAccount_id());
            ps.setString(2, account.getUsername());
            ps.setString(3, account.getPassword());
            ps.executeUpdate();
            ResultSet rs= ps.getGeneratedKeys();
            if(rs.next()){
                int accountId = (int) rs.getLong(1);
                account.setAccount_id(accountId);
            }
            conn.close();

        } catch(SQLException e){
            e.printStackTrace();
            return null;
        }

       
        return account;
    }

    //Process login
    public Account loginToAccount(){
        return null;
    }

}
