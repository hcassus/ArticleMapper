package com.mhp.coding.challenges.mapping.support;

import com.mhp.coding.challenges.mapping.models.db.Article;
import com.mhp.coding.challenges.mapping.models.db.Image;
import com.mhp.coding.challenges.mapping.models.db.ImageSize;
import com.mhp.coding.challenges.mapping.models.db.blocks.*;

import java.util.*;

import static java.util.Collections.singletonList;

public class TestArticleHelper {
    public Article createDummyArticleWithMultipleBlocks(Long id) {
        final Article result = createBaseArticle(id);
        result.setBlocks(createBlocks(id));
        return result;
    }

    public Article createDummyArticleWithTextBlock(Long id) {
        final Article result = createBaseArticle(id);
        result.setBlocks(new HashSet<>(singletonList(createTextBlock(1000L, "Text for article ",1))));
        return result;
    }

    public Article createDummyArticleWithVideoBlock(Long id) {
        final Article result = createBaseArticle(id);
        result.setBlocks(new HashSet<>(singletonList(createVideoBlock(1))));
        return result;
    }

    public Article createDummyArticleWithImageBlock(Long id) {
        final Article result = createBaseArticle(id);
        result.setBlocks(new HashSet<>(singletonList(createImageBlock(1))));
        return result;
    }

    public Article createDummyArticleWithGalleryBlock(Long id) {
        final Article result = createBaseArticle(id);
        result.setBlocks(new HashSet<>(singletonList(createGalleryBlock(1))));
        return result;
    }

    public Article createDummyArticleWithUnmappedBlock(long id) {
        final Article result = createBaseArticle(id);
        result.setBlocks(new HashSet<>(singletonList(createUnmappedBlock())));
        return result;
    }

    private Article createBaseArticle(Long id) {
        final Article result = new Article();
        result.setId(id);
        result.setAuthor("Max Mustermann");
        result.setDescription("Article Description " + id);
        result.setTitle("Article Nr.: " + id);
        result.setLastModifiedBy("Hans Müller");
        result.setLastModified(new Date());
        return result;
    }

    private Set<ArticleBlock> createBlocks(Long articleId){
        final Set<ArticleBlock> result = new HashSet<>();
        result.add(createTextBlock(articleId, "Some Text for ", 0));
        result.add(createImageBlock(1));
        result.add(createTextBlock(articleId, "Second Text for ", 2));
        result.add(createGalleryBlock(3));
        result.add(createTextBlock(articleId, "Third Text for ", 4));
        result.add(createVideoBlock(5));
        return result;
    }

    private UnmappedBlock createUnmappedBlock() {
        return new UnmappedBlock();
    }

    private VideoBlock createVideoBlock(int id) {
        final VideoBlock videoBlock = new VideoBlock();
        videoBlock.setType(VideoBlockType.YOUTUBE);
        videoBlock.setUrl("https://youtu.be/myvideo");
        videoBlock.setSortIndex(id);
        return videoBlock;
    }

    private GalleryBlock createGalleryBlock(int index) {
        final GalleryBlock galleryBlock = new GalleryBlock();
        galleryBlock.setSortIndex(index);

        final List<Image> galleryImages = new ArrayList<>();
        galleryImages.add(createImage(2L));
        galleryImages.add(createImage(3L));
        galleryBlock.setImages(galleryImages);

        return galleryBlock;
    }

    private ImageBlock createImageBlock(int index) {
        final ImageBlock imageBlock = new ImageBlock();
        imageBlock.setImage(createImage(1L));
        imageBlock.setSortIndex(index);
        return imageBlock;
    }

    private TextBlock createTextBlock(Long articleId, String text, int index) {
        final TextBlock textBlock = new TextBlock();
        textBlock.setText(text + articleId);
        textBlock.setSortIndex(index);
        return textBlock;
    }

    private Image createImage(Long imageId){
        final Image result = new Image();
        result.setId(imageId);
        result.setLastModified(new Date());
        result.setLastModifiedBy("Max Mustermann");
        result.setImageSize(ImageSize.LARGE);
        result.setUrl("https://someurl.com/image/" + imageId);
        return result;
    }
}
