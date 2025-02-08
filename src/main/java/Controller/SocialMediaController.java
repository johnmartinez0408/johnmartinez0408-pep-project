package Controller;

import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.management.InvalidAttributeValueException;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;

import Model.Account;
import Model.Message;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    
    AccountService accountService;
    MessageService messageService;
    
    //Constructor
    public SocialMediaController(){
        accountService = new AccountService();
        messageService = new MessageService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);
        
        //Endpoint handlers
        //Register Account
        app.post("/register", this::addAccountHandler);

        //Process login
        app.post("/login", this::loginHandler);

        //Create message
        app.post("/messages", this::createMessageHandler);

        //Get all messages
        app.get("/messages", this::getAllMessagesHandler);

        //Get message by id
        app.get("/messages/{message_id}", this::getMessageByIdHandler);

        //Delete message by id
        app.delete("/messages/{message_id}", this::deleteMessageByIdHandler);
        
        //Update message by id
        app.patch("/messages/{message_id}", this::updateMessageByIdHandler);

        //Get messages by specified author
        app.get("/accounts/{account_id}/messages", this::getMessagesByAccountHandler);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    //Register new account
    private void addAccountHandler(Context context) throws JsonProcessingException{
        ObjectMapper om = new ObjectMapper();
        Account account = om.readValue(context.body(), Account.class);
       
        try{
            accountService.addAccount(account);
            context.json(om.writeValueAsString(account));
            context.status(200);
        }catch(InvalidAttributeValueException e){ //Exception when username is null, pw is less than 4 chars, etc
            e.printStackTrace();
            context.status(400);
        }

    }

    //Handle login attempt
    private void loginHandler(Context context) throws JsonProcessingException{
        ObjectMapper om = new ObjectMapper();
        Account account = om.readValue(context.body(), Account.class);
        Account loggedInAccount = accountService.loginToAccount(account);
        
        if(loggedInAccount != null){
            context.json(om.writeValueAsString(account));
            context.status(200);
        }else{
            context.status(401);
        }
    }

    //Create message
    private void createMessageHandler(Context context) throws JsonProcessingException{
        ObjectMapper om = new ObjectMapper();
        Message inputMessage = om.readValue(context.body(), Message.class);
        Message outputMessage = messageService.addMessage(inputMessage);
        if(outputMessage!=null){
            context.json(om.writeValueAsString(outputMessage));
            context.status(200);
        }else{
            context.status(400);
        }
    }

    //Get all messages
    private void getAllMessagesHandler(Context context){
        List<Message> messages = messageService.getAllMessages();
        context.json(messages);
    } 

    //Get message by id
    public void getMessageByIdHandler(Context context){
        int messageId = Integer.parseInt(context.pathParam("message_id"));
        Message message = messageService.getMessageById(messageId);
        if(message!=null){
            context.json(message);
        }
    }

    //Delete message by id
    public void deleteMessageByIdHandler(Context context){
        int messageId = Integer.parseInt(context.pathParam("message_id"));
        Message message = messageService.deleteMessageById(messageId);
        if(message!=null){
            context.json(message);
        }
    }

    //Update message by id
    public void updateMessageByIdHandler(Context context) throws JsonProcessingException{
        ObjectMapper om = new ObjectMapper();
        Message inputMessage = om.readValue(context.body(), Message.class);
        int messageId = Integer.parseInt(context.pathParam("message_id"));
        Message message = messageService.updateMessageById(messageId, inputMessage.getMessage_text());
        if(message!=null){
            context.json(om.writeValueAsString(message));
            context.status(200);
        }else{
            context.status(400);
        }
    }

    //Get messages by specified author
    public void getMessagesByAccountHandler(Context context){
        int authorId = Integer.parseInt(context.pathParam("account_id"));
        List<Message> messages = messageService.getMessagesByAccount(authorId);
        context.json(messages);
    }

}