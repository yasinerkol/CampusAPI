package Campus.Model;

public class Attestation {

    private String name;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return "Attestation{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
