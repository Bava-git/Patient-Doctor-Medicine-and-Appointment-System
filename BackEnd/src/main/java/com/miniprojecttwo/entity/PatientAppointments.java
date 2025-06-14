package com.miniprojecttwo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "patientappointmentdb")
public class PatientAppointments {

    @Id
    @Column(name = "patientappointment_id")
    @JsonProperty("patientappointment_id")
    private String patientappointmentId;

    @Column(name = "appointment_datetime")
    @JsonProperty("appointment_datetime")
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm a")
    @FutureOrPresent
    @NotNull
    private LocalDateTime appointmentDateTime;

    @Column(name = "doctor_id")
    @JsonProperty("doctor_id")
    private String doctorId;

    @Column(name = "doctor_name")
    @JsonProperty("doctor_name")
    @NotBlank
    @Size(min = 3, max = 30)
    private String doctorName;

    @Column(name = "doctor_education")
    @JsonProperty("doctor_education")
    @NotBlank
    private String doctorEducation;

    @Column(name = "doctor_specializedfield")
    @JsonProperty("doctor_specializedfield")
    @NotBlank
    private String doctorSpecializedField;

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

    @Column(name = "patient_issue")
    @JsonProperty("patient_issue")
    @NotBlank
    private String patientIssue;

}