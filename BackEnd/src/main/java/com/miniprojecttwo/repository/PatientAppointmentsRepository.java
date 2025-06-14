package com.miniprojecttwo.repository;

import com.miniprojecttwo.entity.AppointmentManager;
import com.miniprojecttwo.entity.PatientAppointments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientAppointmentsRepository extends JpaRepository<PatientAppointments, Long> {

    PatientAppointments findBypatientappointmentId(String patientappointmentId);

    Integer deleteBypatientappointmentId(String patientappointmentId);

}
