package com.miniprojecttwo.service;

import com.miniprojecttwo.entity.MedicationManager;
import com.miniprojecttwo.repository.MedicationManagerRepository;
import com.miniprojecttwo.repository.MedicationManagerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class MedicationManagerService {

    private MedicationManagerRepository medicationManagerRepository;

    public MedicationManager findBymedicineId(String medicineId) {
        return medicationManagerRepository.findBymedicineId(medicineId);
    }

    public List<MedicationManager> listBypatientappointment_id(String patientappointmentId) {
        return medicationManagerRepository.findBypatientappointmentId(patientappointmentId);
    }

    public MedicationManager createMedication(MedicationManager medicationManager) {
        return medicationManagerRepository.save(medicationManager);
    }

    public MedicationManager updateMedication(String medicineId, MedicationManager updateMedicationManager) {
        MedicationManager medic = medicationManagerRepository.findBymedicineId(medicineId);

        if (medic != null) {
            medic.setMedicineName(updateMedicationManager.getMedicineName());
            medic.setMedicineDays(updateMedicationManager.getMedicineDays());
            medic.setMedicineAfterFood(updateMedicationManager.isMedicineAfterFood());
            medic.setMedicineMorning(updateMedicationManager.isMedicineMorning());
            medic.setMedicineAfternoon(updateMedicationManager.isMedicineAfternoon());
            medic.setMedicineNight(updateMedicationManager.isMedicineNight());
            medic.setPatientappointmentId(updateMedicationManager.getPatientappointmentId());
            medic.setAppointmentDateTime(updateMedicationManager.getAppointmentDateTime());
            medic.setDoctorId(updateMedicationManager.getDoctorId());
            medic.setDoctorName(updateMedicationManager.getDoctorName());
            medic.setPatientId(updateMedicationManager.getPatientId());
            medic.setPatientName(updateMedicationManager.getPatientName());
            return medicationManagerRepository.save(medic);
        }

        return null;
    }

    public List<MedicationManager> listMedication() {
        return medicationManagerRepository.findAll();
    }

    @Transactional
    public Integer deleteMedication(String medicineId) {
        return medicationManagerRepository.deleteBymedicineId(medicineId);
    }

}
