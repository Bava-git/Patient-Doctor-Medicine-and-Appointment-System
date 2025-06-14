package com.miniprojecttwo.service;

import com.miniprojecttwo.entity.PatientAppointments;
import com.miniprojecttwo.entity.PatientAppointments;
import com.miniprojecttwo.repository.PatientAppointmentsRepository;
import com.miniprojecttwo.repository.PatientAppointmentsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class PatientAppointmentsService {

    private PatientAppointmentsRepository patientAppointmentsRepository;

    public PatientAppointments findBypatientappointmentId(String patientappointmentId) {
        return patientAppointmentsRepository.findBypatientappointmentId(patientappointmentId);
    }

    public PatientAppointments createPatientAppointments(PatientAppointments patientAppointments) {
        return patientAppointmentsRepository.save(patientAppointments);
    }

    public PatientAppointments updatePatientAppointments(String patientappointmentId, PatientAppointments updatePatientAppointments) {
        PatientAppointments patapp = patientAppointmentsRepository.findBypatientappointmentId(patientappointmentId);

        if (patapp != null) {
            patapp.setAppointmentDateTime(updatePatientAppointments.getAppointmentDateTime());
            patapp.setPatientId(updatePatientAppointments.getPatientId());
            patapp.setPatientName(updatePatientAppointments.getPatientName());
            patapp.setPatientAge(updatePatientAppointments.getPatientAge());
            patapp.setPatientIssue(updatePatientAppointments.getPatientIssue());
            patapp.setDoctorId(updatePatientAppointments.getDoctorId());
            patapp.setDoctorName(updatePatientAppointments.getDoctorName());
            patapp.setDoctorEducation(updatePatientAppointments.getDoctorEducation());
            patapp.setDoctorSpecializedField(updatePatientAppointments.getDoctorSpecializedField());
            return patientAppointmentsRepository.save(patapp);
        }

        return null;
    }

    public List<PatientAppointments> listPatientAppointments() {
        return patientAppointmentsRepository.findAll();
    }

    @Transactional
    public Integer deletePatientAppointments(String patientappointmentId) {
        return patientAppointmentsRepository.deleteBypatientappointmentId(patientappointmentId);
    }

}
