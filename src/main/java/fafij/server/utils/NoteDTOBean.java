package fafij.server.utils;

import com.fasterxml.jackson.annotation.JsonProperty;
import fafij.server.dto.CategoryDTO;

public class NoteDTOBean {
    public Long id;
    public String date;
    public Long sum;
    public CategoryDTO category;
    public String comment;
}
