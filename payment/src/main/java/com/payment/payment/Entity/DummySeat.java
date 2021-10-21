package com.payment.payment.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
//@Data
@AllArgsConstructor
@NoArgsConstructor
public class DummySeat {
    
    public int trainId;
    public String trainName;
    public int row[];
    public int column[];
    public String source;
    public String destination;
    public int fare;
    public int customerId;
    
    public int getTrainId() {
        return trainId;
    }
    public void setTrainId(int trainId) {
        this.trainId = trainId;
    }
    public String getTrainName() {
        return trainName;
    }
    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }
    public int[] getRow() {
        return row;
    }
    public void setRow(int[] row) {
        this.row = row;
    }
    public int[] getColumn() {
        return column;
    }
    public void setColumn(int[] column) {
        this.column = column;
    }
    public String getSource() {
        return source;
    }
    public void setSource(String source) {
        this.source = source;
    }
    public String getDestination() {
        return destination;
    }
    public void setDestination(String destination) {
        this.destination = destination;
    }
    public int getFare() {
        return fare;
    }
    public void setFare(int fare) {
        this.fare = fare;
    }
    public int getCustomerId() {
        return customerId;
    }
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }


    
}
