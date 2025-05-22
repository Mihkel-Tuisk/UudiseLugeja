/**
Projekti Autorid:        Mihkel Tuisk, Gregor Saar

Programmi Kirjeldus:     Saada kiirelt suvalisi uudiseid teatud uudisteportaalidest. Konsoolist pead sisestama millisest
                         uudiseportaalist saada uudiseid.

Etapid:                  1. Loo Skraaper klass
                         2. Loo Utils klass
                         3. Loo Delfi ja ERR klass
                         4. Loo Uudis klass
                         6. Pane kõik kokku main klassis ja testi.
                         7. Loo graafika jaoks klass ning eventide jaoks eraldi klassid
                         8. Testi ja muuda paigutust, kuni näeb välja ja töötab õigesti

Liikmete panus:          Mihkel Tuisk 50%
                         Gregor Saar 50%

Ligikaudne Ajakulu:      Esimene etapp: 7h 30min
                         Teine etapp: 5h

Tegemise Mured:          Igal uudiseportaalil on erinev viis kuidas kuvatakse uudiseid html-is
                         Javafx üldiselt ajab segadusse sellega, et mis elemente on hea kasutada

Hinnang Töö Lõppemusele: 9.2/10. String lihtsustaHtmlSildid(String tekst) vajab veel arendamist, vahepeal satub HTMLi
                         inimloetavasse teksti sisse.
                         Vahepeal pealkirja ja lingi tekst võib mitte mahtuda korralikult

Kas Programm Töötab?     Main meetodis on võimal testida kõiki variante. Alguses lõin Skraaperi, et testida kuidas
                         saada HTML sisu lehekülgedelt, siis lõin Utils classi, et lihtsusdada elu. Pealse seda
                         testisin kuidas täpsemalt HTMList välja võtta pealkiri ja sisu (Delfi ja ERR class).

                         UudiseLugeja meetodis on graafiline kasutajaliides.
 */

/**
 * Faili Autor: Mihkel Tuisk
*/

import java.util.List;
import java.util.Scanner;

public class main {
    /**
     * Kuvab suvalise ERR uudise.
     * Kui ERR-lehelt ei leita ühtegi uudist, kuvatakse veateade.
     */
    private static void kuvaSuvalineERRUudis() {
        ERR ERR = new ERR();
        List<Uudis> uudised = ERR.leiaKõikUudised();

        if (!uudised.isEmpty()) {
            Uudis uudis = uudised.get(Utils.saaSuvalineArv(uudised.size()));
            ERR.päriUudiseSisu(uudis);
            System.out.println(uudis);
            return;
        }

        System.out.println("Uudiseid ei leitud.");
    }

    /**
     * Kuvab suvalise Delfi uudise.
     * Kui Delfi lehelt ei leita ühtegi uudist, kuvatakse veateade.
     */
    private static void kuvaSuvalineDelfiUudis() {
        Delfi delfi = new Delfi();
        List<Uudis> uudised = delfi.leiaKõikUudised();

        if (!uudised.isEmpty()) {
            Uudis uudis = uudised.get(Utils.saaSuvalineArv(uudised.size()));
            delfi.päriUudiseSisu(uudis);
            System.out.println(uudis);
            return;
        }

        System.out.println("Uudiseid ei leitud.");
    }

    /**
     * Peameetod.
     *
     * @throws InterruptedException Kui programm katkestatakse.
     */
    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Siin on uudiseportaalide numbrid, palun vali üks neist, mille suvalist uudist saada:");
        System.out.println("ERR:   1");
        System.out.println("Delfi: 2");

        System.out.print("Palun sisesta uudiseportaali number siia: ");

        int number = scanner.nextInt();

        switch (number) {
            case 1:
                kuvaSuvalineERRUudis();
                break;
            case 2:
                kuvaSuvalineDelfiUudis();
                break;
            default:
                System.out.println("Vale number. Palun vali 1 (ERR) või 2 (Delfi).");
        }

        scanner.close();
    }
}