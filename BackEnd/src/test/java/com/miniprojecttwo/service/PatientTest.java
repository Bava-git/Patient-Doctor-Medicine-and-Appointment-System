package com.miniprojecttwo.service;

import com.miniprojecttwo.entity.Patient;
import com.miniprojecttwo.repository.PatientRepository;
import com.miniprojecttwo.service.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PatientTest {

    @Mock
    private PatientRepository patientRepository;
    @InjectMocks
    private PatientService patientService;


    private Patient patient1;
    private Patient patient2;

    @BeforeEach
    void setup() {
        patient1 = new Patient("P1", "Patient1", 30, "Pattamadai", "patient1@gmail.com", "9988776655", "Fever");
        patient2 = new Patient("P2", "Patient2", 25, "Tirunelveli", "patient2@gmail.com", "9988775544", "Cough");
    }

    @Test
    void getAllPatients() {
        when(patientRepository.findAll()).thenReturn(Arrays.asList(patient1, patient2));
        List<Patient> patientList = patientService.listPatient();

        // validate
        assertEquals(2, patientList.size());
        verify(patientRepository, times(1)).findAll();
    }

    @Test
    void savePatient() {
        when(patientRepository.save(any(Patient.class))).thenReturn(patient1);
        Patient patient = patientService.createPatient(patient1);

        // validate
        assertNotNull(patient);
        assertEquals("Patient1", patient.getPatientName());
        verify(patientRepository, times(1)).save(patient1);
    }

    @Test
    void getPatientById() {
        when(patientRepository.findBypatientId(patient2.getPatientId())).thenReturn(patient2);
        Patient patient = patientService.findBypatientId(patient2.getPatientId());

        assertEquals("Patient2", patient.getPatientName());
        verify(patientRepository, times(1)).findBypatientId(patient2.getPatientId());
    }

    @Test
    void getPatientByEmailId() {
        when(patientRepository.findByPatientEmail(patient2.getPatientEmail())).thenReturn(patient2);
        Patient patient = patientService.findByPatientEmail(patient2.getPatientEmail());

        assertEquals("patient2@gmail.com", patient.getPatientEmail());
        verify(patientRepository, times(1)).findByPatientEmail(patient2.getPatientEmail());
    }

    @Test
    void getPatientByContact() {
        when(patientRepository.findBypatientContact(patient2.getPatientContact())).thenReturn(patient2);
        Patient patient = patientService.findBypatientContact(patient2.getPatientContact());

        assertEquals("9988775544", patient.getPatientContact());
        verify(patientRepository, times(1)).findBypatientContact(patient2.getPatientContact());
    }

    @Test
    void updatePatient() {
        Patient updatePat = new Patient("P1", "Lavish", 30, "Pattamadai", "patient1@gmail.com", "9988776655", "Fever");

        when(patientRepository.findBypatientId("P1")).thenReturn(patient1);
        when(patientRepository.save(any(Patient.class))).thenReturn(updatePat);

        patientService.updatePatient("P1", updatePat);
        verify(patientRepository, times(1)).findBypatientId("P1");
        verify(patientRepository, times(1)).save(patient1);
    }

    @Test
    void deletePatient(){
        patientService.deletePatient("P1");
        verify(patientRepository, times(1)).deleteBypatientId("P1");
    }


}
