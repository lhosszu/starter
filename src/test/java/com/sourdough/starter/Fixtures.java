package com.sourdough.starter;

import com.sourdough.starter.user.User;

public class Fixtures {

    public static User.UserBuilder johnDoe() {
        return User.enabled()
                   .name("john-doe")
                   .password("lipsum");
    }
}
