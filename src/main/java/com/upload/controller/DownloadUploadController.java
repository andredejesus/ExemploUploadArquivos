package com.upload.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.upload.entidade.UploadEntidade;
import com.upload.service.UploadService;

@RestController
@Controller
public class DownloadUploadController {

	@Autowired
	private UploadService uploadService;
	
	// Lista todos os arquivos retornados da banco
	@RequestMapping(value = "/listaUploads", method = RequestMethod.GET)
	public ModelAndView listaUploads() {
		
		ModelAndView mv = new ModelAndView("listaUploads");
		mv.addObject("uploads", uploadService.listarUploads());
		return mv;
	}
	
	// Apresenta os arquivos na view e disponibiliza para download
	@RequestMapping(value = "download/file/{id}/{fileName}", method = RequestMethod.GET)
	public ResponseEntity<byte[]> download(@PathVariable("id") Long id, @PathVariable("fileName") String fileName){
		
		BufferedInputStream inputStream = null;
		
		HttpHeaders headers = new HttpHeaders();
		
		UploadEntidade uploadEntidade = uploadService.listarUploadPorId(id);
		
		String dir = uploadEntidade.getFilePath();
		String name = uploadEntidade.getFileName();
		String type = uploadEntidade.getFileType();
		
		try {
		
		// Faço a união do meu diretório retornado com o nome do arquivo retornado
		// Recebo o arquivo vindo do meu diretório
		Path path = Paths.get(dir + File.separator + name);
		
		String temporario = path.getFileName().toString();
		
		// Verifica se o nome que está no diretório é igual ao nome retornado do banco
		if(temporario.equalsIgnoreCase(name)) {
			
			// Recebe todas as informações do arquivo e torna ele um inputStream
			inputStream = new BufferedInputStream(new FileInputStream(path.toFile()));
			
			// Insiro um cabeçalho ao arquivo para ser apresentado
			headers.setContentType(MediaType.valueOf(type));
			
			// Pega o inputStream e joga ele em um array de bytes com o cabeçalho
			return new ResponseEntity<byte[]>(IOUtils.toByteArray(inputStream), headers, HttpStatus.OK);
		}
		
		}catch (IOException e) {
           System.out.println(e.getMessage());
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
            	System.out.println(e.getMessage());
            }
        }
		
		return null;
	}
	
}
