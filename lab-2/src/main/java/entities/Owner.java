package entities;

import tools.OwnerException;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
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
}
