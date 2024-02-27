package com.spring.ocr.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import java.io.File;
import java.io.IOException;

public class OcrServiceNanonets {
    public static void main(String[] args) {
        String apiKey = "19b53ba1-d4eb-11ee-9d87-725751de663d";
        String modelId = "c846b738-7fda-4d92-89a1-0ebc4166a62a"; // Optional, use only if you have a custom model
        String filePath = "C:\\Users\\garvi\\OneDrive\\Pictures\\Screenshots\\ebill.png";
// or
//        String filePath = "C:\\Users\\garvi\\Downloads\\N3531046505 (21).pdf";

        OcrServiceNanonets OCRServiceNanonets = new OcrServiceNanonets(apiKey, modelId);
        String extractedText = OCRServiceNanonets.performOCR(filePath);
//        String jsonResponse = "{\"message\":\"Success\",\"result\":[{\"message\":\"Success\",\"input\":\"image.png\",\"prediction\":[{\"id\":\"acf04516-a322-49b3-b149-f12f934e840a\",\"label\":\"seller_address\",\"xmin\":189,\"ymin\":34,\"xmax\":422,\"ymax\":46,\"score\":0.58660215,\"ocr_text\":\"G.P.H. Compound , Pologround , Indore ( M.P. )\",\"type\":\"field\",\"status\":\"correctly_predicted\",\"page_no\":0,\"label_id\":\"2b60f581-9f6d-4444-87ef-ce8d959c8a7a\"},{\"id\":\"a3de5f94-7e2a-4062-b7b2-a32b4ba8b351\"}] } ]}";

        parseJsonResponse(extractedText);
//        System.out.println("Extracted Text:\n" + extractedText);
    }

    private final String apiKey;
    private final String modelId;

    public OcrServiceNanonets(String apiKey, String modelId) {
        this.apiKey = apiKey;
        this.modelId = modelId;
    }

    public String performOCR(String filePath) {
        OkHttpClient client = new OkHttpClient();

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", "file.png",
                        RequestBody.create(MediaType.parse("application/octet-stream"), new File(filePath)))
                .build();

        Request request = new Request.Builder()
                .url("https://app.nanonets.com/api/v2/OCR/Model/c846b738-7fda-4d92-89a1-0ebc4166a62a/LabelFile/?async=false")
                .post(requestBody)
                .addHeader("Authorization", Credentials.basic("19b53ba1-d4eb-11ee-9d87-725751de663d", ""))
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
    public static void parseJsonResponse(String jsonResponse) {
        try {
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

                                System.out.println("Label: " + label + ", Value: " + ocrText);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

