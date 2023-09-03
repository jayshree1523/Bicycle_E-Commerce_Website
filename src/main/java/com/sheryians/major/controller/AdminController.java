package com.sheryians.major.controller;

import com.sheryians.major.dto.ProductDTO;
import com.sheryians.major.model.Product;
import com.sheryians.major.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.sheryians.major.model.Category;
import com.sheryians.major.service.CategoryService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	CategoryService categoryService;
	@GetMapping("/")
	public String adminHome() {
		return "adminHome";
	}
	
	@GetMapping("/categories")
	public String getCat(Model model) {
		model.addAttribute("categories", categoryService.getAllCategory());
		return "categories";
		
	}
	
	//request type is get here
	@GetMapping("/categories/add")
	public String getCatAdd(Model model) {
		//now we need to send a object
		model.addAttribute("category", new Category());
		return "categoriesAdd";
	}
	
	//request type is post here
	@PostMapping("/categories/add")
	public String postCatAdd(@ModelAttribute("category") Category category) {
		categoryService.addCategory(category);
		return "redirect:/admin/categories";
	}



	//for delete
	@GetMapping("/categories/delete/{id}")
	public String deleteCat(@PathVariable int id) //fetching  id for deleting
	{
		//create a method in CategoryService removeCategoryById

         categoryService.removeCategoryById(id);
		 return "redirect:/admin/categories";
	}



	//for update
//WE ALSO NEED ID and send object
	@GetMapping("/categories/update/{id}")
	public  String updateCat(@PathVariable int id,Model model)
	{
		Optional<Category> category=categoryService.getCategoryById( id);
		//does id exist in optional
		 if(category.isPresent())
		 {
			 model.addAttribute("category",category.get());
			 //get is used to get data from optional
			 return "categoriesAdd";
		 }
		 else
		 {
			  return "404" ;
		 }

	}
//////////Product Section ------------------>
	public static String uploadDir= System.getProperty("user.dir")+"/src/main/resources/static/productImages";
	//effiective path for image to store in folder .   user.dir gives root folder main

@Autowired
	ProductService productService;

	@GetMapping("/products")
	public String products(Model model)
	{
		model.addAttribute("products",productService.getAllProduct());// 2  parameter-> token name and  value is List

		return "products";
	}

//for productAdd page
@GetMapping("/products/add")
public String productAddGet(Model model)
{
	model.addAttribute("productDTO",new ProductDTO());// 2  parameter-> token name and  value is List
 model.addAttribute("categories",categoryService.getAllCategory() );
	return "productsAdd";
}



//When user clicks on submit button in Add a new Product
@PostMapping("/products/add")
	public String productAddPost( @ModelAttribute("productDTO")ProductDTO productDTO,
	@RequestParam("productImage") MultipartFile file,
	@RequestParam ("imgName") String imgName) throws IOException {
		Product product = new Product();
		product.setId(productDTO.getId());
		product.setName(productDTO.getName());
		product.setCategory(categoryService.getCategoryById(productDTO.getCategoryId()).get());
		product.setPrice(productDTO.getPrice());
		product.setWeight(productDTO.getWeight());
		product.setDescription(productDTO.getDescription());
		String imageUUID;
		//check is user giving image
	if(!file.isEmpty()) {
		imageUUID = file.getOriginalFilename();
		Path fileNameAndPath = Paths.get(uploadDir, imageUUID);//stores path and name of img
		Files.write(fileNameAndPath, file.getBytes());
	}
	else
	{
		imageUUID = imgName;
	}

	product.setImageName (imageUUID);
	productService.addProduct(product);


		return "redirect:/admin/products";
	}



	//delete
	@GetMapping("/product/delete/{id}")
	public String deleteProduct(@PathVariable long id) //fetching  id for deleting
	{
		//create a method in CategoryService removeCategoryById

		productService.removeProductsById(id);
		return "redirect:/admin/products";
	}

//update

	@GetMapping("/product/update/{id}")
	public String updateProductGet(@PathVariable long id , Model model) // will send in model to update
	{
		//product is coming and need to make it  productdto , just opposite of productAddPost
     Product product = productService.getProductsById(id).get(); // fecth product from db
     ProductDTO productDTO= new ProductDTO(); // will send this product dto object
		productDTO.setId (product.getId());
		productDTO.setName (product.getName());
		productDTO.setCategoryId((product.getCategory ().getId()));
		productDTO.setPrice (product.getPrice());
		productDTO.setWeight ((product.getWeight()));
		productDTO.setDescription (product.getDescription());
		productDTO.setImageName (product.getImageName());
		model.addAttribute("categories", categoryService.getAllCategory());//sending obj1
		model.addAttribute( "productDTO", productDTO); // sending 2 obj
		return "productsAdd";




	}
	//--------------------------------------ADMIN SECTION OVER--------------------------------



}
