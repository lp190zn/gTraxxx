/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sk.mlp.file.video;

/**
 *
 * @author Matej Pazdič
 */
public class YouTubeAgentException extends Exception {

    /**
     * Konštruktor triedy YouTubeAgentException.
     */
    public YouTubeAgentException() {
        super();
    }

    /**
     * Preťažený konštruktor triedy YouTubeAgentException.
     * @param message - správa ktorá sa posiela pri vyhodení výnimky
     */
    public YouTubeAgentException(String message) {
        super(message);
    }

    /**
     * Preťažený konštruktor triedy YouTubeAgentException.
     * @param message - správa ktorá sa posiela pri vyhodení vynímky
     * @param cause - príčina výnimky
     */
    public YouTubeAgentException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Preťažený konštruktor triedy YouTubeAgentException.
     * @param cause - príčina výnimky
     */
    public YouTubeAgentException(Throwable cause) {
        super(cause);
    }
}
