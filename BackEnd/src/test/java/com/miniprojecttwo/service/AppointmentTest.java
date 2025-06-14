package com.miniprojecttwo.service;

import com.miniprojecttwo.entity.AppointmentManager;
import com.miniprojecttwo.entity.AppointmentManager;
import com.miniprojecttwo.repository.AppointmentManagerRepository;
import com.miniprojecttwo.repository.AppointmentManagerRepository;
import com.miniprojecttwo.service.AppointmentManagerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AppointmentTest {

    @Mock
    private AppointmentManagerRepository appointmentManagerRepository;
    @InjectMocks
    private AppointmentManagerService appointmentManagerService;

    private AppointmentManager appointmentManager1;
    private AppointmentManager appointmentManager2;


    @BeforeEach
    void setup() {
        appointmentManager1 = new AppointmentManager("A1",
                LocalDate.of(2025, 5, 10),
                LocalTime.of(10, 0), LocalTime.of(13, 0),
                "D1", "Doctor1", "MBBS", "Pulmonologist");

        appointmentManager2 = new AppointmentManager("A2",
                LocalDate.of(2025, 5, 15),
                LocalTime.of(17, 0), LocalTime.of(20, 0),
                "D2", "Doctor2", "MBBS", "Ophthalmologist");
    }

    @Test
    void getAllAppointmentManager() {
        when(appointmentManagerRepository.findAll()).thenReturn(Arrays.asList(appointmentManager1, appointmentManager2));
        List<AppointmentManager> appointmentManagers = appointmentManagerService.listAppointment();

        // validate
        assertEquals(2, appointmentManagers.size());
        verify(appointmentManagerRepository, times(1)).findAll();
    }

    @Test
    void saveAppointmentManager() {
        when(appointmentManagerRepository.save(any(AppointmentManager.class))).thenReturn(appointmentManager1);
        AppointmentManager appointmentManager = appointmentManagerService.createAppointment(appointmentManager1);

        // validate
        assertNotNull(appointmentManager);
        assertEquals("Doctor1", appointmentManager.getDoctorName());
        verify(appointmentManagerRepository, times(1)).save(appointmentManager1);
    }

    @Test
    void getAppointmentManagerById() {
        when(appointmentManagerRepository.findByappointmentId(appointmentManager2.getAppointmentId())).thenReturn(appointmentManager2);
        AppointmentManager appointmentManager = appointmentManagerService.findByappointmentId(appointmentManager2.getAppointmentId());

        assertEquals("Doctor2", appointmentManager.getDoctorName());
        verify(appointmentManagerRepository, times(1)).findByappointmentId(appointmentManager2.getAppointmentId());
    }

    @Test
    void updateAppointmentManager() {
        AppointmentManager updatePat = new AppointmentManager("A2",
                LocalDate.of(2025, 5, 20),
                LocalTime.of(17, 0), LocalTime.of(20, 0),
                "D2", "Doctor2", "MBBS", "");

        when(appointmentManagerRepository.findByappointmentId("A2")).thenReturn(appointmentManager1);
        when(appointmentManagerRepository.save(any(AppointmentManager.class))).thenReturn(updatePat);

        appointmentManagerService.updateAppointment("A2", updatePat);
        verify(appointmentManagerRepository, times(1)).findByappointmentId("A2");
        verify(appointmentManagerRepository, times(1)).save(appointmentManager1);
    }

    @Test
    void deleteAppointmentManager() {
        appointmentManagerService.deleteAppointment("A1");
        verify(appointmentManagerRepository, times(1)).deleteByappointmentId("A1");
    }


}
