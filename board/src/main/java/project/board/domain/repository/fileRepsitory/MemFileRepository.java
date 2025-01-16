package project.board.domain.repository.fileRepsitory;

import project.board.domain.entity.AttachFile;
import project.board.domain.entity.ImageFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemFileRepository implements FileRepository{
    Map<Long, AttachFile> attachStore = new HashMap<>();
    Map<Long, ImageFile> imageStore = new HashMap<>();

    private static Long sequenceAttach = 0L;
    private static Long sequenceImage = 0L;

    @Override
    public void saveImage(List<ImageFile> imageFile) {
        for (ImageFile file : imageFile) {
            imageStore.put(sequenceImage++, file);
        }
    }

    @Override
    public void saveAttach(AttachFile attachFile) {
        attachStore.put(sequenceAttach++, attachFile);
    }

    @Override
    public void deleteImage(Long id) {
        imageStore.remove(id);
    }

    @Override
    public void deleteAttach(Long id) {
        attachStore.remove(id);
    }
}
