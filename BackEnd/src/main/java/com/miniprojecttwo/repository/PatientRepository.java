package com.miniprojecttwo.repository;

import com.miniprojecttwo.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    Patient findBypatientId(String patientId);

    Integer deleteBypatientId(String patientId);

    Patient findByPatientEmail(String patientEmail);

    Patient findBypatientContact(String patientContact);

}
