package Servidor;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;

@Entity
public class Vaga {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_vaga;
    private double faixa_salarial;
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "id_empresa")
    private Empresa empresa;

    // Construtores, getters e setters aqui

    public Vaga() {
        // Construtor padrão
    }

    public Vaga(double faixa_salarial, String descricao, Empresa empresa) {
        this.faixa_salarial = faixa_salarial;
        this.descricao = descricao;
        this.empresa = empresa;
    }

    public int getId_vaga() {
        return id_vaga;
    }

    public void setId_vaga(int id_vaga) {
        this.id_vaga = id_vaga;
    }

    public double getFaixa_salarial() {
        return faixa_salarial;
    }

    public void setFaixa_salarial(double faixa_salarial) {
        this.faixa_salarial = faixa_salarial;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }
}