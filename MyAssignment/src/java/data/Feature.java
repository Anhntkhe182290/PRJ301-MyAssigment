package data;

import java.util.List;

public class Feature {

    private String id;
    private String url;
    private List<Role> roles;

    public Feature() {
    }

    public Feature(String id, String url) {
        this.id = id;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "Feature{id='" + id + "', url='" + url + "'}";
    }
}
