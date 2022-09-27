package com.webjournal.utils;


/*
@author Микола
@project WebJournal
@class FullTextSearchPred
@version 1.0.0
@since 26.09.2022 - 13.03
*/

import org.hibernate.query.criteria.internal.CriteriaBuilderImpl;
import org.hibernate.query.criteria.internal.ParameterContainer;
import org.hibernate.query.criteria.internal.ParameterRegistry;
import org.hibernate.query.criteria.internal.Renderable;
import org.hibernate.query.criteria.internal.compile.RenderingContext;
import org.hibernate.query.criteria.internal.expression.LiteralExpression;
import org.hibernate.query.criteria.internal.predicate.AbstractSimplePredicate;

import javax.persistence.criteria.Expression;
import java.io.Serializable;

/**
 * Цей клас, це спраба реалізувати власну предикату, для використання postgres fullTextSearch, але через те що hql
 * не може прочитати сивол @, довелося відмовитися від цієї ідеї. Можливо в майбутньому вийде виправити цю помилку,
 * тому я залишив цей клас.
 */
@Deprecated
class FullTextSearchPred extends AbstractSimplePredicate implements Serializable {
    private final Expression<String> matchExpression;
    private final Expression<String> pattern;

    public FullTextSearchPred(CriteriaBuilderImpl criteriaBuilder, Expression<String> matchExpression, String pattern) {
        super(criteriaBuilder);
        this.matchExpression = matchExpression;
        this.pattern = (Expression)(new LiteralExpression(criteriaBuilder, pattern));
    }

    public Expression<String> getMatchExpression() {
        return this.matchExpression;
    }

    public Expression<String> getPattern() {
        return this.pattern;
    }

    public void registerParameters(ParameterRegistry registry) {
        ParameterContainer.Helper.possibleParameter(this.getMatchExpression(), registry);
        ParameterContainer.Helper.possibleParameter(this.getPattern(), registry);
    }

    public String render(boolean isNegated, RenderingContext renderingContext) {
        StringBuilder buffer = new StringBuilder();
        buffer.append(((Renderable)this.getMatchExpression()).render(renderingContext))
                .append(" @@ ")
                .append(((Renderable)this.getPattern()).render(renderingContext));

        return buffer.toString();
    }

}
