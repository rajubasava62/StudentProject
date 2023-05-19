package com.tcs.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tcs.security.entity.Student;
@Repository
public interface InstRepository extends JpaRepository<Student, Long>{

}
