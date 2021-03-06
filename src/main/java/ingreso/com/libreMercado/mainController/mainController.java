package ingreso.com.libreMercado.mainController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ingreso.com.libreMercado.model.DaoProducto;
import ingreso.com.libreMercado.model.Producto;

@Controller
public class mainController {

	@Autowired
	private DaoProducto daoProducto;


	//Home
	@RequestMapping(value = "/",
			method = RequestMethod.GET)
	public ModelAndView inicioGet(){

		ModelAndView modelAndView = new ModelAndView();

		modelAndView.setViewName("index");

		return modelAndView;
	}

	@RequestMapping(value= "agregarProducto",
			method = RequestMethod.GET)
	public ModelAndView agregarProductoGET(){

		ModelAndView modelAndView = new ModelAndView();

		modelAndView.addObject("Producto", new Producto());

		modelAndView.setViewName("agregarProducto");

		return modelAndView;

	}

	@RequestMapping(value= "agregarProducto",
			method = RequestMethod.POST)
	public ModelAndView agregarProductoPOST(@ModelAttribute Producto producto){

		ModelAndView modelAndView = new ModelAndView();

		producto.precioPorCantidad();
		daoProducto.save(producto);

		modelAndView.addObject("producto", new Producto());

		modelAndView.setViewName("redirect:/agregarProducto.html");

		return modelAndView;
	}

	@RequestMapping(value= "verProductos",
			method = RequestMethod.GET)
	public ModelAndView mostrarProductos(){

		ModelAndView modelAndView = new ModelAndView();

		modelAndView.addObject("listaDeProductos", daoProducto.findAll());

		modelAndView.setViewName("verProductos");

		return modelAndView;
	}

	@RequestMapping(value = "/eliminarProducto",
			method = RequestMethod.GET)
	public ModelAndView eliminarRecetaGET(@RequestParam("id") int id){
		//Producto producto  = daoProducto.findOne(id);

		daoProducto.delete(id);

		ModelAndView modelAndView = new ModelAndView();

		modelAndView.addObject("listaDeProductos", daoProducto.findAll());
		modelAndView.setViewName("verProductos");

		return modelAndView;

	}

	@RequestMapping(value = "/comprarProducto",
			method = RequestMethod.GET)
	public ModelAndView comprarProductoGET(@RequestParam("id") int id){
		//Producto producto  = daoProducto.findOne(id);

		ModelAndView modelAndView = new ModelAndView();

		Producto producto = daoProducto.findOne(id);

		producto.setCantidad(producto.getCantidad() - 1);
		daoProducto.save(producto);

		if(producto.getCantidad() <= 0) {
			daoProducto.delete(id);
		}

		modelAndView.addObject("listaDeProductos", daoProducto.findAll());
		modelAndView.setViewName("verProductos");

		return modelAndView;

	}




}
