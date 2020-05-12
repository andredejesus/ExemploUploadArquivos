package com.upload.service;

import java.util.List;

import com.upload.entidade.UploadEntidade;

public interface UploadService {
	
	public void salvarUpload(UploadEntidade upload);
	
	public List<UploadEntidade> listarUploads();
	
	public UploadEntidade listarUploadPorId(Long id);
	
	public String criarCaminho();
	
	public void removerUpload(Long id);

}
