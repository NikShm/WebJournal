package com.webjournal.services;


import com.webjournal.dto.SearchDTO;
import com.webjournal.entity.Post;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;

/*
@author Микола
@project WebJournal
@class PostServiceImpl
@version 1.0.0
@since 20.09.2022 - 19.07
*/
@Service
public class PostServiceImpl implements PostService {
    private final EntityManager entityManager;

    public PostServiceImpl (EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Page<Post> getPage(SearchDTO searchDTO) {
        List<Post> list = entityManager.createNativeQuery(getQuery(searchDTO), Post.class).getResultList();
        Page<Post> all = new PageImpl<>(list);
        return all;
    }

    /**
     * Для реалізації, пагінації, в даному випадку я використав Native Query, через те що треба було реалізувати,
     * fullTextSearch. Hibernate Search я не зміг використати через те, що він використовує свій тип предикати, який
     * не піддходе для реалізаціх пагінвції через репозиторій. Також у criteriaBuilder немає відповідної функції, яка б
     * повернула потрібну предикату. Чому не я зміг реалізувати свою предикату дивіться нижче.
     * @see com.webjournal.utils.FullTextSearchPred
     */
    private String getQuery(SearchDTO search) {
        StringBuilder query = new StringBuilder("SELECT * FROM post");
        if (search.getSearch() != null) {
            query.append(" WHERE post.ts_content @@ phraseto_tsquery('english', '").append(search.getSearch()).append("')");
//            query.append(" and to_tsvector(title) @@ phraseto_tsquery('english', '").append(search.getSearch()).append("')");
        }
        if (search.getSortDirection() != null && search.getSortField() != null){
            query.append(" ORDER BY ").append(search.getSortField()).append(" ").append(search.getSortDirection());
        }
        if (search.getPage() != null && search.getPageSize() != null){
            query.append(" limit ").append(search.getPageSize()).append(" offset ").append(search.getPage());
        }
        return query.toString();
    }
}
