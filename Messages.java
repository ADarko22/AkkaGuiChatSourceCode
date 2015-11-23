package remote;

import java.io.Serializable;

import akka.actor.ActorRef;

public class Messages implements Serializable{
	
	class ChatMessage implements Serializable {
    	private String content;
    
    	public ChatMessage(String s) { 
    		content = s;
    	}

        public String getContent() {
        	return content;
        }	
	}

	class LoginMessage implements Serializable {
		
		private String nickname;

		public LoginMessage(String nickname) { 
			this.nickname = nickname;
		}

		public String getNickname() {
			return nickname;
		}	
	}
	class RejectLogin implements Serializable {
		
		private String cause;

		public RejectLogin(String cause) {
			this.cause = cause;
		}

		public String getCause() {
			return cause;
		}
	}
	class AckLogin implements Serializable {
		private String users;

		public AckLogin(String users) {
			this.users = users;
		}

		public String getUsers() {

			return this.users;

		}
	}
	class ToPrintMessage implements Serializable{
		private String content;
		
		public ToPrintMessage(String s){
			this.content = s;
		}

    	public String getContent(){
    		return this.content;
    	}
	}

	class LogoutMessage implements Serializable {}
}
