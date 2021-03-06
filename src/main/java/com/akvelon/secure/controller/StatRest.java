package com.akvelon.secure.controller;

import com.akvelon.secure.service.dao.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;



@Controller
@Transactional
@Secured(value = {"ROLE_EMPLOYEE", "ROLE_ADMIN"})
@RequestMapping(value = "/stat")
public class StatRest {

    @Autowired
    StatisticsService statDao = new StatisticsService();

    @RequestMapping( value = "/getStat", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Object> getStat() throws NoSuchFieldException, IllegalAccessException {
        List<Object> stat = statDao.getStat();
        return stat;
    }

    @RequestMapping( value = "/getStatUnique", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Object> getStatUnique() throws NoSuchFieldException, IllegalAccessException {
        List<Object> stat = statDao.getStatUnique();
        return stat;
    }
}
