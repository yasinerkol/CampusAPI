package Campus.Model;

public class HR_Positions {

  private String name;
  private String shortName;
  private String id;
  private String tanentId;
  private  Boolean active;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getTanentId() {
        return tanentId;
    }

    public void setTanentId(String tanentId) {
        this.tanentId = tanentId;
    }

    @Override
    public String toString() {
        return "HR_Positions{" +
                "name='" + name + '\'' +
                ", shortName='" + shortName + '\'' +
                ", id='" + id + '\'' +
                ", tanentId='" + tanentId + '\'' +
                '}';
    }
}
