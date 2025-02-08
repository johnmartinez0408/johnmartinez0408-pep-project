package Service;
import DAO.MessageDAO;
import Model.Message;
import java.util.List;

public class MessageService {

    public MessageDAO messageDAO;
    public AccountService accountService;

    //Constructor
    public MessageService(){
        messageDAO = new MessageDAO();
        accountService = new AccountService();
    }
    
    //Create message
    public Message addMessage(Message inputMessage){
        String inputMessageText = inputMessage.getMessage_text();
        if(inputMessageText.equals("") || inputMessageText.length()>255){
            return null;
        }
        //If message is posted by account that doesn't exist, return null
        if(!accountService.accountExists(inputMessage.getPosted_by())){
            return null;
        }

        Message m = messageDAO.addMessage(inputMessage);

        return m;
    }

    //Get all messages
    public List<Message> getAllMessages(){
        return messageDAO.getAllMessages();
    }

    //Get message by id
    public Message getMessageById(int messageId){
        return messageDAO.getMessageById(messageId);
    }

    //Delete message by id
    public Message deleteMessageById(int messageId){
        Message existingMessage = getMessageById(messageId);
        //If message was deleted, return what the message was
        if(messageDAO.deleteMessageById(messageId)){
            return existingMessage;
        }else{
            return null;
        }
    }

    //Update message by id
    public Message updateMessageById(int messageId, String newMessageText){
        
        if(newMessageText == null || newMessageText.equals("") || newMessageText.length()>255){
            return null;
        }
        Message existingMessage = getMessageById(messageId);

        //If message was updated in database return the message with the new text value
        if(messageDAO.updateMessageById(messageId, newMessageText)){
            existingMessage.setMessage_text(newMessageText);
            return existingMessage;
        }else{
            return null;
        } 
    }

    //Get messages by specified author
    public List<Message> getMessagesByAccount(int accountId){
        return messageDAO.getMessagesByAccount(accountId);
    }
}
