package net.blog.springbootrestapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import net.blog.springbootrestapi.entity.Category;
import net.blog.springbootrestapi.payload.CategoryDto;
import net.blog.springbootrestapi.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    //build add category rest api
    @Operation(
            summary = "Create Category REST API",
            description = "Create Category REST API is used to save Category into database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http Status 201 created"

    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryDto> addCategory(@RequestBody CategoryDto categoryDto){
        CategoryDto savedCategoryDto = categoryService.addCategory(categoryDto);
        return new ResponseEntity<>(savedCategoryDto, HttpStatus.CREATED);
    }
    //get category rest api
    @Operation(
            summary = "Get Category By Id REST API",
            description = "Get Category By Id REST API used to fetch a particular category from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 success"

    )
    @GetMapping("{id}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable("id") Long categoryId){
        CategoryDto category = categoryService.getCategory(categoryId);
        return  ResponseEntity.ok(category);
    }
    //get all categories
    @Operation(
            summary = "Get Category By Id REST API",
            description = "Get Category By Id REST API used to fetch all  category from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 success"

    )

    @GetMapping()
    public ResponseEntity<List<CategoryDto>> getAllCategories(){
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    //update category
    @Operation(
            summary = "Update category REST API",
            description = "Update Category REST API used to update a particular category from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 success"

    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("{id}")
    public  ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto,@PathVariable("id") Long categoryId){

        return ResponseEntity.ok(categoryService.updateCategory(categoryDto,categoryId));

    }
    //delete category
    @Operation(
            summary = "Delete Category By Id REST API",
            description = "Delete Category By Id REST API used to delete a particular category from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 success"

    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable("id") Long categoryId){
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.ok("Category deleted successfully!");
    }
}
