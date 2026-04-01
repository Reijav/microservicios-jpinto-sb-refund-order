package com.jpinto.refundquery.infraestructure.repository;

import com.jpinto.refundquery.infraestructure.document.RefundOrderDocument;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface RefundOrderRepository extends MongoRepository<RefundOrderDocument, String> {
}
