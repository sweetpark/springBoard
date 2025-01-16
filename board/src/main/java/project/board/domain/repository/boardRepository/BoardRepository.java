package project.board.domain.repository.boardRepository;

import project.board.domain.entity.Board;
import project.board.web.dto.UploadFile;

import java.util.List;

public interface BoardRepository {

    //로그인 아이디를 저장
    public void save(Board board);
    public void delete(Long id);
    public Board findByBoard(Long id);
    public List<Board> findBoardsByMember(Long id);
    public List<Board> findAll();
    public void updateBoard(Long id, String title, /*UploadFile storeFile, List<UploadFile> images,*/ String body);
    public void clear();
}
