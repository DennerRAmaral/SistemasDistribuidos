package servidor;

public class Usuarios {
    private int id;
    private String tipo;
    private String nome;
    private String email;
    private String senha;
    public Usuarios(int id, String tipo, String nome, String email, String senha){
        this.id= id;
        this.tipo = tipo;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    };

    public int getId() {
        return id;
    }

    public String getTipo() {
        return tipo;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }
}
