package com.jpinto.orchestator.client.talenthuman;

import com.jpinto.orchestator.client.talenthuman.dto.ResponseEmployee;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
public class TalentHumanRestClientService {
    private final RestClient talentHumanRestClient;

    public ResponseEmployee findById(@PathVariable Long id){

        return talentHumanRestClient.get().uri("/employee/by-id/{id}", id)
                .retrieve()
                .body(ResponseEmployee.class);
    }

    public ResponseEmployee findByUserId(@PathVariable Long id){
        return talentHumanRestClient.get().uri("/employee/by-user-id/{id}", id)
                .retrieve()
                .body(ResponseEmployee.class);
    }
}
