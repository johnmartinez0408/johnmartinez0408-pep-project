package DAO;

import Util.ConnectionUtil;
import java.util.List;
import java.util.LinkedList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;

import Model.Message;
import Util.ConnectionUtil;

public class MessageDAO {
    
    //Create message
    public Message addMessage(Message message){
        String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?)";
        Connection conn = ConnectionUtil.getConnection();
        try{
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, message.getPosted_by());
            ps.setString(2, message.getMessage_text());
            ps.setLong(3, message.getTime_posted_epoch());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()){
                int generatedId = (int) rs.getLong(1);
                message.setMessage_id(generatedId);
            }else{
                return null;
            }

        }catch(SQLException e){
            e.printStackTrace();
            return null;
        }
        return message;
    }

    //Get all messages
    public List<Message> getAllMessages(){
        String sql = "SELECT * FROM message;";
        Connection conn = ConnectionUtil.getConnection();
        
        try{
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            List<Message> messages = new LinkedList<Message>();
            while(rs.next()){
                Message m = new Message();
                m.setMessage_id(rs.getInt("message_id"));
                m.setPosted_by(rs.getInt("posted_by"));
                m.setMessage_text(rs.getString("message_text"));
                m.setTime_posted_epoch(rs.getLong("time_posted_epoch"));
                messages.add(m);
            }
            return messages;

        }catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    //Get message by id
    public Message getMessageById(int messageId){
        String sql = "SELECT * FROM message WHERE message_id = ?;";
        Connection conn = ConnectionUtil.getConnection();
        
        try{
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, messageId);
            ResultSet rs = ps.executeQuery();
            Message message = new Message();
            if(rs.next()){
                message.setMessage_id(rs.getInt("message_id"));
                message.setMessage_text(rs.getString("message_text"));
                message.setPosted_by(rs.getInt("posted_by"));
                message.setTime_posted_epoch(rs.getLong("time_posted_epoch"));
                return message;
            }else{
                return null;
            }

        }catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    //Delete message by id
    public boolean deleteMessageById(int messageId){
        String sql = "DELETE FROM message WHERE message_id = ?;";
        Connection conn = ConnectionUtil.getConnection();
        try{
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,messageId);
            int rowsDeleted = ps.executeUpdate();
            if(rowsDeleted>0){
                return true;
            }else{
                return false;
            }
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    //Update message by id
    public boolean updateMessageById(int messageId){
        String sql = "UPDATE message SET message_text=? WHERE message_id=?";
        Connection conn = ConnectionUtil.getConnection();
        try{
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, messageId);
            int rowsUpdated = ps.executeUpdate();
            if(rowsUpdated>0){
                return true;
            }else{
                return false;
            }

        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    //Get messages by specified author
    public List<Message> getMessagesByAccount(int accountId){
        String sql = "SELECT * FROM message WHERE posted_by = ?";
        Connection conn = ConnectionUtil.getConnection();
        try{
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, accountId);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){

            }
            
        }catch(SQLException e){
            e.printStackTrace();
            return null;
        }
        return null;
    }
}
