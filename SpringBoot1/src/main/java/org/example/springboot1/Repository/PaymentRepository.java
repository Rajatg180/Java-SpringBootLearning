package org.example.springboot1.Repository;

import org.example.springboot1.DTO.RequestDTO;
import org.example.springboot1.Entity.PaymentEntity;
import org.springframework.stereotype.Repository;

@Repository
public class PaymentRepository {

    public PaymentEntity getPaymentById(RequestDTO requestDTO){
        PaymentEntity paymentEntity = executeQuery(requestDTO);
        return paymentEntity;
    }

    private  PaymentEntity executeQuery(RequestDTO requestDTO){
        PaymentEntity paymentEntity = new PaymentEntity();
        // mapping request dto with entity ( connect with db and fetch data)
        paymentEntity.setId(requestDTO.getId());
        paymentEntity.setCurrency("INR");
        paymentEntity.setName("RajatGore");
        return  paymentEntity;
    }
}
