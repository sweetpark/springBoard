package project.board.domain.repository.fileRepsitory;

import project.board.domain.entity.UploadFile;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemFileRepository implements FileRepository{

    Map<Long, UploadFile> store = new HashMap<>();

    private static Long sequence = 0L;

    @Override
    public void save(UploadFile file) {
        file.setId(++sequence);
        store.put(sequence, file);

    }

    @Override
    public void delete(Long id) {
        store.remove(id);
    }

    @Override
    public List<UploadFile> findAll() {
        return store.values().stream().toList();
    }

    @Override
    public UploadFile findById(Long id) {
        return store.get(id);
    }


}
