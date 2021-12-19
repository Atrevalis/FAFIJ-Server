package fafij.server.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import fafij.server.entity.Category;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import java.util.Objects;

@EnableTransactionManagement
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryDTO {
    public String name;

    @Transactional
    public List<CategoryDTO> getCategoryDTOList(List<Category> categoryList){
        List<CategoryDTO> categoryDTOList = new ArrayList<>();
        for(Category category : categoryList){
            CategoryDTO categoryDTO = new CategoryDTO();
            categoryDTO.setName(category.getName());
            categoryDTOList.add(categoryDTO);
        }
        return categoryDTOList;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoryDTO that = (CategoryDTO) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public void setName(String name) {
        this.name = name;
    }
}
