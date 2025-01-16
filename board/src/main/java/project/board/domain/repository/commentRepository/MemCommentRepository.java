package project.board.domain.repository.commentRepository;


import project.board.domain.entity.Comment;

import java.util.HashMap;
import java.util.Map;

public class MemCommentRepository implements CommentRepository {

    Map<Long, Comment> store = new HashMap<>();
    public static Long sequnece = 0L;

    @Override
    public void save(Comment comment) {
        comment.setId(++sequnece);
        store.put(sequnece, comment);
    }

    @Override
    public void delete(Long id) {
        store.remove(id);
    }
}
