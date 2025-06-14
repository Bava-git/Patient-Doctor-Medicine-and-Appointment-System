package com.miniprojecttwo.service;

import com.miniprojecttwo.entity.Patient;
import com.miniprojecttwo.repository.PatientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class PatientService {

    private PatientRepository patientRepository;

    public Patient findBypatientId(String patientId) {
        return patientRepository.findBypatientId(patientId);
    }

    public Patient findByPatientEmail(String PatientEmail) {
        return patientRepository.findByPatientEmail(PatientEmail);
    }

    public Patient findBypatientContact(String patientContact){
        return patientRepository.findBypatientContact(patientContact);
    }

    public Patient createPatient(Patient patient) {
        return patientRepository.save(patient);
    }

    public Patient updatePatient(String patientId, Patient updatepatient) {
        Patient pat = patientRepository.findBypatientId(patientId);

        if (pat != null) {
            pat.setPatientName(updatepatient.getPatientName());
            pat.setPatientAge(updatepatient.getPatientAge());
            pat.setPatientPlace(updatepatient.getPatientPlace());
            pat.setPatientEmail(updatepatient.getPatientEmail());
            pat.setPatientContact(updatepatient.getPatientContact());
            pat.setPatientIssue(updatepatient.getPatientIssue());
            return patientRepository.save(pat);
        }

        return null;
    }

    public List<Patient> listPatient() {
        return patientRepository.findAll();
    }

    @Transactional
    public Integer deletePatient(String patientId) {
        return patientRepository.deleteBypatientId(patientId);
    }

}
