package project.board.domain.service.fileService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import project.board.domain.entity.UploadFile;
import project.board.domain.repository.fileRepsitory.FileRepository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Slf4j
@Service
@RequiredArgsConstructor
public class FileService {

    private static final String WIN_FILE_DIR = "C:/Users/user/Downloads/";
    private static final String MAC_FILE_DIR = "/tmp/";
    private String fileDir;
    private final FileRepository fileRepository;

    //파일저장 (이미지 ,첨부파일 여러개 가능)
    public void saveFile(List<UploadFile> files, Long boardId){
        for (UploadFile file : files) {
            file.setBoardId(boardId);
            fileRepository.save(file);
        }
    }

    //파일 삭제 (해당 id)
    public void deleteFile(Long id){
        UploadFile uploadFile = fileRepository.findById(id);

        String uploadFileName = uploadFile.getUploadFilename();
        String storeFileName = uploadFile.getStoreFilename();

        File file = new File(getFullPath(storeFileName));
        if(file.delete()){
            log.info("이미지 파일 삭제 {}" ,uploadFileName);
        }

        fileRepository.delete(id);
    }

    //파일 삭제 (게시판 삭제시)
    public void deleteByBoard(Long boardId){
        List<UploadFile> uploadFiles = fileRepository.findAll();
        for (UploadFile uploadFile : uploadFiles) {
            if(uploadFile.getBoardId().equals(boardId)){
                log.info("게시판 삭제로 인한 이미지 파일 삭제 {}", uploadFile.getUploadFilename());
                deleteFile(uploadFile.getId());
            }
        }
    }

    //파일 찾기
    public List<UploadFile> findAll(){
        return fileRepository.findAll();
    }

    //파일 업로드
    public List<UploadFile> storeFiles(List<MultipartFile> multipartFiles) throws IOException{
        List<UploadFile> uploadFiles = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if(!multipartFile.isEmpty()){
                uploadFiles.add(storeFile(multipartFile));
            }
        }
        return uploadFiles;
    }

    public UploadFile storeFile(MultipartFile multipartFile) throws IOException{
        if (multipartFile.isEmpty()){
            throw new RuntimeException("저장할 파일이 없습니다.");
        }

        String originalName = multipartFile.getOriginalFilename();
        String storeFileName = createStoreFileName(originalName);
        multipartFile.transferTo(new File(getFullPath(storeFileName)));
        return new UploadFile(originalName, storeFileName);
    }

    //파일 다운로드



    //파일 경로
    public String getFullPath(String filename){
        if (System.getProperty("os.name").toLowerCase().contains("win")){
            fileDir = WIN_FILE_DIR;
        }else if(System.getProperty("os.name").toLowerCase().contains("mac")){
            fileDir = MAC_FILE_DIR;
        }else{
            fileDir = "/tmp/";
        }
        return fileDir + filename;
    }

    //private 서버 저장 이름
    private String createStoreFileName(String originalFileName){
        String ext = extractExt(originalFileName);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }
    //private 확장자명 보이게
    private String extractExt(String originalFileName){
        int pos = originalFileName.lastIndexOf(".");
        return originalFileName.substring(pos+1);
    }
}
