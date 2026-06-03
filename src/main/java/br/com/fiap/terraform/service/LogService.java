package br.com.fiap.terraform.service;

import br.com.fiap.terraform.dto.OperationLogResponse;
import br.com.fiap.terraform.repository.OperationLogRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class LogService {

    private final OperationLogRepository operationLogRepository;
    private final TerraFormMapper mapper;

    public LogService(OperationLogRepository operationLogRepository, TerraFormMapper mapper) {
        this.operationLogRepository = operationLogRepository;
        this.mapper = mapper;
    }

    public List<OperationLogResponse> findLogs(Long greenhouseId, String planetCode) {
        if (greenhouseId != null) {
            return operationLogRepository.findTop100ByGreenhouseIdOrderByCreatedAtDesc(greenhouseId)
                    .stream()
                    .map(mapper::toOperationLogResponse)
                    .toList();
        }

        if (planetCode != null && !planetCode.isBlank()) {
            return operationLogRepository.findTop100ByGreenhousePlanetCodeOrderByCreatedAtDesc(planetCode)
                    .stream()
                    .map(mapper::toOperationLogResponse)
                    .toList();
        }

        return operationLogRepository.findTop100ByOrderByCreatedAtDesc()
                .stream()
                .map(mapper::toOperationLogResponse)
                .toList();
    }
}

