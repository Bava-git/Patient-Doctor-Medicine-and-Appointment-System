package com.miniprojecttwo.controller;

import com.miniprojecttwo.entity.Doctor;
import com.miniprojecttwo.entity.Patient;
import com.miniprojecttwo.service.DoctorService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctor")
@CrossOrigin("")
@AllArgsConstructor
public class DoctorController {

    private DoctorService doctorService;

    @GetMapping("/id/{DoctorId}")
    public ResponseEntity<?> findByDoctorId(@PathVariable String DoctorId) {
        Doctor pat = doctorService.findByDoctorId(DoctorId);
        if (pat != null) {
            return ResponseEntity.ok(pat); // 200 OK
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Doctor not found, " + DoctorId); // 404 NOT_FOUND
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> createDoctor(@RequestBody Doctor doctor) {

        Doctor isExist = doctorService.findByDoctorId(doctor.getDoctorId());
        if (isExist != null) {
            return ResponseEntity.status(HttpStatus.FOUND).body("ID already exist " + doctor.getDoctorId());
        }

        Doctor pat = doctorService.createDoctor(doctor);
        if (pat != null) {
            return new ResponseEntity<>(pat, HttpStatus.CREATED); // 201 CREATED
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST); // 400 BAD_REQUEST
        }
    }

    @PutMapping("/update/{DoctorId}")
    public ResponseEntity<?> updateDoctor(@PathVariable String DoctorId, @RequestBody Doctor updatedoctor) {
        Doctor pat = doctorService.updateDoctor(DoctorId, updatedoctor);
        if (pat != null) {
            return ResponseEntity.status(HttpStatus.OK).body("Updated successfully."); // 200 OK
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Doctor not found, " + DoctorId); // 404 NOT_FOUND
    }

    @GetMapping
    public ResponseEntity<List<Doctor>> listDoctor() {
        List<Doctor> pat = doctorService.listDoctor();
        if (pat != null) {
            return new ResponseEntity<>(pat, HttpStatus.OK); // 200 OK
        } else {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT); // 204 NO_CONTENT
        }
    }

    @Transactional
    @DeleteMapping("/delete/{DoctorId}")
    public ResponseEntity<?> deleteDoctor(@PathVariable String DoctorId) {
        int isDeleted = doctorService.deleteDoctor(DoctorId);
        if (isDeleted > 0) {
            return ResponseEntity.status(HttpStatus.OK).body("Deleted successfully"); // 200 OK
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Doctor not found, " + DoctorId); // 404 NOT_FOUND
        }
    }
}
