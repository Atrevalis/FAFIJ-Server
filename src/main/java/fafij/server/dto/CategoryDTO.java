package fafij.server.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryDTO {
    public String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
