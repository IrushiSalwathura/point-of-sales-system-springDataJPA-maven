package lk.ijse.dep.business.custom.impl;

import lk.ijse.dep.business.custom.CustomerBO;
import lk.ijse.dep.entity.Customer;
import lk.ijse.dep.repository.CustomerRepository;
import lk.ijse.dep.util.CustomerTM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
@Transactional
public class CustomerBOImpl implements CustomerBO {
    @Autowired
    private CustomerRepository customerRepository;

    @Transactional(readOnly = true)
    public String getNewCustomerId() throws Exception {
        String lastCustomerId = customerRepository.getFirstLastCustomerIdByOrderByIdDesc().getId();
        if (lastCustomerId == null) {
            return "C001";
        } else {
            int maxId = Integer.parseInt(lastCustomerId.replace("C", ""));
            maxId = maxId + 1;
            String id = "";
            if (maxId < 10) {
                id = "C00" + maxId;
            } else if (maxId < 100) {
                id = "C0" + maxId;
            } else {
                id = "C" + maxId;
            }
            return id;
        }
    }

    @Transactional(readOnly = true)
    public List<CustomerTM> getAllCustomers() throws Exception {
        List<Customer> allCustomers = customerRepository.findAll();
        ArrayList<CustomerTM> customers = new ArrayList<>();
        for (Customer customer : allCustomers) {
            customers.add(new CustomerTM(customer.getId(), customer.getName(), customer.getAddress()));
        }
        return customers;

    }

    public void saveCustomer(String id, String name, String address) throws Exception {
        customerRepository.save(new Customer(id, name, address));


    }

    public void updateCustomer(String name, String address, String id) throws Exception {

        customerRepository.save(new Customer(id, name, address));

    }

    public void deleteCustomer(String customerId) throws Exception {

        customerRepository.deleteById(customerId);
    }
}
