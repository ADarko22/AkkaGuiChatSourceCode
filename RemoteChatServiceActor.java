package remote;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.typesafe.config.ConfigFactory;
import java.util.HashMap;

public class RemoteChatServiceActor extends UntypedActor {
  
  
  private GuiServer chat;
  private HashMap <ActorRef,String> users;
  private Messages messages;
        
  public RemoteChatServiceActor(GuiServer chat) {
    this.chat = chat;
    messages = new Messages();
    users = new HashMap<>();
<<<<<<< HEAD
  }

  private void writeToLog(String logMessage) {
      this.chat.getLog().setText(this.chat.getLog().getText()+logMessage+"\n");
=======

    /*
    for(ActorRef ref : users.keySet())
        ref.tell(,getSelf());
    */
  }

  private void writeToLog(String logMessage) {
      this.chat.getLog().setText(this.chat.getLog().getText()+logMessage);
>>>>>>> refs/remotes/marianobasile/master
  }
        
  @Override
  public void onReceive(Object message) {
      
      switch(message.getClass().getSimpleName()) {

        case "LoginMessage": 
                            handleLoginMessage(message); 
                            break;

        case "LogoutMessage": 
                            //handleLogoutMessage(message); 
                             break;

        case "ChatMessage": 
<<<<<<< HEAD
                            handleChatMessage(message); 
=======
                            //handleChatMessage(message); 
>>>>>>> refs/remotes/marianobasile/master
                            break;

        default: 
                writeToLog("Messaggio non riconosciuto!...");
                break;
                        
      }
  }

  private void handleLoginMessage(Object message) {
          writeToLog("ok");
  }
<<<<<<< HEAD
  
  private void handleChatMessage(Object message){
	    
	  writeToLog(users.get(getSender())+" writes a message");
	  
	  Messages.ToPrintMessage toPrint = messages.new ToPrintMessage(users.get(getSender())+":\n"+((Messages.ChatMessage)message).getContent().trim());
//test
getSender().tell(toPrint, getSelf());
	  
	  for(ActorRef ref : users.keySet())
		  ref.tell(toPrint, getSelf());      
  }
  
=======

>>>>>>> refs/remotes/marianobasile/master
  public static void main(String[] args) {

    GuiServer frame = new GuiServer();
    frame.setVisible(true);
    frame.setLocationRelativeTo(null);
    frame.setTitle("Server - Akka Chat");

    final ActorSystem system = ActorSystem.create("ChatSystem", ConfigFactory.load(("chat")));

    final ActorRef remoteActor =system.actorOf(Props.create(RemoteChatServiceActor.class,frame), "remoteActor");

    frame.setActorReference(remoteActor);
    frame.printBootstrapMessage();
  }

   
}
