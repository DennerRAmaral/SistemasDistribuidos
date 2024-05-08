package Servidor;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class TesteConexaoBanco {

    public static void main(String[] args) {
        String persistenceUnitName = "sistemadistribuidodb";
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnitName);
        EntityManager em = emf.createEntityManager();
        try {
            if (em.isOpen()) {
                System.out.println("Conexão com o banco de dados estabelecida!");
            } else {
                System.out.println("Erro ao conectar ao banco de dados.");
            }
        } finally {
            em.close();
            emf.close();
        }
    }
}