package org.example;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class MageRepository {
    private Collection<Mage> collection = new ArrayList<>();

    public Optional<Mage> find(String name) {
        return collection.stream().filter(mage -> mage.getName().equals(name)).findAny();
    }

    public void delete(String name) {
        Mage mage = find(name).orElse(null);
        if (mage == null) {
            throw new IllegalArgumentException();
        }
        collection.remove(mage);
    }

    public void save(Mage mage) {
        if (find(mage.getName()).isPresent()) {
            throw new IllegalArgumentException();
        }
        collection.add(mage);
    }
}