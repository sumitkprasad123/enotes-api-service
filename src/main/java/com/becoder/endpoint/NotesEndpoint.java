package com.becoder.endpoint;

import static com.becoder.util.Constants.DEFAULT_PAGE_NO;
import static com.becoder.util.Constants.DEFAULT_PAGE_SIZE;
import static com.becoder.util.Constants.ROLE_ADMIN;
import static com.becoder.util.Constants.ROLE_ADMIN_USER;
import static com.becoder.util.Constants.ROLE_USER;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.becoder.dto.NotesRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Notes", description = "All the notes related operation")
@RequestMapping("/api/v1/notes")
public interface NotesEndpoint {

	@Operation(summary = "save notes", tags = { "Notes", "User" }, description = "User can save own notes")
	@PostMapping(value = "/", consumes = "multipart/form-data")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> saveNotes(
			@RequestParam @Parameter(description = "Json String Notes", required = true, content = @Content(schema = @Schema(implementation = NotesRequest.class))) String notes,
			@RequestParam(required = false) MultipartFile file) throws Exception;

	@Operation(summary = "get all notes", tags = { "Notes" }, description = "get all notes")
	@GetMapping("/")
	@PreAuthorize(ROLE_ADMIN)
	public ResponseEntity<?> getAllNotes();

	@Operation(summary = "download uploaded file", tags = { "Notes",
			"User" }, description = "User can download uploaded file in notes")
	@GetMapping("/download/{id}")
	@PreAuthorize(ROLE_ADMIN_USER)
	public ResponseEntity<?> downloadFile(@PathVariable Integer id) throws Exception;

	@Operation(summary = "get all notes  by user", tags = { "Notes",
			"User" }, description = "User can get all own notes with pagination")
	@GetMapping("/user-notes")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> getAllNotesByUser(
			@RequestParam(name = "pageNo", defaultValue = DEFAULT_PAGE_NO) Integer pageNo,
			@RequestParam(name = "pageSize", defaultValue = DEFAULT_PAGE_SIZE) Integer pageSize);

	@Operation(summary = "search notes", tags = { "Notes",
			"User" }, description = "User can search own notes by keyword with pagination.")
	@GetMapping("/search")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> getNotesByUserSearch(@RequestParam(name = "key", defaultValue = "") String key,
			@RequestParam(name = "pageNo", defaultValue = DEFAULT_PAGE_NO) Integer pageNo,
			@RequestParam(name = "pageSize", defaultValue = DEFAULT_PAGE_SIZE) Integer pageSize);

	@Operation(summary = "delete notes (soft delete)", tags = { "Notes",
			"User" }, description = "User can delete own notes which is not delete permanantely")
	@GetMapping("/delete/{id}")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> deleteNotes(@PathVariable Integer id) throws Exception;

	@Operation(summary = "restore notes", tags = { "Notes",
			"User" }, description = "User can restore notes which he deleted")
	@GetMapping("/restore/{id}")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> restoreNotes(@PathVariable Integer id) throws Exception;

	@Operation(summary = "delete the notes", tags = { "Notes",
			"User" }, description = "User can get all its notes which is soft deleted.")
	@GetMapping("/recycle-bin")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> getUserRecycleBinNotes() throws Exception;

	@Operation(summary = "hard delete note", tags = { "Notes",
			"User" }, description = "User can be able to hard delete own notes")
	@DeleteMapping("/delete/{id}")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> hardDeleteNotes(@PathVariable Integer id) throws Exception;

	@Operation(summary = "delete all notes from recycle bin", tags = { "Notes",
			"User" }, description = "User delete own notes permanantely which is in recycle bin")
	@DeleteMapping("/delete")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> emptyUserRecycleBin();

	@Operation(summary = "favourite notes", tags = { "Notes",
			"User" }, description = "User can save own notes as a favourite")
	@GetMapping("/fav/{noteId}")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> favouriteNotes(@PathVariable Integer noteId) throws Exception;

	@Operation(summary = "un favourite notes", tags = { "Notes",
			"User" }, description = "User can remove the notes from favourite list")
	@GetMapping("/un-fav/{favNoteId}")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> unFavouriteNotes(@PathVariable Integer favNoteId) throws Exception;

	@Operation(summary = "get all favourite notes", tags = { "Notes",
			"User" }, description = "User can get all its favourite notes")
	@GetMapping("/fav-note")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> getUserFavouriteNote();

	@Operation(summary = "copy notes", tags = { "Notes", "User" }, description = "User can copy the notes")
	@GetMapping("/copy/{id}")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> copyNotes(@PathVariable Integer id) throws Exception;

}
