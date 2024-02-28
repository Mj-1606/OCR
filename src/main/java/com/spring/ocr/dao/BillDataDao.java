package com.spring.ocr.dao;

import com.spring.ocr.entity.BillData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillDataDao extends JpaRepository<BillData,Integer> {

}
