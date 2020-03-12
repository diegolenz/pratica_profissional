package com.contas.domain.service;

import imp.financeiro.contas_a_receber.ContaReceberDao;
import lib.model.financeiro.contas.ContaReceber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

@RestController
public class ContasResource {

    private ContaReceberDao dao;

    @Autowired
    public ContasResource() {
        this.dao = new ContaReceberDao();
    }

    @RequestMapping(value = "/contas", method = RequestMethod.GET)
    public List<ContaReceber> getContasByCnpj(@PathVariable("cnpj") String cnpj) {
        try {
            return dao.getByCnpj(cnpj);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}