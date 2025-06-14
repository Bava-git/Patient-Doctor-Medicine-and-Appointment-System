package com.miniprojecttwo.repository;

import com.miniprojecttwo.entity.Doctor;
import com.miniprojecttwo.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    Doctor findByDoctorId(String doctorId);

    Integer deleteByDoctorId(String doctorId);

}
