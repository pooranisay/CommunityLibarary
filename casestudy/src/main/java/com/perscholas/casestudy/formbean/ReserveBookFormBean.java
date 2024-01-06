package com.perscholas.casestudy.formbean;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter public class ReserveBookFormBean {
    private Integer id;
    private Integer userId;
    private Integer bookId;
    private LocalDate checkoutDate;
    private LocalDate dueDate;
    private String status;

    // Constructors, getters, and setters

    public ReserveBookFormBean() {
        // Default constructor
    }





}
