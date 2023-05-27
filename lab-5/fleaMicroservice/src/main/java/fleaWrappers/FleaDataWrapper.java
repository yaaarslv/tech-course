package fleaWrappers;

import entities.Cat;
import entities.Flea;
import fleaTools.FleaException;

public class FleaDataWrapper {
    private Long id;

    private String name;

    private Long catId;

    public FleaDataWrapper(Long id, String name, Long catId) {
        this.id = id;
        this.name = name;
        this.catId = catId;
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

    public static FleaDataWrapper toWrapper(Flea flea) {
        return new FleaDataWrapper(flea.getId(), flea.getName(), flea.getCatId());
    }

    public Flea fromWrapper() {
        Flea flea = new Flea();
        flea.setId(id);
        flea.setName(name);
        flea.setCatId(catId);
        return flea;
    }
}
