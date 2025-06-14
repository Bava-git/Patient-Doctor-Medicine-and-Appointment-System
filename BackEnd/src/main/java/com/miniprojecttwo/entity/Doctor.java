package com.miniprojecttwo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "doctordb")
public class Doctor {

    @Id
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