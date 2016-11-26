/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sk.mlp.logger;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Trieda ClearFileLog je Servlet, ktorý je určený na vymazanie obsahu 
 * logovacieho súboru. Táto možnosť je určená iba pre 
 * používateľov s rolou "Administrator".
 * @author Matej Pazdič
 */
public class ClearFileLog extends HttpServlet {

    /**
     * Metóda processRequest je obslužná metóda, ktorá sa volá po vyvolaní daného servletu na strane používateľa. 
     * Pričom sa servlet vykonáva na strane servera.
     * @param request - objekt požiadavky, ktorý sa prenáša zo strany klienta na stranu servera
     * @param response - objekt odozvy servera, ktorý sa prenáša zo strany servera na stranu klienta
     * @throws ServletException
     * @throws IOException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        FileLogger.getInstance().resetLogFile();
        
        request.getRequestDispatcher("About.jsp").forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Metóda doGet je obslužná metóda, ktorá sa volá po vyvolaní daného servletu na strane používateľa. 
     * Pričom sa servlet vykonáva na strane servera.
     * @param request - objekt požiadavky, ktorý sa prenáša zo strany klienta na stranu servera
     * @param response - objekt odozvy servera, ktorý sa prenáša zo strany servera na stranu klienta
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     *Metóda doPost je obslužná metóda, ktorá sa volá po vyvolaní daného servletu na strane používateľa. 
     * Pričom sa servlet vykonáva na strane servera.
     * @param request - objekt požiadavky, ktorý sa prenáša zo strany klienta na stranu servera
     * @param response - objekt odozvy servera, ktorý sa prenáša zo strany servera na stranu klienta
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Metóda getServletInfo je urćená na zistenie popisu činnosti daného servletu.
     *
     * @return Návratová hodnota je je reťazec znakov predstavujúci popis činnosti servletu.
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
