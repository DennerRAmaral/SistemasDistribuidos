package Servidor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;

@Entity
public class CandidatoVaga {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_candidato_vaga;
    private boolean visualizou;

    @ManyToOne
    @JoinColumn(name = "id_candidato")
    private Candidato candidato;

    @ManyToOne
    @JoinColumn(name = "id_vaga")
    private Vaga vaga;

    public CandidatoVaga(boolean visualizou, Candidato candidato, Vaga vaga) {
        this.visualizou = visualizou;
        this.candidato = candidato;
        this.vaga = vaga;
    }

    public int getId_candidato_vaga() {
        return id_candidato_vaga;
    }

    public void setId_candidato_vaga(int id_candidato_vaga) {
        this.id_candidato_vaga = id_candidato_vaga;
    }

    public boolean isVisualizou() {
        return visualizou;
    }

    public void setVisualizou(boolean visualizou) {
        this.visualizou = visualizou;
    }

    public Candidato getCandidato() {
        return candidato;
    }

    public void setCandidato(Candidato candidato) {
        this.candidato = candidato;
    }

    public Vaga getVaga() {
        return vaga;
    }

    public void setVaga(Vaga vaga) {
        this.vaga = vaga;
    }
}