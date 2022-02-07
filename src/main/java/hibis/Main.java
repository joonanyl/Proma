package hibis;

import hibis.Postinumero;
import hibis.SesFac;

public class Main {

    public static void main(String[] args) {
        SesFac sessari = new SesFac();
        Postinumero postinumero = new Postinumero("asddddddddddddddddddd", "11111");

        sessari.createPostinumero(postinumero);
        sessari.Finalize();
    }
}
