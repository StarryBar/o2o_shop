package o2o.web.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/frontend")
public class FrontendController {
	
	@RequestMapping(value="/productdetail",method=RequestMethod.GET)
	private String showProductDetail() {
		return "frontend/productdetail";
	}
	
	@RequestMapping(value="/shopdetail",method=RequestMethod.GET)
	private String showShopDetail() {
		return "frontend/shopdetail";
	}
	
	@RequestMapping(value="/shoplist",method=RequestMethod.GET)
	private String showSHopList() {
		return "frontend/shoplist";
	}
	@RequestMapping(value="/index",method=RequestMethod.GET)
	private String index() {
		return "frontend/index";
	}
}
