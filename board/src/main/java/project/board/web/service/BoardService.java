package project.board.web.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.board.domain.dto.Board;
import project.board.domain.dto.UploadFile;
import project.board.domain.repository.boardRepository.BoardRepository;

import java.util.List;

@Service
public class BoardService {

    private final BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository){
        this.boardRepository = boardRepository;
    }

    @Transactional
    public void saveBoard(Board board){
        boardRepository.save(board);
    }

    @Transactional
    public void updateBoard(Long id, String title, UploadFile attachFile, List<UploadFile> images, String body){
        boardRepository.updateBoard(id,title,attachFile,images,body);
    }

}
