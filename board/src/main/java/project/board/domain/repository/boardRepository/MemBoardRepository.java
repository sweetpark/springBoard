package project.board.domain.repository.boardRepository;

import lombok.RequiredArgsConstructor;
import project.board.domain.entity.Board;
import project.board.domain.repository.memberRepository.MemberRepository;
import project.board.web.dto.UploadFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MemBoardRepository implements BoardRepository{

    private Map<Long, Board> store = new HashMap<>();
    private Long sequence = 0L;

    @Override
    public void save(Board board) {
        board.setId(++sequence);
        board.setCreate_time(LocalDateTime.now());
        store.put(board.getId(), board);
    }

    @Override
    public void delete(Long id) {
        store.remove(id);
    }

    @Override
    public Board findByBoard(Long id) {
        return store.get(id);
    }

    @Override
    public List<Board> findBoardsByMember(Long id) {

        ArrayList<Board> memberBoards = new ArrayList<>();
        List<Board> boards= findAll();
        for (Board board : boards) {
            if (board.getMemberId().equals(id))
            {
                memberBoards.add(board);
            }
        }
        return memberBoards;
    }

    @Override
    public List<Board> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public void updateBoard(Long id, String title,/* UploadFile attachFile, List<UploadFile> images ,*/String body) {

        Board findBoard = findByBoard(id);
        findBoard.setTitle(title);
//        findBoard.setAttachFile(attachFile);
//        findBoard.setImageFiles(images);
        findBoard.setBody(body);

    }

    @Override
    public void clear() {
        store.clear();
    }
}
