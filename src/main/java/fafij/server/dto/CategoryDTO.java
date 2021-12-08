package fafij.server.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Objects;

@EnableTransactionManagement
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryDTO {
    public String name;

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
