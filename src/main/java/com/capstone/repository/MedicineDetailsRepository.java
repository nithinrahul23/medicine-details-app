package com.capstone.repository;

import com.capstone.entity.MedicineDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicineDetailsRepository extends JpaRepository<MedicineDetails,String> {
    public MedicineDetails findByMedicineName(String medicineName);
}
