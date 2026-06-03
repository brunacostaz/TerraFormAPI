package br.com.fiap.terraform.controller;

import br.com.fiap.terraform.dto.OperationLogResponse;
import br.com.fiap.terraform.service.LogService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/logs")
public class LogController {

    private final LogService logService;

    public LogController(LogService logService) {
        this.logService = logService;
    }

    @GetMapping
    public List<OperationLogResponse> findLogs(
            @RequestParam(required = false) Long greenhouseId,
            @RequestParam(required = false) String planetCode) {
        return logService.findLogs(greenhouseId, planetCode);
    }
}

