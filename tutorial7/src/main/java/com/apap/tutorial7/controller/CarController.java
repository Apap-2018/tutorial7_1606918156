package com.apap.tutorial7.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.apap.tutorial7.model.CarModel;
import com.apap.tutorial7.service.CarService;

@RestController
@RequestMapping("/car")
public class CarController {
	@Autowired
	private CarService carService;
	
	
	
	@PostMapping
	private CarModel addCarSubmit(@RequestBody CarModel car) {
		return carService.addCar(car);
	}
	
//	@GetMapping(value = "/{carId}")
//	private CarModel viewCar (@PathVariable ("carId") long carId, Model model) {
//		return carService.getCarDetailById(carId).get();
//	}
	
	@DeleteMapping
	private String deleteDealer(@RequestParam("carId") long id, Model model) {
		CarModel car = carService.getCarDetailById(id).get();
		carService.deleteCar(car);
		return "delete";
	}
	
	@RequestMapping(value = "/{id}")
	private String updateCarSubmit(
			@PathVariable(value = "id") long id,
			@RequestParam("brand") String brand,
			@RequestParam("type") String type,
			@RequestParam("price") Long price,
			@RequestParam("amount") Integer amount) {
		CarModel car = (CarModel) carService.getCarDetailById(id).get();
		if(car.equals(null)) {
			return "Couldn't find car";
		}
		car.setBrand(brand);
		car.setType(type);
		car.setPrice(price);
		car.setAmount(amount);
		return "update";
	}
	
	@GetMapping()
	private List<CarModel> viewAllCar(Model model){
		return carService.allCar();
	
	}
	
	@GetMapping(value = "/{carId}")
	private CarModel viewCar(@PathVariable("carId") long carId, Model model) {
		return carService.getCarDetailById(carId).get();
	}
	
	
	
}
	
//	@Autowired 
//	private DealerService dealerService;
//	
//	@RequestMapping(value = "/car/add/{dealerId}", method = RequestMethod.GET)
//	private String add(@PathVariable(value = "dealerId") Long dealerId, Model model) {
//		DealerModel dealer  = dealerService.getDealerDetailById(dealerId).get();
//		ArrayList<CarModel> list = new ArrayList<CarModel>();
//		list.add(new CarModel());
//		dealer.setListCar(list);
//		
//		model.addAttribute("dealer", dealer);
//		return "addCar";
//	}
//	
//	@RequestMapping(value = "/car/add/", method = RequestMethod.POST)
//	private String addCarSubmit(@ModelAttribute CarModel car) {
//		carService.addCar(car);
//		return "add";
//	}
//	
//	@RequestMapping(value="/car/add/{dealerId}", method = RequestMethod.POST, params= {"addRow"})
//	public String addRow(@ModelAttribute DealerModel dealer, BindingResult bindingResult, Model model) {
//		if (dealer.getListCar() == null) {
//            dealer.setListCar(new ArrayList<CarModel>());
//        }
//		dealer.getListCar().add(new CarModel());
//		
//		model.addAttribute("dealer", dealer);
//		return "addCar";
//	}
//	
//	@RequestMapping(value="/car/add/{dealerId}", method = RequestMethod.POST, params={"removeRow"})
//	public String removeRow(@ModelAttribute DealerModel dealer, final BindingResult bindingResult, final HttpServletRequest req, Model model) {
//	    final Integer rowId = Integer.valueOf(req.getParameter("removeRow"));
//	    dealer.getListCar().remove(rowId.intValue());
//	    
//	    model.addAttribute("dealer", dealer);
//	    return "addCar";
//	}
//	
//	@RequestMapping(value = "/car/add/{dealerId}", method = RequestMethod.POST, params={"save"})
//	private String addCarSubmit(@ModelAttribute DealerModel dealer) {
//		DealerModel dealerNow  = dealerService.getDealerDetailById(dealer.getId()).get();
//		for(CarModel car: dealer.getListCar()) {
//			car.setDealer(dealerNow);
//			carService.addCar(car);
//		}
//		return "add";
//	}
//	
//	@RequestMapping(value = "/car/delete/{carId}", method = RequestMethod.GET)
//	private String deleteDealerSubmit(@PathVariable(value = "carId") Long id,Model model) {
//		DealerModel updated = dealerService.getDealerDetailById(id).orElse(null);
//		List<CarModel> cars = carService.allCarDealer(updated);
//		model.addAttribute("cars",cars);
//		model.addAttribute("title", "Delete Car");
//		return "deleteCar";
//	}
//	@RequestMapping(value = "/car/delete", method = RequestMethod.POST)
//	private String deleteCar(@ModelAttribute DealerModel dealer, Model model) {
//		for (CarModel car : dealer.getListCar()) {
//			carService.deleteCar(car);
//		}
//		return "delete";
//	}
//	
////	@RequestMapping(value = "/car/delete/id={carId}", method = RequestMethod.POST)
////	private String deleteCarSubmit(@PathVariable(value = "carId") Long id, @ModelAttribute CarModel car) {
////		CarModel deleted = carService.getCarDetailById(id).orElse(null);
////		carService.deleteCar(deleted);
////		return "update";
////	}
//
//	@RequestMapping(value = "/car/update/{dealerId}", method = RequestMethod.GET)
//	private String update(@PathVariable(value = "dealerId") Long id, Model model) {
//		DealerModel updated = dealerService.getDealerDetailById(id).orElse(null);
//		List<CarModel> cars = carService.allCarDealer(updated);
//		model.addAttribute("cars",cars);
//		model.addAttribute("title", "Update Car");
//		model.addAttribute("title", "Update Car");
//		return "updateCar";
//	}
//	
//	@RequestMapping(value = "/car/update/id={carId}", method = RequestMethod.POST)
//	private String updateDealerSubmit(@PathVariable(value = "carId") Long id, @ModelAttribute CarModel car) {
//		CarModel updated = carService.getCarDetailById(id).orElse(null);
//		updated.setBrand(car.getBrand());
//		updated.setType(car.getType());
//		updated.setAmount(car.getAmount());
//		updated.setPrice(car.getPrice());
//		carService.addCar(updated);
//		return "update";
//	}
//
//}
