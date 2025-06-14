package com.miniprojecttwo.service;

import com.miniprojecttwo.entity.Doctor;
import com.miniprojecttwo.repository.DoctorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class DoctorService {

    private DoctorRepository doctorRepository;

    public Doctor findByDoctorId(String DoctorId) {
        return doctorRepository.findByDoctorId(DoctorId);
    }

    public Doctor createDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    public Doctor updateDoctor(String DoctorId, Doctor updateDoctor) {
        Doctor doc = doctorRepository.findByDoctorId(DoctorId);

        if (doc != null) {
            doc.setDoctorName(updateDoctor.getDoctorName());
            doc.setDoctorEducation(updateDoctor.getDoctorEducation());
            doc.setDoctorSpecializedField(updateDoctor.getDoctorSpecializedField());
            return doctorRepository.save(doc);
        }

        return null;
    }

    public List<Doctor> listDoctor() {
        return doctorRepository.findAll();
    }

    @Transactional
    public Integer deleteDoctor(String DoctorId) {
        return doctorRepository.deleteByDoctorId(DoctorId);
    }

}
