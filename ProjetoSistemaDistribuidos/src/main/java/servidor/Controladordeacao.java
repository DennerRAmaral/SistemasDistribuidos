package servidor;

import com.fasterxml.jackson.databind.JsonNode;
import org.mindrot.jbcrypt.BCrypt;

public class Controladordeacao {
    private String action;
    private JsonNode jnode;
    private static Usuarios[] usuariosCadastrados;
    private String email;
    private String senha;


    public Controladordeacao(JsonNode jnode, Usuarios[] usuariosCadastrados) {
        this.jnode = jnode;
        this.action = jnode.get("action").asText();
        this.usuariosCadastrados = usuariosCadastrados;

    }

    public String seletor() {
        switch (action) {
            case "login":
                return loginresposta();
            default:
                System.out.println("acao invalida: " + action);
                throw new IllegalStateException("Unexpected value: " + action);

        }
    }

    private String loginresposta() {
        email = jnode.get("data").get("email").asText();
        senha = jnode.get("data").get("password").asText();
        for (int i = 0; i < usuariosCadastrados.length; i++) {
            if (email.equals(usuariosCadastrados[i].getEmail())) {
                if (verificarSenha(senha, usuariosCadastrados[i].getSenha())) {
                    return "{\"action\":\"login\",\n" +
                            "\"error\":false,\n" +
                            "\"message\":\"logado com sucesso\"\"data\": {\n" +
                            "    \"token\" : \"" + "\"\n" +
                            "  }}";
                }
            }
        }

        return "{\"action\":\"login\",\n" +
                "\"error\":true,\n" +
                "\"message\":\"Falha ao logar\"}";
    }

    private boolean verificarSenha(String senhaDigitada, String senhaArmazenada) {
        return BCrypt.checkpw(senhaDigitada, senhaArmazenada);
    }
}
