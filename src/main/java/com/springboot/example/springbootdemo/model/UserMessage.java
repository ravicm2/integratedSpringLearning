package com.springboot.example.springbootdemo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "UserMessage")
public class UserMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    @NotNull
    private String fullName;
    @Email
    @NotNull
    private String email;
    @Valid
    @NotNull
    private String phoneNo;
    @NotNull
    private String message;
}
