package com.isees.services;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Function;
import com.isees.dtos.ApplicationDto;
import com.isees.dtos.CoursePopularityDto;
import com.isees.entities.Application;
import com.isees.entities.Courses;
import com.isees.repos.ApplicationRepo;
import com.isees.repos.CoursesRepo;

import freemarker.template.Configuration;
import freemarker.template.Template;
import jakarta.mail.internet.MimeMessage;

@Service
public class ApplicationService {

    @Autowired
    private ApplicationRepo applicationRepo;

    @Autowired
    private CoursesRepo coursesRepo;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private Configuration freemarkerConfig;

    @Value("${app.upload.dir:C:/uploads}")
    private String uploadDir;

    // =====================================================
    // SAVE APPLICATION
    // =====================================================
    @Transactional
   

    public Long saveApplication(ApplicationDto dto,
            MultipartFile adharFile,
            MultipartFile photoFile,
            MultipartFile signatureFile,
            MultipartFile marksheetFile) throws Exception {

        String uploadDir = System.getProperty("user.dir") + "/uploads/";

        // create folder if not exists
        File dir = new File(uploadDir);
        if (!dir.exists()) dir.mkdirs();

        // 🔹 common function to save file
        Function<MultipartFile, String> saveFile = file -> {
            try {
                if (file == null || file.isEmpty()) return null;

                String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                Path path = Paths.get(uploadDir + fileName);
                Files.write(path, file.getBytes());

                return "uploads/" + fileName;
            } catch (Exception e) {
                throw new RuntimeException("File upload failed: " + file.getOriginalFilename());
            }
        };

        // ===== MAP DTO TO ENTITY =====
        Application app = new Application();
        app.setStudentname(dto.getStudentname());
        app.setCoursename(dto.getCoursename());
        app.setDate(dto.getDate());
        app.setFathername(dto.getFathername());
        app.setMothername(dto.getMothername());
        app.setMobilenumber(dto.getMobilenumber());
        app.setDateofbirth(dto.getDateofbirth());
        app.setAddress(dto.getAddress());
        app.setEmailid(dto.getEmailid());
        app.setGender(dto.getGender());
        app.setState(dto.getState());
        app.setDistrict(dto.getDistrict());
        app.setTaluka(dto.getTaluka());
        app.setTotalFees(dto.getTotalFees());
        app.setAmountPaid(dto.getAmountPaid());
        app.setRemainingFees(dto.getRemainingFees());

        // ⭐⭐ SAVE FILES USING YOUR FIELD NAMES ⭐⭐
        app.setAdharcard_img(saveFile.apply(adharFile));
        app.setPhoto_img(saveFile.apply(photoFile));
        app.setSignature_img(saveFile.apply(signatureFile));
        app.setMarkshitphoto(saveFile.apply(marksheetFile));

        // save in DB
        Application saved = applicationRepo.save(app);

        return saved.getId();
    }


    public String updatePayment(String email, String course, double amount){
    	Application app = applicationRepo.findByEmailidAndCoursename(email, course);
    	if(app == null) return "Application not found";

    		app.setAmountPaid(app.getAmountPaid() + amount);
    		app.setRemainingFees(app.getTotalFees() - app.getAmountPaid());
    		applicationRepo.save(app);

    		return "Payment updated successfully";
    }

    // =====================================================
    // EMAIL
    // =====================================================
    private void sendConfirmationEmail(Application app, Courses course) throws Exception {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        Map<String, Object> model = new HashMap<>();
        model.put("studentname", app.getStudentname());
        model.put("coursename", course.getCoursename());
        model.put("year", Year.now().getValue());
        model.put("date", LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("dd MMM yyyy")));

        Template template = freemarkerConfig
                .getTemplate("email-admission-confirmation.ftl");

        String html = FreeMarkerTemplateUtils
                .processTemplateIntoString(template, model);

        helper.setTo(app.getEmailid());
        helper.setSubject("ISEES Admission Confirmation");
        helper.setText(html, true);

        mailSender.send(message);
    }

    // =====================================================
    // FETCH
    // =====================================================
    public List<Application> getAll() {
        return applicationRepo.findAll();
    }

    public Optional<Application> getById(Long id) {
        return applicationRepo.findById(id);
    }

    public Application getByEmailSingle(String email) {
        return applicationRepo.findTopByEmailidOrderByIdDesc(email).orElse(null);
    }

    public boolean isFeeComplete(String email, String courseName) {

        Application app = applicationRepo.findByEmailidAndCoursename(email, courseName);

        if (app == null) return false;

        double paid = app.getAmountPaid() == null ? 0 : app.getAmountPaid();
        double total = app.getTotalFees() == null ? 0 : app.getTotalFees();

        return paid >= total;
    }



	public Application getByEmailAndCourse(String email, String course) {
	    return applicationRepo.findByEmailidAndCoursename(email, course);
	}

//	public double getRemainingAmount(String email, String courseName) {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	public byte[] downloadCertificate(String email, String studentName, String courseName) {
//		// TODO Auto-generated method stub
//		return null;
//	}

//	public boolean isFeeComplete(String email, String courseName) {
//		// TODO Auto-generated method stub
//		return false;
//	}
	

    // ----------------- Update Payment -----------------
    @Transactional
    public String updatePayment(String email, String course, Double amount) {
        Application app = getByEmailAndCourse(email, course);
        if (app == null) return "Application not found!";

        if (app.getAmountPaid() == null) app.setAmountPaid(0.0);
        if (app.getTotalFees() == null) app.setTotalFees(0.0);

        double newPaid = app.getAmountPaid() + amount;
        if (newPaid > app.getTotalFees()) return "Paid amount exceeds total fee!";

        double remaining = Math.max(app.getTotalFees() - newPaid, 0);
        app.setAmountPaid(newPaid);
        app.setRemainingFees(remaining);

        // Update payment & enrollment status
        if (remaining == 0) {
            app.setPaymentStatus("PAID");
            app.setEnrolled(true);   // ✅ Enroll student when fully paid
        } else {
            app.setPaymentStatus("PARTIAL");
        }

        applicationRepo.save(app);
        return "Payment updated successfully!";
    }

    @Transactional
    public Application updatePaymentAfterStripe(Long appId, double paidAmount, String sessionId) {
        Application app = applicationRepo.findById(appId)
            .orElseThrow(() -> new RuntimeException("Application not found"));

        // Check if this session/payment is already recorded
        if(app.getStripeSessionIds() != null && app.getStripeSessionIds().contains(sessionId)) {
            return app; // payment already added
        }

        if(app.getAmountPaid() == null) app.setAmountPaid(0.0);

        app.setAmountPaid(app.getAmountPaid() + paidAmount);
        app.setRemainingFees(app.getTotalFees() - app.getAmountPaid());

        if(app.getAmountPaid() >= app.getTotalFees()) {
            app.setPaymentStatus("PAID");
            app.setEnrolled(true);
        } else {
            app.setPaymentStatus("PARTIAL");
        }

        // Save session ID to avoid duplicate
        if(app.getStripeSessionIds() == null) app.setStripeSessionIds(new ArrayList<>());
        app.getStripeSessionIds().add(sessionId);

        return applicationRepo.save(app);
    }


    @Transactional
    public void saveUpiScreenshot(Long applicationId, String fileName) {

        Application app = applicationRepo.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        // Save screenshot file name
        app.setUpiScreenshot(fileName);

        // Mark as waiting for admin verification
        app.setPaymentStatus("PENDING_VERIFICATION");

        applicationRepo.save(app);
    }

    @Transactional
    public void verifyUpiPayment(Long id) {

        Application app = applicationRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        // 40% admission fee
        double admissionFee = app.getTotalFees() * 0.40;

        if(app.getAmountPaid() == null)
            app.setAmountPaid(0.0);

        // Add payment
        app.setAmountPaid(app.getAmountPaid() + admissionFee);

        // Calculate remaining fee
        double remaining = app.getTotalFees() - app.getAmountPaid();
        app.setRemainingFees(remaining);

        // Update payment status
        if(remaining <= 0){
            app.setPaymentStatus("PAID");
            app.setEnrolled(true);
        } else {
            app.setPaymentStatus("PARTIAL");
        }

        applicationRepo.save(app);
    }


    public void addUpiPaymentDirect(Long appId, double amount){

        Application app = applicationRepo.findById(appId)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        double oldPaid = app.getAmountPaid() == null ? 0 : app.getAmountPaid();

        app.setAmountPaid(oldPaid + amount);
        app.setPaymentStatus("PAID_UPI");

        applicationRepo.save(app);
    }


	    // =========================
	    // GET APPLICATION BY ID
	    // =========================
	  
    public List<Application> getRecentAdmissions() {
        return applicationRepo.findTop5ByOrderByIdDesc();
    }

    public Double getPendingFees() {
        Double total = applicationRepo.getTotalPendingFees();
        return total != null ? total : 0.0;
    }
    
    public Double getMonthlyIncome(){

        LocalDateTime start = LocalDateTime.now()
                .withDayOfMonth(1)
                .withHour(0).withMinute(0).withSecond(0);

        LocalDateTime end = LocalDateTime.now();

        Double total = applicationRepo.getMonthlyIncome(start, end);

        return total != null ? total : 0.0;
    }
    
    //courses popularity
    public List<CoursePopularityDto> getCoursePopularity(){
        return applicationRepo.getCoursePopularity();
    }
    
    public double getRemainingAmount(String email, String courseName) {

        Application app = applicationRepo.findByEmailidAndCoursename(email, courseName);

        if (app == null) return 0;

        double paid = app.getAmountPaid() == null ? 0 : app.getAmountPaid();
        double total = app.getTotalFees() == null ? 0 : app.getTotalFees();

        double remaining = total - paid;

        if (remaining < 0) remaining = 0;

        return remaining;
    }
    private void applyPayment(Application app, double amount){

        if(app.getAmountPaid() == null) app.setAmountPaid(0.0);
        if(app.getTotalFees() == null) app.setTotalFees(0.0);

        double newPaid = app.getAmountPaid() + amount;
        app.setAmountPaid(newPaid);

        double remaining = app.getTotalFees() - newPaid;
        if(remaining < 0) remaining = 0;

        app.setRemainingFees(remaining);

        // ✅ VERY IMPORTANT FOR REPORT
        app.setPaymentDate(LocalDateTime.now());

        if(remaining == 0){
            app.setPaymentStatus("PAID");
            app.setEnrolled(true);
        }else{
            app.setPaymentStatus("PARTIAL");
        }
    }
    
}
