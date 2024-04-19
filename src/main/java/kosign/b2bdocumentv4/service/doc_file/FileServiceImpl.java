package kosign.b2bdocumentv4.service.doc_file;

import kosign.b2bdocumentv4.entity.doc_articles.DocumentArticles;
import kosign.b2bdocumentv4.entity.doc_articles.DocumentArticlesRepository;
import kosign.b2bdocumentv4.entity.doc_file.DocumentFile;
import kosign.b2bdocumentv4.entity.doc_file.DocumentFileRepository;
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
import java.util.List;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    private final DocumentArticlesRepository articlesRepository;
    private final DocumentFileRepository fileRepository;

    @Value("${file_path}")
    private String file_path;

    @Value("${base_url_img}")
    private String base_url;

    public FileServiceImpl(DocumentArticlesRepository articlesRepository, DocumentFileRepository fileRepository) {
        this.articlesRepository = articlesRepository;
        this.fileRepository = fileRepository;
    }

    @Override
    public DocumentFile fileUpload(MultipartFile file, String articleId) throws IOException {

        Path root = Paths.get(file_path);

        DocumentArticles articleData = articlesRepository.findById(Long.valueOf(articleId))
                .orElseThrow(() -> new NotFoundExceptionClass("Article with ID [" + articleId + "]"));

        DocumentFile documentFile = new DocumentFile();
        documentFile.setFile_article_id(articleData.getFile_article_id());
        documentFile.setFile_idnt_id(LocalDate.now().toString());


        try {
            String fileName = file.getOriginalFilename();
            if (fileName != null &&
                    fileName.contains(".jpg") ||
                    fileName.contains(".jpeg") ||
                    fileName.contains(".ong") ||
                    fileName.contains((".png")) ||
                    fileName.contains((".webp"))
            ) {
                fileName = UUID.randomUUID() +  "." + StringUtils.getFilenameExtension(fileName);

                // create directory if not exist
                if (!Files.exists(root)) {
                    Files.createDirectories(root);
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
        //work here
        return fileRepository.findByFile_article_id(articleId);
    }
}
