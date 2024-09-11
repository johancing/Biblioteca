package com.johancastro.cun.edu.persistence;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "loans", schema = "biblioteca")
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long loanId;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "user_id", nullable = false)
    private User userId;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "book_id", nullable = false)
    private Book bookId;
    @Column(name = "loan_date", nullable = false)
    private Date loanDate;
    @Column(name = "estimated_date_return", nullable = false)
    private Date estimatedDateReturn;
    @Column(name = "return_date")
    private Date returnDate;

}
