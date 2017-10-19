/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import algorithms.SMDKAnonymity;
import config.Config;
import java.io.File;
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
 * @author leoomoreira
 */
public class UploadHierarchyFilesServlet extends HttpServlet {

    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String fileDataRaw = null;

        DiskFileItemFactory factory = new DiskFileItemFactory();
        try {
            List<String> attributes = new ArrayList<String>();
            List<String> hierarchyFiles = new ArrayList<String>();

            File repository = new File(Config.UPLOAD_TEMP);
            factory.setRepository(repository);
            ServletFileUpload upload = new ServletFileUpload(factory);

            List<FileItem> fileItems = upload.parseRequest(request);
            Iterator i = fileItems.iterator();
            while (i.hasNext()) {
                FileItem item = (FileItem) i.next();
                if (item.isFormField()) {
                    if (item.getFieldName().equals("dataRaw")) {
                        fileDataRaw = item.getString();
                    } else {
                        if (item.getFieldName().startsWith("chk_")) {
                            attributes.add(item.getString());
                        }
                    }
                } else {
                    if (item.getSize() > 0) {
                        String fileName = item.getName();
                        System.out.println(fileName);
                        item.write(new File(Config.UPLOAD_DATARAW + fileName));
                        hierarchyFiles.add(Config.UPLOAD_DATARAW + fileName);
                    }
                }
            }

            System.out.println("Data Raw: " + fileDataRaw);
            for (int k = 0; k < attributes.size(); k++) {
                System.out.println(attributes.get(k));
            }
            System.out.println("---");
            for (int k = 0; k < hierarchyFiles.size(); k++) {
                System.out.println(hierarchyFiles.get(k));
            }
            SMDKAnonymity kanonymity = new SMDKAnonymity();
            boolean result = kanonymity.execute(attributes, hierarchyFiles, fileDataRaw);
            if (result) {
                System.out.println("aeeeeee!!!");
                request.setAttribute("message", "Arquivo foi processado com sucesso");
            } else {
                request.setAttribute("message", "Arquivo não foi processado com sucesso");
            }
        } catch (Exception ex) {
            request.setAttribute("message", "Arquivo não foi processado com sucesso");
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
        dispatcher.forward(request, response);

    }

}
