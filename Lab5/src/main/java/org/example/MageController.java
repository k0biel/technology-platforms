package org.example;

public class MageController {
    private MageRepository repository;

    public MageController(MageRepository repository) {
        this.repository = repository;
    }

    public String find(String name) {
        return repository.find(name).map(Mage::getName).orElse("not found");
    }

    public String delete(String name) {
        try {
            repository.delete(name);
            return "done";
        } catch (IllegalArgumentException e) {
            return "not found";
        }
    }

    public String save(String name, String level) {
        if (repository.find(name).isPresent()) {
            return "bad request";
        }
        Mage mage = new Mage();
        mage.setName(name);
        mage.setLevel(Integer.parseInt(level));
        repository.save(mage);
        return "done";
    }
}