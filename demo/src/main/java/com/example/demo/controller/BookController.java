package com.example.demo.controller;


import com.example.demo.entity.Book;
import com.example.demo.entity.Category;
import com.example.demo.services.BookService;
import com.example.demo.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BookController {
    @Autowired
    private BookService bookService;
    @Autowired
    private CategoryService categoryService;
    @GetMapping
    public String showAllBooks(Model model){
        List<Book>books = bookService.getAllBook();
        model.addAttribute("books", books);
        return "book/list";
    }
    @GetMapping("/add")
    public  String addBookForm(Model model){
        model.addAttribute("book",new Book());
        model.addAttribute("categories",categoryService.getAllCategories());
        return "book/add";
    }
    @PostMapping("/add")
    public String addBook(@ModelAttribute("book") Book book){
        bookService.addBook(book);
        return "redirect:/books";
    }
    @GetMapping("/edit/{id}")
    public String editBook(@PathVariable("id") Long id, Model model) {
        model.addAttribute("book", bookService.getBookById(id));
        model.addAttribute("categories",categoryService.getAllCategories());
        return "book/edit";
    }

    @PostMapping("/edit")
    public String updateBook(@ModelAttribute("book") Book book) {
        bookService.updateBook(book);
        return "redirect:/books";
    }
    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable("id") Long id) {
        bookService.deleteBook(id);
        return "redirect:/books";
    }
}
