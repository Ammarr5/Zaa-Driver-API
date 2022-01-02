package com.web.ZAA;

import com.web.ZAA.Core.Account;

public interface Authentication {
    public Account login(String username,String password);
    public void register();
}
