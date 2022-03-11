package com.hackerrank.selenium.server;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;

import static java.nio.file.Paths.get;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;

public class SecureApiServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Cache-Control", "private, no-cache");
        response.setHeader("Pragma", "no-cache");
        response.setCharacterEncoding("UTF-8");

        //random unique number
        String randomNumber = String.valueOf(Math.random());

        // This is the handler for the administrator's home page.
        Files.write(get("random.txt", ""), randomNumber.getBytes(), TRUNCATE_EXISTING);
        response.getWriter().println("<html><body><p align=\"center\">" + randomNumber + "</p></body></html>");
    }
}
