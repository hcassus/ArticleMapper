package com.mhp.coding.challenges.mapping.mappers;

import com.google.common.collect.Ordering;
import com.mhp.coding.challenges.mapping.exception.UnrecognizedBlockTypeException;
import com.mhp.coding.challenges.mapping.models.db.Article;
import com.mhp.coding.challenges.mapping.models.db.Image;
import com.mhp.coding.challenges.mapping.models.db.blocks.GalleryBlock;
import com.mhp.coding.challenges.mapping.models.db.blocks.ImageBlock;
import com.mhp.coding.challenges.mapping.models.db.blocks.TextBlock;
import com.mhp.coding.challenges.mapping.models.db.blocks.VideoBlock;
import com.mhp.coding.challenges.mapping.models.dto.ArticleDto;
import com.mhp.coding.challenges.mapping.models.dto.ImageDto;
import com.mhp.coding.challenges.mapping.models.dto.blocks.*;
import com.mhp.coding.challenges.mapping.support.TestArticleHelper;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class ArticleMapperTest {

    private ArticleMapper mapper;
    private TestArticleHelper articleBuilder;

    @Before
    public void setup() {
        mapper = new ArticleMapper();
        articleBuilder = new TestArticleHelper();
    }

    @Test
    public void testBasePropertiesAreCorrectlyMapped() {
        Article article = articleBuilder.createDummyArticleWithMultipleBlocks(1L);
        ArticleDto dto = mapper.map(article);

        assertThat(dto.getId(), equalTo(article.getId()));
        assertThat(dto.getTitle(), equalTo(article.getTitle()));
        assertThat(dto.getAuthor(), equalTo(article.getAuthor()));
        assertThat(dto.getDescription(), equalTo(article.getDescription()));
    }

    @Test
    public void testBlocksAreOrdered() {
        Article article = articleBuilder.createDummyArticleWithMultipleBlocks(1L);
        ArticleDto dto = mapper.map(article);

        List<Integer> order = dto.getBlocks().stream()
                .map(ArticleBlockDto::getSortIndex)
                .collect(Collectors.toCollection(LinkedList::new));

        assertTrue(Ordering.natural().isOrdered(order));
    }

    @Test
    public void testTextBlocksAreMapped() {
        Article article = articleBuilder.createDummyArticleWithTextBlock(1L);

        ArticleDto dto = mapper.map(article);

        TextBlock articleBlock = (TextBlock) article.getBlocks().iterator().next();
        TextBlockDto dtoBlock = (TextBlockDto) dto.getBlocks().iterator().next();

        assertThat(dtoBlock.getText(), equalTo(articleBlock.getText()));
        assertThat(dtoBlock.getSortIndex(), equalTo(articleBlock.getSortIndex()));
    }

    @Test
    public void testVideoBlocksAreMapped() {
        Article article = articleBuilder.createDummyArticleWithVideoBlock(1L);

        ArticleDto dto = mapper.map(article);

        VideoBlock articleBlock = (VideoBlock) article.getBlocks().iterator().next();
        VideoBlockDto dtoBlock = (VideoBlockDto) dto.getBlocks().iterator().next();

        assertThat(dtoBlock.getSortIndex(), equalTo(articleBlock.getSortIndex()));
        assertThat(dtoBlock.getUrl(), equalTo(articleBlock.getUrl()));
        assertThat(dtoBlock.getType(), equalTo(articleBlock.getType()));
    }

    @Test
    public void testGalleryBlocksAreMapped() {
        Article article = articleBuilder.createDummyArticleWithGalleryBlock(1L);

        ArticleDto dto = mapper.map(article);

        GalleryBlock articleBlock = (GalleryBlock) article.getBlocks().iterator().next();
        GalleryBlockDto dtoBlock = (GalleryBlockDto) dto.getBlocks().iterator().next();

        assertThat(dtoBlock.getSortIndex(), equalTo(articleBlock.getSortIndex()));
        assertImagesAreMapped(dtoBlock.getImages(), articleBlock.getImages());
    }

    private void assertImagesAreMapped(List<ImageDto> dtoImages, List<Image> dbImages) {
        assertThat(dtoImages, hasSize(dbImages.size()));
        for (int i =0; i < dtoImages.size(); i++){
            assertImageIsMapped(dtoImages.get(i), dbImages.get(i));
        }
    }

    @Test
    public void testImageBlocksAreMapped() {
        Article article = articleBuilder.createDummyArticleWithImageBlock(1L);

        ArticleDto dto = mapper.map(article);

        ImageBlock articleBlock = (ImageBlock) article.getBlocks().iterator().next();
        ImageBlockDto dtoBlock = (ImageBlockDto) dto.getBlocks().iterator().next();

        assertThat(dtoBlock.getSortIndex(), equalTo(articleBlock.getSortIndex()));
        assertImageIsMapped(dtoBlock.getImage(), articleBlock.getImage());
    }

    private void assertImageIsMapped(ImageDto dtoBlock, Image articleBlock) {
        assertThat(dtoBlock.getUrl(), equalTo(articleBlock.getUrl()));
        assertThat(dtoBlock.getImageSize(), equalTo(articleBlock.getImageSize()));
        assertThat(dtoBlock.getId(), equalTo(articleBlock.getId()));
    }

    @Test(expected = UnrecognizedBlockTypeException.class)
    public void testUnmappedBlocksThrowException() {
        Article article = articleBuilder.createDummyArticleWithUnmappedBlock(1L);

        ArticleDto dto = mapper.map(article);
    }
}