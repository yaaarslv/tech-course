package entities;

import tools.OwnerException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.sql.Date;

@Entity
@Table(name = "Owner")
public class Owner {
    @Id
    @Column(name = "Идентификатор")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "Имя")
    private String name;

    @Column (name = "\"Дата рождения\"")
    private Date birthdate;

    public Owner(String name, Date birthdate) throws OwnerException {
        if (name.isBlank()) {
            throw OwnerException.nameIsNullException();
        }

        this.name = name;
        this.birthdate = birthdate;
    }

    public Owner() {

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
}
