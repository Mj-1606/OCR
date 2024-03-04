package com.spring.ocr.store;

import com.spring.ocr.dao.BillDataDao;
import com.spring.ocr.entity.BillData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 To run H2 database go to http://localhost:8080/h2-console
 then set jdbc url to = jdbc:h2:mem:testdb
 username=sa
 password=password
 */
@Service
public class BillDataStore {
    @Autowired
    BillDataDao billDataDao;

    public void addEntry(BillData billData) {
        billDataDao.save(billData);
    }

    public void deleteEntry(BillData billData) {
        billDataDao.delete(billData);
    }

    public void deleteById(int id) {
        billDataDao.deleteById(id);
    }

    public BillData getById(int id) {
        return billDataDao.getReferenceById(id);
    }
    public List<BillData> getAllbyBillNumber(String billNumber){
        return billDataDao.getAllbyBillNumber(billNumber);
    }

}
