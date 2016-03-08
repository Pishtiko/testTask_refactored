package com.akvelon.secure.controller;

import com.akvelon.secure.entity.*;
import com.akvelon.secure.entity.enums.OrderStatus;
import com.akvelon.secure.experimental.temporary_entity.TestChild;
import com.akvelon.secure.experimental.temporary_entity.TestParent;
import com.akvelon.secure.service.*;
import com.akvelon.secure.service.dao.*;
import com.akvelon.secure.util.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.*;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@RestController
@Transactional
@RequestMapping(value = "/test")
public class TestMyRest {


    @Autowired
    private GenericDaoImpl<Product,Integer> prDao = new GenericDaoImpl<>(Product.class);

    @Autowired
    CustomerDao customerDao = new CustomerDao();

    @Autowired
    HttpServletRequest request ;

    @Autowired
    AdminDao adminDao = new AdminDao();

    @Autowired
    GenericDaoImpl<OrderProduct, Integer> opDao = new GenericDaoImpl(OrderProduct.class);

    @Autowired
    GenericDaoImpl<OrderEntity, Integer> ordDao = new GenericDaoImpl(OrderEntity.class);

    @Autowired
    StatisticsService statDao = new StatisticsService();

    @PersistenceContext
    EntityManager entityManager;


    @RequestMapping( value = "/testView", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<TotalSalaryView> testView() throws NoSuchFieldException, IllegalAccessException {
      final String query = "FROM TotalSalary";
        return entityManager.createQuery(query).getResultList();

    }

    @RequestMapping( value = "/getStat", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Object> getStat() throws NoSuchFieldException, IllegalAccessException {
        List<Object> stat = statDao.getStat();
//        Object s = stat.get(0);
//        Field[] allFields = s.getClass().getDeclaredFields();
//        for (Field each : allFields) {
//            Field field = s.getClass().getDeclaredField(each.getName());
//            System.out.println(each.getName());
//            System.out.println( field.getName());
//        }
        return stat;
    }

    @RequestMapping( value = "/testtest", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TestParent> testtest(){
        List<TestParent> result = new ArrayList<>();
        List<TestChild> children = new ArrayList<>();
        TestParent result2 = new TestParent();

        for(int i = 0; i<10;i++){
            TestChild child = new TestChild();
            child.setChildField("igbya #"+i);
            children.add(child);
            entityManager.persist(child);
            entityManager.flush();
        }
      /*
        result2.setChildId(children);

        for (TestChild ch :
                children) {
            result2

        }
*/
        return result;
    }

    @RequestMapping( value = "/testOrdersTable", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String testOrdersTable(){

        dfafadsf();

        return "Шя глянем";
    }

    private void dfafadsf() {
        for (int i = 0; i<10; i++)
        {
            Product product = new Product();
            product.setProductName("Картоха "+String.valueOf(i));
            product.setDescription("Вкусная");
            product.setPrice(10+i);
            entityManager.persist(product);
            entityManager.refresh(product);
        }
        entityManager.flush();
        List<Product> produkti = customerDao.getProductListByParam("Price");
        Set<Product> prod = produkti.stream().collect(Collectors.toSet());

        for (Product pr12:produkti ) {
            OrderProduct op12 = new OrderProduct();
            OrderEntity orderEntity = new OrderEntity();
            orderEntity.setUserId(Helper.getCurrentUser());
            orderEntity.setStatus(OrderStatus.UNCONFIRMED);
            entityManager.persist(orderEntity);
            entityManager.flush();
            op12.setIdd(orderEntity);
            op12.setProductId(pr12);
            op12.setCount(5);
            entityManager.persist(op12);
            entityManager.flush();
            entityManager.flush();
        }
        entityManager.flush();
        entityManager.close();
    }

    @RequestMapping( value = "/getCartT", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<OrderProduct> getCartT() {
        return customerDao.getMyCartContent();
    }

    @RequestMapping( value = "/addUser/{role}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String createUserTest(@PathVariable String role) {

        User user1 = new User("kakashkin", "7110eda4d09e062aa5e4a390b0a572ac0d2c0220");

        if (!adminDao.createUser(user1, role))
            return "Нормалёк";
        return "АйАйАй";
    }


    @RequestMapping( value = "/testMest}", method = RequestMethod.GET)
    @ResponseBody
    public String shnyagovit(@AuthenticationPrincipal UserServiceImpl customUser){
        System.out.println("первый попал");
        String name = Helper.getCurrentUsersName();
        return name;
    }

    @RequestMapping( value = "/testShmest", method = RequestMethod.GET)
    @ResponseBody
    public String shnyag(@AuthenticationPrincipal User customUser){
        return customUser.getLogin().toString();
    }

    @RequestMapping(value = "/poisk1", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Product> poisk1(){
        return prDao.searchFor("productName", "ка", Product.class);
    }

    @RequestMapping(value = "/testOe")
    @ResponseBody
    public List<OrderEntity> testOrderss(){
        return customerDao.getOrdersOrderedByParam("userName", "kakashkin");
    }

}
