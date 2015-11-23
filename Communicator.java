package remote;
//....//
import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import akka.actor.ActorSystem;
import akka.actor.Props;
import java.io.Serializable;
import akka.actor.ActorIdentity;
import akka.actor.Identify;
import akka.actor.Terminated;
import akka.actor.ReceiveTimeout;
import akka.japi.Procedure;
import static java.util.concurrent.TimeUnit.SECONDS;
import scala.concurrent.duration.Duration;
import com.typesafe.config.ConfigFactory;


public class Communicator extends UntypedActor {

    private GuiChat chat;
    private final String path;
    private ActorRef remoteActor = null;
    
    /*constructor*/
    public Communicator(String path, GuiChat chat) {
        this.chat = chat;
        this.path = path;
        sendIdentifyRequest();
    }
    /*send an Identify request to the server address*/
    private void sendIdentifyRequest() {

        getContext().actorSelection(path).tell(new Identify(path), getSelf());
        getContext().system().scheduler().scheduleOnce(Duration.create(3, SECONDS), getSelf(),ReceiveTimeout.getInstance(), getContext().dispatcher(), getSelf());
    }
    
    /*try to establilish te connection with the server*/
    @Override
    public void onReceive(Object message) throws Exception {
    	
    	/*when server reply with its own ActorRef*/
        if (message instanceof ActorIdentity) {
        	
            remoteActor = ((ActorIdentity) message).getRef();
            
            if (remoteActor == null) {
            	
                System.out.println("Remote actor not available: " + path);
            }
            else {
            	
            	this.chat.getRoom().setText("SUCCESSFULLY CONNECTED TO THE SERVER!!");
                getContext().watch(remoteActor);
                /*Communicator becomes to interacte with the chat service*/
                getContext().become(active, true);
             }
        }
        /*when timeout is elapsed without the server reply, retry the Identify*/
        else if (message instanceof ReceiveTimeout) {
            sendIdentifyRequest();
        }
        /**/
        else {
            writeToRoom("Not ready yet");
        }
    }
    /*define the Communicator new behaviour after the connection with the server*/
    Procedure<Object> active = new Procedure<Object>() {
    @Override
        public void apply(Object message) {
          
            switch(message.getClass().getSimpleName()) {

                case "Terminated": 
                        writeToRoom("Calculator terminated");
                        sendIdentifyRequest();
                        getContext().unbecome();
                        break;

                case "ReceiveTimeout": 
                        				//ignore
                         				break;

                case "Reply": 
                                		break;
                
                case "ChatMessage":
                						remoteActor.tell(message, getSelf());
                                		break;

                case "ToPrintMessage": //print the message in the GUI (è meglio usare msgToPrint come private?)
                    					String msgToPrint = ((Messages.ToPrintMessage)message).getContent();
					                    writeToRoom(msgToPrint);
					                    break;
  
                default: 
                       //writeToRoom("Messaggio non riconosciuto!...");
            }
    	}
    };
    
    /*update the chatroom*/
    private void writeToRoom(String logMessage) {
    	
       this.chat.getRoom().setText(chat.getRoom().getText()+"\n"+logMessage);
    }
    
    /*______START the CHAT CLIENT______*/
	public static void main(String[] args) {

        GuiChat frame = new GuiChat();
	    frame.setVisible(true);
	    frame.setLocationRelativeTo(null);
	    frame.setTitle("Remote Chat with Akka");
	    

        final ActorSystem system = ActorSystem.create("ClientChatSystem", ConfigFactory.load("remotelookup"));

        final String path = "akka.tcp://ChatSystem@127.0.0.1:2552/user/remoteActor";

        final ActorRef communicator = system.actorOf(Props.create(Communicator.class, path, frame), "communicator");
            
            frame.setActorReference(communicator);
    }
}


