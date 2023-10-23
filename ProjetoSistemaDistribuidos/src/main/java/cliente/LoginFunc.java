package cliente;

import java.io.IOException;
import org.mindrot.jbcrypt.BCrypt;

public class LoginFunc {
    private UserInteraction userInteraction;
    private SocketManager socketManager;
    private String password;
    private String email;
    private String hashedPassword;
    private String json;

    public LoginFunc( UserInteraction userInteraction,SocketManager socketManager){
        this.userInteraction = userInteraction;
        this.socketManager = socketManager;
    }

    public void loginrun() throws IOException {
        email = userInteraction.getEmail();
        password = userInteraction.getPassword();
        hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        json = "{\"action\": \"login\",\"data\": {\"email\": \"" + email + "\",\"password\": \""
                + hashedPassword + "\"}}";
        System.out.println("Enviando JSON:\n" + json);
        socketManager.sendMessage(json);
    }
}
