package com.fdmgroup.CreditCardProject.controller;

import com.fdmgroup.CreditCardProject.exception.InsufficientBalanceException;
import com.fdmgroup.CreditCardProject.exception.ItemNotFoundException;
import com.fdmgroup.CreditCardProject.model.AuthUser;
import com.fdmgroup.CreditCardProject.model.RewardItem;
import com.fdmgroup.CreditCardProject.model.RewardTransaction;
import com.fdmgroup.CreditCardProject.model.User;
import com.fdmgroup.CreditCardProject.service.RewardItemService;
import com.fdmgroup.CreditCardProject.service.RewardTransactionService;
import com.fdmgroup.CreditCardProject.service.UserService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller

public class RewardsController {

	@Autowired
	private RewardTransactionService rewardTransactionService;

	@Autowired
	private RewardItemService rewardItemService;

	@Autowired
	private UserService userService;

	@GetMapping("/rewards")
	public String goToRewards(@AuthenticationPrincipal AuthUser principal, Model model) {
		User currentUser = userService.getUserByUsername(principal.getUsername());
		List<RewardItem> rewardItems = rewardItemService.getAllItems();
		model.addAttribute("rewards", rewardItems);
		model.addAttribute("user", currentUser);
		return "rewards";
	}

	@PostMapping("/rewards/purchase")
	public String purchaseRewardItem(@AuthenticationPrincipal AuthUser principal, @RequestParam String itemId,
			RedirectAttributes redirectAttributes) {
		User currentUser = userService.getUserByUsername(principal.getUsername());
		try {
			RewardItem itemToPurchase = rewardItemService.getItemById(Long.parseLong(itemId));
			RewardTransaction transaction = rewardTransactionService.handleTransaction(currentUser, itemToPurchase);
			return "redirect:/rewards/receipt/" + transaction.getTransactionId();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ItemNotFoundException e) {
			// TODO Auto-generated catch block
			redirectAttributes.addFlashAttribute("popup", "itemNotFound");
			e.printStackTrace();
		} catch (InsufficientBalanceException e) {
			// TODO Auto-generated catch block
			redirectAttributes.addFlashAttribute("popup", "insufficientFunds");
			e.printStackTrace();
		}
		return "redirect:/rewards";
	}

}
