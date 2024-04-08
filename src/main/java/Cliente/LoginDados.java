package Cliente;

public class LoginDados {
    private String operacao;
    private String email;
    private String senha;

    LoginDados(String email, String senha){
        this.setOperacao("login");
        this.setEmail(email);
        this.setSenha(senha);
    }

    public String getOperacao() {
        return operacao;
    }

    public void setOperacao(String operacao) {
        this.operacao = operacao;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
