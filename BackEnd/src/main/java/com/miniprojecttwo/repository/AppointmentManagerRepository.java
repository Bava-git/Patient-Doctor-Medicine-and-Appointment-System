package com.miniprojecttwo.repository;

import com.miniprojecttwo.entity.AppointmentManager;
import com.miniprojecttwo.entity.AppointmentManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentManagerRepository extends JpaRepository<AppointmentManager, Long> {

    AppointmentManager findByappointmentId(String appointmentId);

    Integer deleteByappointmentId(String appointmentId);

}
