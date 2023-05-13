package entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import tools.FleaException;

@Entity
@Table(name = "Flea")
public class Flea {
    @Id
    @Column(name = "Идентификатор")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "Имя")
    private String name;

    @Column (name = "Котик")
    private long catId;

    public Flea(String name, long catId) throws FleaException {
        if (name.isBlank()) {
            throw FleaException.nameIsNullException();
        }

        this.name = name;
        this.catId = catId;
    }

    public Flea() {
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

    public Long getCatId() {
        return catId;
    }

    public void setCatId(Long catId) {
        this.catId = catId;
    }
}

