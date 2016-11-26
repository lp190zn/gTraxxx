/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sk.mlp.file.image;

/**
 * Trieda ThumbnailException je využitá ako pomocná trieda, ktorá 
 * predstavuje výnimku pri vytváraní obrázkových ukážok. 
 * Je vyhadzovaná pri broblémoch s vytvorením súboru, s načítaním 
 * vystupného obrázkového súboru atď.
 * @author Matej Pazdič
 */
public class ThumbnailException extends Exception {

    /**
     * Metóda obsluhy vyhodenia vynímky.
     */
    public ThumbnailException() {
        super();
    }

    /**
     * Metóda obsluhy vyhodenia vynímky.
     * @param message - popis vynímky
     */
    public ThumbnailException(String message) {
        super(message);
    }

    /**
     * Metóda obsluhy vyhodenia vynímky.
     * @param message - poipis vynímky
     * @param cause - dôvod vyhodenia vynímky
     */
    public ThumbnailException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Metóda obsluhy vyhodenia vynímky.
     * @param cause - dôvod vyhodenia vynímky
     */
    public ThumbnailException(Throwable cause) {
        super(cause);
    }
}
