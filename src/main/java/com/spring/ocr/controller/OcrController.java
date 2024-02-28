package com.spring.ocr.controller;

import com.spring.ocr.dto.BillDataDto;
import com.spring.ocr.entity.BillData;
import com.spring.ocr.service.impl.OcrServiceNanonets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ocr")
public class OcrController {
    @Autowired
    OcrServiceNanonets ocrServiceNanonets;

    @GetMapping("/hi")
    String hii() {
        return "Hello";
    }

    @PostMapping("/upload")  // for single document at a time
    BillDataDto uploadDocument() {
        String filePath = "C:\\Users\\garvi\\OneDrive\\Pictures\\Screenshots\\ebill.png";

        BillDataDto billDataDto = ocrServiceNanonets.getExtractedData(filePath);

        return billDataDto;
    }
    @PostMapping("/verified")  // after data is verified by user it can be store in DB
    BillData verifiedData(@RequestBody BillData billData) {

        return billData;
    }
}
