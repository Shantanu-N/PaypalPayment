package com.capg.Payment.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import com.paypal.api.payments.PaymentExecution;


@Service
public class PaymentService {
	@Autowired
	private APIContext apiContext;
	
	public Payment createpayment(Double total,String Currency,String Method,String cancelURL,String successURL)throws PayPalRESTException {
		Amount amount=new Amount();
		amount.setCurrency(Currency);
		
		total=new BigDecimal(total).setScale(2,RoundingMode.HALF_UP).doubleValue();
		amount.setTotal(String.format("%.2f", total));
		
		Transaction transaction=new Transaction();
		transaction.setAmount(amount);
		
		List<Transaction>transactions=new ArrayList<>();
		transactions.add(transaction);
		
		Payer payer=new Payer();
		payer.setPaymentMethod(Method.toString());
		
		Payment payment=new Payment();
		payment.setPayer(payer);
		payment.setTransactions(transactions);
		RedirectUrls redirectUrls=new RedirectUrls();
		redirectUrls.setCancelUrl(cancelURL);
		redirectUrls.setReturnUrl(successURL);
		payment.setRedirectUrls(redirectUrls);
		
		return payment.create(apiContext);
	}
	
	public Payment executePayment(String paymentId,String payerId) throws PayPalRESTException{
		Payment payment=new Payment();
		payment.setId(paymentId);
		PaymentExecution paymentExecute=new PaymentExecution();
		paymentExecute.setPayerId(payerId);
		return payment.execute(apiContext, paymentExecute);
	}
}
