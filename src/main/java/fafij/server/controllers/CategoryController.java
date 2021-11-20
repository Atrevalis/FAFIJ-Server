package fafij.server.controllers;
import fafij.server.Repository.CategoryService;
import fafij.server.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping("/createCategory")
    public void createCategory(@RequestBody Category request, HttpServletResponse response){
        try{
            categoryService.createCategory(request);
            List<Category> categoryList = categoryService.findAllByName(request.getName());
            if(request.getName().equals(categoryList.get(0).getName())) {
                response.setStatus(HttpServletResponse.SC_CREATED);
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
            }
        }catch (Exception e){
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/deleteCategory")
    public void deleteCategory(@RequestBody Category request, HttpServletResponse response){
        try{
            categoryService.deleteCategory(request);
            response.setStatus(HttpServletResponse.SC_CREATED);
        }catch (Exception e){
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/findCategory")
    public void findCategory(@RequestBody Category request, HttpServletResponse response){
        try {
            categoryService.findAllByName(request.getName());
            response.setStatus(HttpServletResponse.SC_CREATED);
        }catch (Exception e){
            response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
        }
    }

    @PostMapping("/listCategory")
    public void listCategory(HttpServletResponse response){
        try {
            categoryService.findAll();
            response.setStatus(HttpServletResponse.SC_CREATED);
        }catch (Exception e){
            response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
        }
    }
}
