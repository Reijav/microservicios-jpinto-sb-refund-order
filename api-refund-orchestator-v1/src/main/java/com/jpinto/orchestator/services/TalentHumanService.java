package com.jpinto.orchestator.services;

import com.jpinto.orchestator.client.talenthuman.TalentHumanRestClientService;
import com.jpinto.orchestator.client.talenthuman.dto.ResponseEmployee;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

@Service
@RequiredArgsConstructor
public class TalentHumanService {
    private final TalentHumanRestClientService talentHumanRestClientService;

    public ResponseEmployee findById(Long id) {
        return talentHumanRestClientService.findById(id);
    }

    public ResponseEmployee findByUserId(Long id) {
        return talentHumanRestClientService.findByUserId(id);
    }
}
