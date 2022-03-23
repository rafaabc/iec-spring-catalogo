package pro.gsilva.catalogo.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import pro.gsilva.catalogo.dto.MusicaDTO;
import pro.gsilva.catalogo.model.Categoria;
import pro.gsilva.catalogo.model.Musica;
import pro.gsilva.catalogo.service.CatalogoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pro.gsilva.catalogo.service.CategoriaService;

@Controller
public class CatalogoController {
    
    @Autowired 
    private CatalogoService catalogoService;
    @Autowired
    private CategoriaService categoriaService;

    @RequestMapping(value="/musicas", method=RequestMethod.GET)
    public ModelAndView getMusicas() {
        ModelAndView mv = new ModelAndView("musicas");
        List<Musica> musicas = catalogoService.findAll();
        mv.addObject("musicas", musicas);
        mv.addObject("categorias", categoriaService.findAll());
        return mv;
    }

    @RequestMapping(value="/musicas/{id}", method=RequestMethod.GET)
    public ModelAndView getMusicaDetalhes(@PathVariable("id") long id) {
        ModelAndView mv = new ModelAndView("musicaDetalhes");
        Musica musica = catalogoService.findById(id);
        mv.addObject("musica", musica);
        return mv;
    }

    @RequestMapping(value = "/musicas/edit/{id}", method = RequestMethod.GET)
    public ModelAndView getFormEdit(@PathVariable("id") long id) {
        ModelAndView mv = new ModelAndView("musicaForm");
        Musica musica = catalogoService.findById(id);
        MusicaDTO musicaDTO = new MusicaDTO();
        musicaDTO.setId(musica.getId());
        musicaDTO.setAutor(musica.getAutor());
        musicaDTO.setTitulo(musica.getTitulo());
        musicaDTO.setData(musica.getData());
        musicaDTO.setLetra(musica.getLetra());
        musicaDTO.setCategoriaId(musica.getCategoria().getId());
        mv.addObject("categorias", categoriaService.findAll());
        mv.addObject("musica", musicaDTO);
        return mv;
    }

    @RequestMapping(value="/addMusica", method=RequestMethod.GET)
    public ModelAndView getMusicaForm(MusicaDTO musica) {
        ModelAndView mv = new ModelAndView("musicaForm");
        mv.addObject("categorias", categoriaService.findAll());
        mv.addObject("musica", new MusicaDTO());
        return mv;
    }
    
    @RequestMapping(value="/addMusica", method=RequestMethod.POST)
    public ModelAndView salvarMusica(@Valid @ModelAttribute("musica") MusicaDTO musicaDTO,
           BindingResult result, Model model) {
        if (result.hasErrors()) {
            ModelAndView musicaForm = new ModelAndView("musicaForm");
            musicaForm.addObject("mensagem", "Verifique os errors do formulário");
            return musicaForm;
        }

        Musica musica = new Musica();
        musica.setData(LocalDate.now());
        musica.setId(musicaDTO.getId());
        musica.setAutor(musicaDTO.getAutor());
        musica.setTitulo(musicaDTO.getTitulo());
        musica.setLetra(musicaDTO.getLetra());

        Optional<Categoria> categoria = categoriaService.findById(musicaDTO.getCategoriaId());
        musica.setCategoria(categoria.get());
        catalogoService.save(musica);
        return new ModelAndView("redirect:/musicas");
    }

    @GetMapping("/musicas/pesquisar")
    public ModelAndView pesquisar(@RequestParam(value = "titulo", required = false) String titulo,
                                  @RequestParam(value = "categoriaId", required = false) Long categoriaId) {
        ModelAndView mv = new ModelAndView("musicas");
        List<Musica> musicas = new ArrayList();

        if (titulo != null && !titulo.equals("")) {
            musicas = catalogoService.findByTitulo(titulo);
        } else if (categoriaId != null) {
            musicas = catalogoService.findByCategoriaId(categoriaId);
        } else {
            musicas = catalogoService.findAll();
        }

        mv.addObject("musicas", musicas);
        mv.addObject("categorias", categoriaService.findAll());
        return mv;
    }
    
    @RequestMapping(value="/delMusica/{id}", method=RequestMethod.GET)
    public String delMusica(@PathVariable("id") long id) {
        catalogoService.excluir(id);
        return "redirect:/musicas";
    }
        

}
