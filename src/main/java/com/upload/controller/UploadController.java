package com.upload.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.upload.entidade.UploadEntidade;
import com.upload.service.UploadService;

@Controller
public class UploadController {
	
	@Autowired
	private UploadService uploadService;
    
    @RequestMapping(value = {"", "/", "/uploadFile"})
    public String uploadFile() {
    	return "uploadFile";
    }
    
    @RequestMapping(value = "/upload/{file}", method = RequestMethod.POST)
    public String uploadArquivo(@PathVariable("file") MultipartFile file, RedirectAttributes attr) {
    	
    	UploadEntidade upload = new UploadEntidade();
    	
    	upload.setFileName(file.getOriginalFilename());
    	upload.setFileType(file.getContentType());
    	upload.setFileSize(file.getSize());
    	
    	try {
    		// Recebe um array de bytes do arquivo que foi feito o upload
			byte[] bytes = file.getBytes();
			
			// Recebo o caminho onde ficará o arquivo
			upload.setFilePath(uploadService.criarCaminho());
			
			File dir = new File(upload.getFilePath());
			
			// Verifica se o diretório existe, se não existir, é criado o diretório
			if(!dir.exists()) {
				dir.mkdirs();
			}
			
			// Pego o caminho existente e o nome do arquivo
			File serverFile = new File(dir.getAbsolutePath() + File.separator + upload.getFileName());
			
			//Verifica se o arquivo existe, se não existir, é adicionado o arquivo no diretório
			if(!serverFile.exists()) {
				
				// Adiciona o arquivo no diretório
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();
				
				// Salva os dados do arquivo no banco
				uploadService.salvarUpload(upload);
				
				attr.addFlashAttribute("mensagemSucesso", "O upload do arquivo " + upload.getFileName() + " foi realizado com sucesso");
				
 			}else {
 				attr.addFlashAttribute("mensagemAlerta", "Já foi realizado o upload de arquivo com esse nome: " + upload.getFileName());
 			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	ModelAndView mv = new ModelAndView("uploadFile");
    	
    	return "redirect:/uploadFile";
    }

}
