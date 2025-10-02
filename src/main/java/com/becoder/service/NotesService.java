package com.becoder.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.becoder.dto.FavouriteNoteDto;
import com.becoder.dto.NotesDto;
import com.becoder.dto.NotesResponse;
import com.becoder.entity.FileDetails;

public interface NotesService {

	public Boolean saveNotes(String notes, MultipartFile file) throws Exception;

	public List<NotesDto> getAllNotes();

	public byte[] downloadFile(FileDetails fileDetails) throws Exception;

	public FileDetails getFileDetails(Integer id) throws Exception;

	public NotesResponse getAllNotesByUser(Integer userId, Integer pageNo, Integer pageSize);

	public void softDelete(Integer id) throws Exception;

	public void restoreNotes(Integer id) throws Exception;

	public List<NotesDto> getUserRecycleBinNotes(Integer userId);

	public void hardDeleteNotes(Integer id) throws Exception;

	public void emptyRecycleBin(Integer userId);

	public void favotriteNotes(Integer noteId) throws Exception;

	public void unFavouriteNotes(Integer favouriteNoteId) throws Exception;

	public List<FavouriteNoteDto> getUserFavouriteNotes();

	public Boolean copyNotes(Integer id) throws Exception;

}
