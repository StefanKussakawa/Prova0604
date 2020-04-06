package dao;

import dto.UserDTO;
import model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.util.List;

public class UserDAO {

    private EntityManagerFactory entityManagerFactory;
    private EntityManager em;

    public UserDAO() {
        this.entityManagerFactory = Persistence.createEntityManagerFactory("facebook");
        this.em = this.entityManagerFactory.createEntityManager();
    }

    public void save(UserDTO user) {
        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();
    }

    public void update(UserDTO user) {
        UserDTO byId = findById(user.getId());
        if (byId != null) {
            em.getTransaction().begin();
            em.merge(user);
            em.getTransaction().commit();
        } else {
            System.out.println("Não foi possível achar o user passado como parametro");
        }
    }

    public List<UserDTO> findAll() {
        return em.createQuery("from usuarios ").getResultList();
    }

    public UserDTO findById(Long id) {
        return em.find(UserDTO.class, id);
    }

    public void remove(Long id) {
        em.getTransaction().begin();
        UserDTO user = em.find(UserDTO.class, 1L);
        if (user != null) {
            em.remove(user);
        }
        em.getTransaction().commit();
    }
}