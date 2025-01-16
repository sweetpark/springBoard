package project.board.domain.service.boardService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.board.domain.entity.Board;
import project.board.web.dto.UploadFile;
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
