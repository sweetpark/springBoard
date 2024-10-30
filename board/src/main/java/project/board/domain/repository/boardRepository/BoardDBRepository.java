package project.board.domain.repository.boardRepository;

import project.board.domain.dto.Board;
import project.board.domain.dto.UploadFile;

import java.util.List;

public class BoardDBRepository implements BoardRepository{
    @Override
    public void save(Board board) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public Board findByBoard(Long id) {
        return null;
    }

    @Override
    public List<Board> findBoardsByMember(String loginId) {
        return List.of();
    }

    @Override
    public List<Board> findAll() {
        return List.of();
    }

    @Override
    public void updateBoard(Long id, String title, UploadFile attachFile, List<UploadFile> images,String body) {

    }

    @Override
    public void clear() {

    }
}
