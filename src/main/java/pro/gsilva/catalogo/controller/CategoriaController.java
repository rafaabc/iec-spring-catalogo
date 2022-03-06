package pro.gsilva.catalogo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pro.gsilva.catalogo.model.Categoria;
import pro.gsilva.catalogo.service.CategoriaService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class CategoriaController {
    @Autowired
    private CategoriaService categoriaService;

    @RequestMapping(value="/categorias", method= RequestMethod.GET)
//    @PreAuthorize("hasRole('ROLE_USER')")
    public ModelAndView getCategorias(@RequestParam("page") Optional<Integer> page,
                                      @RequestParam("size") Optional<Integer> size) {

        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);

        ModelAndView mv = new ModelAndView("categorias");
        Page<Categoria> categorias = categoriaService.findAll(PageRequest.of(currentPage - 1, pageSize));
        mv.addObject("categorias", categorias);

        int totalPages = categorias.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            mv.addObject("pageNumbers", pageNumbers);
        }

        return mv;
    }

    @RequestMapping(value = "/categoria-form", method = RequestMethod.GET)
    public ModelAndView getCategoriaForm() {
        ModelAndView mv = new ModelAndView("categoriaForm");
        mv.addObject("categoria", new Categoria());
        return mv;
    }

    @RequestMapping(value = "categoria-form/{id}", method = RequestMethod.GET)
    public ModelAndView getCategoriaEditForm(@PathVariable("id") Long id) {
        ModelAndView mv = new ModelAndView("categoriaForm");
        Categoria categoria = categoriaService.findById(id);
        mv.addObject("categoria", categoria);
        return mv;
    }

    @RequestMapping(value = "categorias/add", method = RequestMethod.POST)
    public ModelAndView postCategoria(@Valid @ModelAttribute("categoria") Categoria categoria,
                                      BindingResult result, Model model) {
        if (result.hasErrors()) {
            ModelAndView mv = new ModelAndView("categoriaForm");
            mv.addObject("mensagem", "Verifique os erros do formulário");
            return mv;
        }
        categoriaService.save(categoria);
        ModelAndView mv = new ModelAndView("redirect:/categorias");
        return mv;
    }
}
