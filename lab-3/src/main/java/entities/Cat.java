package entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import models.CatColor;
import tools.CatException;
import tools.OwnerException;

import java.sql.Date;

@Entity
@Table(name = "Cat")
public class Cat {
    @Id
    @Column(name = "Идентификатор")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @Column(name = "\"Длина хвоста\"")
    private int tailLength;

    public Cat(String name, Date birthdate, String breed, CatColor color, long ownerId, int tailLength) throws OwnerException, CatException {
        if (name.isBlank()) {
            throw CatException.nameIsNullException();
        }

        if (breed.isBlank()) {
            throw CatException.breedIsNullException();
        }

        this.name = name;
        this.birthdate = birthdate;
        this.breed = breed;
        this.color = color;
        this.ownerId = ownerId;
        this.tailLength = tailLength;
    }

    public Cat() {
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
}

