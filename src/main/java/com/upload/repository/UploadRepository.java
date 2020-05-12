package com.upload.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.upload.entidade.UploadEntidade;

@Repository
public interface UploadRepository extends JpaRepository<UploadEntidade, Long> {

}
