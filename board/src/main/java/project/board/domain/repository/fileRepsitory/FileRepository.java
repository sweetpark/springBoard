package project.board.domain.repository.fileRepsitory;

import project.board.domain.entity.AttachFile;
import project.board.domain.entity.ImageFile;

import java.util.List;

public interface FileRepository {
    void saveImage(List<ImageFile> imageFile);
    void saveAttach(AttachFile attachFile);
    void deleteImage(Long id);
    void deleteAttach(Long id);
}
