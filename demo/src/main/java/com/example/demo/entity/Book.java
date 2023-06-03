package com.example.demo.entity;

import com.example.demo.Validator.annotation.ValidCategoryId;
import com.example.demo.Validator.annotation.ValidUserId;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import lombok.Data;

@Data
@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "title", length = 50)
    @NotNull(message = "Title must not be null")
    @Size(max = 50, min = 1, message = "Title must be less than 50 character")
    private String title;

    @Column(name = "author", length = 50)
    @Size(max = 50, message = "Author must be less than 50 characters")
    private String author;

    @Column(name = "price")
    @NotNull(message = "Price is required")
    @Positive(message = "Price must be greater than 0")
    private double price;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    @ValidCategoryId
    private Category category;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ValidUserId
    private User user;
}