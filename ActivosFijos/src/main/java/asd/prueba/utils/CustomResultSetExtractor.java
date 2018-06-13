package asd.prueba.utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Alejandro Cadena
 */
public class CustomResultSetExtractor implements ResultSetExtractor{

    private final Page page;
    private final int startRow;
    private final int pageSize;
    private final RowMapper rowMapper;
    
    public CustomResultSetExtractor(Page page, int startRow, int pageSize, RowMapper rowMapper) {
        super();
        this.page = page;
        this.startRow = startRow;
        this.pageSize = pageSize;
        this.rowMapper = rowMapper;
    }

    @Override
    public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
        final List pageItems = page.getPageItems();
        int currentRow = 0;
        while (rs.next() && currentRow <startRow + pageSize) {
            if (currentRow >= startRow) {
                pageItems.add(rowMapper.mapRow(rs, currentRow));
            }
            currentRow++;
        }
        return page;
    }
    
}
