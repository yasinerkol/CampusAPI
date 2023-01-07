package Campus.Model;

public class SchoolLocation {
  private String id;
  private String name;
  private String shortName;
  private int capacity;

  private boolean active;

  private String type;

  private String school;

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

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

  public String getType() {
      return type;
  }

  public void setType(String type) {
      this.type = type;
  }

  public String getSchool() {
      return school;
  }

  public void setSchool(String school) {
      this.school = school;
  }

    @Override
    public String toString() {
        return "SchoolLocation{" +
                "id='" + id + '\'' +
                ", Name='" + name + '\'' +
                ", ShortName='" + shortName + '\'' +
                ", Capacity=" + capacity +
               ", active=" + active +
               ", type='" + type + '\'' +
               ", school='" + school + '\'' +
                '}';
    }
}
