package Controller;

import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.management.InvalidAttributeValueException;

import com.fasterxml.jackson.core.JsonProcessingException;

import Model.Account;

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

        //Get all messages

        //Get message by id

        //Delete message by id

        //Update message by id

        //Get messages by specified author

        app.start(8080);
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
        context.json(om.writeValueAsString(account));
        if(loggedInAccount != null){
            context.status(200);
        }else{
            context.status(401);
        }
    }
}