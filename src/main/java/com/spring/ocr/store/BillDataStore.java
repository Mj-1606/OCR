package com.spring.ocr.store;

import com.spring.ocr.dao.BillDataDao;
import com.spring.ocr.dto.BillDataDto;
import com.spring.ocr.entity.BillData;
import org.springframework.beans.factory.annotation.Autowired;

public class BillDataStore {
    @Autowired
    BillDataDao billDataDao;
public void addEntry(BillData billData){
    billDataDao.save(billData);
}

}
