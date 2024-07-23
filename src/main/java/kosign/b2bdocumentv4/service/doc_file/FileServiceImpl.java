package kosign.b2bdocumentv4.service.doc_file;

import kosign.b2bdocumentv4.entity.doc_articles.DocumentArticles;
import kosign.b2bdocumentv4.entity.doc_articles.DocumentArticlesRepository;
import kosign.b2bdocumentv4.entity.doc_file.DocumentFile;
import kosign.b2bdocumentv4.entity.doc_file.DocumentFileRepository;
import kosign.b2bdocumentv4.utils.CommonValidation;
import kosign.b2bdocumentv4.exception.NotFoundExceptionClass;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    private final DocumentArticlesRepository articlesRepository;
    private final CommonValidation commonValidation;
    private final DocumentFileRepository fileRepository;

    @Value("${file_path}")
    private String file_path;

    @Value("${base_url_img}")
    private String base_url;

    public FileServiceImpl(DocumentArticlesRepository articlesRepository, CommonValidation commonValidation, DocumentFileRepository fileRepository) {
        this.articlesRepository = articlesRepository;
        this.commonValidation = commonValidation;
        this.fileRepository = fileRepository;
    }

    @Override
    public DocumentFile fileUpload(MultipartFile file) throws IOException {

        Path root = Paths.get(file_path);
//        String fileArticleId = articlesRepository.getFileArticleById(Long.valueOf(articleId));

//        if(fileArticleId == null){
//            throw new NotFoundExceptionClass("Article not Found.");
//        }

        DocumentFile documentFile = new DocumentFile();
        documentFile.setFile_article_id("00" + LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
        documentFile.setFile_idnt_id(LocalDate.now().toString());


        try {
            String fileName = file.getOriginalFilename();
            if (fileName != null &&
                    fileName.contains(".jpg") ||
                    fileName.contains(".jpeg") ||
                    fileName.contains(".JPG") ||
                    fileName.contains(".ong") ||
                    fileName.contains((".png")) ||
                    fileName.contains((".webp")) ||
                    fileName.contains((".xlsx")) ||
                    fileName.contains((".docx")) ||
                    fileName.contains((".pdf")) ||
                    fileName.contains((".gif")) ||
                    fileName.contains((".csv")) ||
                    fileName.contains((".doc")) ||
                    fileName.contains((".xls")) ||
                    fileName.contains((".ppt")) ||
                    fileName.contains((".pptx")) ||
                    fileName.contains((".mp3")) ||
                    fileName.contains((".mp4")) ||
                    fileName.contains((".webm")) ||
                    fileName.contains((".rar")) ||
                    fileName.contains((".zip")) ||
                    fileName.contains((".tar"))
            ) {
                // create directory if not exist
                if (!Files.exists(root)) {
                    Files.createDirectories(root);
                }
                // Ensure the filename is unique by checking if it already exists
                Path destinationFile = root.resolve(Paths.get(fileName)).normalize().toAbsolutePath();
                if (!destinationFile.getParent().equals(root.toAbsolutePath())) {
                    throw new IOException("Cannot store file outside current directory.");
                }

                // If file exists, append a number to make it unique
                int counter = 1;
                String baseName = fileName.substring(0, fileName.lastIndexOf('.'));
                String extension = fileName.substring(fileName.lastIndexOf('.'));
                while (Files.exists(destinationFile)) {
                    fileName = baseName + "(" + counter++ + ")" + extension;
                    destinationFile = root.resolve(
                                    Paths.get(fileName))
                            .normalize().toAbsolutePath();
                }


                Files.copy(file.getInputStream(), root.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
                documentFile.setFile_nm(fileName);
                documentFile.setThum_img_path(base_url + fileName);
                documentFile.setImg_path(root + "/" + fileName);
                documentFile.setFile_size(String.valueOf(file.getSize()));

                return fileRepository.save(documentFile);
            }
            else {
                throw new NotFoundExceptionClass("Unknown file type.");
            }
        } catch (IOException e){
            throw new IOException("File not found!!");
        }
    }

    @Override
    public List<DocumentFile> fileList(String articleId) {
        if(!commonValidation.isNumeric(articleId)){
            throw new IllegalArgumentException("Article ID must be a number.");
        }
        List <DocumentFile> files = fileRepository.findByArticleIdAndStatus(Integer.parseInt(articleId));
        if(!files.isEmpty()) return files;
        else throw new NotFoundExceptionClass("Article with ID ["+ articleId +"] not found.");
    }

    @Override
    public void deletefile(Integer fileId) throws IOException {
        Path root = Paths.get(file_path);
        DocumentFile docFile = fileRepository.findById(fileId)
                .orElseThrow(() -> new NotFoundExceptionClass("File not found"));
        String fileName = docFile.getFile_nm();
        if(fileName != null){
            Path imagePath = root.resolve(fileName);
            Files.deleteIfExists(imagePath);
        }

        fileRepository.deleteById(fileId);
    }
}
