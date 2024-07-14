package com.capstone.dao;

import com.capstone.entity.MedicineDetails;
import com.capstone.entity.MedicineType;
import com.capstone.exception.AppException;
import com.capstone.service.MedicineDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medicoApp")
@CrossOrigin
public class MedicineDetailsDao {

    @Autowired
    private MedicineDetailsService medicineDetailsService;

    @GetMapping("/mediType")
    public MedicineType[] getMedicineType(){
        return MedicineType.values();
    }


    @PostMapping("/medDetails")
    public MedicineDetails addMedicineDetails(@RequestBody MedicineDetails medicineDetails) throws AppException {
        String temp = medicineDetails.getMedicineName();
        if(temp != null && !"".equals(temp)){
            MedicineDetails medicineDets = medicineDetailsService.fetchMedicineDetailsByMedicineName(temp);
            if(medicineDets != null){
                throw new AppException("Medicine Details with name "+ temp +" is already exsits.");
            }
        }
        return medicineDetailsService.saveOrUpdateMedicineDetails(medicineDetails);
    }

    @GetMapping("/allMediDetails")
    public List<MedicineDetails> getAllDetails() throws AppException{
         List<MedicineDetails> mediDetails = medicineDetailsService.getAllDetails();
         if(mediDetails == null){
             throw new AppException("Medicine Details not found");
         }
         return mediDetails;
    }

    @PutMapping("/medDetails/{medicineName}")
    public MedicineDetails updateQuantityAndStatus( @PathVariable String medicineName,  @RequestBody MedicineDetails medicineDetails) throws AppException{
        return medicineDetailsService.updateQuantityAndStatus(medicineName, medicineDetails.getTotalQuantity());
    }

    @DeleteMapping("/{medicineName}")
    public void deleteMedicineDetails(@PathVariable String medicineName) throws AppException{
        medicineDetailsService.deleteMedicineDetails(medicineName);
    }

}
