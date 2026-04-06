package com.jpinto.refundquery.infraestructure.repository;

import com.jpinto.refundquery.infraestructure.document.RefundOrderDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;


public interface RefundOrderRepository extends MongoRepository<RefundOrderDocument, String> {

    Optional<RefundOrderDocument> findByPayment_Id(String id);
}
