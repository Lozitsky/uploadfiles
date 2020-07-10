package com.kirilo.java.upload.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

/*@WebServlet(
        name = "upload",
        urlPatterns = {"/upload"})
@MultipartConfig(
        location="d:\\temp\\upload",
        fileSizeThreshold=1024*1024,
        maxFileSize=5*1024*1024,
        maxRequestSize=2*5*1024*1024)*/
public class FileUploadServlet30 extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        try (PrintWriter out = resp.getWriter()) {
            final Collection<Part> parts = req.getParts();
            out.write("<h2>Number of parts: " + parts.size() + "</h2>");
            for (Part part : parts) {
                printPartInfo(part, out);
                String fileName = getFileName(part);
                if (fileName != null) {
                    part.write(fileName);
                }
            }
        }
    }

    // Gets the file name from the "content-disposition" header
    private String getFileName(Part part) {
        for (String token : part.getHeader("content-disposition").split(";")) {
            if (token.trim().startsWith("filename")) {
                return token.substring(token.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }

    // Print the headers for the given Part
    private void printPartInfo(Part part, PrintWriter out) {
        final StringBuilder builder = new StringBuilder();
        builder.append("<p>Name: ").append(part.getName()).append("<br>")
                .append("ContentType: ").append(part.getContentType()).append("<br>")
                .append("Size: ").append(part.getSize()).append("<br>");
        for (String header : part.getHeaderNames()) {
            builder.append(header).append(": ").append(part.getHeader(header)).append("<br>");
        }
        builder.append("</p>");
        out.write(builder.toString());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        getServletContext().getRequestDispatcher("/FileUpload.html").forward(req, resp);
        getServletContext().getRequestDispatcher("/FileUploadMultipart.html").forward(req, resp);
    }
}
