package hibis;

import javax.persistence.*;

@Entity
@Table(name = "postinumero")
public class Postinumero {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "postinumero")
    private String postinumero;

    @Column(name = "kaupunginNimi")
    private String kaupunginNimi;

    public Postinumero() {}

    public Postinumero(String nimi, String postinumero) {
        this.kaupunginNimi = nimi;
        this.postinumero = postinumero;
    }

    public String getPostinumero() {
        return this.postinumero;
    }
}
