package com.mhp.coding.challenges.mapping.services;

import com.mhp.coding.challenges.mapping.mappers.ArticleMapper;
import com.mhp.coding.challenges.mapping.models.db.Article;
import com.mhp.coding.challenges.mapping.repositories.ArticleRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.singletonList;
import static org.mockito.Mockito.*;

public class ArticleServiceTest {

    private ArticleService service;
    private ArticleRepository repository;
    private ArticleMapper mapper;

    @Before
    public void setup() {
        repository = Mockito.mock(ArticleRepository.class);
        mapper = Mockito.mock(ArticleMapper.class);
        service = new ArticleService(repository, mapper);
    }

    @Test
    public void testList() {
        Article article = new Article();
        List<Article> articles = singletonList(article);
        when(repository.all()).thenReturn(articles);

        service.list();

        verify(repository,times(1)).all();
        verify(mapper,times(articles.size())).map(article);
    }

    @Test
    public void testFindById() {
        long id = 1L;
        Article article = new Article();
        when(repository.findBy(id)).thenReturn(Optional.of(article));

        service.articleForId(id);

        verify(repository,times(1)).findBy(id);
        verify(mapper,times(1)).map(article);
    }

}