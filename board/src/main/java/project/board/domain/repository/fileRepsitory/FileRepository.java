package project.board.domain.repository.fileRepsitory;


import project.board.domain.entity.UploadFile;

import java.util.List;

public interface FileRepository {
    void save(UploadFile file);
    void delete(Long id);
    List<UploadFile> findAll();
    UploadFile findById(Long id);
}
