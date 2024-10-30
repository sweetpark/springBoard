package project.board.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import project.board.domain.dto.Board;
import project.board.domain.dto.Member;
import project.board.domain.dto.UploadFile;
import project.board.domain.repository.boardRepository.BoardRepository;
import project.board.domain.repository.boardRepository.fileStore.FileStore;
import project.board.domain.repository.memberRepository.MemberRepository;


import java.io.IOException;
import java.util.List;


@Controller
public class AdminController {

    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final FileStore fileStore;

    public AdminController(MemberRepository memberRepository, BoardRepository boardRepository, FileStore fileStore){
        this.memberRepository = memberRepository;
        this.boardRepository = boardRepository;
        this.fileStore =fileStore;
    }

    @GetMapping("/admin")
    public String admin(Model model){
        List<Member> members = memberRepository.findAll();
        List<Board> boards = boardRepository.findAll();
        model.addAttribute("members", members);
        model.addAttribute("boards", boards);
        return "admin/admin";
    }

    @GetMapping("/admin/members/{loginId}")
    public String getMembersEdit(@PathVariable("loginId") String loginId){
        return "member/edit-form";
    }

    @PostMapping("/admin/members/{loginId}")
    public String postMembersEdit(@PathVariable("loginId") String loginId,
                                  @RequestParam("name") String name,
                                  @RequestParam("passwd") String passwd,
                                  @RequestParam("address") String address){

        Member member =new Member();
        member.setName(name);
        member.setPasswd(passwd);
        member.setAddress(address);

        memberRepository.updateMember(loginId, member);
        return "redirect:/admin";
    }


    @GetMapping("/admin/boards/{id}")
    public String getEditBoard() {
        return "post/board-edit";
    }

    @PostMapping("/admin/boards/{id}")
    public String postEditBoard(@PathVariable("id") Long id,
                                @RequestParam("title") String title,
                                @RequestParam("attachFile") MultipartFile attachFile,
                                @RequestParam("imageFiles") List<MultipartFile> images,
                                @RequestParam("body") String body) throws IOException {
        UploadFile uploadAttachFile = fileStore.storeFile(attachFile);
        List<UploadFile> storeImageFiles = fileStore.storeFiles(images);

        boardRepository.updateBoard(id,title,uploadAttachFile,storeImageFiles,body);
        return "redirect:/admin";
    }




}
