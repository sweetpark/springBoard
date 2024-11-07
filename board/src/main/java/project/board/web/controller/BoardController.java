package project.board.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;
import project.board.domain.dto.Board;
import project.board.domain.dto.BoardForm;
import project.board.domain.dto.Member;
import project.board.domain.dto.UploadFile;
import project.board.domain.repository.boardRepository.BoardRepository;
import project.board.domain.repository.boardRepository.fileStore.FileStore;
import project.board.domain.repository.memberRepository.MemberRepository;
import project.board.web.service.BoardService;


import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.List;


@Slf4j
@Controller
@RequestMapping(value="/members/{loginId}/boards")
public class BoardController {

    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final BoardService boardService;
    private final FileStore fileStore;

    public BoardController(MemberRepository memberRepository, BoardRepository boardRepository, BoardService boardService, FileStore fileStore){
        this.memberRepository = memberRepository;
        this.boardRepository = boardRepository;
        this.boardService = boardService;
        this.fileStore = fileStore;
    }

    @GetMapping
    public String board(@PathVariable("loginId") String loginId, Model model) {
        Member member = memberRepository.findByLoginId(loginId); // loginId로 member 조회
        List<Board> boards = boardRepository.findAll(); // 해당 회원의 게시판 조회

        model.addAttribute("member", member); // member 객체 추가
        model.addAttribute("boards", boards); // boards 리스트 추가
        return "post/boards";
    }

    @GetMapping("{id}")
    public String getBoard(@PathVariable("loginId") String loginId , @PathVariable("id") Long id, HttpServletRequest request, Model model){

        Member member = memberRepository.findByLoginId(loginId);
        Board board = boardRepository.findByBoard(id);
        String previousUrl = request.getHeader("referer");

        model.addAttribute("previousUrl", previousUrl);
        model.addAttribute("member", member);
        model.addAttribute("board", board);
        return "post/board";
    }


    @GetMapping("/edit/{id}")
    public String getEditBoard(@PathVariable("loginId") String loginId ,@PathVariable("id") Long id,Model model){
        Member member = memberRepository.findByLoginId(loginId);
        Board board = boardRepository.findByBoard(id);

        model.addAttribute("member",member);
        model.addAttribute("board",board);
        return "post/board-edit";
    }

    @PostMapping("/edit/{id}")
    public String postEditBoard(@PathVariable("loginId") String loginId,
                                @PathVariable("id") Long id,
                                @RequestParam("title") String title,
                                @RequestParam("attachFile") MultipartFile attachFile,
                                @RequestParam("imageFiles") List<MultipartFile> images,
                                @RequestParam("body") String body) throws IOException{

        Board board = boardRepository.findByBoard(id);

        fileStore.deleteFile(board.getAttachFile(), board.getImageFiles());

        UploadFile uploadAttachFile = fileStore.storeFile(attachFile);
        List<UploadFile> storeImageFiles = fileStore.storeFiles(images);

        boardService.updateBoard(id,title,uploadAttachFile,storeImageFiles,body);
        return "redirect:/members/"+loginId+"/boards";
    }

    @GetMapping("new/form")
    public String getBoardForm(@PathVariable("loginId") String loginId, Model model){
        Member member = memberRepository.findByLoginId(loginId);
        model.addAttribute("member", member);
        model.addAttribute("board", new BoardForm());
        return "post/board-form";
    }

    @PostMapping("new/form")
    public String postBoardForm(@PathVariable("loginId") String loginId, @ModelAttribute("board") BoardForm form) throws IOException {
        UploadFile attachFile = fileStore.storeFile(form.getAttachFile());
        List<UploadFile> storeImageFiles = fileStore.storeFiles(form.getImageFiles());

        Board board = new Board();
        board.setId(form.getId());
        board.setTitle(form.getTitle());
        board.setBody(form.getBody());
        board.setAttachFile(attachFile);
        board.setImageFiles(storeImageFiles);
        board.setMemberInfo(memberRepository.findByLoginId(loginId));

        boardService.saveBoard(board);
        return "redirect:/members/"+loginId+"/boards";
    }

    @ResponseBody
    @GetMapping("/images/{filename}")
    public Resource downloadImage(@PathVariable String filename) throws MalformedURLException{
        return new UrlResource("file:" + fileStore.getFullPath(filename));
    }

    @GetMapping("/attach/{id}")
    public ResponseEntity<Resource> downloadAttach(@PathVariable Long id) throws MalformedURLException{

        Board board = boardRepository.findByBoard(id);
        if (board == null || board.getAttachFile() == null) {
            return ResponseEntity.notFound().build(); // 파일이 없을 경우 예외 처리
        }

        String storeFileName = board.getAttachFile().getStoreFileName();
        String uploadFileName = board.getAttachFile().getUploadFileName();

        UrlResource resource = new UrlResource("file:" + fileStore.getFullPath(storeFileName));

        String encodedUploadFileName = UriUtils.encode(uploadFileName, StandardCharsets.UTF_8);
        String contentDisposition = "attachment; filename=\"" + encodedUploadFileName + "\"";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(resource);
    }


    // 게시글 삭제
    @PostMapping("{id}/delete")
    public String boardDelete(@PathVariable("loginId") String loginId, @PathVariable("id") Long id){
        //파일 삭제
        Board board = boardRepository.findByBoard(id);
        fileStore.deleteFile(board.getAttachFile(), board.getImageFiles());
        //레포지토리 삭제 (파일경로만 들어있음)
        boardRepository.delete(id);

        return "redirect:/members/" + loginId;
    }

}
