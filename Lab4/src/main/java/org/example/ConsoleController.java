package org.example;

import javax.persistence.EntityManagerFactory;
import java.util.Scanner;

public class ConsoleController {
    private final TowerService towerService;
    private final MageService mageService;

    public ConsoleController(EntityManagerFactory entityManagerFactory) {
        this.towerService = new TowerService(entityManagerFactory);
        this.mageService = new MageService(entityManagerFactory);
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        String command;

        System.out.println("Enter a command (addTower, addMage, displayTowers, displayMages, removeTower, removeMage, exit):");

        while (!(command = scanner.nextLine()).equals("exit")) {
            switch (command) {
                case "addTower":
                    System.out.println("Enter tower name:");
                    String towerName = scanner.nextLine();
                    System.out.println("Enter tower height:");
                    int towerHeight = Integer.parseInt(scanner.nextLine());
                    towerService.addTower(towerName, towerHeight);
                    break;
                case "addMage":
                    System.out.println("Enter mage name:");
                    String mageName = scanner.nextLine();
                    System.out.println("Enter mage level:");
                    int mageLevel = Integer.parseInt(scanner.nextLine());
                    System.out.println("Enter tower name for the mage:");
                    String mageTowerName = scanner.nextLine();
                    mageService.addMage(mageName, mageLevel, mageTowerName);
                    break;
                case "displayTowers":
                    towerService.displayTowers();
                    break;
                case "displayMages":
                    mageService.displayMages();
                    break;
                case "removeTower":
                    System.out.println("Enter tower name to remove:");
                    String removeTowerName = scanner.nextLine();
                    towerService.removeTower(removeTowerName);
                    break;
                case "removeMage":
                    System.out.println("Enter mage name to remove:");
                    String removeMageName = scanner.nextLine();
                    mageService.removeMage(removeMageName);
                    break;
                default:
                    System.out.println("Invalid command. Available commands: addTower, addMage, displayTowers, displayMages, removeTower, removeMage, exit");
                    break;
            }
            System.out.println("Enter a command (addTower, addMage, displayTowers, displayMages, removeTower, removeMage, exit):");
        }
        scanner.close();
    }
}