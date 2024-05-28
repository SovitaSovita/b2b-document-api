package kosign.b2bdocumentv4.service.doc_file;

import kosign.b2bdocumentv4.entity.doc_articles.DocumentArticles;
import kosign.b2bdocumentv4.entity.doc_file.DocumentFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileService {
    DocumentFile fileUpload(MultipartFile imageFile) throws IOException;

    List<DocumentFile> fileList(String articleId);

    void deletefile(Integer fileId) throws IOException;
}
