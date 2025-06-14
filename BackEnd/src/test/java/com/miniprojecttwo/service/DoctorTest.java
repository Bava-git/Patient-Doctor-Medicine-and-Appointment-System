package com.miniprojecttwo.service;

import com.miniprojecttwo.entity.Doctor;
import com.miniprojecttwo.repository.DoctorRepository;
import com.miniprojecttwo.service.DoctorService;
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
public class DoctorTest {

    @Mock
    private DoctorRepository doctorRepository;
    @InjectMocks
    private DoctorService doctorService;

    private Doctor doctor1;
    private Doctor doctor2;

    @BeforeEach
    void setup() {
        doctor1 = new Doctor("D1", "Doctor1", "MBBS", "Pulmonologist");
        doctor2 = new Doctor("D2", "Doctor2", "MBBS", "Ophthalmologist");
    }

    @Test
    void getAllDoctor() {
        when(doctorRepository.findAll()).thenReturn(Arrays.asList(doctor1, doctor2));
        List<Doctor> doctorList = doctorService.listDoctor();

        // validate
        assertEquals(2, doctorList.size());
        verify(doctorRepository, times(1)).findAll();
    }

    @Test
    void saveDoctor() {
        when(doctorRepository.save(any(Doctor.class))).thenReturn(doctor1);
        Doctor patient = doctorService.createDoctor(doctor1);

        // validate
        assertNotNull(patient);
        assertEquals("Doctor1", patient.getDoctorName());
        verify(doctorRepository, times(1)).save(doctor1);
    }

    @Test
    void getDoctorById() {
        when(doctorRepository.findByDoctorId(doctor2.getDoctorId())).thenReturn(doctor2);
        Doctor patient = doctorService.findByDoctorId(doctor2.getDoctorId());

        assertEquals("Doctor2", patient.getDoctorName());
        verify(doctorRepository, times(1)).findByDoctorId(doctor2.getDoctorId());
    }

    @Test
    void updateDoctor() {
        Doctor updatePat = new Doctor("D1", "Lavish", "MBBS", "");

        when(doctorRepository.findByDoctorId("D1")).thenReturn(doctor1);
        when(doctorRepository.save(any(Doctor.class))).thenReturn(updatePat);

        doctorService.updateDoctor("D1", updatePat);
        verify(doctorRepository, times(1)).findByDoctorId("D1");
        verify(doctorRepository, times(1)).save(doctor1);
    }

    @Test
    void deleteDoctor() {
        doctorService.deleteDoctor("D1");
        verify(doctorRepository, times(1)).deleteByDoctorId("D1");
    }


}
