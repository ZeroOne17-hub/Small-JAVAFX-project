package model;

public class PodatakZaTabelu {
    private String id;
    private String od;
    private String za;

    public PodatakZaTabelu(String id, String od, String za) {
        this.id = id;
        this.od = od;
        this.za = za;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOd() {
        return od;
    }

    public void setOd(String od) {
        this.od = od;
    }

    public String getZa() {
        return za;
    }

    public void setZa(String za) {
        this.za = za;
    }

    @Override
    public String toString() {
        return "PodatakZaTabelu{" +
                "id='" + id + '\'' +
                ", od='" + od + '\'' +
                ", za='" + za + '\'' +
                '}';
    }
}

