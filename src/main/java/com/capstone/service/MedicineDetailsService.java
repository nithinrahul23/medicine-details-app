package com.capstone.service;

import com.capstone.entity.MedicineDetails;
import com.capstone.entity.Status;
import com.capstone.exception.AppException;
import com.capstone.repository.MedicineDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicineDetailsService {

    @Autowired
    private MedicineDetailsRepository medicineDetailsRepository;

    public MedicineDetails saveOrUpdateMedicineDetails(MedicineDetails medicineDetails){
        String status = getStatus(medicineDetails.getTotalQuantity());
        medicineDetails.setStatus(Status.valueOf(status));
        return medicineDetailsRepository.save(medicineDetails);
    }

    private String getStatus(Integer totalQuantity){
        String status = "";
        if(totalQuantity <= 0){
            status = "OUTOFSTOCK";
        } else if (totalQuantity < 10) {
            status = "LOWSTOCK";
        } else{
            status = "INSTOCK";
        }
        return status;
    }

    public MedicineDetails fetchMedicineDetailsByMedicineName(String medicineName){
        return medicineDetailsRepository.findByMedicineName(medicineName);
    }

    public List<MedicineDetails> getAllDetails(){
        List<MedicineDetails> mediDetails = medicineDetailsRepository.findAll();
        return mediDetails;
    }

    public MedicineDetails updateQuantityAndStatus(String medicineName, Integer totalQuantity) throws AppException{
        MedicineDetails medicines = medicineDetailsRepository.findByMedicineName(medicineName);
        if(medicines == null){
            throw new AppException("No medicine details found with given medicine name");
        }
        medicines.setTotalQuantity(totalQuantity);
        String status = getStatus(medicines.getTotalQuantity());
        medicines.setStatus(Status.valueOf(status));
        return medicineDetailsRepository.save(medicines);
    }

    public void deleteMedicineDetails(String medicineName) throws AppException{
        MedicineDetails medicineDetails = medicineDetailsRepository.findByMedicineName(medicineName);
        if(medicineDetails == null){
            throw new AppException("No details found for the given medicine name");
        }
        medicineDetailsRepository.delete(medicineDetails);
    }
}
