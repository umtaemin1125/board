package com.study.board.controller;

import com.study.board.entity.Board;
import com.study.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;

    @GetMapping("/board/write") //localhost:8080/board/write 로 접속했을때 boardwrite 파일을 보여주는 맵핑
    public String boardWriteForm(){
        return "boardwrite";
    }

    @PostMapping("/board/writepro")
     public String boardWritepro(Board board, Model model, MultipartFile file) throws Exception{

        boardService.write(board, file);

        model.addAttribute("message","글 작성이 완료되었습니다.");
        model.addAttribute("searchUrl","/board/list");
        return "message";
     }

     @GetMapping("/board/list")  // BoardList 페이징 처리 (JPA), 페이징 블럭
    public String boardList(Model model,@PageableDefault(page = 0, size = 10, sort ="id", direction = Sort.Direction.DESC) Pageable pageable){

         Page<Board> list= boardService.boardList(pageable);

         int nowPage = list.getPageable().getPageNumber() +1; // 페이지가 0부터 시작하기때문
         int startPage = Math.max(nowPage -4, 1) ;
         int endPage = Math.min(nowPage +5,list.getTotalPages()) ;

        model.addAttribute("list",list);
        model.addAttribute("nowPage", nowPage); //model에 담아 넘겨줌
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "boardlist";
     }

     @GetMapping("/board/view") //localhost:8080/board/view?id=1 get으로 넘겨주는 방식임
    public String boardView(Model model,Integer id){

        model.addAttribute("board",boardService.boardview(id));

        return "boardview";
     }

     @GetMapping("/board/delete")
    public String boardDelete(Integer id){

        boardService.boardDelete(id);
        return "redirect:/board/list";
     }

     @GetMapping("/board/modify/{id}")
        public String boardModify(@PathVariable("id") Integer id, Model model){


         model.addAttribute("board", boardService.boardview(id));
        return "boardmodify";
     }

    @PostMapping("/board/update/{id}")
    public String boardUpdate(@PathVariable("id") Integer id, Board board, Model model, MultipartFile file) throws Exception{

        Board boardTemp = boardService.boardview(id);
        boardTemp.setTitle(board.getTitle());
        boardTemp.setContent(board.getContent());

        boardService.write(boardTemp, file);

        model.addAttribute("message","글 수정이 완료되었습니다.");
        model.addAttribute("searchUrl","/board/list");

        return "message";
    }
}
