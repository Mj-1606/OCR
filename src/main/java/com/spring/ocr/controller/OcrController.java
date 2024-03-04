package com.spring.ocr.controller;

import com.spring.ocr.dto.BillDataDto;
import com.spring.ocr.entity.BillData;
import com.spring.ocr.service.IOcrService;
import com.spring.ocr.store.BillDataStore;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/ocr")
public class OcrController {
    private static final Logger logger = Logger.getLogger(OcrController.class.getName());
    @Autowired
    @Qualifier("ocrServiceNanonets")
    IOcrService ocrService;
    @Autowired
    BillDataStore billDataStore;

    @PostMapping("/upload")
    public ResponseEntity<BillDataDto> uploadDocument(@RequestParam("file") MultipartFile file) {
        // Check if file is empty
        if (file.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        String filePath = "F:\\Projects\\ocr\\temp\\" + file.getOriginalFilename();
        try {
            // Saving the file locally
            file.transferTo(new File(filePath));

            // Process the file using your OCR service
            BillDataDto billDataDto = ocrService.getExtractedData(filePath);

            return new ResponseEntity<>(billDataDto, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/add")
        // after data is verified by user it can be store in DB
    ResponseEntity<BillData> addData(@RequestBody BillData billData) {
        billDataStore.addEntry(billData);
        logger.info("adding data");
        return new ResponseEntity<>(billData, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    ResponseEntity<String> deleteData(@RequestBody BillData billData) {
        billDataStore.deleteEntry(billData);
        return new ResponseEntity<>("Succesfully deleted", HttpStatus.OK);
    }

    @DeleteMapping("/deleteById/{id}")
    ResponseEntity<String> deleteById(@PathVariable Integer id) {
        billDataStore.deleteById(id);
        return new ResponseEntity<>("Succesfully deleted", HttpStatus.OK);
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        try {
            // Assuming billDataService.getById() internally handles the Optional and throws EntityNotFoundException
            BillData billData = billDataStore.getById(id);
            return new ResponseEntity<>(billData, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("Entity for given Id not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getByBillNumber/{billNumber}")
    ResponseEntity<List<BillData>> getAllbyBillNumber(@PathVariable String billNumber) {
        List<BillData> billDataList = billDataStore.getAllbyBillNumber(billNumber);
        return new ResponseEntity<>(billDataList, HttpStatus.OK);
    }

    @GetMapping("/status")
    String hii() {
        return "Application is Running.";
    }

}
