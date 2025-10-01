package com.becoder.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import com.becoder.dto.NotesDto;
import com.becoder.dto.NotesDto.CategoryDto;
import com.becoder.entity.FileDetails;
import com.becoder.entity.Notes;
import com.becoder.exception.ResourceNotFoundException;
import com.becoder.repository.CategoryRepository;
import com.becoder.repository.FileDetailsRepository;
import com.becoder.repository.NotesRepository;
import com.becoder.service.NotesService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class NotesServiceImpl implements NotesService {

	@Autowired
	private NotesRepository notesRepo;

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private CategoryRepository categoryRepo;

	@Autowired
	private FileDetailsRepository fileRepo;

	@Value("${file.upload.path}")
	private String uploadpath;

	@Override
	public Boolean saveNotes(String notes, MultipartFile file) throws Exception {

		ObjectMapper ob = new ObjectMapper();
		NotesDto notesDto = ob.readValue(notes, NotesDto.class);

		// validation notes
		checkCategoryExist(notesDto.getCategory());
		Notes notesMap = mapper.map(notesDto, Notes.class);

		// multipartFile file save
		FileDetails fileDetails = saveFileDetails(file);

		if (!ObjectUtils.isEmpty(fileDetails)) {
			notesMap.setFileDetails(fileDetails);
		} else {
			notesMap.setFileDetails(null);
		}

		Notes saveNotes = notesRepo.save(notesMap);

		if (!ObjectUtils.isEmpty(saveNotes)) {
			return true;
		}
		return false;
	}

	private FileDetails saveFileDetails(MultipartFile file) throws IOException {

		if (!ObjectUtils.isEmpty(file) && !file.isEmpty()) {

			String originalFileName = file.getOriginalFilename();
			String extension = FilenameUtils.getExtension(originalFileName);
			List<String> extensionAllow = Arrays.asList("pdf", "xlsx", "jpg", "png");
			if (!extensionAllow.contains(extension)) {
				throw new IllegalArgumentException("invalid file format! Upload only .pdf, .xlsx, .jpg, .png");
			}

			String randomString = UUID.randomUUID().toString();

			String uploadFileName = randomString + "." + extension;

			// declare path
			File saveFile = new File(uploadpath);
			if (!saveFile.exists()) {
				saveFile.mkdir();
			}

			// path: enotes-api-service/notes/java.pdf
			String storePath = uploadpath.concat(uploadFileName);

			// upload file
			long upload = Files.copy(file.getInputStream(), Paths.get(storePath));
			if (upload != 0) {
				FileDetails fileDetails = new FileDetails();
				fileDetails.setFileSize(file.getSize());
				fileDetails.setUploadFileName(uploadFileName);
				fileDetails.setOriginalFileName(originalFileName);
				fileDetails.setDisplayFileName(getDisplayName(originalFileName));
				fileDetails.setPath(storePath);
				FileDetails saveFileDetails = fileRepo.save(fileDetails);
				return saveFileDetails;
			}

		}
		return null;
	}

	private String getDisplayName(String originalFileName) {
		// java_programming_tutorial.pdf
		String extension = FilenameUtils.getExtension(originalFileName);
		String fileName = FilenameUtils.removeExtension(originalFileName);

		if (fileName.length() > 8) {
			fileName = fileName.substring(0, 7);
		}
		fileName = fileName + "." + extension;
		return fileName;
	}

	private void checkCategoryExist(CategoryDto category) throws Exception {
		categoryRepo.findById(category.getId()).orElseThrow(() -> new ResourceNotFoundException("Category id invalid"));
	}

	@Override
	public List<NotesDto> getAllNotes() {
		return notesRepo.findAll().stream().map(note -> mapper.map(note, NotesDto.class)).toList();
	}

	@Override
	public byte[] downloadFile(FileDetails fileDetails) throws Exception {
		InputStream io = new FileInputStream(fileDetails.getPath());
		return StreamUtils.copyToByteArray(io);
	}

	@Override
	public FileDetails getFileDetails(Integer id) throws Exception {
		FileDetails fileDetails = fileRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("File is not available."));
		return fileDetails;
	}

}
