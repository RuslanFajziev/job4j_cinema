package ru.job4j.cinema.store;

import ru.job4j.cinema.model.TicketCinema;

import java.util.List;

public class DbStoreTest {
    public static void main(String[] args) {
        List<TicketCinema> lst = DbStore.instOf().listTickets(1);
        System.out.print(lst.isEmpty());
        lst.forEach(x -> System.out.println(x));
    }
}