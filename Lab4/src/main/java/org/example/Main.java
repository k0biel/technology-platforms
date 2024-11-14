package org.example;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {
    private static EntityManagerFactory entityManagerFactory;

    public static void main(String[] args) {
        entityManagerFactory = Persistence.createEntityManagerFactory("myPersistenceUnit");

        populateDatabase();

        ConsoleController consoleController = new ConsoleController(entityManagerFactory);
        consoleController.start();

        entityManagerFactory.close();
    }

    private static void populateDatabase() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        try {
            Tower tower1 = new Tower("Tower1", 100);
            Tower tower2 = new Tower("Tower2", 150);
            entityManager.persist(tower1);
            entityManager.persist(tower2);

            Mage mage1 = new Mage("Mage1", 10, tower1);
            Mage mage2 = new Mage("Mage2", 20, tower2);
            entityManager.persist(mage1);
            entityManager.persist(mage2);

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }
}