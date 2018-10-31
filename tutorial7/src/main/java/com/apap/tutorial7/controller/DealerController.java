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

import com.apap.tutorial7.model.DealerModel;
import com.apap.tutorial7.rest.DealerDetail;
import com.apap.tutorial7.rest.Setting;
import com.apap.tutorial7.service.DealerService;

@RestController
@RequestMapping("/dealer")
public class DealerController {
	@Autowired
	private DealerService dealerService;
	
	@Autowired
	RestTemplate restTemplate;
	
	@Bean
	public RestTemplate rest() {
		return new RestTemplate();
	}
	
	@PostMapping(value = "/add")
	private DealerModel addDealerSubmit(@RequestBody DealerModel dealer) {
		return dealerService.addDealer(dealer);
	}
	
	@GetMapping(value = "/{dealerId}")
	private DealerModel viewDealer (@PathVariable ("dealerId") long dealerId, Model model) {
		return dealerService.getDealerDetailById(dealerId).get();
	}
	
	@DeleteMapping(value = "/delete")
	private String deleteDealer(@RequestParam("dealerId") long id, Model model) {
		DealerModel dealer = dealerService.getDealerDetailById(id).get();
		dealerService.deleteDealer(dealer);
		return "delete";
	}
	
	@RequestMapping(value = "/{id}")
	private String updateDealerSubmit(
			@PathVariable(value = "id") long id,
			@RequestParam("alamat") String alamat,
			@RequestParam("telp") String telp) {
		DealerModel dealer = (DealerModel) dealerService.getDealerDetailById(id).get();
		if(dealer.equals(null)) {
			return "Couldn't find your dealer";
		}
		dealer.setAlamat(alamat);
		dealer.setNoTelp(telp);
		dealerService.addDealer(dealer);
		return "update";
	}
	
	@GetMapping()
	private List<DealerModel> viewAllDealer(Model model){
		return dealerService.allDealer();
	}
	
	@GetMapping(value = "/status/{dealerId}")
	private String getStatus (@PathVariable ("dealerId") long dealerId) throws Exception{
		String path = Setting.dealerUrl + "/dealer?id=" + dealerId;
		return restTemplate.getForEntity(path, String.class).getBody();
	}
	
	@GetMapping(value = "/full/{dealerId}")
	private DealerDetail postStatus (@PathVariable ("dealerId") long dealerId) throws Exception{
		String path = Setting.dealerUrl + "/dealer";
		DealerModel dealer = dealerService.getDealerDetailById(dealerId).get();
		DealerDetail detail = restTemplate.postForObject(path, dealer, DealerDetail.class);
		return detail;
	}
}
	
//	@Autowired
//	private CarService carService;
//
//	@RequestMapping("/")
//	private String home(Model model) {
//		model.addAttribute("title", "Home");
//		return "home";
//	}
//	
//	@RequestMapping(value = "/dealer/add", method = RequestMethod.GET)
//	private String add(Model model){
//		model.addAttribute("dealer", new DealerModel());
//		model.addAttribute("title", "Add Dealer");
//		return "addDealer";
//	}
//	
//	@RequestMapping(value = "/dealer/add", method = RequestMethod.POST)
//	private String addDealerSubmit(@ModelAttribute DealerModel dealer) {
//		dealerService.addDealer(dealer);
//		return "add";
//	}
//	
//	@RequestMapping(value = "/dealer/view", method = RequestMethod.GET)
//	public String viewDealer(@RequestParam("dealerId") Long id, Model model) {
//		DealerModel archive = dealerService.getDealerDetailById(id).orElse(null);
//		if(archive==null) {
//			model.addAttribute("error", true);
//			model.addAttribute("dealer", null);
//			return "view-dealer";
//		}
//			
//		model.addAttribute("dealer", archive);
//		Collections.sort(archive.getListCar());
//		model.addAttribute("carList", archive.getListCar());
//		model.addAttribute("title", "View Dealer");
//		
//		return "view-dealer";
//		
//	}
//
//	
//	@RequestMapping(value = "/dealer/all", method = RequestMethod.GET)
//	private String getAllDealer(Model model) {
//				
//		List<DealerModel> dealers = dealerService.allDealer();
//		
//		Map<String,String> dealerCars = new HashMap<>();
//		
//		for(int i = 0 ;i < dealers.size();i++) {
//			if(!dealerCars.containsKey(dealers.get(i).getAlamat()+ " " + dealers.get(i).getNoTelp())){
//				List<CarModel> cars = carService.allCarDealer(dealers.get(i));
//				List<String> carsSpec = cars.stream().map(car -> car.toString()).collect(Collectors.toList()); 
//				dealerCars.put(dealers.get(i).getAlamat()+ " " + dealers.get(i).getNoTelp(),carsSpec.toString());
//			}
//		}
//		
//		model.addAttribute("dealerCarMap", dealerCars);
//		model.addAttribute("title", "View All");
//		
//		return "view-all";
//	}
//	
//	@RequestMapping(value = "/dealer/delete/{dealerId}", method = RequestMethod.GET)
//	private String deleteDealerSubmit(@PathVariable(value = "dealerId") String id, Model model) {
//		DealerModel deleted = dealerService.getDealerDetailById(Long.parseLong(id)).orElse(null);
//		dealerService.deleteDealer(deleted);
//		model.addAttribute("title", "Dealer Deleted");
//		return "delete";
//	}
//	
//	@RequestMapping(value = "/dealer/update/{dealerId}", method = RequestMethod.GET)
//	private String update(@PathVariable(value = "dealerId") String id, Model model) {
//		DealerModel updated = dealerService.getDealerDetailById(Long.parseLong(id)).orElse(null);
//		model.addAttribute("update", updated);
//		model.addAttribute("title", "Update Dealer");
//		return "updateDealer";
//	}
//	
//	@RequestMapping(value = "/dealer/update/id={dealerId}", method = RequestMethod.POST)
//	private String updateDealerSubmit(@PathVariable(value = "dealerId") String id, @ModelAttribute DealerModel dealer) {
//		DealerModel updated = dealerService.getDealerDetailById(Long.parseLong(id)).orElse(null);
//		updated.setAlamat(dealer.getAlamat());
//		updated.setNoTelp(dealer.getNoTelp());
//		dealerService.addDealer(updated);
//		return "update";
//	}
	

