package entities;

import tools.OwnerException;
import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "Owner")
public class Owner {
    private static final int START_ID = 0;

    @Id
    @Column(name = "Идентификатор")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column (name = "Имя")
    private String name;

    @Column (name = "\"Дата рождения\"")
    private Date birthdate;

    public Owner(String name, Date birthdate) throws OwnerException {
        if (name.isBlank()) {
            throw OwnerException.nameIsNullException();
        }

        this.id = START_ID;
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
