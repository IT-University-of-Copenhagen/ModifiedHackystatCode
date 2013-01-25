/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.naming.Context;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import util.RoutingManager;

/**
 *
 * @author aufeef
 */
public class ControllerServlet extends HttpServlet {
  

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        try {
            String contextPath = request.getContextPath();
            String queryString = request.getQueryString();
            String servletPath = request.getRequestURL().toString();
            String hackystatAttributes = "";
            String targetUri = "";

            if( contextPath != null && contextPath.length() > 0 && servletPath != null && servletPath.length() > contextPath.length() )
            {
                int contextIndex = servletPath.indexOf(contextPath);
                hackystatAttributes = servletPath.substring( contextIndex+contextPath.length(),
                        servletPath.length());
                hackystatAttributes = hackystatAttributes.trim();
            }

            if( hackystatAttributes != null && hackystatAttributes.length() > 0 )
            {

                String tempAttributes = hackystatAttributes;
                tempAttributes = tempAttributes.replaceAll("/", "").trim();

                if( tempAttributes.length() > 0 )
                {
                    //String serverUri = "http://dasha.ics.hawaii.edu:9876";
                    String serverUri = RoutingManager.getSensorBaseUri(getServletContext());

                    if( queryString != null && queryString.trim().length() > 0 )
                    {
                        targetUri = serverUri + hackystatAttributes + "?" + queryString;
                    }
                    else
                    {
                        targetUri = serverUri + hackystatAttributes;
                    }

                    response.sendRedirect(targetUri);
                }
                //response.sendRedirect("http://dasha.ics.hawaii.edu:9876/sensorbase/sensordatatypes");
            }

            //RequestDispatcher rd = request.getRequestDispatcher("http://dasha.ics.hawaii.edu:9876/sensorbase/sensordatatypes");
            //rd.forward(request, response);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        finally {
//            out.close();
        }
    } 

    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "This is controller servlet for the Hackystat services";
    }// </editor-fold>

}
