package ownerWrappers;

import entities.Owner;

import java.sql.Date;

public class OwnerDataWrapper {
    private Long id;

    private String name;

    private Date birthdate;

    public OwnerDataWrapper(Long id, String name, Date birthdate) {
        this.id = id;
        this.name = name;
        this.birthdate = birthdate;
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

    public static OwnerDataWrapper toWrapper(Owner owner) {
        return new OwnerDataWrapper(owner.getId(), owner.getName(), owner.getBirthdate());
    }

    public Owner fromWrapper() {
        Owner owner = new Owner();
        owner.setId(id);
        owner.setName(name);
        owner.setBirthdate(birthdate);
        return owner;
    }
}
