package com.miniprojecttwo.service;

import com.miniprojecttwo.entity.AppointmentManager;
import com.miniprojecttwo.entity.PatientAppointments;
import com.miniprojecttwo.repository.AppointmentManagerRepository;
import com.miniprojecttwo.repository.PatientAppointmentsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PatientAppointmentTest {

    @Mock
    private PatientAppointmentsRepository patientAppointmentsRepository;
    @InjectMocks
    private PatientAppointmentsService patientAppointmentsService;

    private PatientAppointments patientAppointments1;
    private PatientAppointments patientAppointments2;


    @BeforeEach
    void setup() {
        patientAppointments1 = new PatientAppointments("PA1",
                LocalDateTime.of(2025, 5, 10, 17, 0),
                "D1", "Doctor1", "MBBS", "Pulmonologist",
                "P1", "Patient1", 25, "Fever");

        patientAppointments2 = new PatientAppointments("PA2",
                LocalDateTime.of(2025, 5, 10, 17, 0),
                "D2", "Doctor2", "MBBS", "Ophthalmologist",
                "P2", "Patient2", 30, "Cough");
    }


    @Test
    void getAllAppointmentManager() {
        when(patientAppointmentsRepository.findAll()).thenReturn(Arrays.asList(patientAppointments1, patientAppointments2));
        List<PatientAppointments> patientAppointments = patientAppointmentsService.listPatientAppointments();

        // validate
        assertEquals(2, patientAppointments.size());
        verify(patientAppointmentsRepository, times(1)).findAll();
    }

    @Test
    void saveAppointmentManager() {
        when(patientAppointmentsRepository.save(any(PatientAppointments.class))).thenReturn(patientAppointments1);
        PatientAppointments patientAppointments = patientAppointmentsService.createPatientAppointments(patientAppointments1);

        // validate
        assertNotNull(patientAppointments);
        assertEquals("Doctor1", patientAppointments.getDoctorName());
        verify(patientAppointmentsRepository, times(1)).save(patientAppointments1);
    }

    @Test
    void getAppointmentManagerById() {
        when(patientAppointmentsRepository.findBypatientappointmentId(patientAppointments2.getPatientappointmentId())).thenReturn(patientAppointments2);
        PatientAppointments patientAppointments = patientAppointmentsService.findBypatientappointmentId(patientAppointments2.getPatientappointmentId());

        assertEquals("Doctor2", patientAppointments.getDoctorName());
        verify(patientAppointmentsRepository, times(1)).findBypatientappointmentId(patientAppointments2.getPatientappointmentId());
    }

    @Test
    void updateAppointmentManager() {
        PatientAppointments updatePatApp = new PatientAppointments("PA2",
                LocalDateTime.of(2025, 5, 10, 17, 0),
                "D2", "Doctor2", "MBBS", "",
                "P2", "Patient2", 30, "Cough");

        when(patientAppointmentsRepository.findBypatientappointmentId("PA2")).thenReturn(patientAppointments1);
        when(patientAppointmentsRepository.save(any(PatientAppointments.class))).thenReturn(updatePatApp);

        patientAppointmentsService.updatePatientAppointments("PA2", updatePatApp);
        verify(patientAppointmentsRepository, times(1)).findBypatientappointmentId("PA2");
        verify(patientAppointmentsRepository, times(1)).save(patientAppointments1);
    }

    @Test
    void deleteAppointmentManager() {
        patientAppointmentsService.deletePatientAppointments("PA1");
        verify(patientAppointmentsRepository, times(1)).deleteBypatientappointmentId("PA1");
    }


}
