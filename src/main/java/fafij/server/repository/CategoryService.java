package fafij.server.repository;
import fafij.server.entity.Category;
import fafij.server.entity.Journal;
import fafij.server.service.CategoryRepository;
import fafij.server.service.JournalRepository;
import fafij.server.service.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private final CategoryRepository categoryRepository;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private JournalRepository journalRepository;

    public CategoryService(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    public void createCategory(String category, String journalName) {
        Category ctgr = new Category();
        ctgr.setName(category);
        ctgr.setIdJournal(journalRepository.findByName(journalName));
        categoryRepository.save(ctgr);
    }
    public void deleteCategory(Category category) {
        categoryRepository.delete(category);
    }

    public List<Category> findAllByName(String name){
        return categoryRepository.findAllByName(name);
    }
    public List<String> findAllByIdJournal(String journalName){
        List<Category> category = categoryRepository.findAllByIdJournal(journalRepository.findByName(journalName));
        List<String> string = new ArrayList<>();
        for(int i=0; i<category.size(); i++){
            string.add(category.get(i).getName());
        }
        return string;
    }

    public Category findByNameAndIdJournal(String name, String journal){
        Journal jrnl = journalRepository.findByName(journal);
        return categoryRepository.findByNameAndIdJournal(name, jrnl);
    }

    public Boolean checkCategory(String name, String journal){
        return !categoryRepository.existsByNameAndIdJournal(name, journalRepository.findByName(journal));
    }

    public List<Category> findAllById(Iterable<Long> id){
        return categoryRepository.findAllById(id);
    }
}