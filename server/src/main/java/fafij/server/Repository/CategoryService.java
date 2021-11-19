package fafij.server.Repository;
import fafij.server.entity.Category;
import fafij.server.service.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    public void createCategory(Category category) {
        categoryRepository.save(category);
    }
    public void deleteCategory(Category category) {
        categoryRepository.delete(category);
    }

    public List<Category> findAllByName(String name){
        return categoryRepository.findAllByName(name);
    }
    public List<Category> findAll(){
        return categoryRepository.findAll();
    }
    public List<Category> findAllById(Iterable<Long> id){
        return categoryRepository.findAllById(id);
    }
}