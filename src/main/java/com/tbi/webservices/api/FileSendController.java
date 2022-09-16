package com.tbi.webservices.api;

import com.tbi.webservices.service.FileSendService;
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

    private final FileSendService fileSendService;

    @GetMapping("/callService")
    public ResponseEntity<?> getStatusFromService(@RequestParam(name = "distributionId") String distributionId
    ,@RequestParam(name = "fileName") String fileName){
        return ResponseEntity.ok(fileSendService.callService(distributionId,fileName));
    }

}
