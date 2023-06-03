package com.example.demo.controller;

import com.example.demo.entity.Book;
import com.example.demo.services.BookService;
import com.example.demo.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public String addBookForm(Model model){
        model.addAttribute("book", new Book());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "book/add";
    }
    @PostMapping("/add")
    public String addBook(@Valid @ModelAttribute("book") Book book, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()){
            model.addAttribute("categories", categoryService.getAllCategories());
            return "book/add";
        }
        bookService.addBook(book);
        return "redirect:/books";
    }
    @GetMapping("/edit/{id}")
    public String editBookForm(@PathVariable("id") Long id, Model model) {
        // Get the book by ID
        Book book = bookService.getBookById(id);

        // Add the book and categories to the model
        model.addAttribute("book", book);
        model.addAttribute("categories", categoryService.getAllCategories());

        return "book/edit";
    }

    // POST request to handle the form submission
    @PostMapping("/edit/{id}")
    public String editBook(@ModelAttribute("book") Book book) {
        Long id = book.getId();

        // Cập nhật thông tin sách
        bookService.updateBook(book);

        return "redirect:/books";
    }
    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable("id") Long id){
        bookService.deleteBook(id);
        return "redirect:/books";
    }
}
