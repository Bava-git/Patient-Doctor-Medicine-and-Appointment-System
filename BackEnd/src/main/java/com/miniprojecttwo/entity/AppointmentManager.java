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

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "appointmentdb")
public class AppointmentManager {

    @Id
    @Column(name = "appointment_id")
    @JsonProperty("appointment_id")
    private String appointmentId;

    @Column(name = "appointment_date")
    @JsonProperty("appointment_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @FutureOrPresent
    @NotNull
    private LocalDate appointmentDate;

    @Column(name = "appointment_start_time")
    @JsonProperty("appointment_start_time")
    @DateTimeFormat(pattern = "hh:mm a")
    @NotNull
    private LocalTime appointmentStartTime;

    @Column(name = "appointment_end_time")
    @JsonProperty("appointment_end_time")
    @DateTimeFormat(pattern = "hh:mm a")
    @NotNull
    private LocalTime appointmentEndTime;

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

}
