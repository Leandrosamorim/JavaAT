package br.edu.infnet.appservices;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.edu.infnet.database.QuotationRepository;
import br.edu.infnet.models.Quotation;

public class QuotationAppService {
    private QuotationRepository _quotationRepository;

    public QuotationAppService(QuotationRepository quotationRepository) {
        _quotationRepository = quotationRepository;
    }

    public boolean Add(Quotation quotation) {
        try {
            _quotationRepository.add((quotation));
        } catch (SQLException e) {
            System.out.println("Exceção Gerada: " + e);
        }
        return true;
    }

    public boolean Edit(Quotation quotation) {
        try {
            _quotationRepository.edit(quotation);
        } catch (SQLException e) {
            System.out.println("Exceção Gerada: " + e);
        }
        return true;
    }

    public Quotation GetById(String id) {
        var quotation = new Quotation();
        try {
            quotation = _quotationRepository.getById(id);
        } catch (SQLException e) {
            System.out.println("Exceção Gerada: " + e);
        }
        return quotation;
    }

    public ArrayList<Quotation> GetByProduct(String productName) {
        ArrayList<Quotation> quotation = new ArrayList<>();
        try {
            quotation = _quotationRepository.getByProductName(productName);
        } catch (SQLException e) {
            System.out.println("Exceção Gerada: " + e);
        }
        return quotation;
    }

    public boolean Remove(String id) {
        try {
            _quotationRepository.removeById(id);
        } catch (SQLException e) {
            System.out.println("Exceção Gerada: " + e);
        }
        return true;
    }

    public List<Quotation> GetAll() {
        List<Quotation> list = new ArrayList<>();
        try {
            list = _quotationRepository.getAll();
        } catch (Exception e) {
            System.out.println("Exceção Gerada: " + e);
        }
        return list;
    }

    public void ExportAllAsCsv() {
        String pattern = "MM-dd-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(new Date().getTime());

        try (PrintWriter writer = new PrintWriter(new File("Quotations" + date + ".csv"))) {

            var list = this.GetAll();
            String header = "Exported All Quotations \n";
            String header2 = "Name - Amount - Total \n";
            StringBuilder sb = new StringBuilder();
            list.forEach(x -> sb.append(x.GetProduct().GetName()).append(" - ").append(x.GetAmount()).append(" - ")
                    .append(x.GetTotal()).append("\n"));

            writer.write(header);
            writer.write(header2);
            writer.write(sb.toString());

            System.out.println("done!");

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public void ExportById(String id) {
        String pattern = "MM-dd-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(new Date().getTime());

        try (PrintWriter writer = new PrintWriter(new File("Quotation" + date + ".csv"))) {

            var quotation = this.GetById(id);
            String header = "Exported Single Quotations \n";
            String header2 = "Name - Amount - Total \n";
            StringBuilder sb = new StringBuilder();
            sb.append(quotation.GetProduct().GetName()).append(" - ").append(quotation.GetAmount()).append(" - ")
                    .append(quotation.GetTotal()).append("\n");

            writer.write(header);
            writer.write(header2);
            writer.write(sb.toString());

            System.out.println("done!");

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

}
