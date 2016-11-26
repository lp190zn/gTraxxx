package sk.mlp.filter;


import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sk.mlp.logger.FileLogger;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Trieda SessionChceck je pomocná trieda, ktorá implmentuje rozhranie Filter. 
 * Je využitá na filtrovanie prístupov na stránky a servlety, ktoré sú 
 * uložené v priečinku "Logged". Filtrovanie sa vykonáva na základe faktu, či 
 * je používateľ prihlásený alebo nie je. Ak nioeje je automaticky presmerovaný
 * na stránku s prihlasením.
 * @author Matej Pazdič
 */
public class SessionCheck implements Filter {

    /**
     * Metóda init je vykonaná pred spustením filtra.
     * @param filterConfig - konfigurácia filtra
     * @throws ServletException
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        System.out.println("FILTER: Filter started!!!");
    }

    /**
     * Metóda doFilter slúži na samotné vykonanie funkcie filtra, čiže na filtrovanie prístupu len pre prihlásených používateľov.
     * @param arg0 - objekt požiadavky, ktorý sa prenáša zo strany klienta na stranu servera
     * @param arg1 - objekt odozvy servera, ktorý sa prenáša zo strany servera na stranu klienta
     * @param arg2 - objekt reprezentujúci naviazané dalśie filtre
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) arg0;
        HttpServletResponse response = (HttpServletResponse) arg1;
        
        if(request.getSession().getAttribute("username") == null)
        {
            FileLogger.getInstance().createNewLog("Warning: Somebody unregistered were attempting to go to MainPage !!!");
            response.sendRedirect("../LoginPage.jsp");
            return;
        }
        arg2.doFilter(request, response);
    }

    /**
     * Metóda volaná po ukončení filtrácie.
     */
    @Override
    public void destroy() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
