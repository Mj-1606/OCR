package com.spring.ocr.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.ocr.dto.BillDataDto;
import okhttp3.*;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public interface IOcrService {
    BillDataDto getExtractedData(String filePath);

    String performOCR(String filePath);

    BillDataDto parseJsonResponse(String jsonResponse);
}
