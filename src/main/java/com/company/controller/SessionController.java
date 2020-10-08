package com.company.controller;

import com.company.model.user.User;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SessionController {

    Map<UUID, User> sessions = new HashMap<>();
}
