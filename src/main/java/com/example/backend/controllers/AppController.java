package com.example.backend.controllers;

import com.example.backend.models.Account;
import com.example.backend.models.AccountRepository;
import com.example.backend.models.User;
import com.example.backend.models.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping(value="/api")
@CrossOrigin
public class AppController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    private int accCnt;


    @PostMapping("/createUser")
    public String createUser(@RequestBody User user) {

        if (userRepository.existsById(user.getUserName())) {
            return "FAILURE";
        }

        userRepository.save(user);
        return "SUCCESS";
    }

    @GetMapping("/login")
    public String loginUser(@RequestParam(value = "userName") String userName,
                            @RequestParam(value = "password") String password) {

        if (userRepository.existsById(userName)) {
            var user = userRepository.findById(userName);
            if (user.get().getPassword().equals(password)) {
                return "SUCCESS";
            } else {
                return "PASSWORD";
            }
        } else {
            return "USERNAME";
        }
    }

    @GetMapping("/getAccounts")
    public Account[] getAccounts(@RequestParam(value = "userName") String userName) {
        List<Account> accList = new ArrayList<>();
        Iterable<Account> accountIterable = accountRepository.findAll();
        Iterator<Account> accountIterator = accountIterable.iterator();

        while (accountIterator.hasNext()) {
            Account account = accountIterator.next();
            if (account.getUserName().equals(userName)) {
                accList.add(account);
            }
        }

        Account[] result = new Account[accList.size()];
        int i = 0;
        for (Account acc: accList)
            result[i++] = acc;

        return result;
    }

    @PostMapping("/addAccount")
    public String addAccount(@RequestBody Account account) {

        account.setId(accCnt++);
        accountRepository.save(account);
        return "SUCCESS";
    }
}
