package org.m2i.web;

import java.io.File;
import java.io.FileInputStream;


import org.apache.commons.io.IOUtils;
import org.m2i.dao.INoteRepository;
import org.m2i.entity.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class NoteController {

	@Autowired
	private INoteRepository iNoteRepository;
	@Value("${dir.images}")
	private String imageDir;

	@RequestMapping(value = "/note", method = RequestMethod.GET)
	public String formProduit(Model model) {
		return "notes";
	}

	@RequestMapping(name = "/index", method = { RequestMethod.GET, RequestMethod.POST })
	public String index(Model model, @RequestParam(name = "page", defaultValue = "0") int p,
			@RequestParam(name = "size", defaultValue = "6") int s,
			@RequestParam(name = "motCle", defaultValue = "") String mc) {
		Page<Note> page = iNoteRepository.cherche("%" + mc + "%", new PageRequest(p, s));
		int[] pages = new int[page.getTotalPages()];
		model.addAttribute("size", s);
		model.addAttribute("pagedef", p);
		model.addAttribute("pages", pages);
		model.addAttribute("notes", page);
		model.addAttribute("motCle", mc);
		return "allNote";
	}

//	@RequestMapping(value = "/index", method = { RequestMethod.GET, RequestMethod.POST })
//	public String getAll(Model model) {
//		List<Note> notes = iNoteRepository.findAll();
//		model.addAttribute("notes", notes);
//		return "allNote";
//	}

	@RequestMapping(value = "/saveNote", method = RequestMethod.POST)
	public String save(Model model, Note note, @RequestParam(name = "picture") MultipartFile file) throws Exception {

		if (!(file.isEmpty())) {
			note.setPhoto(file.getOriginalFilename());
		}
		iNoteRepository.save(note);
		if (!(file.isEmpty())) {
			note.setPhoto(file.getOriginalFilename());
			file.transferTo(new File(imageDir + note.getId()));
			model.addAttribute("file", file);


		}
		model.addAttribute("notes", note);
		return "details";
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Model model, Long id) {
		Note p = iNoteRepository.findById(id).get();
		System.out.println("$$$$$$$$$$$$$$$$$" + p.toString());
		model.addAttribute("note", p);
		return "edit";
	}

	@RequestMapping(value = "/showNote", method = RequestMethod.GET)
	public String show(Model model, Long id) {
		Note p = iNoteRepository.findById(id).get();
		System.out.println("$$$$$$$$$$$$$$$$$" + p.toString());
		model.addAttribute("notes", p);
		return "details";
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(Long id, String motCle, int page, int size) {
		iNoteRepository.deleteById(id);
		return "redirect:/index?page=" + page + "&size=" + size + "&page=" + page + "&motCle=" + motCle;

	}

	@RequestMapping(value ="/getp", produces = {MediaType.IMAGE_JPEG_VALUE,MediaType.APPLICATION_PDF_VALUE})
	@ResponseBody
	public byte[] getPIC(Long id,Model model ) throws Exception {
		File fil = new File(imageDir + id);
		return IOUtils.toByteArray(new FileInputStream(fil));

	}
	
	@RequestMapping(value = "/notepdf",method=RequestMethod.GET)
	public String getnotepdf() {
		return "allNotePdf";

	}
}
