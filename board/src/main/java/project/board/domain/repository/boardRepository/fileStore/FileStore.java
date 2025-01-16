package project.board.domain.repository.boardRepository.fileStore;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import project.board.web.dto.UploadFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@Slf4j
public class FileStore {

    @Value("${file.dir}")
    private String fileDir;

    public String getFullPath(String filename){
        return fileDir + filename;
    }

    public List<UploadFile> storeFiles(List<MultipartFile> multipartFiles) throws IOException{
        List<UploadFile> storeFileResult = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if (!multipartFile.isEmpty()){
                storeFileResult.add(storeFile(multipartFile));
            }
        }
        return storeFileResult;
    }

    public UploadFile storeFile(MultipartFile multipartFile) throws IOException{
        if (multipartFile.isEmpty()){
            return null;
        }

        String originalName = multipartFile.getOriginalFilename();
        String storeFileName = createStoreFileName(originalName);
        multipartFile.transferTo(new File(getFullPath(storeFileName)));
        return new UploadFile(originalName, storeFileName);
    }

    public void deleteFile(UploadFile attachFile, List<UploadFile> imageFiles){
        if (attachFile != null){
            String attachStoreFileName = attachFile.getStoreFileName();
            String attachUploadFileName = attachFile.getUploadFileName();
            File file = new File(getFullPath(attachStoreFileName));
            if (file.delete()){
                log.info("파일 삭제 {}", attachUploadFileName);
            }
        }

        if(imageFiles != null){
            for (UploadFile imageFile : imageFiles){
                String imageStoreFileName = imageFile.getStoreFileName();
                String UploadImageFileName = imageFile.getUploadFileName();

                File file = new File(getFullPath(imageStoreFileName));
                if( file.delete()){
                    log.info("이미지 파일 삭제 {}" ,UploadImageFileName);
                }
            }
        }

    }

    private String createStoreFileName(String originalFileName){
        String ext = extractExt(originalFileName);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }

    private String extractExt(String originalFileName){
        int pos = originalFileName.lastIndexOf(".");
        return originalFileName.substring(pos+1);
    }



}
