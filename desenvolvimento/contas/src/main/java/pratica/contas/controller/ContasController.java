package pratica.contas.controller;

import imp.financeiro.contas_a_receber.ContaReceberDao;
import lib.model.financeiro.contas.ContaReceber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.Model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ContasController {

    private ContaReceberDao dao;

    @Autowired
    public ContasController() {
        this.dao = new ContaReceberDao();
    }

    @RequestMapping(value = "/contas", method = RequestMethod.GET)
    public ModelAndView getContasByCnpj(@RequestParam(value = "documento") String documento, Model model) {
        List<ContaReceber> contasReceber = new ArrayList<>();
        try {
            contasReceber = dao.getByCnpj(documento);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (contasReceber != null) {
            model.addAttribute("contas", contasReceber);
        }
       // return contasReceber;
        return new ModelAndView("index.html");
       // return contasReceber;
    }

}