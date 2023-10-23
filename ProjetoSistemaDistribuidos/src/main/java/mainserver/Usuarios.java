package mainserver;

public class Usuarios {
    int id;
    String tipo;
    String nome;
    String email;
    String senha;
    public Usuarios(int id, String tipo, String nome, String email, String senha){
        this.id= id;
        this.tipo = tipo;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    };
}
