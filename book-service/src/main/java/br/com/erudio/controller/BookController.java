package br.com.erudio.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.erudio.model.Book;
import br.com.erudio.proxy.CambioProxy;
import br.com.erudio.repository.BookRepository;

@RestController
@RequestMapping("/book-service")
public class BookController {

	@Autowired
	private Environment environment;

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private CambioProxy proxy;

	@GetMapping(value = "/{id}/{currency}")
	public Book findBook(@PathVariable Long id, @PathVariable String currency) {
		Optional<Book> book = bookRepository.findById(id);

		if (!book.isPresent())
			throw new RuntimeException("Book not found.");

		var cambio = proxy.getCambio(book.get().getPrice(), "USD", currency);
		var port = environment.getProperty("local.server.port");
		book.get().setEnvironment("Book port: " + port + " Cambio port: " + cambio.getEnvironment());
		book.get().setConvertedValue(cambio.getConvertedValue());

		return book.get();
	}
}
