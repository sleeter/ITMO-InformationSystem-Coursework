package itmo.sleeter.infosys.service

import itmo.sleeter.infosys.model.Customer
import itmo.sleeter.infosys.repository.CustomerRepository
import org.springframework.stereotype.Service

@Service
class CustomerService(
    val customerRepository: CustomerRepository
) {
    fun getCustomer(id: Long): Customer {
        return customerRepository.findById(id).get()
    }
}