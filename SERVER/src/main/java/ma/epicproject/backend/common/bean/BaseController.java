package ma.epicproject.backend.common.bean;


import ma.epicproject.backend.common.exception.GlobalException;
import ma.epicproject.backend.common.util.FileUtils;
import ma.epicproject.backend.common.util.Utils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;


public abstract class BaseController {

	@Autowired
	private MessageSource messageSource;
	@Value("${uploads.location.download}")
	private String EXPORT_FOLDER;

	@ExceptionHandler
	public ResponseEntity<ErrorResponse> exceptionHandler(Exception e, HttpServletRequest request) throws IOException {
		GlobalException globalException = new GlobalException(e, messageSource, request.getRequestURI());

		ErrorResponse errorResponse = new ErrorResponse(globalException.getStatus(), e, globalException.getMessage(), request.getRequestURI());
		return new ResponseEntity<>(errorResponse, globalException.getStatus());
	}
	// Download file
	protected ResponseEntity<InputStreamResource> getExportedFileResource(ExportModel exportModel) throws Exception {

		if (exportModel != null && exportModel.getList() != null && !exportModel.getList().isEmpty()) {
			// Vérifiez si le dossier EXPORT_FOLDER existe, sinon créez-le
			File exportFolder = new File(EXPORT_FOLDER);
			if (!exportFolder.exists()) {
				boolean folderCreated = exportFolder.mkdirs(); // Crée tous les dossiers nécessaires
				if (!folderCreated) {
					throw new IOException("Impossible de créer le dossier d'exportation : " + EXPORT_FOLDER);
				}
			}
			String fichier = Utils.exportedList(exportModel, EXPORT_FOLDER);
			File file = new File(fichier);
			FileInputStream inputStream = new FileInputStream(file);
			InputStreamResource inputStreamResource = new InputStreamResource(inputStream);
			String fileName = FileUtils.getFileName(file.getName());
			return ResponseEntity.ok().eTag(fileName).contentLength(file.length()).contentType(MediaType.parseMediaType(Files.probeContentType(file.toPath()))).body(inputStreamResource);

		}

		return new ResponseEntity(HttpStatus.NOT_FOUND);
	}



}