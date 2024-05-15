package kosign.b2bdocumentv4.controller;


import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import kosign.b2bdocumentv4.payload.BaseResponse;
import kosign.b2bdocumentv4.payload.document_favorite.DocumentFavoriteDeleteRequest;
import kosign.b2bdocumentv4.payload.document_favorite.DocumentFavoriteRequest;
import kosign.b2bdocumentv4.service.doc_favorite.DocFavoriteServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/v1/favorite")
@CrossOrigin
@RequiredArgsConstructor
public class FavoriteController {

    private final DocFavoriteServiceImpl docFavoriteService;

    // Check is favorite
    @GetMapping("/check_if_favorite")
    public BaseResponse checkIfHaveFavorite(@RequestParam String user_id,
                                            @RequestParam Long article_id,
                                            @RequestParam Long dept_id) {
        return docFavoriteService.checkIsFavorite(user_id, article_id, dept_id);
    }

    // List
    @GetMapping("/favorite")
    public BaseResponse getFavoriteByUser(@RequestParam String user_id) {
        return docFavoriteService.listFavorite(user_id);
    }

    // Add
    @PostMapping("/add_favorite")
    public BaseResponse addFavorite(@RequestBody DocumentFavoriteRequest documentFavoriteRequest) {
        return docFavoriteService.saveFavorite(documentFavoriteRequest);
    }

    // Delete
    @PostMapping("/delete_favorite")
    public BaseResponse deleteFavoriteByArticle(@RequestBody DocumentFavoriteDeleteRequest documentFavoriteDeleteRequest) {
        return docFavoriteService.deleteFavorite(documentFavoriteDeleteRequest);
    }
}
