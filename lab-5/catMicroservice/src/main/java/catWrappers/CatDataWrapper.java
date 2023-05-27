package catWrappers;

import entities.Cat;
import models.CatColor;

import java.sql.Date;

public class CatDataWrapper {
    private Long id;

    private String name;

    private Date birthdate;

    private String breed;

    private CatColor color;

    private Long ownerId;

    private int tailLength;

    public CatDataWrapper(Long id, String name, Date birthdate, String breed, CatColor color, long ownerId, int tailLength) {
        this.id = id;
        this.name = name;
        this.birthdate = birthdate;
        this.breed = breed;
        this.color = color;
        this.ownerId = ownerId;
        this.tailLength = tailLength;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public CatColor getColor() {
        return color;
    }

    public void setColor(CatColor color) {
        this.color = color;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public int getTailLength() {
        return tailLength;
    }

    public void setTailLength(int tailLength) {
        this.tailLength = tailLength;
    }

    public static CatDataWrapper toWrapper(Cat cat) {
        return new CatDataWrapper(cat.getId(), cat.getName(), cat.getBirthdate(), cat.getBreed(), cat.getColor(), cat.getOwnerId(), cat.getTailLength());
    }

    public Cat fromWrapper() {
        Cat cat = new Cat();
        cat.setId(id);
        cat.setName(name);
        cat.setBirthdate(birthdate);
        cat.setBreed(breed);
        cat.setColor(color);
        cat.setOwnerId(ownerId);
        cat.setTailLength(tailLength);
        return cat;
    }
}