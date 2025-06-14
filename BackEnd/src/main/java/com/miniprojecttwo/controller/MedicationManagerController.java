package com.miniprojecttwo.controller;

import com.miniprojecttwo.entity.MedicationManager;
import com.miniprojecttwo.service.MedicationManagerService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medication")
@CrossOrigin("http://localhost:3000")
public class MedicationManagerController {

    @Autowired
    private MedicationManagerService medicationManagerService;

    @GetMapping("/id/{MedicationId}")
    public ResponseEntity<?> findByMedicationId(@PathVariable String MedicationId) {
        MedicationManager medic = medicationManagerService.findBymedicineId(MedicationId);
        if (medic != null) {
            return ResponseEntity.ok(medic); // 200 OK
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("MedicationManager not found, " + MedicationId); // 404 NOT_FOUND
        }
    }

    @GetMapping("/patientappointmentid/{patientappointmentId}")
    public ResponseEntity<?> listBypatientappointment_id(@PathVariable String patientappointmentId) {
        List<MedicationManager> medic = medicationManagerService.listBypatientappointment_id(patientappointmentId);
        if (medic != null) {
            return ResponseEntity.ok(medic); // 200 OK
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("MedicationManager not found, " + patientappointmentId); // 404 NOT_FOUND
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> createMedication(@RequestBody MedicationManager medicationManager) {
        MedicationManager medic = medicationManagerService.createMedication(medicationManager);
        if (medic != null) {
            return new ResponseEntity<>(medic, HttpStatus.CREATED); // 201 CREATED
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST); // 400 BAD_REQUEST
        }
    }

    @PutMapping("/update/{MedicationId}")
    public ResponseEntity<?> updateMedication(@PathVariable String MedicationId, @RequestBody MedicationManager updatemedicationManager) {
        MedicationManager medic = medicationManagerService.updateMedication(MedicationId, updatemedicationManager);
        if (medic != null) {
            return ResponseEntity.status(HttpStatus.OK).body("Updated successfully."); // 200 OK
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("MedicationManager not found, " + MedicationId); // 404 NOT_FOUND
    }

    @GetMapping
    public ResponseEntity<List<MedicationManager>> listMedication() {
        List<MedicationManager> medic = medicationManagerService.listMedication();
        if (medic != null) {
            return new ResponseEntity<>(medic, HttpStatus.OK); // 200 OK
        } else {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT); // 204 NO_CONTENT
        }
    }

    @Transactional
    @DeleteMapping("/delete/{MedicationId}")
    public ResponseEntity<?> deleteMedication(@PathVariable String MedicationId) {
        int isDeleted = medicationManagerService.deleteMedication(MedicationId);
        if (isDeleted > 0) {
            return ResponseEntity.status(HttpStatus.OK).body("Deleted successfully"); // 200 OK
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("MedicationManager not found, " + MedicationId); // 404 NOT_FOUND
        }
    }
}
