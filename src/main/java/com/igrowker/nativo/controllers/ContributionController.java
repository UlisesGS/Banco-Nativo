package com.igrowker.nativo.controllers;

import com.igrowker.nativo.dtos.contribution.ResponseContributionGetDto;
import com.igrowker.nativo.services.ContributionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/contribuciones")
public class ContributionController {
    private final ContributionService contributionService;

    @GetMapping("/{id}")
    public ResponseEntity<ResponseContributionGetDto> getOneContribution(@PathVariable String id) {
        ResponseContributionGetDto response = contributionService.getOneContribution(id);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<ResponseContributionGetDto>> getAll() {
        List<ResponseContributionGetDto> response = contributionService.getAll();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/transaction-status/{status}")
    public ResponseEntity<List<ResponseContributionGetDto>> getContributionsByTransactionStatus(@PathVariable String status) {
        List<ResponseContributionGetDto> response = contributionService.getContributionsByTransactionStatus(status);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
