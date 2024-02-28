package com.spring.ocr.dto;

public class BillDataDto {
    private String billNumber;
    private Double noOfUnits;   // electricity units used
    private String billAmount;
    private String currency;
    private String billDate;
    private String provider;
    private String providerAddress;
    private String consumerName;
    private String consumerAddress;

    public String getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
    }

    public Double getNoOfUnits() {
        return noOfUnits;
    }

    public void setNoOfUnits(Double noOfUnits) {
        this.noOfUnits = noOfUnits;
    }

    public String getBillAmount() {
        return billAmount;
    }

    public void setBillAmount(String billAmount) {
        this.billAmount = billAmount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getBillDate() {
        return billDate;
    }

    public void setBillDate(String billDate) {
        this.billDate = billDate;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getConsumerName() {
        return consumerName;
    }

    public void setConsumerName(String consumerName) {
        this.consumerName = consumerName;
    }

    public String getConsumerAddress() {
        return consumerAddress;
    }

    public void setConsumerAddress(String consumerAddress) {
        this.consumerAddress = consumerAddress;
    }

    public String getProviderAddress() {
        return providerAddress;
    }

    public void setProviderAddress(String providerAddress) {
        this.providerAddress = providerAddress;
    }

}
