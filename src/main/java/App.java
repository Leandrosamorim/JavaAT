import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.edu.infnet.UI.MenuJFrame;
import br.edu.infnet.appservices.ProductAppService;
import br.edu.infnet.appservices.QuotationAppService;
import br.edu.infnet.database.ProductRepository;
import br.edu.infnet.database.QuotationRepository;
import br.edu.infnet.models.Product;
import br.edu.infnet.models.Quotation;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author 55229
 */
class App {
    public static void main(String[] args){
        try{
            ProductRepository productRepository = ProductRepository.getInstance();
            QuotationRepository quotationRepository = QuotationRepository.getInstance();
            ProductAppService productAppService = new ProductAppService(productRepository);
            QuotationAppService quotationAppService = new QuotationAppService(quotationRepository);
            System.out.println("Conectado");
            productRepository.init();
            quotationRepository.init();

            var testProduct1 = new Product("Lápis", 100, 1.44);
            var testProduct2 = new Product("Caneta", 50, 2.50);

            quotationAppService.Add(new Quotation(testProduct1, 30 ));
            var lapis = quotationAppService.GetByProduct("Lápis");
            System.out.println(testProduct2.GetContent());

            //quotationAppService.ExportAllAsCsv();

        }catch(SQLException e){
            System.out.println(e);
        }
    }
    
}
