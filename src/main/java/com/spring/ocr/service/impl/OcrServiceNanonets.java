package com.spring.ocr.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.ocr.dto.BillDataDto;
import com.spring.ocr.entity.BillData;
import okhttp3.*;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class OcrServiceNanonets {
    private static final String apiKey = "19b53ba1-d4eb-11ee-9d87-725751de663d";
    private static final String modelId = "c846b738-7fda-4d92-89a1-0ebc4166a62a";
//    public static void main(String[] args) {
//        String filePath = "C:\\Users\\garvi\\OneDrive\\Pictures\\Screenshots\\ebill.png";
//
////        String filePath = "C:\\Users\\garvi\\Downloads\\N3531046505 (21).pdf";
//
//        OcrServiceNanonets OCRServiceNanonets = new OcrServiceNanonets();
//        String extractedText = OCRServiceNanonets.performOCR(filePath);
////        String jsonResponse = "{\"message\":\"Success\",\"result\":[{\"message\":\"Success\",\"input\":\"image.png\",\"prediction\":[{\"id\":\"acf04516-a322-49b3-b149-f12f934e840a\",\"label\":\"seller_address\",\"xmin\":189,\"ymin\":34,\"xmax\":422,\"ymax\":46,\"score\":0.58660215,\"ocr_text\":\"G.P.H. Compound , Pologround , Indore ( M.P. )\",\"type\":\"field\",\"status\":\"correctly_predicted\",\"page_no\":0,\"label_id\":\"2b60f581-9f6d-4444-87ef-ce8d959c8a7a\"},{\"id\":\"a3de5f94-7e2a-4062-b7b2-a32b4ba8b351\"}] } ]}";
//
//        BillData billData= parseJsonResponse(extractedText);
//        System.out.println(billDataDto.toString());
//    }

    public OcrServiceNanonets() {

    }

    public BillDataDto getExtractedData(String filePath){
        String extractedText = performOCR(filePath);
        BillDataDto billdataDto= parseJsonResponse(extractedText);
        return billdataDto;
    }
    public static String performOCR(String filePath) {
        OkHttpClient client = new OkHttpClient();

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", "file.png",
                        RequestBody.create(MediaType.parse("application/octet-stream"), new File(filePath)))
                .build();

        Request request = new Request.Builder()
                .url("https://app.nanonets.com/api/v2/OCR/Model/c846b738-7fda-4d92-89a1-0ebc4166a62a/LabelFile/?async=false")
                .post(requestBody)
                .addHeader("Authorization", Credentials.basic(apiKey, ""))
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected response code: " + response);
            }

            // Extract the text from the response
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
            return "Error during OCR: " + e.getMessage();
        }
    }

    public static BillDataDto parseJsonResponse(String jsonResponse) {
        try {
            BillDataDto billDataDto=new BillDataDto();
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonResponse);

            if (jsonNode.has("result") && jsonNode.get("result").isArray()) {
                JsonNode resultsNode = jsonNode.get("result");

                for (JsonNode result : resultsNode) {
                    if (result.has("prediction") && result.get("prediction").isArray()) {
                        JsonNode predictionsNode = result.get("prediction");

                        for (JsonNode prediction : predictionsNode) {
                            if (prediction.has("label") && prediction.has("ocr_text")) {
                                String label = prediction.get("label").asText();
                                String ocrText = prediction.get("ocr_text").asText();
                                if(label.equalsIgnoreCase("invoice_number")){
                                    billDataDto.setBillNumber(ocrText);
                                }
                                else if(label.equalsIgnoreCase("invoice_date")){
                                    billDataDto.setBillDate(ocrText);
                                }else if(label.equalsIgnoreCase("invoice_amount")){
                                    billDataDto.setBillAmount(ocrText);
                                }else if(label.equalsIgnoreCase("currency")){
                                    billDataDto.setCurrency(ocrText);
                                }else if(label.equalsIgnoreCase("seller_name")){
                                    billDataDto.setProvider(ocrText);
                                }else if(label.equalsIgnoreCase("seller_address")){
                                    billDataDto.setProviderAddress(ocrText);
                                }else if(label.equalsIgnoreCase("buyer_name")){
                                    billDataDto.setConsumerName(ocrText);
                                }else if(label.equalsIgnoreCase("buyer_address")){
                                    billDataDto.setConsumerAddress(ocrText);
                                }
//                                System.out.println("Label: " + label + ", Value: " + ocrText);
                            }
                        }
                        return billDataDto;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

