/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import config.Config;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author italo
 */
public class UploadDataRawServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        File uploadTemp = new File(Config.UPLOAD_TEMP);
        if (!uploadTemp.exists()) {
            uploadTemp.mkdir();
        }
        File uploadDataraw = new File(Config.UPLOAD_DATARAW);
        if (!uploadDataraw.exists()) {
            uploadDataraw.mkdir();
        }

        DiskFileItemFactory factory = new DiskFileItemFactory();
        try {
            String fileUploaded = null;

            File repository = new File(Config.UPLOAD_TEMP);
            factory.setRepository(repository);
            ServletFileUpload upload = new ServletFileUpload(factory);
            List<FileItem> fileItems = upload.parseRequest(request);
            Iterator i = fileItems.iterator();
            while (i.hasNext()) {
                FileItem fi = (FileItem) i.next();
                if (fi.getSize() > 0) {
                    String fileName = fi.getName();
                    fi.write(new File(Config.UPLOAD_DATARAW + fileName));
                    fileUploaded = Config.UPLOAD_DATARAW + fileName;
                }
            }

            /**/
            BufferedReader reader = new BufferedReader(new FileReader(fileUploaded));
            String csvHeader = null;
            while (reader.ready()) {
                csvHeader = reader.readLine();
                break;
            }
            List<String> csvColumns = new ArrayList<String>();
            String[] temp = csvHeader.split(";");
            for (int j = 0; temp != null && j < temp.length; j++) {
                csvColumns.add(temp[j]);
            }
            reader.close();
            /**/
            request.setAttribute("csvColumns", csvColumns);
            request.setAttribute("dataRaw", fileUploaded);
            request.setAttribute("message", "Arquivo recebido com sucesso");

        } catch (Exception ex) {
            request.setAttribute("message", "Arquivo não foi recebido com sucesso");
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
        dispatcher.forward(request, response);
    }

}
