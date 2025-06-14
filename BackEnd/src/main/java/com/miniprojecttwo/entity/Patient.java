package com.miniprojecttwo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "patientdb")
public class Patient {

    @Id
    @Column(name = "patient_id")
    @JsonProperty("patient_id")
    private String patientId;

    @Column(name = "patient_name")
    @JsonProperty("patient_name")
    @NotBlank
    @Size(min = 3, max = 30)
    private String patientName;

    @Column(name = "patient_age")
    @JsonProperty("patient_age")
    private int patientAge;

    @Column(name = "patient_place")
    @JsonProperty("patient_place")
    @NotBlank
    private String patientPlace;

    @Column(name = "patient_email")
    @JsonProperty("patient_email")
    @NotBlank
    @Email
    private String patientEmail;

    @Column(name = "patient_contact")
    @JsonProperty("patient_contact")
    @NotBlank
    private String patientContact;

    @Column(name = "patient_issue")
    @JsonProperty("patient_issue")
    @NotBlank
    private String patientIssue;

}