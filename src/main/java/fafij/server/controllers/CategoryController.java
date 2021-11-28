package fafij.server.controllers;
import fafij.server.repository.UserRolesService;
import fafij.server.entity.Category;
import fafij.server.repository.CategoryService;
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
    public void createCategory(@RequestParam String category, @RequestParam String login, @RequestParam String journalName, HttpServletResponse response){
        try{
            Long idRole = userRolesService.findByUserAndJournal(login, journalName).getIdRole().getId();
            if(idRole == 1 || idRole == 2){
                categoryService.createCategory(category, journalName);
                response.setStatus(HttpServletResponse.SC_CREATED);
            }else{
                response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
            }
        }catch (Exception e){
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/deleteCategory")
    public void deleteCategory(@RequestParam String category, @RequestParam String login, @RequestParam String journalName, HttpServletResponse response){
        try{
            Long idRole = userRolesService.findByUserAndJournal(login, journalName).getIdRole().getId();
            if(idRole == 1 || idRole == 2){
                Category ctgr = categoryService.findByNameAndIdJournal(category, journalName);
                categoryService.deleteCategory(ctgr);
                response.setStatus(HttpServletResponse.SC_CREATED);
            }else{
                response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
            }
        }catch (Exception e){
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/listCategory")
    public @ResponseBody
    String findAll(@RequestParam String journalName){
        return this.categoryService.findAllByIdJournal(journalName).toString();
    }
}
