package com.webjournal.utils;


import org.hibernate.query.criteria.internal.CriteriaBuilderImpl;
import org.hibernate.query.criteria.internal.ParameterContainer;
import org.hibernate.query.criteria.internal.ParameterRegistry;
import org.hibernate.query.criteria.internal.Renderable;
import org.hibernate.query.criteria.internal.compile.RenderingContext;
import org.hibernate.query.criteria.internal.expression.LiteralExpression;
import org.hibernate.query.criteria.internal.predicate.AbstractSimplePredicate;
import org.springframework.data.util.Predicates;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.io.Serializable;
import java.util.Locale;

/*
@author Микола
@project FreshBeauty
@class QueryHelper
@version 1.0.0
@since 14.07.2022 - 22.54
*/
public class QueryHelper {
    private QueryHelper() {
        throw new UnsupportedOperationException();
    }

    public static Predicate ilike(Path<String> path, CriteriaBuilder criteriaBuilder, String value) {
        return criteriaBuilder.like(criteriaBuilder.lower(path), "%" + value.toLowerCase(Locale.ROOT) + "%");
    }


    public static Predicate full(Path<String> path, CriteriaBuilder criteriaBuilder, String value) {
        return new FullTextSearchPred((CriteriaBuilderImpl) criteriaBuilder, path.as(String.class), "to_tsquery('english','" + value + "')");
    }
}
