package asd.prueba.utils;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

/**
 *
 * @author Alejandro Cadena
 * @param <Model>
 */
public class PaginationHelper<Model> {
    
    public Page<Model> fetchPage(
            final JdbcTemplate jt,
            final String sqlCountRows,
            final String sqlFetchRows,
            final Object args[],
            final int pageNo,
            final int pageSize,
            final RowMapper<Model> rowMapper) {
        
        // determine how many rows are available
        final int rowCount = jt.queryForObject(sqlCountRows, args, Integer.class);
        
        // calculate the number of pages
        int pageCount = rowCount / pageSize;
        if (rowCount > pageSize * pageCount) {
            pageCount++;
        }
        
        // create the page object
        final Page<Model> page = new Page();
        page.setPageNumber(pageNo);
        page.setPagesAvailable(pageCount);
        page.setTotalItems(rowCount);
        
        // fetch a single page of results
        final int startRow = (pageNo - 1) * pageSize;
        jt.query(
                sqlFetchRows,
                args,
                new CustomResultSetExtractor(page, startRow, pageSize, rowMapper)
        );
        return page;
    }
    
    
    public Page<Model> fetchPage(
            final NamedParameterJdbcTemplate jt,
            final String sqlCountRows,
            final String sqlFetchRows,
            final MapSqlParameterSource mapParams,
            final int pageNo,
            final int pageSize,
            final RowMapper<Model> rowMapper) {
        
        // determine how many rows are available
        final int rowCount = jt.queryForObject(sqlCountRows, mapParams, Integer.class);
        
        // calculate the number of pages
        int pageCount = rowCount / pageSize;
        if (rowCount > pageSize * pageCount) {
            pageCount++;
        }
        
        // create the page object
        final Page<Model> page = new Page();
        page.setPageNumber(pageNo);
        page.setPagesAvailable(pageCount);
        page.setTotalItems(rowCount);
        
        // fetch a single page of results
        final int startRow = (pageNo - 1) * pageSize;
        jt.query(
                sqlFetchRows,
                mapParams,
                new CustomResultSetExtractor(page, startRow, pageSize, rowMapper)
        );
        return page;
    }
    
    
    public Page<Model> fetchPage(
            final NamedParameterJdbcTemplate jt,
            final String sqlCountRows,
            final String sqlFetchRows,
            final int pageNo,
            final int pageSize,
            final RowMapper<Model> rowMapper) {
        
        // determine how many rows are available
        SqlParameterSource parameters = new MapSqlParameterSource();
        final Integer rowCount = jt.queryForObject(sqlCountRows, parameters, Integer.class);
        
        // calculate the number of pages
        int pageCount = rowCount / pageSize;
        if (rowCount > pageSize * pageCount) {
            pageCount++;
        }
        
        // create the page object
        final Page<Model> page = new Page();
        page.setPageNumber(pageNo);
        page.setPagesAvailable(pageCount);
        page.setTotalItems(rowCount);
        
        // fetch a single page of results
        final int startRow = (pageNo - 1) * pageSize;
        jt.query(
                sqlFetchRows,
                parameters,
                new CustomResultSetExtractor(page, startRow, pageSize, rowMapper)
        );
        return page;
    }

}
