package org.example;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public class TowerService {
    private final EntityManagerFactory entityManagerFactory;

    public TowerService(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public void addTower(String towerName, int towerHeight) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        try {
            Tower tower = new Tower(towerName, towerHeight);
            entityManager.persist(tower);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }

    public void removeTower(String towerName) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        try {
            Tower tower = entityManager.find(Tower.class, towerName);
            entityManager.remove(tower);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }

    public void displayTowers() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Tower> towers = entityManager.createQuery("SELECT t FROM Tower t", Tower.class).getResultList();
        System.out.println("Towers: ");
        for (Tower tower : towers) {
            System.out.println(tower);
        }
        entityManager.close();
    }
}