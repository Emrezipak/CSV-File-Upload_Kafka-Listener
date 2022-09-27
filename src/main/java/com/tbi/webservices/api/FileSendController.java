package com.tbi.webservices.api;

import com.tbi.webservices.payload.response.ResponseMessage;
import com.tbi.webservices.service.SendRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class FileSendController {

    private final SendRequestService sendRequestService;

    @GetMapping("/callService")
    public ResponseEntity<ResponseMessage> getStatusFromService(@RequestParam(name = "distributionId") String distributionId){
        return ResponseEntity.ok(sendRequestService.callService(distributionId));
    }

}
