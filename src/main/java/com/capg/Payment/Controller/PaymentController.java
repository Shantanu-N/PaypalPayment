package com.capg.Payment.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.capg.Payment.Service.PaymentService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import com.capg.Payment.Model.PaymentOrder;


@Controller
@RequestMapping("/PaymentInfo")
public class PaymentController {
	
	@Autowired
	PaymentService service;
	
	public static final String SUCCESS_URL ="pay/success";
	public static final String CANCEL_URL = "pay/cancel";
	
	@GetMapping("/")
	public String home() {
		return "home";
	}
	@PostMapping("/pay")
	public String payment(@ModelAttribute("order") PaymentOrder order) {
		try {
			Payment payment=service.createpayment(order.getPrice(), order.getCurrency(), order.getMethod(), "http://localhost:8079/"+CANCEL_URL, "http://localhost:8079/"+SUCCESS_URL);
			for(Links link:payment.getLinks()) {
				if(link.getRel().equals("approval_url")) {
					return "redirect:"+link.getHref();
				}
			}
		} catch (PayPalRESTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "redirect:/";
	}
	@GetMapping(value=CANCEL_URL)
	public String CancelPay() {
		return "cancel";
	}
	@GetMapping(value=SUCCESS_URL)
	public String SuccessPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) {
		try {
            Payment payment = service.executePayment(paymentId, payerId);
            System.out.println(payment.toJSON());
            if (payment.getState().equals("approved")) {
                return "success";
            }
		}catch (PayPalRESTException e) {
   	     System.out.println(e.getMessage());
		}
		return "redirect:/";
	}
	
	
}
		
	
	
		
	
	