package com.miniprojecttwo.controller;

import com.miniprojecttwo.entity.AppointmentManager;
import com.miniprojecttwo.service.AppointmentManagerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointment")
@CrossOrigin("")
@AllArgsConstructor
public class AppointmentManagerController {

    private AppointmentManagerService appointmentManagerService;

    @GetMapping("/id/{appointmentId}")
    public ResponseEntity<?> findByappointmentId(@PathVariable String appointmentId) {
        AppointmentManager app = appointmentManagerService.findByappointmentId(appointmentId);
        if (app != null) {
            return ResponseEntity.ok(app); // 200 OK
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("AppointmentManager not found, " + appointmentId); // 404 NOT_FOUND
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> createAppointment(@RequestBody AppointmentManager appointmentManager) {

        AppointmentManager isExist = appointmentManagerService.findByappointmentId(appointmentManager.getAppointmentId());
        if (isExist != null) {
            return ResponseEntity.status(HttpStatus.FOUND).body("ID already exist " + appointmentManager.getAppointmentId());
        }

        AppointmentManager app = appointmentManagerService.createAppointment(appointmentManager);
        if (app != null) {
            return new ResponseEntity<>(app, HttpStatus.CREATED); // 201 CREATED
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST); // 400 BAD_REQUEST
        }
    }

    @PutMapping("/update/{appointmentId}")
    public ResponseEntity<?> updateAppointment(@PathVariable String appointmentId, @RequestBody AppointmentManager appointmentManager) {
        AppointmentManager app = appointmentManagerService.updateAppointment(appointmentId, appointmentManager);
        if (app != null) {
            return ResponseEntity.status(HttpStatus.OK).body("Updated successfully."); // 200 OK
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("AppointmentManager not found, " + appointmentId); // 404 NOT_FOUND
    }

    @GetMapping
    public ResponseEntity<List<AppointmentManager>> listAppointment() {
        List<AppointmentManager> app = appointmentManagerService.listAppointment();
        if (app != null) {
            return new ResponseEntity<>(app, HttpStatus.OK); // 200 OK
        } else {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT); // 204 NO_CONTENT
        }
    }

    @Transactional
    @DeleteMapping("/delete/{appointmentId}")
    public ResponseEntity<?> deleteAppointment(@PathVariable String appointmentId) {
        int isDeleted = appointmentManagerService.deleteAppointment(appointmentId);
        if (isDeleted > 0) {
            return ResponseEntity.status(HttpStatus.OK).body("Deleted successfully"); // 200 OK
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("AppointmentManager not found, " + appointmentId); // 404 NOT_FOUND
        }
    }
}
