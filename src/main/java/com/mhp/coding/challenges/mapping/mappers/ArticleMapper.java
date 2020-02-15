package com.mhp.coding.challenges.mapping.mappers;

import com.mhp.coding.challenges.mapping.exception.UnrecognizedBlockTypeException;
import com.mhp.coding.challenges.mapping.models.db.Article;
import com.mhp.coding.challenges.mapping.models.db.Image;
import com.mhp.coding.challenges.mapping.models.db.blocks.ArticleBlock;
import com.mhp.coding.challenges.mapping.models.db.blocks.GalleryBlock;
import com.mhp.coding.challenges.mapping.models.db.blocks.ImageBlock;
import com.mhp.coding.challenges.mapping.models.dto.ArticleDto;
import com.mhp.coding.challenges.mapping.models.dto.ImageDto;
import com.mhp.coding.challenges.mapping.models.dto.blocks.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ArticleMapper {

    public ArticleDto map(Article article) {
        ArticleDto dto = new ArticleDto();
        BeanUtils.copyProperties(article, dto, "blocks");

        Set<ArticleBlockDto> blocks = mapBlocks(article);
        dto.setBlocks(blocks);
        return dto;
    }

    private Set<ArticleBlockDto> mapBlocks(Article article) {
        return article.getBlocks().stream()
                .map(this::mapBlock)
                .sorted(Comparator.comparingInt(ArticleBlockDto::getSortIndex))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    private ArticleBlockDto mapBlock(ArticleBlock articleBlock) {
        ArticleBlockDto blockDto;

        switch (articleBlock.getClass().getSimpleName()){
            case "GalleryBlock":
                blockDto = new GalleryBlockDto();
                BeanUtils.copyProperties(articleBlock, blockDto,"images");
                mapImageList(blockDto, articleBlock);
                break;
            case "ImageBlock":
                blockDto = new ImageBlockDto();
                BeanUtils.copyProperties(articleBlock, blockDto, "image");
                mapImageDto(blockDto, articleBlock);
                break;
            case "TextBlock":
                blockDto = new TextBlockDto();
                BeanUtils.copyProperties(articleBlock, blockDto);
                break;
            case "VideoBlock":
                blockDto = new VideoBlockDto();
                BeanUtils.copyProperties(articleBlock, blockDto);
                break;
            default:
                throw new UnrecognizedBlockTypeException();
        }


        return blockDto;
    }

    private void mapImageList(ArticleBlockDto blockDto, ArticleBlock block) {
        if(!(blockDto instanceof GalleryBlockDto) || !(block instanceof GalleryBlock)){
            throw new UnsupportedOperationException("Trying to map images on a non-galery block");
        }

        GalleryBlock galleryBlock = (GalleryBlock) block;
        GalleryBlockDto galleryBlockDto = (GalleryBlockDto) blockDto;
        List<ImageDto> images = galleryBlock.getImages().stream()
                .map(this::mapImage)
                .collect(Collectors.toList());
        galleryBlockDto.setImages(images);
    }

    private ImageDto mapImage(Image image) {
        ImageDto dto = new ImageDto();
        BeanUtils.copyProperties(image, dto);
        return dto;
    }

    private void mapImageDto(ArticleBlockDto blockDto, ArticleBlock block) {
        if(!(blockDto instanceof ImageBlockDto) || !(block instanceof ImageBlock)){
            throw new UnsupportedOperationException("Trying to map images on a non-image block");
        }

        ImageBlock imageBlock = (ImageBlock) block;
        ImageBlockDto imageBlockDto = (ImageBlockDto) blockDto;
        ImageDto imageDto = mapImage(imageBlock.getImage());
        imageBlockDto.setImage(imageDto);
    }

    public Article map(ArticleDto articleDto) {
        // Nicht Teil dieser Challenge.
        return new Article();
    }
}
