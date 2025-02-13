package com.boot.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.boot.board.BoardDao;
import com.boot.board.BoardDto;

@Controller
public class BoardController {

	@Autowired
	private BoardDao dao;

	@GetMapping("/list")
	public String list(Model model) {
		List<BoardDto> list = dao.findAllByOrderByMynoDesc();
		model.addAttribute("list", list);
		return "board/boardmain";
	}
	
	@GetMapping("/detail")
	public String detail(Model model, int myno) {
		model.addAttribute("dto", dao.findByMyno(myno));
		return "board/boarddetail";
	}
	
	@GetMapping("/insertform")
	public String insertForm() {
		return "board/boardinsert";
	}
	
	@PostMapping("/insertres")
	public String insertRes(BoardDto dto) {
		dto.setMydate(new Date());
		dao.save(dto);
		return "redirect:list";
	}
	
	@GetMapping("/updateform")
	public String updateForm(Model model, int myno) {
		model.addAttribute("dto", dao.findByMyno(myno));
		System.out.println("업데이트 초기값: "+dao.findByMyno(myno).toString());
		return "board/boardupdate";
	}
	
	@PostMapping("/updateres")
	public String updateRes(BoardDto dto) {
		dto.setMydate(new Date());
		System.out.println(dto.toString());
		BoardDto newdto = dao.findByMyno(dto.getMyno()); 
		System.out.println("updateres: "+newdto);
		newdto.update(dto.getMytitle(), dto.getMycontent());
		dao.save(newdto);
		return "redirect:detail?myno="+newdto.getMyno();
	}
	
	@GetMapping("/delete")
	public String delete(int myno) {
		dao.deleteByMyno(myno);
		return "redirect:list";
	}
	
}
