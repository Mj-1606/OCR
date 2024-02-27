package com.spring.ocr.controller;

import com.spring.ocr.entity.BillData;
import com.spring.ocr.service.impl.OcrServiceTesseract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ocr")
public class OcrController {
    @Autowired
    OcrServiceTesseract ocrService;
@GetMapping("/hi")  // testing
    String hii(){
    return "Hello";
}

@PostMapping("/upload")
List<BillData> uploadDocument(){
   return null;
}
}
