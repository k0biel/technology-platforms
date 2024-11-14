package org.example;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public class MageService {
    private final EntityManagerFactory entityManagerFactory;

    public MageService(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public void addMage(String mageName, int mageLevel, String towerName) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        try {
            Tower tower = entityManager.find(Tower.class, towerName);
            if (tower != null) {
                Mage mage = new Mage(mageName, mageLevel, tower);
                entityManager.persist(mage);
                entityManager.getTransaction().commit();
            }
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }

    public void removeMage(String mageName) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        try {
            Mage mage = entityManager.find(Mage.class, mageName);
            entityManager.remove(mage);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }

    public void displayMages() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Mage> mages = entityManager.createQuery("SELECT m FROM Mage m", Mage.class).getResultList();
        System.out.println("Mages: ");
        for (Mage mage : mages) {
            System.out.println(mage);
        }
        entityManager.close();
    }
}