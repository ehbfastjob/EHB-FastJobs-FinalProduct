package be.ehb.ipg13.fastjobs;

/**
 * Created by Nadir on 19/04/2015.
 */
public class ModelTest {
    public String id;
    public String titel;
    public String gemeente;

    public ModelTest() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public String getGemeente() {
        return gemeente;
    }

    public void setGemeente(String gemeente) {
        if (!(gemeente == null) || !(gemeente.equals("")) || !(gemeente.equals(" "))) {
            this.gemeente = gemeente;
        } else {
            this.gemeente = "NULL";
        }
    }
}
