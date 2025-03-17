package data;

import java.util.List;

public class Role {

    private String rid;
    private String rname;
    private List<Feature> features;

    public Role() {
    }

    public Role(String rid, String rname) {
        this.rid = rid;
        this.rname = rname;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public String getRname() {
        return rname;
    }

    public void setRname(String rname) {
        this.rname = rname;
    }

    public List<Feature> getFeatures() {
        return features;
    }

    public void setFeatures(List<Feature> features) {
        this.features = features;
    }
}
