package com.example.codeball.repositories;

import com.example.codeball.models.Enrollment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface EnrollmentRepository extends CrudRepository<Enrollment, Integer> {

}
