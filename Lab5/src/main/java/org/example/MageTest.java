package org.example;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MageTest {
    @Test
    public void testSaveMageInRepository() {
        System.out.println("Test saving a new mage - repository");

        MageRepository repository = new MageRepository();
        Mage mage = new Mage();
        mage.setName("Mage1");
        mage.setLevel(1);

        repository.save(mage);
        assertEquals(mage, repository.find("Mage1").orElse(null));
    }

    @Test
    public void testSaveExistingMageInRepository() {
        System.out.println("Test saving an existing mage - repository");

        MageRepository repository = new MageRepository();
        Mage mage = new Mage();
        mage.setName("Mage1");
        mage.setLevel(1);

        repository.save(mage);
        assertThrows(IllegalArgumentException.class, () -> repository.save(mage));
    }

    @Test
    public void testFindNonExistingMageInRepository() {
        System.out.println("Test finding a non-existing mage - repository");

        MageRepository repository = new MageRepository();

        assertFalse(repository.find("Mage2").isPresent());
    }

    @Test
    public void testFindExistingMageInRepository() {
        System.out.println("Test finding an existing mage - repository");

        MageRepository repository = new MageRepository();
        Mage mage = new Mage();
        mage.setName("Mage1");
        mage.setLevel(1);

        repository.save(mage);
        assertTrue(repository.find("Mage1").isPresent());
    }

    @Test
    public void testDeleteMageInRepository() {
        System.out.println("Test deleting an existing mage - repository");

        MageRepository repository = new MageRepository();
        Mage mage = new Mage();
        mage.setName("Mage1");
        mage.setLevel(1);

        repository.save(mage);
        repository.delete("Mage1");
        assertFalse(repository.find("Mage1").isPresent());
    }

    @Test
    public void testDeleteNonExistingMageInRepository() {
        System.out.println("Test deleting a non-existing mage - repository");

        MageRepository repository = new MageRepository();

        assertThrows(IllegalArgumentException.class, () -> repository.delete("Mage2"));
    }

    @Test
    public void testSaveNewMageInController() {
        System.out.println("Test saving a new mage - controller");

        MageRepository repository = Mockito.mock(MageRepository.class);
        MageController controller = new MageController(repository);
        Mage mage = new Mage();
        mage.setName("Mage1");
        mage.setLevel(1);

        when(repository.find("Mage1")).thenReturn(Optional.empty());
        doNothing().when(repository).save(any(Mage.class));
        assertEquals("done", controller.save("Mage1", "1"));
    }

    @Test
    public void testSaveExistingMageInController() {
        System.out.println("Test saving an existing mage - controller");

        MageRepository repository = Mockito.mock(MageRepository.class);
        MageController controller = new MageController(repository);
        Mage mage = new Mage();
        mage.setName("Mage1");
        mage.setLevel(1);

        when(repository.find("Mage1")).thenReturn(Optional.of(mage));
        assertEquals("bad request", controller.save("Mage1", "1"));
    }

    @Test
    public void testFindExistingMageInController() {
        System.out.println("Test finding an existing mage - controller");

        MageRepository repository = Mockito.mock(MageRepository.class);
        MageController controller = new MageController(repository);
        Mage mage = new Mage();
        mage.setName("Mage1");
        mage.setLevel(1);

        when(repository.find("Mage1")).thenReturn(Optional.of(mage));
        assertEquals("Mage1", controller.find("Mage1"));
    }

    @Test
    public void testFindNonExistingMageInController() {
        System.out.println("Test finding a non-existing mage - controller");

        MageRepository repository = Mockito.mock(MageRepository.class);
        MageController controller = new MageController(repository);

        when(repository.find("Mage2")).thenReturn(Optional.empty());
        assertEquals("not found", controller.find("Mage2"));
    }

    @Test
    public void testDeleteExistingMageInController() {
        System.out.println("Test deleting an existing mage - controller");

        MageRepository repository = Mockito.mock(MageRepository.class);
        MageController controller = new MageController(repository);
        Mage mage = new Mage();
        mage.setName("Mage1");
        mage.setLevel(1);

        when(repository.find("Mage1")).thenReturn(Optional.of(mage));
        doNothing().when(repository).delete("Mage1");
        assertEquals("done", controller.delete("Mage1"));
    }

    @Test
    public void testDeleteNonExistingMageInController() {
        System.out.println("Test deleting a non-existing mage - controller");

        MageRepository repository = Mockito.mock(MageRepository.class);
        MageController controller = new MageController(repository);

        doThrow(new IllegalArgumentException()).when(repository).delete("Mage2");
        assertEquals("not found", controller.delete("Mage2"));
    }
}