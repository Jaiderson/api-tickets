package com.goldenrace.tickets;

import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@RunWith(JUnitPlatform.class)
@SelectPackages(value = {"com.goldenrace.tickets.services","com.goldenrace.tickets.controllers"})
class TicketsApplicationTests {

}
