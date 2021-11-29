package fafij.server.controllers;
import fafij.server.repository.CategoryService;
import fafij.server.repository.UserRolesService;
import fafij.server.entity.Category;
import fafij.server.requestbodies.CategoryBody;
import fafij.server.requestbodies.JournalName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/private")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UserRolesService userRolesService;

    @PostMapping("/addCategory")
    public void createCategory(@RequestBody CategoryBody categoryBody, HttpServletResponse response){
        try{
            if(categoryService.checkCategory(categoryBody.getCategory(), categoryBody.getJournalName()) && categoryBody.getCategory() != null && !categoryBody.getCategory().isEmpty()){
                Long idRole = userRolesService.findByUserAndJournal(categoryBody.getLogin(), categoryBody.getJournalName()).getIdRole().getId();
                if(idRole == 1 || idRole == 2){
                    categoryService.createCategory(categoryBody.getCategory(), categoryBody.getJournalName());
                    response.setStatus(HttpServletResponse.SC_CREATED);
                }else{
                    response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
                }

            }else{
                response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
            }
        }catch (Exception e){
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/deleteCategory")
    public void deleteCategory(@RequestBody CategoryBody categoryBody, HttpServletResponse response){
        try{
            Long idRole = userRolesService.findByUserAndJournal(categoryBody.getLogin(), categoryBody.getJournalName()).getIdRole().getId();
            if(idRole == 1 || idRole == 2){
                Category ctgr = categoryService.findByNameAndIdJournal(categoryBody.getCategory(), categoryBody.getJournalName());
                categoryService.deleteCategory(ctgr);
                response.setStatus(HttpServletResponse.SC_CREATED);
            }else{
                response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
            }
        }catch (Exception e){
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/listCategory")
    public @ResponseBody
    String findAll(@RequestBody JournalName journalName){
        return this.categoryService.findAllByIdJournal(journalName.getJournalName()).toString();
    }
}
