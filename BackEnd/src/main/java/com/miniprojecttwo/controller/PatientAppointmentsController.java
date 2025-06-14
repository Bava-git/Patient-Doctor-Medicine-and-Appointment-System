package com.miniprojecttwo.controller;

import com.miniprojecttwo.entity.PatientAppointments;
import com.miniprojecttwo.service.PatientAppointmentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patientappointments")
@CrossOrigin("http://localhost:3000")
public class PatientAppointmentsController {

    @Autowired
    private PatientAppointmentsService patientAppointmentsService;

    @GetMapping("/id/{patientappointmentId}")
    public ResponseEntity<?> findBypatientappointmentId(@PathVariable String patientappointmentId) {
        PatientAppointments patapp = patientAppointmentsService.findBypatientappointmentId(patientappointmentId);
        if (patapp != null) {
            return ResponseEntity.ok(patapp); // 200 OK
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("PatientAppointments not found, " + patientappointmentId); // 404 NOT_FOUND
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> createPatientAppointments(@RequestBody PatientAppointments patientAppointments) {

        System.out.println("patientAppointments " +patientAppointments);

        PatientAppointments isUserExist = patientAppointmentsService.findBypatientappointmentId(patientAppointments.getPatientappointmentId());
        System.out.println(isUserExist);
        if (isUserExist != null) {
            return ResponseEntity.status(HttpStatus.FOUND).body("Appointment already exist " + patientAppointments.getPatientappointmentId());
        }

        PatientAppointments patapp = patientAppointmentsService.createPatientAppointments(patientAppointments);
        if (patapp != null) {
            return new ResponseEntity<>(patapp, HttpStatus.CREATED); // 201 CREATED
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST); // 400 BAD_REQUEST
        }
    }

    @PutMapping("/update/{patientappointmentId}")
    public ResponseEntity<?> updatePatientAppointments(@PathVariable String patientappointmentId, @RequestBody PatientAppointments updatepatientAppointments) {
        PatientAppointments patapp = patientAppointmentsService.updatePatientAppointments(patientappointmentId, updatepatientAppointments);
        if (patapp != null) {
            return ResponseEntity.status(HttpStatus.OK).body("Updated successfully."); // 200 OK
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("PatientAppointments not found, " + patientappointmentId); // 404 NOT_FOUND
    }

    @GetMapping
    public ResponseEntity<List<PatientAppointments>> listPatientAppointments() {
        List<PatientAppointments> patapp = patientAppointmentsService.listPatientAppointments();
        if (patapp != null) {
            return new ResponseEntity<>(patapp, HttpStatus.OK); // 200 OK
        } else {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT); // 204 NO_CONTENT
        }
    }

    @Transactional
    @DeleteMapping("/delete/{patientappointmentId}")
    public ResponseEntity<?> deletePatientAppointments(@PathVariable String patientappointmentId) {
        int isDeleted = patientAppointmentsService.deletePatientAppointments(patientappointmentId);
        if (isDeleted > 0) {
            return ResponseEntity.status(HttpStatus.OK).body("Deleted successfully"); // 200 OK
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("PatientAppointments not found, " + patientappointmentId); // 404 NOT_FOUND
        }
    }
}
