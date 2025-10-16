package com.becoder.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.becoder.dto.FavouriteNoteDto;
import com.becoder.dto.NotesDto;
import com.becoder.dto.NotesResponse;
import com.becoder.endpoint.NotesEndpoint;
import com.becoder.entity.FileDetails;
import com.becoder.service.NotesService;
import com.becoder.util.CommonUtil;

@RestController
public class NotesController implements NotesEndpoint {

	@Autowired
	private NotesService notesService;

	@Override
	public ResponseEntity<?> saveNotes(String notes, MultipartFile file) throws Exception {
		Boolean saveNotes = notesService.saveNotes(notes, file);
		if (saveNotes) {
			return CommonUtil.createBuildResponseMessage("Notes saved success", HttpStatus.CREATED);
		}

		return CommonUtil.createErrorResponseMessage("Notes not saved", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<?> getAllNotes() {
		List<NotesDto> notes = notesService.getAllNotes();
		if (CollectionUtils.isEmpty(notes)) {
			return ResponseEntity.noContent().build();
		}
		return CommonUtil.createBuildResponse(notes, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> downloadFile(Integer id) throws Exception {

		FileDetails fileDetails = notesService.getFileDetails(id);
		byte[] data = notesService.downloadFile(fileDetails);

		HttpHeaders headers = new HttpHeaders();
		String contentType = CommonUtil.getContentType(fileDetails.getOriginalFileName());
		headers.setContentType(MediaType.parseMediaType(contentType));
		headers.setContentDispositionFormData("attachment", fileDetails.getOriginalFileName());

		return ResponseEntity.ok().headers(headers).body(data);
	}

	@Override
	public ResponseEntity<?> getAllNotesByUser(Integer pageNo, Integer pageSize) {

		NotesResponse notes = notesService.getAllNotesByUser(pageNo, pageSize);
		return CommonUtil.createBuildResponse(notes, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> getNotesByUserSearch(String key, Integer pageNo, Integer pageSize) {

		NotesResponse notes = notesService.getNotesByUserSearch(pageNo, pageSize, key);
		return CommonUtil.createBuildResponse(notes, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> deleteNotes(Integer id) throws Exception {

		notesService.softDelete(id);
		return CommonUtil.createBuildResponseMessage("Delete Success", HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> restoreNotes(Integer id) throws Exception {
		notesService.restoreNotes(id);
		return CommonUtil.createBuildResponseMessage("Notes restore success.", HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> getUserRecycleBinNotes() throws Exception {
		List<NotesDto> notes = notesService.getUserRecycleBinNotes();
		if (CollectionUtils.isEmpty(notes)) {
			return CommonUtil.createBuildResponseMessage("Notes not available in recycle bin.", HttpStatus.NOT_FOUND);
		}
		return CommonUtil.createBuildResponse(notes, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> hardDeleteNotes(Integer id) throws Exception {

		notesService.hardDeleteNotes(id);
		return CommonUtil.createBuildResponseMessage("Delete success.", HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> emptyUserRecycleBin() {
		notesService.emptyRecycleBin();
		return CommonUtil.createBuildResponseMessage("Delete all Notes from Recycle bin.", HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> favouriteNotes(Integer noteId) throws Exception {
		notesService.favotriteNotes(noteId);
		return CommonUtil.createBuildResponseMessage("Notes added favourite", HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<?> unFavouriteNotes(Integer favNoteId) throws Exception {
		notesService.unFavouriteNotes(favNoteId);
		return CommonUtil.createBuildResponseMessage("Removed favourite.", HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> getUserFavouriteNote() {
		List<FavouriteNoteDto> notes = notesService.getUserFavouriteNotes();
		if (CollectionUtils.isEmpty(notes)) {
			return ResponseEntity.noContent().build();
		}
		return CommonUtil.createBuildResponse(notes, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> copyNotes(Integer id) throws Exception {
		Boolean copyNotes = notesService.copyNotes(id);
		if (copyNotes) {
			return CommonUtil.createErrorResponseMessage("Notes Copy Success.", HttpStatus.CREATED);
		}
		return CommonUtil.createBuildResponseMessage("Copy Faild ! Try Again.", HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
