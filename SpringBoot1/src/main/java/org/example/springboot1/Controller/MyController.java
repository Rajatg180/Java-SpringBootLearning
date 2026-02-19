package org.example.springboot1.Controller;

import org.example.springboot1.DTO.RequestDTO;
import org.example.springboot1.DTO.ResponseDTO;
import org.example.springboot1.Service.PaymentService;
import org.example.springboot1.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping( value = "/payments")
public class MyController {

    @Autowired
    private PaymentService paymentService;

    // path variable
    @GetMapping("/{Id}")
    public ResponseDTO data(@PathVariable(name = "Id") Long id) {
        RequestDTO requestDTO = new RequestDTO();
        requestDTO.setId(id);
        return paymentService.getPaymentDetailsById(requestDTO);
    }

    // request param
    @GetMapping("/params")
    public String data1(
            @RequestParam( name = "userName") String username,
            @RequestParam( name = "id", required = false) int id
    ) {
        return username;
    }

    // post mapping
    // body will get mapped with User class (java object)
    @PostMapping(path = "/saveuser")
    public String saveUser(@RequestBody User user){
        return "User saved successfully";
    }


    // when we need to pass body , status code , and data
    @GetMapping(path = "/dummy")
    public ResponseEntity<String> dummyMethod(){
        String name = "Hello rajat gore";
        return ResponseEntity.status(200).body(name);
    }
}
