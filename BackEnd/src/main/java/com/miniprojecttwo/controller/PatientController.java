package com.miniprojecttwo.controller;

import com.miniprojecttwo.entity.AppointmentManager;
import com.miniprojecttwo.entity.Patient;
import com.miniprojecttwo.service.PatientService;
import jakarta.annotation.security.PermitAll;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patient")
@CrossOrigin("http://localhost:3000")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @GetMapping("/id/{patientId}")
    public ResponseEntity<?> findBypatientId(@PathVariable String patientId) {
        Patient pat = patientService.findBypatientId(patientId);
        if (pat != null) {
            return ResponseEntity.ok(pat); // 200 OK
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Patient not found, " + patientId); // 404 NOT_FOUND
        }
    }

    @GetMapping("/email/{PatientEmail}")
    public ResponseEntity<?> findByPatientEmail(@PathVariable String PatientEmail) {
        Patient pat = patientService.findByPatientEmail(PatientEmail);
        if (pat != null) {
            return ResponseEntity.ok(pat); // 200 OK
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Patient not found, " + PatientEmail); // 404 NOT_FOUND
        }
    }

    @GetMapping("/contact/{PatientContact}")
    public ResponseEntity<?> findBypatientContact(@PathVariable String PatientContact) {
        Patient pat = patientService.findBypatientContact(PatientContact);
        if (pat != null) {
            return ResponseEntity.ok(pat); // 200 OK
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Patient not found, " + PatientContact); // 404 NOT_FOUND
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> createPatient(@RequestBody Patient patient) {

        Patient isExist = patientService.findBypatientId(patient.getPatientId());
        if (isExist != null) {
            return ResponseEntity.status(HttpStatus.FOUND).body("ID already exist " + patient.getPatientId());
        }

        Patient pat = patientService.createPatient(patient);
        if (pat != null) {
            return new ResponseEntity<>(pat, HttpStatus.CREATED); // 201 CREATED
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST); // 400 BAD_REQUEST
        }
    }

    @PutMapping("/update/{patientId}")
    public ResponseEntity<?> updatePatient(@PathVariable String patientId, @RequestBody Patient updatepatient) {
        Patient pat = patientService.updatePatient(patientId, updatepatient);
        if (pat != null) {
            return ResponseEntity.status(HttpStatus.OK).body("Updated successfully."); // 200 OK
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Patient not found, " + patientId); // 404 NOT_FOUND
    }

    @GetMapping
    public ResponseEntity<List<Patient>> listPatient() {
        List<Patient> pat = patientService.listPatient();
        if (pat != null) {
            return new ResponseEntity<>(pat, HttpStatus.OK); // 200 OK
        } else {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT); // 204 NO_CONTENT
        }
    }

    @Transactional
    @DeleteMapping("/delete/{patientId}")
    public ResponseEntity<?> deletePatient(@PathVariable String patientId) {
        int isDeleted = patientService.deletePatient(patientId);
        if (isDeleted > 0) {
            return ResponseEntity.status(HttpStatus.OK).body("Deleted successfully"); // 200 OK
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Patient not found, " + patientId); // 404 NOT_FOUND
        }
    }
}
