package Campus.Model;

public class GradeLevels {
    private String name;
    private String shotName;
    private String order;
    private String id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShotName() {
        return shotName;
    }

    public void setShotName(String shotName) {
        this.shotName = shotName;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "GradeLevels{" +
                "name='" + name + '\'' +
                ", shotName='" + shotName + '\'' +
                ", order='" + order + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
