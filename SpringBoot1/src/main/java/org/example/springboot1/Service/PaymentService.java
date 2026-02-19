package org.example.springboot1.Service;

import org.example.springboot1.DTO.RequestDTO;
import org.example.springboot1.DTO.ResponseDTO;
import org.example.springboot1.Entity.PaymentEntity;
import org.example.springboot1.Repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    @Autowired
    PaymentRepository paymentRepository;

    public ResponseDTO getPaymentDetailsById(RequestDTO requestDTO){
        PaymentEntity paymentEntity = paymentRepository.getPaymentById(requestDTO);

        // map entity to response dto
        ResponseDTO responseDTO = mappEntityWithRespons(paymentEntity);
        return  responseDTO;
    }

    private  ResponseDTO mappEntityWithRespons(PaymentEntity paymentEntity){
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setId(paymentEntity.getId());
        responseDTO.setCurr(paymentEntity.getCurrency());
        responseDTO.setName(paymentEntity.getName());
        return  responseDTO;
    }
}
