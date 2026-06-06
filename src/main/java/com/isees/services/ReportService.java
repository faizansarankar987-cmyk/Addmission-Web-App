package com.isees.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.isees.entities.Application;
import com.isees.entities.Expenses;
import com.isees.repos.ApplicationRepo;
import com.isees.repos.ExpensesRepo;


@Service
public class ReportService {

    @Autowired
    private ApplicationRepo applicationRepo;

    @Autowired
    private ExpensesRepo expensesRepo;

    public List<Application> getAdmissionReport(LocalDateTime from, LocalDateTime to) {
        return applicationRepo.findByDateBetween(from, to);
    }

    public List<Application> getFeeReport(LocalDateTime from, LocalDateTime to) {
        return applicationRepo.findByPaymentDateBetween(from, to);
    }

    public List<Expenses> getExpenseReport(LocalDateTime from, LocalDateTime to) {
        return expensesRepo.findByDateBetween(from, to);
    }
}