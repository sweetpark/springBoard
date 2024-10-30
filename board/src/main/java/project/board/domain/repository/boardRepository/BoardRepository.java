package project.board.domain.repository.boardRepository;

import org.springframework.web.multipart.MultipartFile;
import project.board.domain.dto.Board;
import project.board.domain.dto.UploadFile;

import java.util.List;

public interface BoardRepository {

    //로그인 아이디를 저장
    public void save(Board board);
    public void delete(Long id);
    public Board findByBoard(Long id);
    public List<Board> findBoardsByMember(String loginId);
    public List<Board> findAll();
    public void updateBoard(Long id, String title, UploadFile storeFile, List<UploadFile> images, String body);
    public void clear();
}
