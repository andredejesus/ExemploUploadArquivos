package com.upload.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.upload.entidade.UploadEntidade;
import com.upload.service.UploadService;

@Controller
public class RemoverUploadController {

	@Autowired
	private UploadService uploadService;
	
	@RequestMapping(value = "/remover/file/{id}", method = {RequestMethod.GET, RequestMethod.DELETE})
	public String removerUpload(@PathVariable("id") Long id, RedirectAttributes attr) throws IOException {
		
		UploadEntidade uploadEntidade = uploadService.listarUploadPorId(id);
		
		if(uploadEntidade.getId() != null) {
			
			// Busco o arquivo no diretório
			Path path = Paths.get(uploadEntidade.getFilePath() + File.separator + uploadEntidade.getFileName());
			
			if(Files.deleteIfExists(path)) {
				uploadService.removerUpload(id);
				attr.addFlashAttribute("mensagemSucesso", "O arquivo " + uploadEntidade.getFileName() + " foi removido com sucesso! ");
			}
			
		}
		
		return "redirect:/listaUploads";
	}
	
}
