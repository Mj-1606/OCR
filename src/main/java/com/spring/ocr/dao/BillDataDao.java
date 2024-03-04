package com.spring.ocr.dao;

import com.spring.ocr.entity.BillData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillDataDao extends JpaRepository<BillData,Integer> {
    @Query("from BillData where billNumber=?1")  // custom queries
List<BillData> getAllbyBillNumber(String billNumber);
}
