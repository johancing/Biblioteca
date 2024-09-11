package com.johancastro.cun.edu.persistence;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "user", schema = "biblioteca")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;
    @Column(name = "document_type", nullable = false)
    private String documentType;
    @Column(name = "document_number", nullable = false)
    private String documentNumber;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(name = "birth_date", nullable = false)
    private Date birthdate;
    @Column(name = "address", nullable = false)
    private String address;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "email")
    private String email;

}
