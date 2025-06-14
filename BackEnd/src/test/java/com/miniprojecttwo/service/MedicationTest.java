package com.miniprojecttwo.service;

import com.miniprojecttwo.entity.AppointmentManager;
import com.miniprojecttwo.entity.MedicationManager;
import com.miniprojecttwo.repository.AppointmentManagerRepository;
import com.miniprojecttwo.repository.MedicationManagerRepository;
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
public class MedicationTest {

    @Mock
    private MedicationManagerRepository medicationManagerRepository;
    @InjectMocks
    private MedicationManagerService medicationManagerService;

    private MedicationManager medicationManager1;
    private MedicationManager medicationManager2;


    @BeforeEach
    void setup() {
        medicationManager1 = new MedicationManager("M1",
                "Dolo 650",
                5,
                true,
                true,
                true,
                true,
                "D1",
                "Ashika",
                "P1",
                "Patient",
                LocalDateTime.of(2025, 5, 10, 17, 0),
                "PA1"
        );

        medicationManager2 = new MedicationManager("M2",
                "Calpol 500",
                5,
                true,
                false,
                true,
                true,
                "D2",
                "Murugan",
                "P2",
                "Mani",
                LocalDateTime.of(2025, 5, 10, 17, 0),
                "PA1"
        );

    }

    @Test
    void getAllAppointmentManager() {
        when(medicationManagerRepository.findAll()).thenReturn(Arrays.asList(medicationManager1, medicationManager2));
        List<MedicationManager> medicationManagers = medicationManagerService.listMedication();

        // validate
        assertEquals(2, medicationManagers.size());
        verify(medicationManagerRepository, times(1)).findAll();
    }

    @Test
    void saveAppointmentManager() {
        when(medicationManagerRepository.save(any(MedicationManager.class))).thenReturn(medicationManager1);
        MedicationManager medicationManager = medicationManagerService.createMedication(medicationManager1);

        // validate
        assertNotNull(medicationManager);
        assertEquals("Ashika", medicationManager.getDoctorName());
        verify(medicationManagerRepository, times(1)).save(medicationManager1);
    }

    @Test
    void getAppointmentManagerById() {
        when(medicationManagerRepository.findBymedicineId(medicationManager2.getMedicineId())).thenReturn(medicationManager2);
        MedicationManager medicationManager = medicationManagerService.findBymedicineId(medicationManager2.getMedicineId());

        assertEquals("Murugan", medicationManager.getDoctorName());
        verify(medicationManagerRepository, times(1)).findBymedicineId(medicationManager2.getMedicineId());
    }

    @Test
    void updateAppointmentManager() {
        MedicationManager updateMEdicationManager = new MedicationManager("M2",
                "Calpol 500",
                5,
                true,
                false,
                true,
                true,
                "D2",
                "Kanesh",
                "P2",
                "Mani",
                LocalDateTime.of(2025, 5, 10, 17, 0),
                "PA1"
        );

        when(medicationManagerRepository.findBymedicineId("M2")).thenReturn(medicationManager1);
        when(medicationManagerRepository.save(any(MedicationManager.class))).thenReturn(updateMEdicationManager);

        medicationManagerService.updateMedication("M2", updateMEdicationManager);
        verify(medicationManagerRepository, times(1)).findBymedicineId("M2");
        verify(medicationManagerRepository, times(1)).save(medicationManager1);
    }

    @Test
    void deleteAppointmentManager() {
        medicationManagerService.deleteMedication("M1");
        verify(medicationManagerRepository, times(1)).deleteBymedicineId("M1");
    }


}
