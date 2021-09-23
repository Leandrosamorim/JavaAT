package br.edu.infnet.appservices;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.edu.infnet.database.ProductRepository;
import br.edu.infnet.models.Product;

public class ProductAppService {
    private ProductRepository _productRepository;

    public ProductAppService(ProductRepository productRepository) {
        _productRepository = productRepository;
    }

    public boolean Add(Product product) {
        try {
            _productRepository.add((product));
        } catch (SQLException e) {
            System.out.println("Exceção Gerada: " + e);
        }
        return true;
    }

    public boolean Edit(Product product) {
        try {
            _productRepository.edit(product);
        } catch (SQLException e) {
            System.out.println("Exceção Gerada: " + e);
        }
        return true;
    }

    public Product GetById(int id) {
        var product = new Product();
        try {
            product = _productRepository.getById(id);
        } catch (SQLException e) {
            System.out.println("Exceção Gerada: " + e);
        }
        return product;
    }

    public ArrayList<Product> GetByName(String productName) {
        var products = new ArrayList<Product>();
        try {
            products = _productRepository.getByName(productName);
        } catch (SQLException e) {
            System.out.println("Exceção Gerada: " + e);
        }
        return products;
    }

    public boolean Remove(int id) {
        try {
            _productRepository.removeById(id);
        } catch (SQLException e) {
            System.out.println("Exceção Gerada: " + e);
        }
        return true;
    }

    public List<Product> GetAll(){
        List<Product> list = new ArrayList<>();
        try {
            list = _productRepository.getAll();
        } catch (Exception e) {
            System.out.println("Exceção Gerada: " + e);
        }
        return list;
    }

}
