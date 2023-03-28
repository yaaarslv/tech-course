package entities;

import models.CatColor;
import tools.CatException;
import tools.OwnerException;
import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "Cat")
public class Cat {
    private static final int START_ID = 0;

    @Id
    @Column(name = "Идентификатор")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column (name = "Имя")
    private String name;

    @Column (name = "\"Дата рождения\"")
    private Date birthdate;

    @Column (name = "Порода")
    private String breed;

    @Enumerated(EnumType.STRING)
    @Column (name = "Цвет")
    private CatColor color;

    @Column (name = "Владелец")
    private long ownerId;

    public Cat(String name, Date birthdate, String breed, CatColor color, long ownerId) throws OwnerException, CatException {
        if (name.isBlank()) {
            throw CatException.nameIsNullException();
        }

        if (breed.isBlank()) {
            throw CatException.breedIsNullException();
        }

        this.id = START_ID;
        this.name = name;
        this.birthdate = birthdate;
        this.breed = breed;
        this.color = color;
        this.ownerId = ownerId;
    }

    public Cat() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }
}

