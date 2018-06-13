package asd.prueba.utils;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alejandro Cadena
 * @param <Model>
 */
public class Page<Model> {
    
    private int pageNumber;
    private int pagesAvailable;
    private int totalItems;
    private List<Model> pageItems = new ArrayList();
    
    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }
    
    public void setPagesAvailable(int pagesAvailable) {
        this.pagesAvailable = pagesAvailable;
    }
    
    public void setPageItems(List<Model> pageItems) {
        this.pageItems = pageItems;
    }
    
    public int getPageNumber() {
        return pageNumber;
    }
    
    public int getPagesAvailable() {
        return pagesAvailable;
    }
    
    public List<Model> getPageItems() {
        return pageItems;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }
    
}
