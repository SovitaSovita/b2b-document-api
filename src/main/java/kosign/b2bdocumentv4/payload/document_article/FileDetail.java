package kosign.b2bdocumentv4.payload.document_article;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileDetail {
    private int fileId;
    private String fileIdntId;
    private String fileNm;
    private String fileSize;
    private String thumImgPath;
    private String imgPath;
}
