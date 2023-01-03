package com.erich.management.Interceptor;


import org.hibernate.dialect.pagination.AbstractSimpleLimitHandler;
import org.hibernate.query.spi.Limit;
import org.hibernate.query.spi.QueryOptions;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;



public class Interceptors{ //extends //AbstractSimpleLimitHandler {

  /*  @Override
    public String processSql(String sql, Limit limit, QueryOptions queryOptions) {
        if (StringUtils.hasLength(sql) && sql.toLowerCase().startsWith("select")) {
            // select utilisateu0_.
            final String entityName = sql.substring(7, sql.indexOf("."));
            final String idEntreprise = MDC.get("idEntreprise");
            if (StringUtils.hasLength(entityName)
                    && !entityName.toLowerCase().contains("entreprise")
                    && !entityName.toLowerCase().contains("roles")
                    && StringUtils.hasLength(idEntreprise)) {

                if (sql.contains("where")) {
                    sql = sql + " and " + entityName + ".identreprise = " + idEntreprise;
                } else {
                    sql = sql + " where " + entityName + ".identreprise = " + idEntreprise;
                }
            }
        }
        return super.processSql(sql, limit, queryOptions);
    }


    @Override
    protected String limitClause(boolean hasFirstRow) {
        return null;
    }*/
}
