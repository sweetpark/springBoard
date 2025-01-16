package project.board.domain.repository.commentRepository;

import project.board.domain.entity.Comment;

public interface CommentRepository {

    void save(Comment comment);
    void delete(Long id);
}
