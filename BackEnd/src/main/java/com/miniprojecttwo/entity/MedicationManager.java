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
@Table(name = "medicationmanagerdb")
public class MedicationManager {

    @Id
    @Column(name = "medicine_id")
    @JsonProperty("medicine_id")
    private String medicineId;

    @Column(name = "medicine_name")
    @JsonProperty("medicine_name")
    @NotBlank
    @Size(min = 3, max = 30)
    private String medicineName;

    @Column(name = "medicine_days")
    @JsonProperty("medicine_days")
    private int medicineDays;

    @Column(name = "medicine_Morning")
    @JsonProperty("medicine_Morning")
    private boolean medicineMorning;

    @Column(name = "medicine_Afternoon")
    @JsonProperty("medicine_Afternoon")
    private boolean medicineAfternoon;

    @Column(name = "medicine_night")
    @JsonProperty("medicine_night")
    private boolean medicineNight;

    @Column(name = "medicine_afterfood")
    @JsonProperty("medicine_afterfood")
    private boolean medicineAfterFood;

    @Column(name = "doctor_id")
    @JsonProperty("doctor_id")
    private String doctorId;

    @Column(name = "doctor_name")
    @JsonProperty("doctor_name")
    @NotBlank
    @Size(min = 3, max = 30)
    private String doctorName;

    @Column(name = "patient_id")
    @JsonProperty("patient_id")
    private String patientId;

    @Column(name = "patient_name")
    @JsonProperty("patient_name")
    @NotBlank
    @Size(min = 3, max = 30)
    private String patientName;

    @Column(name = "appointment_datetime")
    @JsonProperty("appointment_datetime")
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm a")
    @FutureOrPresent
    @NotNull
    private LocalDateTime appointmentDateTime;

    @Column(name = "patientappointment_id")
    @JsonProperty("patientappointment_id")
    private String patientappointmentId;
}