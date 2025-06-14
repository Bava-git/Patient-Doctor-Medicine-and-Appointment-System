package com.miniprojecttwo.repository;

import com.miniprojecttwo.entity.MedicationManager;
import com.miniprojecttwo.entity.MedicationManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicationManagerRepository extends JpaRepository<MedicationManager, Long> {

    MedicationManager findBymedicineId(String medicineId);

    Integer deleteBymedicineId(String medicineId);

    List<MedicationManager> findBypatientappointmentId(String patientappointmentId);
}
