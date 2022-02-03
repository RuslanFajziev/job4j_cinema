package ru.job4j.cinema.model;

import java.util.Objects;

public class TicketCinema {
    private int id;
    private int sessionId;
    private int row;
    private int cell;
    private int accountId;

    public TicketCinema(int id, int sessionId, int row, int cell, int accountId) {
        this.id = id;
        this.sessionId = sessionId;
        this.row = row;
        this.cell = cell;
        this.accountId = accountId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCell() {
        return cell;
    }

    public void setCell(int cell) {
        this.cell = cell;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    @Override
    public String toString() {
        return "Ticket{"
                + "id=" + id
                + ", sessionId=" + sessionId
                + ", row=" + row
                + ", cell=" + cell
                + ", accountId=" + accountId
                + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TicketCinema ticketCinema = (TicketCinema) o;
        return id == ticketCinema.id && sessionId == ticketCinema.sessionId
                && row == ticketCinema.row && cell == ticketCinema.cell && accountId == ticketCinema.accountId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sessionId, row, cell, accountId);
    }
}