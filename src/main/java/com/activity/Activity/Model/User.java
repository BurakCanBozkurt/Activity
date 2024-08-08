package com.activity.Activity.Model;

import com.activity.Activity.QR.QrCodeGenerator;
import com.activity.Activity.Repository.UserRepository;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
@Entity
@Getter
@Setter
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_Id")
    private Long userId;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name="email",unique = true)
    private String email;

    @Column(name="password")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles ;

    @ManyToMany(mappedBy = "assignedStudents", fetch = FetchType.LAZY)
    private List<Activity> assignedActivities;


    public String generateQRCodeData() {
        return "Kullanici Adi: " + name + " " + surname + "\nEmail: " + email;
    }

    public String getQRCodeFileName() {
        return name.toLowerCase() + "_" + surname.toLowerCase() + "_" + this.userId  +".png";
    }

    public static void QrGenerate(User user) {

        String baseDirectory = "C:\\qR";

        String userData = user.generateQRCodeData();

        int width = 300;

        int height = 300;

        String filePath = baseDirectory + "/" + user.getQRCodeFileName();

        try {
            QrCodeGenerator QRCodeGenerator=new QrCodeGenerator();
            QRCodeGenerator.generateQRCodeImage(userData, width, height, filePath);
            System.out.println("QR kodu oluşturuldu ve kaydedildi: " + filePath);
            } catch (WriterException | IOException e) {
                e.printStackTrace();
            }
        }
    }










