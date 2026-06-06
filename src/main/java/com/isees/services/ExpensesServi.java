package com.isees.services;

import java.io.File;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.isees.dtos.ExpensesDto;
import com.isees.entities.Expenses;
import com.isees.repos.ExpensesRepo;

@Service
public class ExpensesServi {

    @Autowired
    ExpensesRepo r;

    private final String uploadDir = System.getProperty("user.dir") + "/uploads/";
    public Expenses save(ExpensesDto dto, MultipartFile file) throws Exception {

        String uploadDir = System.getProperty("user.dir") + File.separator + "uploads" + File.separator;
        String billUrl = null;

        // if user typed bill number / url
        if(dto.getBillurl() != null && !dto.getBillurl().trim().isEmpty()){
            billUrl = dto.getBillurl().trim();
        }

        // if file uploaded, override billUrl
        if (file != null && !file.isEmpty()) {

            File folder = new File(uploadDir);
            if (!folder.exists()) folder.mkdirs();

            String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            String filePath = Paths.get(uploadDir, filename).toString();
            file.transferTo(new File(filePath));

            billUrl = "http://localhost:8080/uploads/" + filename;
        }

        Expenses e = new Expenses();
        e.setTitle(dto.getTitle());
        e.setCategory(dto.getCategory());
        e.setAmount(dto.getAmount());
        e.setPaidby(dto.getPaidby());

        if(dto.getDate() != null && !dto.getDate().isEmpty()){
            e.setDate(LocalDateTime.parse(dto.getDate().replace(" ", "T")));
        } else {
            e.setDate(LocalDateTime.now());
        }

        e.setDescription(dto.getDescription());
        e.setBillurl(billUrl);

        return r.save(e);
    }

    public List<Expenses> getAll() {
        return r.findAll();
    }

    public Expenses getById(int id) {
        return r.findById((long) id)
                .orElseThrow(() -> new RuntimeException("Expense not found"));
    }

    public void update(int id, ExpensesDto dto, MultipartFile file) throws Exception {

        Expenses expense = r.findById((long) id)
                .orElseThrow(() -> new Exception("Expense not found"));

        expense.setTitle(dto.getTitle());
        expense.setCategory(dto.getCategory());
        expense.setAmount(dto.getAmount());
        expense.setPaidby(dto.getPaidby());

        if(dto.getDate() != null && !dto.getDate().isEmpty()){
            expense.setDate(LocalDateTime.parse(dto.getDate().replace(" ", "T")));
        }else{
            expense.setDate(LocalDateTime.now());
        }

        expense.setDescription(dto.getDescription());

        if (file != null && !file.isEmpty()) {

            File folder = new File(uploadDir);
            if (!folder.exists()) folder.mkdirs();

            String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            String filePath = Paths.get(uploadDir, filename).toString();
            file.transferTo(new File(filePath));

            expense.setBillurl("http://localhost:8080/uploads/" + filename);
        }

        r.save(expense);
    }

    public void delete(int id) {
        if (!r.existsById((long) id)) {
            throw new RuntimeException("Expense not found");
        }
        r.deleteById((long) id);
    }

    public Double getMonthlyExpense() {

        LocalDateTime start = LocalDateTime.now()
                .withDayOfMonth(1)
                .withHour(0).withMinute(0).withSecond(0);

        LocalDateTime end = LocalDateTime.now();

        Double total = r.getMonthlyExpense(start, end);
        return total != null ? total : 0.0;
    }

    public Double getTotalExpense() {
        Double total = r.getTotalExpense();
        return total != null ? total : 0.0;
    }
}