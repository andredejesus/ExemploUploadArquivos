package com.upload.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.upload.entidade.UploadEntidade;
import com.upload.repository.UploadRepository;

@Service
public class UploadServiceImpl implements UploadService {

	@Autowired
	UploadRepository ur;

	@Override
	public void salvarUpload(UploadEntidade upload) {
		ur.save(upload);
		
	}

	@Override
	public List<UploadEntidade> listarUploads() {
		return ur.findAll();
	}

	@Override
	public UploadEntidade listarUploadPorId(Long id) {
		return ur.findById(id).get();
	}

	@Override
	public String criarCaminho() {
		
		LocalDate localDate = LocalDate.now();
		
		String mm = String.valueOf(localDate.getMonthValue());
		String yyyy = String.valueOf(localDate.getYear());
		
		return "C:\\uploads" + "\\" + yyyy + "\\" + mm;
	}

	@Override
	public void removerUpload(Long id) {
		ur.deleteById(id);
	}
	

	
	
	
	
	
}
