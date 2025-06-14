package com.miniprojecttwo.service;

import com.miniprojecttwo.entity.AppointmentManager;
import com.miniprojecttwo.repository.AppointmentManagerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class AppointmentManagerService {

    private AppointmentManagerRepository appointmentManagerRepository;

    public AppointmentManager findByappointmentId(String appointmentId) {
        return appointmentManagerRepository.findByappointmentId(appointmentId);
    }

    public AppointmentManager createAppointment(AppointmentManager appointmentManager) {
        return appointmentManagerRepository.save(appointmentManager);
    }

    public AppointmentManager updateAppointment(String appointmentId, AppointmentManager updateappointmentManager) {
        AppointmentManager app = appointmentManagerRepository.findByappointmentId(appointmentId);

        if (app != null) {
            app.setAppointmentDate(updateappointmentManager.getAppointmentDate());
            app.setAppointmentStartTime(updateappointmentManager.getAppointmentStartTime());
            app.setAppointmentEndTime(updateappointmentManager.getAppointmentEndTime());
            app.setDoctorId((updateappointmentManager.getDoctorId()));
            app.setDoctorName(updateappointmentManager.getDoctorName());
            app.setDoctorEducation(updateappointmentManager.getDoctorEducation());
            app.setDoctorSpecializedField(updateappointmentManager.getDoctorSpecializedField());
            return appointmentManagerRepository.save(app);
        }

        return null;
    }

    public List<AppointmentManager> listAppointment() {
        return appointmentManagerRepository.findAll();
    }

    @Transactional
    public Integer deleteAppointment(String appointmentId) {
        return appointmentManagerRepository.deleteByappointmentId(appointmentId);
    }

}
