// Archivo: src/main/java/com/franquicias/repository/FranquiciaRepository.java
package com.franquicias.repository;

import com.franquicias.model.Franquicia;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FranquiciaRepository extends MongoRepository<Franquicia, String> {
}