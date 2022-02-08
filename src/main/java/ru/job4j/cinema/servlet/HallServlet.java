package ru.job4j.cinema.servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.job4j.cinema.model.TicketCinema;
import ru.job4j.cinema.store.DbStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class HallServlet extends HttpServlet {
    private static final Gson GSON = new GsonBuilder().create();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int sessionId = Integer.valueOf(req.getParameter("session_id"));
        List<TicketCinema> lst = DbStore.instOf().listTickets(sessionId);
        resp.setContentType("application/json; charset=utf-8");
        OutputStream output = resp.getOutputStream();
        String json = GSON.toJson(lst);
        output.write(json.getBytes(StandardCharsets.UTF_8));
        output.flush();
        output.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession sc = req.getSession();
        int sessionId = Integer.valueOf(sc.getAttribute("session_id").toString());
        int row = Integer.valueOf(sc.getAttribute("row").toString());
        int cell = Integer.valueOf(sc.getAttribute("cell").toString());

        var username = req.getParameter("username");
        var email = req.getParameter("email");
        var phone = req.getParameter("phone");

        var db = DbStore.instOf();
        var accountLeft = db.findAccountByEmail(email);
        var accountRight = db.findAccountByPhone(phone);

        var place = row + "." + cell;
        var urlEndpay = "/endpay.jsp?sessionId=" + sessionId + "&"
                + "row=" + row + "&" + "cell=" + cell;
        var urlPayment = "payment.jsp?place=" + place + "&" + "session_id=" + sessionId;

        if (accountLeft != null && accountRight == null) {
            req.setAttribute("error", "Уже есть зарегистрированный пользователь с email: "
                    + email + ", но с другим номером телефона.");
            req.getRequestDispatcher(urlPayment).forward(req, resp);
            return;
        } 
        
        if (accountLeft == null && accountRight != null) {
            req.setAttribute("error", "Уже есть зарегистрированный пользователь с phone: "
                    + phone + ", но с другой эл.почтой.");
            req.getRequestDispatcher(urlPayment).forward(req, resp);
            return;
        } 
        
        if (accountLeft == null && accountRight == null) {
            int accountId = db.regAccount(username, email, phone);
            if (accountId < 0) {
                req.setAttribute("error", "Ошибка регистрации аккаунта.");
                req.getRequestDispatcher(urlPayment).forward(req, resp);
                return;
            }
            var rsl = regTicket(db, sessionId, row, cell, accountId);
            if (rsl) {
                resp.sendRedirect(req.getContextPath() + urlEndpay);
                return;
            }
            req.setAttribute("error", "К сожалению выбранный вами билет был продан.");
            req.getRequestDispatcher("index.jsp").forward(req, resp);
            return;
        } 
        
        if (accountLeft != null && accountRight != null
                && !(accountLeft.getEmail().equals(accountRight.getEmail()))) {
            req.setAttribute("error", "Уже есть зарегистрированные пользователи с phone: "
                    + phone + ", но с другой эл.почтой. С email: " + email + ", но с другим номером телефона.");
            req.getRequestDispatcher(urlPayment).forward(req, resp);
            return;
        } else {
            int accountId = accountLeft.getId();
            var rsl = regTicket(db, sessionId, row, cell, accountId);
            if (rsl) {
                resp.sendRedirect(req.getContextPath() + urlEndpay);
                return;
            }
            req.setAttribute("error", "К сожалению выбранный вами билет был продан.");
            req.getRequestDispatcher("index.jsp").forward(req, resp);
        }
    }

    private Boolean regTicket(DbStore db, int sessionId, int row, int cell, int accountId) {
        var rsl = db.findTicket(sessionId, row, cell);
        if (rsl) {
            return false;
        }
        return db.regTicket(sessionId, row, cell, accountId) > -1;
    }
}